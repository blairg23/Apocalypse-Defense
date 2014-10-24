package com.apocalypsedefense.core;
import java.io.Serializable;
import java.lang.Math;
/**
 */
public class Point implements Serializable {
    /**
     */
    public int x;

    /**
     */
    public int y;

    /**
     * @param y 
     * @param x 
     */
    public Point(int x, int y) {
    	this.x = x;
    	this.y = y;
    }

    /**
     * @return int[]
     */
    public String getCoords() {
    	return x +","+y;
    }

    /**
     * @param point 
     * @return float
     */
    public float getDistance(Point point) {
        int x1 = this.x;
        int x2 = point.x;
        int y1 = this.y;
        int y2 = point.y;
    	return (float) Math.sqrt( Math.pow(x2-x1,2) + Math.pow(y2-y1,2) ); //Distance formula
    }
}


/*

Python code:

class Point():
    def __init__(self, x, y):
        self.x = x
        self.y = y
        self.coords = (self.x,self.y)

    def getCoords(self):
        self.coords = (self.x, self.y)
        return self.coords

    #Check for point equivalence:
    def equals(self, point):
        if self.x == point.x and self.y == point.y:
            return True
        else:
            return False

    #Get distance between this point and a different point:
    def getDistance(self, point):
        x1,x2=self.x, point.x
        y1,y2=self.y, point.y
        return sqrt( (x2-x1)**2 + (y2-y1)**2 ) #Distance formula

*/
