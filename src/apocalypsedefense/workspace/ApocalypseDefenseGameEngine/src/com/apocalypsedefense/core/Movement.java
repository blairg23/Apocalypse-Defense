package com.apocalypsedefense.core;

import java.util.Random;
/**
*/
public class Movement {
	/**
	*/
	public boolean[][] blockedSpaces;
	
	public Point[] possibleChanges = {new Point(-1,1), new Point(0,1),new Point(1,1),new Point(-1,0), 
									   new Point(0,0), new Point(1,0), new Point(-1,-1), new Point(0,-1), new Point(1,-1)};
	
	public Map map;
	/**
	 * @param gameMap 
	*/
	public Movement(Map gameMap) {
		this.blockedSpaces = gameMap.blockedSpaces;
		this.map = gameMap;
	}
	/**
	 * @param currentPosition 
	 * @return Point
	 * Purpose: Randomly walk in one of 9 squares (middle being stand still)
	*/
	public Point randomWalk(Point currentPosition) {
		Random rand = new Random();
		int choice = rand.nextInt(this.possibleChanges.length-1);
		Point newPosition = new Point(0,0);
		newPosition.x = currentPosition.x + this.possibleChanges[choice].x;
		newPosition.y = currentPosition.y + this.possibleChanges[choice].y;
	    return newPosition;
	}
	/**
	 * @param currentPosition 
	 * @param targetPosition 
	 * @return Point
	 * Purpose:Walk toward the target position 
	*/
	public Point directedWalk(Point currentPosition, Point targetPosition) {
		System.out.println("Current Position: " + currentPosition.getCoords());
//	    //walk to the position that provides the minimum distance:
        Point minPos = new Point(0,0);
        float minDist = 99999;
		minPos = currentPosition;
		for(int i=0; i<this.possibleChanges.length;i++){
			Point newPosition = new Point(0,0); //New possible position
			newPosition.x = currentPosition.x + this.possibleChanges[i].x;
			newPosition.y = currentPosition.y + this.possibleChanges[i].y;
			if (!this.map.isBlocked(newPosition)){ //If the position ISN'T blocked:
				//Get the minimum distance:
				float dist = newPosition.getDistance(targetPosition); //New distance
				if (dist < minDist){ //If the distance is smaller than the current minimum distance
					minDist = dist; //Set that distance to the minimum distance
					minPos = newPosition; //Set the position to the minimum position
				}
			}
		}
//        if (currentPosition.x < targetPosition.x){
//            minPos.x = currentPosition.x + 1;
//        }
//        else {
//            minPos.x = currentPosition.x - 1;
//        }
//        if (currentPosition.y < targetPosition.y) {
//            minPos.y = currentPosition.y + 1;
//        }
//        else {
//            minPos.y = currentPosition.y - 1;
//        }
		System.out.println("New Position: " + minPos.getCoords());
		return minPos;
	}
	/**
	 * @param oldPosition 
	 * @param walkType 
	 * @param targetPosition 
	 * @return Point
	 * Purpose: Returns a new position based on the old position, walk type, and a target position, if any is specified.
	*/
	public Point getNewPosition(Point oldPosition, String walkType, Point targetPosition) {
	    Point newPosition;
		if (walkType.equals("Random")){
	    	newPosition = this.randomWalk(oldPosition);
            if (this.map.isBlocked(newPosition)){
                System.out.print("We got here" + walkType);
                return this.getNewPosition(oldPosition, walkType, targetPosition); //find a new position
            }
	    }
		else{
			newPosition = this.directedWalk(oldPosition, targetPosition);
		}
		//if (this.map.isBlocked(newPosition) || (newPosition.y > this.map.height) || (newPosition.x > this.map.width) || (newPosition.y < 0) || newPosition.x < 0){ //if we landed on a blocked position
		return newPosition;
	}
}


/*

Python code:

class Movement():
    def __init__(self, gameMap):
    #Set up initial parameters:
        self.height = gameMap.height
        self.width = gameMap.width
        self.map = gameMap
        self.blockedSpaces = gameMap.mapElements #List of blocked spaces
        self.possibleChanges = [Point(-1, 1), Point(0,1), Point(1, 1),
                                Point(-1, 0), Point(0,0), Point(1,0),
                                Point(-1,-1), Point(0,-1), Point(1,-1)]

    #Randomly walk in one of 9 squares (middle being stand still):
    def randomWalk(self, currentPosition):
        newPosition = [sum(pair) for pair in zip(currentPosition.getCoords(),
                                                 choice(self.possibleChanges).getCoords())]
        newPosition = Point(newPosition[0], newPosition[1]) #Convert to a point
        return newPosition
                       
                       

    #Walk toward the target position:
    def directedWalk(self,currentPosition, targetPosition):
        #Walk to the position that provides the minimum distance:
        minDist = 99999
        minPos = currentPosition
        for position in self.possibleChanges:
            newPosition = Point(0,0)
            newPosition.x = currentPosition.x + position.x
            newPosition.y = currentPosition.y + position.y
            dist = newPosition.getDistance(targetPosition) #New distance
            if  dist < minDist: #if the new distance if less than minimum distance
                minDist = dist #this is the new minimum distance
                minPos = newPosition #this is the new minimum position
        return minPos
    
    #Gets new position.... walkTypes: Random or Directed. 
    #If directed, specify target position
    def getNewPosition(self,oldPosition, walkType, targetPosition=None):
        position = oldPosition #old x,y

        if walkType == 'Random':
            newPosition = self.randomWalk(position)
            
        elif walkType == 'Directed':
            #newPosition = [sum(pair) for pair in zip(position.getCoords(), 
            #                                         self.directedWalk(position,targetPosition))]
            newPosition = self.directedWalk(position,targetPosition)
        elif walkType == None: #Default
            pass
        else:
            print 'ERROR in getNewPosition!' #Debugging msg
        #If we didn't walk off screen or hit another map element:
        if not self.map.isBlocked(newPosition):
            return newPosition
        #if self.width > int(newPosition.x) > 0 and self.height > int(newPosition.y) > 0 and not self.map.isBlocked(newPosition):
        #    return newPosition
        else: #If we did walk off screen or hit another map element:
            return self.getNewPosition(oldPosition, walkType, targetPosition) #Find a new place to go
*/