package com.apocalypsedefense.core;

import java.io.Serializable;

/**
*/
public class Survivor extends Person{
	public String name = "Bill";
	public Armor armor = new Armor(100);
	public Zombie target = null; // Will probably cause problems with serialization

	public Survivor(Point position) {
		super(new Gun("Pistol", "XD-45", 10, 1, 10, 50, 1),100,1,position);
	}
	
	public int attack() {
	    Shared.Log.d("Survivor", this.name + " attacked " + this.target.name + " for " + (this.weapon.damage * 1/this.weapon.rate) + " points of damage!\n");
	    return weapon.attack();
	}

	public void getStats() {
	    System.out.println();
	    System.out.print(this.name);
	    System.out.print("HP: " + this.hp);
	    System.out.print("Armor: " + this.armor.hp);
	    System.out.print("Weapon Name: " + this.weapon.name);
	    System.out.print("Weapon Type: " + this.weapon.wType);
	    System.out.print("Weapon Damage: " + this.weapon.damage);
	    System.out.println("Weapon Fire Rate: " + this.weapon.rate);
	}

}


/*

Python code:

class Survivor(Person):
    def __init__(self, weapon=Gun(), hp=100, size=(2,2), 
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


*/

