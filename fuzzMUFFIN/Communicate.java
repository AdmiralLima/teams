package fuzzMUFFIN;

import battlecode.common.*;

public class Communicate 
{

	/**
	 * Contains methods for robot communication.
	 */
	
	// This will save us from having to pass our RobotController every time we use a method here
	private static RobotController rc = RobotPlayer.rc;
	
	/**
	 * Broadcasts the given location to the given channel.
	 * 
	 * @param MapLocation - Takes the location of the new goal.
	 * @param int - Takes the channel to broadcast the goal location to.
	 * @throws GameActionException
	 */
	public static void yellGoalLocation(MapLocation newGoal, int channel) throws GameActionException
	{
		rc.broadcast(channel, Util.locationToInteger(newGoal));
	}
	
	/**
	 * Gets the encoded location stored at the given channel.
	 * 
	 * @param int - Takes the channel where the goal is located.
	 * @return MapLocation - Returns the location that was encoded in the given channel.
	 * @throws GameActionException
	 */
	public static MapLocation listenGoalLocation(int channel) throws GameActionException
	{
		
		// Get the encoded goal location.
		int encodedLocation = rc.readBroadcast(channel);
		
		// Convert it to a MapLocation and return it.
		return Util.integerToLoc(encodedLocation);
	}
}
