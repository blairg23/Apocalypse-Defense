package com.apocalypsedefense.core;


import java.io.Serializable;

/**
*/
public class MapElement implements Serializable {
	/**
	*/
	public Point position = new Point(0,0);
	/**
	*/
	public int size;
	/**
	*/
	public boolean isSelected;
	/**
	 * @param position 
	 * @param size 
	*/
	public MapElement(int size, Point position) {
		this.position = position;
		this.size = size;
		this.isSelected = false;
	}
	
	public void getStats(){
		System.out.print("Position: " + this.position);
		System.out.print("Size: " + this.size);
		System.out.print("Selected: " + this.isSelected);
	}

}

/*

Python code:
class MapElement():
    def __init__(self, size=(1,1), position=(0,0)):
        self.position = position
        self.size = size
        self.isSelected = False

    def getStats(self):
        print 'Position: ' + str(self.position)
        print 'Size: ' + str(self.size)
        print 'Selected: ' + str(self.isSelected)

*/