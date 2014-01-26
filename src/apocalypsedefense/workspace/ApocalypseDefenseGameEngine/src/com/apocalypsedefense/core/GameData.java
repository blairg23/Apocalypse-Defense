package com.apocalypsedefense.core;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class GameData implements Serializable {
	public Map map;
	public ArrayList<Survivor> survivors;
	public ArrayList<Zombie> zombies;
	public int zombieKillCount;
	public int gold;
	public int wave;
	public int towerDeadCount;

    public void persist(FileOutputStream out) {
        //TODO save data to file
        // This method should be fast, otherwise it should be run in background
    }

    public static GameData load(FileInputStream in) {
        //TODO
        return new GameData();
    }
}
