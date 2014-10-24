package com.apocalypsedefense.core;

import java.io.Serializable;

/**
 */
public class Weapon implements Serializable {
    /**
     */
    public String name;

    /**
     */
    public String wType;

    /**
     */
    public int damage;

    /**
     */
    public int rate;

    /**
     */
    public int range;

    /**
     * @param rate 
     * @param wType 
     * @param attackRange 
     * @param name 
     * @param damage 
     */
    public Weapon(String wType, String name, int damage, int rate, int attackRange) {
    	this.name = name;
    	this.wType = wType;
    	this.damage = damage;
    	this.rate = rate;
    	this.range = attackRange;
    }

    /**
     * @return 
     */
    public int attack() {
        return this.damage;
    }
}


/*
 
Python code:

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
*/
