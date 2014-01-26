package com.apocalypsedefense.core;
/**
*/
public class Gun extends Weapon {
	/**
	*/
	public int clipSize;
	/**
	*/
	public int reloadRate;
	/**
	*/
	public int currentAmmo;
	
	/**
	 * @param reloadRate 
	 * @param rate 
	 * @param wType 
	 * @param attackRange 
	 * @param name 
	 * @param damage 
	 * @param clipSize 
	*/
	public Gun(String wType, String name, int damage, int rate, int attackRange, int clipSize, int reloadRate) {
		super(wType,name,damage,rate,attackRange);
		this.clipSize = clipSize;
		this.reloadRate = reloadRate;
		this.currentAmmo = clipSize;
	}
	
	/**
	 * @return int
	*/
	@Override
	public int attack() {
		int ammoExpelled = 1/this.rate;
		this.currentAmmo -= ammoExpelled;
		if (this.currentAmmo < 0){ //Can't go below 0 bullets
			this.currentAmmo = 0;
			this.reloadGun(); //Reload gun
		}
		return this.damage*ammoExpelled;
	}
	/**
	*/
	public void reloadGun() {
		//TODO Need to add reload rate:
		System.out.print("RELOADING!");
		this.currentAmmo += this.clipSize;
	}

}


/*
Python code:

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
*/