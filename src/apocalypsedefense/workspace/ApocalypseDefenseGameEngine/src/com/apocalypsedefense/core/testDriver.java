package com.apocalypsedefense.core;

import java.util.*;

public class testDriver {

	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Logging
		Shared.Log = new PrintLogAdapter();
		//TODO Change all soutprintlns to calls to Shared.Log.d("core", )
		
		//Game variables:
		Boolean GameRunning = true;
		int height = 10;
		int width = 10;
		

		
		//Creates a game with window size height by width:
		Game game = new Game(height, width);
		//Starts a new game:
		game.startNew();
		
		
		//Create survivor to start game:
		int x = width/2;
		int y = height/2;
		try {
			game.survivorManager.buySurvivorAt(x, y);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		if (game.survivorManager.canBuySurvivorAt(x, y)){
//			try {
//				game.survivorManager.buySurvivorAt(x, y);
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}	
//		}
		
		//Create a single zombie to start game (wave 1):
		Random r = new Random();
		x = r.nextInt(width);
		y = r.nextInt(height);
		game.zombieManager.createZombieAt(x, y);		
		
		//While we're in our game loop:
		while (GameRunning){
            switch (game.getGameState()) {
                case PAUSED:
                    System.out.println("Game is paused.");
                    break;
                case LOST:
                    System.out.println("game state is LOST");
                    GameRunning = false;
                    break;
                case WON:
                    System.out.println("game state is WON");
                    GameRunning = false;
                    break;
                case RUNNING:
                    game.update();
                    break;
                case NOT_STARTED:
                    System.out.println("game hasn't started");
                    break;
                default:
                    System.out.println("game state was unknown");
            }
		}
	}
}
        
        
/*

Python code:
from GameEngine import *

#Game variables:
GameRunning = True
height = 500
width = 500

#Create survivors:
w = Gun()
w.range = width

s1 = Survivor(w)
x = 50
y = 100
p = Point(x,y)
s1.position = p
s1.name = 'Fred'

s2 = Survivor(w)
x = 100
y = 50
p = Point(x,y)
s2.position = p
s2.name = 'Bob'


s3 = Survivor(w)
x = 150
y = 50
p = Point(x,y)
s3.position = p
s3.name = 'Frank'

s4 = Survivor(w)
x = 50
y = 150
p = Point(x,y)
s4.position = p
s4.name = 'Petey'

s5 = Survivor(w)
x = 150
y = 150
p = Point(x,y)
s5.position = p
s5.name = 'Jill'

survivors = [s1, s2, s3, s4, s5]

#Create zombies:
z1 = Zombie()
x = randint(0,width-1)
y = randint(0,height-1)
p = Point(x,y)
z1.position = p
z1.sightRange = width/2

z2 = Zombie()
x = randint(0,width-1)
y = randint(0,height-1)
p = Point(x,y)
z2.position = p
z2.sightRange = width/2

zombies = [z1, z2]

#Creates a game with window size height by width:
g = Game(height, width, survivors=survivors, zombies=zombies)

#Starts a new game:
g.startNew()

#While we're in our game loop:
while GameRunning:
#for x in range(0,500):
    if not g.isPaused and g.isRunning: #If the game isn't paused
        g.update() #Update the game objects
        #Append the plot coords:
#        survivorPlot.append([position for position in g.sPositions])
#        zombiePlot.append([position for position in g.zPositions])
    elif g.isRunning == False:
        GameRunning = False
        if not g.isRunning: #If the game stops
            GameRunning = False #Stop the game loop
    elif g.isPaused:
        print 'Game is paused.'
        
*/