package fuzzMUFFIN;

import battlecode.common.*;

public class Util 
{

	/**
	 * Contains back end methods and constants supporting the rest of the framework.
	 */
	
	// Stores the possible directions for easy access.
    public static final Direction[] directions = {Direction.NORTH, Direction.NORTH_EAST, Direction.EAST, Direction.SOUTH_EAST, Direction.SOUTH, Direction.SOUTH_WEST, Direction.WEST, Direction.NORTH_WEST};
  
    // Needed for encoding map locations as integers and vice versa.
    public static final int mapWidth = RobotPlayer.mapWidth;
    
    // Used for navigation.
    public static final int[] dLooks = {0, -1, 1, -2, 2, -3, 3, 4};
    
    /**
     * Turns the given location into an integer.
     * 
     * @param MapLocation - Takes the location to be turned into an integer.
     * @return int - Returns the integer form of the location.
     */
    public static int locationToInteger(MapLocation toInt) 
    {
    	
    	// The created number is in the format shown in lecture.
        return (toInt.x + mapWidth*toInt.y);
    }
    
    /**
     * Turns the given integer into a location on the map.
     * 
     * @param int - Takes the integer to be turned into a location.
     * @return MapLocation - Returns the map location encoded by the integer.
     */
    public static MapLocation integerToLoc(int i) 
    {
    	
    	// The map location is encoded in the integer in the format 
    	// shown in lecture.
        return new MapLocation(i%mapWidth, i/mapWidth);
    }
}
