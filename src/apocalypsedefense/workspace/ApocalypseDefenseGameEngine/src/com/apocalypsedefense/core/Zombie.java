package com.apocalypsedefense.core;
/**
*/
public class Zombie extends Person {
	/**
	*/
	public String name = "Zombie Bob";
	/**
	*/
	public int level = 1;
	/**
	*/
	public int sightRange = 100000;
	/**
	*/
	public int speed = 1;
	/**
	*/
	public Survivor target = null;
	/**
	*/
	public String state = "Walking";
	/**
	*/
	public int worth = 5;
	

	/**
	 * @param position 
	 * @param hp 
	 * @param level 
	 * @param speed 
	 * @param name 
	 * @param sightRange 
	 * @param weapon 
	 * @param state 
	 * @param target 
	 * @param worth 
	 * @param size 
	*/
	public Zombie(Point position) {
		super(new Weapon("Melee", "Bite/Scratch", 10, 1, 1), 100, 1, position);
	}
	
	/**
	 * @param position 
	 * @param hp 
	 * @param weapon 
	 * @param size 
	*/
	public int attack() {
		System.out.print(this.name + " Level " + this.level + " attacked " + this.target.name + " for " + this.weapon.attack() + " points of damage!\n");
	    return this.weapon.attack();
	}
	
	public void getStats() {
		System.out.print("\n");
		System.out.print(this.name);
		System.out.print("HP: " + this.hp);
		System.out.print("Level: " + this.level);
		System.out.print("Attack Damage: " + this.weapon.attack());
		System.out.print("Sight Range: " + this.sightRange);
		System.out.print("Weapon Name: " + this.weapon.name);
		System.out.print("Weapon Type: " + this.weapon.wType);
		System.out.print("Speed: " + this.speed);
	}

}


/*

Python code:
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

*/