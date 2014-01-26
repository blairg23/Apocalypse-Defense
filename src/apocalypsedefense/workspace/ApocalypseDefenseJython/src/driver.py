'''
Author: Blair Gemmer
Purpose: Runs the main game loop, creates characters, upgrades characters
'''
#TODO: Remove useless comments.

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