package com.apocalypsedefense.core;

import java.io.Serializable;
import java.util.ArrayList;

/**
 */
public class Map implements Serializable {
    /**
     */
    public boolean[][] blockedSpaces;

    /**
     */
    public int height;

    /**
     */
    public int width;

    /**
     * @param height 
     * @param width 
     * @param blockedSpaces 
     */
    public Map(ArrayList<Point> blockedSpaces, int height, int width) {
    	this.blockedSpaces = new boolean[width][height];
    	this.height = height;
    	this.width = width;
    	update(blockedSpaces);
    }

    
    /**
     * @param position 
     * @return Boolean
     * Purpose: Returns true if the position is blocked, false if not.
     */
    public Boolean isBlocked(Point position) {
    	//Check bounds of map:
        if (position.x >= this.width || position.x < 0){
        	return true;
        }
        if (position.y >= this.height || position.y <0){
        	return true;
        }
        //If not out of bounds, return the value of the position (blocked or not):
        return this.blockedSpaces[position.x][position.y];
    }

    /**
     * @param blockedSpaces 
     * Purpose: Update map elements in map.
     */
    public void update(ArrayList<Point> blockedSpaces) {
    	//Reset blocked spaces to false:
    	for (int i=0;i<width;i++){
    		for (int j=0; j<height;j++){
    			
    			this.blockedSpaces[i][j] = false; 
    		}
    	}
    	
    	//Block updated spaces:
    	for (Point element : blockedSpaces){
    		this.blockedSpaces[element.x][element.y] = true;
    	}
    }
}

/*

Python code:

class Map():#Keeps track of the positions that are blocked
    def __init__(self, mapElements=[], height=100, width=100):
        self.mapElements = mapElements
        self.height = height
        self.width = width

    def isBlocked(self,position):
        if position in self.mapElements:
            return True
        else:
            return False

    def update(self,mapElements):
        self.mapElements = mapElements


*/