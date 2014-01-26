#-------------------------------------------------------------------------------
# Name:        module1
# Purpose:
#
# Author:      Pat
#
# Created:     06/03/2012
# Copyright:   (c) Pat 2012
# Licence:     <your licence>
#-------------------------------------------------------------------------------
#!/usr/bin/env python

## New game
game = Game()
assert game.cash == 0
assert game.zombiesKilled == 0
assert game.wave == 0
assert game.map == None

# pick a map
game.map = userSelectedMap
m = game.map
assert m.survivors == None
assert m.zombies == None

# select difficulty
game.difficulty = userSelectedDifficulty # e.g. a string name which is a key to a global tables of Difficulty objects - maybe the user can customize their own difficulty in the game and save as a new name

# start game
game.start() # same method used to resume after pause or exit

## gameplay
# on timer event:
zombies = game.map.zombies
survivors = game.map.survivors
for zIndex,zombie in enumerate(zombies):
    if game.paused:
        zombie.updatePosition(timeStep) # UI reads position to draw zombie
        continue
    if zombie.hp <= 0:
        zombie.die()
        del zombies[zIndex] # not sure if we can/should delete while iterating - might screw up the rest of the enumeration (maybe skip a value)
        continue
    if zombie.selected:
        pass # might need to use this for information overlays
    zombie.attackIfPossible(survivors) # takes into account rate of fire, nearness to survivors

for sIndex,survivor in enumerate(survivors):
    # Do about the same as for zombies