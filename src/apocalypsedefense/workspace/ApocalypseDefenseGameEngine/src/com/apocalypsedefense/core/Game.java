package com.apocalypsedefense.core;

//TODO: Convert arrays to arraylists
//TODO: Rewrite broken methods
//TODO: Complete survivor manager and zombie manager , copy/paste code
import java.util.*;

/**
*/
public class Game {
	/**
	*/
	public int height;
	/**
	*/
	public int width;
	/**
	*/
	public Map map;
	/**
	*/
	public ArrayList<Survivor> survivors = new ArrayList<Survivor>();
	/**
	*/
	public ArrayList<Zombie> zombies = new ArrayList<Zombie>();
	/**
	*/
	public int gold = 10;
	/**
	*/
	public GameData gameData;
	/**
	*/
	public int wave;
    /**
	*/
	/**
	*/
	/**
	*/
	public Point zombieStart = new Point(0, 0);
	/**
	*/
	public int zombieKillCount = 0;
	/**
	*/
	public int towerKillCount = 0;

	public ArrayList<Point> blockedPositions = new ArrayList<Point>();

	public SurvivorManager survivorManager;
	
	public ZombieManager zombieManager;
    private GameState gameState;

    public Game(int height, int width) {
		this.height = height;
		this.width = width;
        System.out.print("Width:" + this.width + "Height:" + this.height);
        // Create a list of map elements to create a map with:
        populateBlockedPositions();

        // Create new NxM map with survivors' and zombies' positions:
        this.map = new Map(this.blockedPositions, this.height, this.width);

        survivorManager = new SurvivorManager(this);

        zombieManager = new ZombieManager(this);

        // Save the new game data:
        this.gameData = new GameData();
        this.gameData.map = this.map;
        this.gameData.survivors = this.survivors;
        this.gameData.zombies = this.zombies;
        this.gameData.zombieKillCount = this.zombieKillCount;
        this.gameData.gold = this.gold;
        this.gameData.wave = this.wave;
        this.gameData.towerDeadCount = this.towerKillCount;

        gameState = GameState.NOT_STARTED;
    }

	/**
	*/
	public void startNew() {
        gameState = GameState.RUNNING;
    }

	/**
	 * 
	 */
	private void populateBlockedPositions() {
		this.blockedPositions.clear();

		for (int s = 0; s < this.survivors.size(); s++) {
			this.blockedPositions.add(this.survivors.get(s).position);

		}

		for (int z = 0; z < this.zombies.size(); z++) {
			this.blockedPositions.add(this.zombies.get(z).position);
		}
	}

	/**
	*/
	public void pause() {
        gameState = GameState.PAUSED;
    }

	/**
	*/
	public void update() {
		
		//updates survivor and zombie logic (targeting, battle, etc.)
		survivorManager.update();
		zombieManager.update();
		
		// If the game is over:
		if (this.survivors.size() == 0) {
			System.out.print("GAME OVER! YOU LOST!");
			System.out.println(this.gameData.toString()); // Prints
															// game
															// stats
            gameState = GameState.LOST;
		}
		
		// If we killed all the zombies:
		if (this.zombies.size() == 0) {
			// Increase the wave:
			this.wave++;
			// If we haven't won yet:
			if (this.wave < 100) {
				// Random start point for zombies: TODO: Change this
				// to whatever map requires
				Random r = new Random();
				for (int z = 0; z<this.wave;z++){
					int x = r.nextInt(this.width - 1);
					int y = r.nextInt(this.height - 1);
					this.zombieStart = new Point(x, y);

					// Create new zombies for this wave:
					zombieManager.createZombieAt(x, y);					
				}
			}
			// However, if we won:
			else {
				System.out.print("GAME OVER! YOU WON!");
				System.out.print(this.gameData.toString()); // Prints
																// game
																// stats
                gameState = GameState.WON;
            }
		}
		// Update map elements:
		populateBlockedPositions();

		// Update map with map elements:
		this.map.update(this.blockedPositions);

		// Save the new game state:
		// (The following are values, so they need to be reassigned; the reference/object fields always stay updated)
		this.gameData.zombieKillCount = this.zombieKillCount;
		this.gameData.gold = this.gold;
		this.gameData.wave = this.wave;
		this.gameData.towerDeadCount = this.towerKillCount;
	}

    public GameState getGameState() {
        return gameState;
    }
}

/*
 * 
 * Python code: class Game():#Keeps track of all game state variables def
 * __init__(self, height, width, gameMap='new',survivors=[],zombies=[],
 * gold=0,gameData={}, wave=1, zombieStart=Point(0,0)): self.height = height
 * self.width = width self.map = gameMap self.survivors = survivors self.zombies
 * = zombies self.gold = gold self.gameData = gameData self.wave = wave
 * self.isRunning = True #Set this to false to stop gameplay self.isPaused =
 * False #Set to true to pause the game self.actorFactory = ActorFactory() #To
 * create new zombies each wave self.shop = Shop() #Shop for survivor towers
 * (buy/upgrade options available) self.zombieStart = zombieStart #The position
 * all zombies start from self.zombieCount = 0 #Number of zombies killed
 * 
 * def startNew(self): #Create a list of map elements to create a map with:
 * self.mapElements = [] self.sPositions = [] self.zPositions = []
 * 
 * for s in self.survivors: self.sPositions.append(s.position.getCoords())
 * self.mapElements.append(s.position.getCoords()) for z in self.zombies:
 * self.zPositions.append(z.position.getCoords())
 * self.mapElements.append(z.position.getCoords())
 * 
 * #Create new NxM map with survivors' and zombies' positions: self.map =
 * Map(self.mapElements, self.height, self.width)
 * 
 * #Save the new game state: self.gameData['map'] = self.map
 * self.gameData['survivors'] = self.survivors self.gameData['zombies'] =
 * self.zombies self.gameData['killCounter'] = self.zombieCount
 * self.gameData['gold'] = self.gold self.gameData['wave'] = self.wave
 * 
 * 
 * def pause(self): self.isPaused = True
 * 
 * def resumeFromQuit(self, gameData): self.gameData = gameData
 * 
 * def resumeFromPause(self): self.isPaused = False
 * 
 * def save(self): return self.gameData
 * 
 * def update(self): #TODO: Add survivor attractant functionality m =
 * Movement(self.map)
 * 
 * #For each zombie in the list of zombies: for z in self.zombies: #If the
 * zombie has a target and target is still alive, if z.target != None and
 * z.target in self.survivors: #If the zombie isn't attacking if z.state ==
 * 'Walking': #Move toward target: z.position = m.getNewPosition(z.position,
 * 'Directed', z.target.position) #Check if the zombie is within attacking
 * distance: if z.position.getDistance(z.target.position) == 1: #If so, switch
 * zombie to attack mode z.state = 'Attacking' #Else, if the zombie is
 * attacking, elif z.state == 'Attacking': #Attack target:
 * z.target.takeDamage(z.attack()) #If the target is killed, if z.target.isAlive
 * == False: #Remove the survivor: print str(z.target.name) + ' DIED!
 * Position:[' + str(z.target.position.getCoords()) + ']' print 'Killer: ' +
 * str(z.name) + ' Position:[' + str(z.position.getCoords()) + ']'
 * self.survivors.remove(z.target) #TODO: Remove this when implementing in
 * Android #To keep game going to 100 rounds: numSurvivors = 1 self.shop =
 * Shop(numTowers=numSurvivors, goldAvailable=self.gold) survivors =
 * self.shop.buyTower() for sv in survivors: x = randint(0,self.width-1) y =
 * randint(0,self.height-1) sv.position = Point(x,y) self.survivors.append(sv)
 * #Set zombie's target to nothing: z.target = None #Set zombie's state to
 * 'walking': z.state = 'Walking' #If the game is over: if len(self.survivors)
 * == 0: print 'GAME OVER! YOU LOST!' for k,v in self.gameData.iteritems():
 * print k,v self.isRunning = False #Else, if the zombie doesn't have a target
 * or the target died and there are still targets, elif z.target == None or
 * z.target not in self.survivors and len(self.survivors) != 0: z.target = None
 * z.state = 'Walking' #Randomly walk: z.position = m.getNewPosition(z.position,
 * 'Random')
 * 
 * #Find the closest survivor: closestTarget = min(
 * [z.position.getDistance(s.position) for s in self.survivors ] ) for s in
 * self.survivors: distance = z.position.getDistance(s.position) #If the
 * survivor is within sight range: if distance == closestTarget:# and distance
 * <= z.sightRange: #Removed this part to test #Set that survivor to the
 * zombie's target: z.target = s
 * 
 * #For each survivor in the list of survivors: for s in self.survivors: #If the
 * survivor has a target and the target is still alive, if s.target != None and
 * s.target in self.zombies: #Shoot the target: s.target.takeDamage(s.attack())
 * #If the target is killed, if s.target.isAlive == False: #Remove the zombie:
 * print str(s.target.name) + ' killed!' self.zombies.remove(s.target)
 * self.zombieCount += 1 #Add kill to zombie kill list print 'Zombie Kill
 * Counter: ' + str(self.zombieCount) self.gold += s.target.worth #Add gold from
 * kill print 'Gold: ' + str(self.gold) #Set survivor's target to nothing:
 * s.target = None #If we killed all the zombies: if len(self.zombies) == 0:
 * #Increase the zombie wave: self.wave += 1 #If we haven't won yet: if
 * self.wave != 100: #Random starting point: #TODO: Change this to whatever map
 * requires x = randint(0,self.width-1) y = randint(0,self.height-1)
 * self.zombieStart = Point(x,y)
 * 
 * #Create new zombies for this wave: self.actorFactory.wave = self.wave
 * self.actorFactory.position = self.zombieStart self.actorFactory.numActors =
 * self.wave self.zombies = self.actorFactory.create()
 * 
 * 
 * #If we won: elif self.wave == 100: print 'GAME OVER! YOU WON!' for k,v in
 * self.gameData.iteritems(): print k,v self.isRunning = False #Else, if the
 * survivor doesn't have a target or the target died and there are still
 * zombies: elif s.target == None or s.target not in self.zombies and
 * len(self.zombies) != 0: #Set the target to nothing to skip checking next
 * time: s.target = None #Find the closest zombie: closestTarget =
 * min([s.position.getDistance(z.position) for z in self.zombies]) for z in
 * self.zombies: distance = s.position.getDistance(z.position) #If the zombie is
 * within gun sight range: if distance == closestTarget and distance <=
 * z.weapon.range: #Set that zombie to the survivor's target: s.target = z #If
 * the survivor targets the zombie, the zombie will target the survivor:
 * z.target = s
 * 
 * #Update map elements: self.mapElements = [] self.sPositions = []
 * self.zPositions = [] for s in self.survivors:
 * self.sPositions.append(s.position.getCoords())
 * self.mapElements.append(s.position.getCoords()) for z in self.zombies:
 * self.zPositions.append(z.position.getCoords())
 * self.mapElements.append(z.position.getCoords())
 * 
 * #Update map with map elements: self.map.update(self.mapElements)
 * 
 * #Update gameData: self.gameData['map'] = self.map
 * self.gameData['survivors'] = self.survivors self.gameData['zombies'] =
 * self.zombies self.gameData['killCounter'] = self.zombieCount
 * self.gameData['gold'] = self.gold self.gameData['wave'] = self.wave
 * 
 * #Update map:
 * 
 * #TODO: Need to add File I/O for Android here to save game state on device
 */
