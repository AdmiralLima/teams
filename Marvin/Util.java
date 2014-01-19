package Marvin;

import battlecode.common.*;

/**
 * This class contains useful constants and methods for performing relevant calculations.
 */
public class Util 
{
	
	// The following are game constants used for robot communication.
	public static final int LOCATIONENCODER = 2000;
	public static final int KILLMECHANNEL = 1;
	public static final int KILLENEMYCHANNEL = 2;
	public static final int SAVEMECHANNEL = 3;

	/**
	 * The method converts the given location to an integer.
	 * 
	 * @param MapLocation - Takes the location to be encoded.
	 * @return int - Returns the integer version of the location.
	 * @throws GameActionException
	 */
	public static int locationInteger(MapLocation location) throws GameActionException
	{
		return location.x + LOCATIONENCODER*location.y;
	}
	
	/**
	 * This method converts the given integer into a location.
	 * 
	 * @param int - Takes the encoded location.
	 * @return MapLocation - Returns the location that was encoded in the integer.
	 * @throws GameActionException
	 */
	public static MapLocation integerLocation(int location) throws GameActionException
	{
		return new MapLocation(location % Util.LOCATIONENCODER, location / Util.LOCATIONENCODER);
	}
}
