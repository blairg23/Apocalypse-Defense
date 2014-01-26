package com.apocalypsedefense.core;

public class Person extends MapElement {
	public int hp;
	public boolean isAlive;
	public Weapon weapon;
	private int previousHealth;

	public Person(Weapon weapon, int hp, int size, Point position) {
		super(size,position);
		this.hp = hp;
		this.isAlive = true;
		this.weapon = weapon;
		previousHealth = hp;
	}
	
	
	public void takeDamage(int damage) {
		this.hp = this.hp - damage;
		if (this.hp <= 0){ //If the actor died
			this.isAlive = false;
		}
	}
	
	public int attack() {
	    return 0;
	}
	
	public void getStats(){
		System.out.print("HP:" + this.hp);
		System.out.print("Position:" + this.position);
	}

	private static final int ATTACK_COUNTER_START = 5; // How many frames to indicate being attacked
	private int wasAttackedCounter=0;
	public boolean isBeingAttacked() {
		if (wasAttackedCounter > 0) {
			wasAttackedCounter--;
			return true;
		}
		if (hp < previousHealth) {
			wasAttackedCounter = ATTACK_COUNTER_START;
			previousHealth = hp;
			return true;
		}
		return false;
	}

}

/*
Python code:
 
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
        print 'HP: ' + str(self.hp)
        print 'Position: ' + str(self.position)

*/