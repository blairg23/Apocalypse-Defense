'''
Author: Blair Gemmer
Purpose: Creates map elements, which interacts within the confines of a map. This can drive any kind of elements, but focuses on the Person class which can be a Zombie class or a Survivor class. Zombie classes can walk around, survivor classes carry various weapons and have armor. All classes have the ability to attack and be attacked.
Implement the ActorFactory class to create different types of characters!
'''
from pylab import *
from items import * #Weapon(), Gun()

class MapElement():
    def __init__(self, size=(1,1), position=(0,0)):
        self.position = position
        self.size = size
        self.isSelected = False

    def getStats(self):
        print 'Position: ' + str(position)
        print 'Size: ' + str(size)
        print 'Selected: ' + str(isSelected)
    
 
class Person(MapElement):
    def __init__(self, weapon, hp=100, size=(1,1), position=(0,0)):
        MapElement.__init__(self, size, position)
        self.hp = hp
        self.isAlive = True
        self.weapon = weapon

    def takeDamage(self, damage):
        self.hp = self.hp - damage
        if self.hp <= 0: #If the actor died
            self.isAlive = False

    def attack(self):
        pass

    def getStats(self):
        print 'HP: ' + str(hp)
        print 'Position: ' + str(position)

class Zombie(Person):
    def __init__(self, weapon=Weapon('Melee', 'Bite/Scratch'), 
                 hp=100, size=(1,1), position=(0,0), 
                 level=1, sightRange=10, speed=1, target=None,
                 name='*Basic Zombie*', state='Walking', worth=5):
        Person.__init__(self,weapon,hp,size,position)
        self.name =  name
        self.level = level
        self.sightRange = sightRange
        self.weapon = weapon
        self.speed = speed
        self.target = target #None or a survivor are the two target types
        self.state = state #walking and attacking are the two states
        self.worth = worth #How much gold the kill is worth (default 10 gold)
    

    def attack(self):
        print self.name + ' Level ' + str(self.level) + ' attacked ' + self.target.name + ' for ' + str(self.weapon.attack()) + ' points of damage!\n'
        return self.weapon.attack()

    def getStats(self):
        print '\n'
        print self.name
        print 'HP: ' + str(self.hp)
        print 'Level: ' + str(self.level)
        print 'Attack Damage: ' + str(self.weapon.damage)
        print 'Sight Range: ' + str(self.sightRange)
        print 'Weapon Name: ' + str(self.weapon.name)
        print 'Weapon Type: ' + str(self.weapon.wType)
        print 'Speed: ' + str(self.speed)

class Survivor(Person):
    def __init__(self, weapon, hp=100, size=(2,2), 
                 position=(0,0), armor=10, name='*Basic Survivor*', 
                 state='Standing',target=None):
        Person.__init__(self,weapon,hp,size, position)
        self.name = name
        self.weapon = weapon
        self.armor = armor
        self.target = target
    
    def attack(self):
        print self.name + ' attacked ' + self.target.name + ' for ' + str(self.weapon.damage * 1/self.weapon.rate) + ' points of damage!\n'
        return self.weapon.shoot()

    def getStats(self):
        print '\n'
        print self.name
        print 'HP: ' + str(self.hp)
        print 'Armor: ' + str(self.armor)
        print 'Weapon Name: ' + str(self.weapon.name)
        print 'Weapon Type: ' + str(self.weapon.wType)
        print 'Weapon Damage: ' + str(self.weapon.damage)
        print 'Weapon Fire Rate: ' + str(self.weapon.rate)
        print 'Clip Size: ' + str(self.weapon.clipSize)


class ActorFactory():
    def __init__(self, wave=1, numActors=1):
        self.wave = wave
        self.numActors = numActors
        
    def create(self, actorType, position=(0,0),numActors=1, wave=1):
        if actorType == 'Zombie':
            zombies = []
            for i in range(0,wave):
#                print 'wave: ' + str(wave)
#                print i
                z = Zombie(level=wave, position=position)
                zombies.append(z)
#            print len(zombies), zombies
            return zombies
        elif actorType == 'Survivor':
            survivors = []
            for i in range(0,self.numActors):
                s = Survivor(position=position)
                survivors.append(s)
            return survivors
        else:
            print 'Not a real type'
