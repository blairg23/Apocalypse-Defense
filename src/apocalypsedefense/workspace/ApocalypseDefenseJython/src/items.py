'''
Author: Blair Gemmer
Purpose: Creates items in the game, specifically weapons
'''

#from pylab import *

#TODO: REMOVE ALL PRINT STATEMENTS FOR ANDROID IMPLEMENTATION
#TODO: Remove useless comments.

class Weapon():
    def __init__(self, wType='Default Weapon Type', name='Default Weapon', 
                 damage=10, rate=1, attackRange=10):
        self.name = name
        self.wType = wType
        self.damage = damage
        self.rate = rate
        self.range = attackRange

    def attack(self):
        return self.damage

class Gun(Weapon):
    def __init__(self, wType='Default Gun Type', name='Default Gun', damage=10, 
                 rate=1, attackRange=300, clipSize=50, reloadRate=1):
        Weapon.__init__(self, wType, name, damage, rate, attackRange)
        self.clipSize = clipSize
        self.reloadRate = reloadRate
        self.currentAmmo = clipSize

    def shoot(self):
        ammoExpelled = 1/self.rate
        self.currentAmmo -= ammoExpelled
        if self.currentAmmo < 0: #Can't go below 0 bullets
            self.currentAmmo = 0
            self.reloadGun() #Reload the gun
        return self.damage*ammoExpelled #Every shot hits

    def reloadGun(self):
        #NEED TO ADD RELOAD RATE
        print 'RELOADING!'
        self.currentAmmo = self.clipSize

class Armor():
    def __init__(self, armorHP=100):
        self.hp = armorHP

    def takeDamage(self, damage):
        self.hp = self.hp - damage
    
###Testing purposes:
##g = Gun(rate=1/5.)
##for t in range(0,100):
##    print 'Current Ammo: ' + str(g.currentAmmo)
##    g.shoot()
##    if g.currentAmmo == 0:
##        print 'RELOADING!'
##        g.reload()
##    
