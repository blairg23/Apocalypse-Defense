package com.apocalypsedefense.core;

import java.io.Serializable;

/**
 */
public class Armor implements Serializable {
    /**
     */
    public int hp = 100;

    /**
     */
    public Armor(int hp) {
    	this.hp = hp;
    }

    /**
     * @param damage 
     */
    public void takeDamage(int damage) {
    	this.hp -= damage;
    }
}

