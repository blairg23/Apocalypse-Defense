'''
Author: Blair Gemmer
Purpose: Creates an interface to all the functions of the game, including starting a new game, resuming a saved game, saving a game, and updating the game state.
'''
#TODO: REMOVE ALL PRINT STATEMENTS FOR ANDROID IMPLEMENTATION

from pylab import *
from actors import *
from items import *
from random import *

class Point():
    def __init__(self, x, y):
        self.x = x
        self.y = y
        self.coords = (self.x,self.y)

    def getCoords(self):
        self.coords = (self.x, self.y)
        return self.coords

    #Check for point equivalence:
    def equals(self, point):
        if self.x == point.x and self.y == point.y:
            return True
        else:
            return False

    #Get distance between this point and a different point:
    def getDistance(self, point):
        x1,x2=self.x, point.x
        y1,y2=self.y, point.y
        return sqrt( (x2-x1)**2 + (y2-y1)**2 ) #Distance formula

class Movement():
    def __init__(self, gameMap):
    #Set up initial parameters:
        self.height = gameMap.height
        self.width = gameMap.width
        self.map = gameMap
        self.blockedSpaces = gameMap.mapElements #List of blocked spaces
        self.possibleChanges = [Point(-1, 1), Point(0,1), Point(1, 1),
                                Point(-1, 0), Point(0,0), Point(1,0),
                                Point(-1,-1), Point(0,-1), Point(1,-1)]

    #Randomly walk in one of 9 squares (middle being stand still):
    def randomWalk(self, currentPosition):
        newPosition = []
        newPosition = [sum(pair) for pair in zip(currentPosition.getCoords(),
                                                 choice(self.possibleChanges).getCoords())]
        newPosition = Point(newPosition[0], newPosition[1]) #Convert to a point
        return newPosition
                       
                       

    #Walk toward the target position:
    def directedWalk(self,currentPosition, targetPosition):
        #Walk to the position that provides the minimum distance:
        minDist = 99999
        minPos = currentPosition
        for position in self.possibleChanges:
            newPosition = Point(0,0)
            newPosition.x = currentPosition.x + position.x
            newPosition.y = currentPosition.y + position.y
            dist = newPosition.getDistance(targetPosition) #New distance
            if  dist < minDist: #if the new distance if less than minimum distance
                minDist = dist #this is the new minimum distance
                minPos = newPosition #this is the new minimum position

        '''
        newPosition = Point(0,0)
        x1, x2 = currentPosition.x, targetPosition.x
        y1, y2 = currentPosition.y, targetPosition.y    

        #Calculate target direction (x-direction):
        if x2 - x1 < 0:
            #Move along X-axis in the negative direction (left):
            newPosition.x = -1 
        elif x2 - x1 == 0:
            newPosition.x = 0 #Don't move along X-axis
        elif x2 - x1 > 0:
            #Move along X-axis in the positive direction (right):
            newPosition.x = 1 
        else:
            print 'ERROR with X!'

        #Check y-direction:
        if y2 - y1 < 0:
            newPosition.y = -1 #Move down Y-axis
        elif y2 - y1 == 0:
            newPosition.y = 0 #Don't move along Y-axis
        elif y2 - y1 > 0:
            newPosition.y = 1 #Move up Y-axis
        else:
            print 'ERROR with Y!'

        #Finally, return our new position:
        return newPosition.getCoords()  
        '''
        return minPos
    
    #Gets new position.... walkTypes: Random or Directed. 
    #If directed, specify target position
    def getNewPosition(self,oldPosition, walkType, targetPosition=None):
        position = oldPosition #old x,y

        if walkType == 'Random':
            newPosition = self.randomWalk(position)
            
        elif walkType == 'Directed':
            #newPosition = [sum(pair) for pair in zip(position.getCoords(), 
            #                                         self.directedWalk(position,targetPosition))]
            newPosition = self.directedWalk(position,targetPosition)
        elif walkType == None: #Default
            pass
        else:
            print 'ERROR in getNewPosition!' #Debugging msg
        #If we didn't walk off screen or hit another map element:
        if self.width > int(newPosition.x) > 0 and self.height > int(newPosition.y) > 0 and not self.map.isBlocked(newPosition):
            return newPosition
        else: #If we did walk off screen or hit another map element:
            return self.getNewPosition(oldPosition, walkType, targetPosition) #Find a new place to go

    #Iterate N # of steps and return an array to plot (for testing purposes)
    def getNpositions(self,originPos, N, walkType, targetPosition=None ):
        originalPosition = originPos
        positionPlot = []
        #Get as many positions as we specified:
        for i in range(0,N):
            newPosition = self.getNewPosition(originalPosition, walkType, targetPosition)
            positionPlot.append(newPosition.getCoords())
            originalPosition = newPosition
        return positionPlot

    #Run a test case with whatever criteria you specify:
    def runTest(self, numZombies, numSurvivors, walkType): 
        plot(self.width/2, self.height/2, 'b*')#Test plot
        targetPosition = Point(self.width/2, self.height/2)
        for i in range(0, numZombies):
            x = randint(0,self.width-1)
            y = randint(0,self.height-1)
            originalPosition = Point(x,y)
            #How many steps the zombie gets to take:
            posPlot = self.getNpositions(originalPosition, 1000, walkType, targetPosition)
            posPlot = array(posPlot)
            print posPlot
            plot(posPlot[:,0], posPlot[:,1], 'r-')
            posPlot = []
        for i in range(0, numSurvivors):
            x = randint(0, self.width-1)
            y = randint(0, self.height-1)
            originalPosition = (x, y)
            plot(originalPosition[0], originalPosition[1], 'b*')
        show()

        
class Map():
    def __init__(self, mapElements=[], height=100, width=100):
        self.mapElements = mapElements
        self.height = height
        self.width = width

    def isBlocked(self,position):
        if position in self.mapElements:
            return True
        else:
            return False

    def update(self,mapElements):
        self.mapElements = mapElements


class Shop():
    def __init(self, goldAvailable = 0):
        self.goldAvailable = goldAvailable

    def buyTower(self):
        pass

    def upgradeTower(self):
        pass


class Game():
    def __init__(self, height, width, 
                 gameMap='new',survivors=[],zombies=[],
                 gold=0,gameState={}, wave=1, zombieStart=Point(0,0)):
        self.height = height
        self.width = width
        self.map = gameMap
        self.survivors = survivors
        self.zombies = zombies
        self.gold = gold
        self.gameState = gameState
        self.wave = wave
        self.isRunning = True #Set this to false to stop gameplay
        self.isPaused = False #Set to true to pause the game
        self.actorFactory = ActorFactory() #To create new characters
        self.zombieStart = zombieStart #The position all zombies start from
        self.zombieCount = 0 #Number of zombies killed
 
    def startNew(self):
        #Create a list of map elements to create a map with:
        self.mapElements = []
        self.sPositions = []
        self.zPositions = []

        for s in self.survivors:
            self.sPositions.append(s.position.getCoords())
            self.mapElements.append(s.position.getCoords())
        for z in self.zombies:
            self.zPositions.append(z.position.getCoords())
            self.mapElements.append(z.position.getCoords())

        #Create new NxM map with survivors' and zombies' positions:
        self.map = Map(self.mapElements, self.height, self.width)

        #Save the new game state:
        self.gameState['map'] = self.map
        self.gameState['survivors'] = self.survivors
        self.gameState['zombies'] = self.zombies
        self.gameState['killCounter'] = self.zombieCount        
        self.gameState['gold'] = self.gold
        self.gameState['wave'] = self.wave


    def pause(self):
        self.isPaused = True

    def resumeFromQuit(self, gameState):
        self.gameState = gameState

    def resumeFromPause(self):
        self.isPaused = False

    def save(self):
        return gameState

    def update(self): #TODO: Add survivor attractant functionality
        m = Movement(self.map)
        
        #For each zombie in the list of zombies:
        for z in self.zombies:
            #If the zombie has a target and target is still alive,
            if z.target != None and z.target in self.survivors:
                #If the zombie isn't attacking
                if z.state == 'Walking':
                    #Move toward target:
                    z.position = m.getNewPosition(z.position, 'Directed', 
                                                  z.target.position)
                    #Check if the zombie is within attacking distance:
                    if z.position.getDistance(z.target.position) == 1:
                    #If so, switch zombie to attack mode
                        z.state = 'Attacking'
                #Else, if the zombie is attacking,
                elif z.state == 'Attacking':
                    #Attack target:
                    z.target.takeDamage(z.attack())
                    #If the target is killed,
                    if z.target.isAlive == False:
                        #Remove the survivor:
                        print str(z.target.name) + ' DIED! Position:[' + str(z.target.position.getCoords()) + ']'
                        print 'Killer: ' + str(z.name) + ' Position:[' + str(z.position.getCoords()) + ']'
                        self.survivors.remove(z.target)
                        #Set zombie's target to nothing:
                        z.target = None
                        #Set zombie's state to 'walking':
                        z.state = 'Walking'
                        #If the game is over:
                        if len(self.survivors) == 0:
                            print 'GAME OVER! YOU LOST!'
                            self.isRunning = False
            #Else, if the zombie doesn't have a target or the target died and there are still targets,
            elif z.target == None or z.target not in self.survivors and len(self.survivors) != 0:
                z.target = None
                z.state = 'Walking'
                #Randomly walk:
                z.position = m.getNewPosition(z.position, 'Random')

                #Find the closest survivor:                
                closestTarget = min( [z.position.getDistance(s.position) 
                                      for s in self.survivors ] )
                for s in self.survivors:
                    distance = z.position.getDistance(s.position)
                    #If the survivor is within sight range:
                    if distance == closestTarget:# and distance <= z.sightRange: #Removed this part to test
                        #Set that survivor to the zombie's target:
                        z.target = s

        #For each survivor in the list of survivors:
        for s in self.survivors:
            #If the survivor has a target and the target is still alive,
            if s.target != None and s.target in self.zombies:
                #Shoot the target:
                s.target.takeDamage(s.attack())
                #If the target is killed,
                if s.target.isAlive == False:
                    #Remove the zombie:
                    print str(s.target.name) + ' killed!'
                    self.zombies.remove(s.target) 
                    self.zombieCount += 1 #Add kill to zombie kill list
                    print 'Zombie Kill Counter: ' + str(self.zombieCount)
                    self.gold += s.target.worth #Add gold from kill
                    print 'Gold: ' + str(self.gold)
                    #Set survivor's target to nothing:
                    s.target == None
                    #If we killed all the zombies:
                    if len(self.zombies) == 0:
                        #Increase the zombie wave:
                        self.wave += 1
                        #If we haven't won yet:
                        if self.wave != 100:
                            #Random starting point:
                            x = randint(0,self.width-1)
                            y = randint(0,self.height-1)
                            self.zombieStart = Point(x,y)    
                            self.zombies = self.actorFactory.create('Zombie', position=self.zombieStart,
                                                                    numActors=self.wave, wave=self.wave)
                        #If we won:
                        elif self.wave == 100:
                            print 'GAME OVER! YOU WON!'
                            self.isRunning = False
            #Else, if the survivor doesn't have a target or the target died and there are still zombies:
            elif s.target == None or s.target not in self.zombies and len(self.zombies) != 0:
                #Set the target to nothing to skip checking next time:
                s.target = None
                #Find the closest zombie:
                closestTarget = min([s.position.getDistance(z.position) 
                                     for z in self.zombies])
                for z in self.zombies:
                    distance = s.position.getDistance(z.position)
                    #If the zombie is within gun sight range:
                    if distance == closestTarget and distance <= z.weapon.range:
                        #Set that zombie to the survivor's target:
                        s.target = z
                        #If the survivor targets the zombie, the zombie will target the survivor:
                        z.target = s

        #Update map elements:
        self.mapElements = []
        self.sPositions = []
        self.zPositions = []
        for s in self.survivors:
            self.sPositions.append(s.position.getCoords())
            self.mapElements.append(s.position.getCoords())
        for z in self.zombies:
            self.zPositions.append(z.position.getCoords())
            self.mapElements.append(z.position.getCoords())

        #Update map with map elements:
        self.map.update(self.mapElements)

        #Update gameState:
        self.gameState['map'] = self.map
        self.gameState['survivors'] = self.survivors
        self.gameState['zombies'] = self.zombies
        self.gameState['killCounter'] = self.zombieCount
        self.gameState['gold'] = self.gold
        self.gameState['wave'] = self.wave

        #Update map:
        
        #TODO: Need to add File I/O for Android here to save game state on device
