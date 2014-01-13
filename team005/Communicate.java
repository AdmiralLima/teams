package team005;

import battlecode.common.*;

/**
 * Contains methods for robot communication.
 */
public class Communicate 
{
	
	// This will save us from having to pass our RobotController every time we use a method here.
	private RobotController rc;
	
	// We need an integer large enough to make sure we can encode locations correctly.
	private static final int encoder = 200;
	
	/**
	 * The constructor simply takes the robot that will be communicating.
	 * 
	 * @param RobotController - The robot that wants to communicate.
	 */
	public Communicate (RobotController thisRC)
	{
		rc = thisRC;
	}
	
	/**
	 * Broadcasts the given location to the given channel.
	 * 
	 * @param MapLocation - Takes the location of the new goal.
	 * @param int - Takes the channel to broadcast the goal location to.
	 * @throws GameActionException
	 */
	public void yellGoalLocation(MapLocation newGoal, int channel) throws GameActionException
	{
		rc.broadcast(channel, newGoal.x + encoder*newGoal.y);
	}
	
	/**
	 * Gets the encoded location stored at the given channel.
	 * 
	 * @param int - Takes the channel where the goal is located.
	 * @return MapLocation - Returns the location that was encoded in the given channel.
	 * @throws GameActionException
	 */
	public MapLocation listenGoalLocation(int channel) throws GameActionException
	{
		
		// Get the encoded goal location.
		int encodedLocation = rc.readBroadcast(channel);
		
		// We need something to do return if there is no goal location.
		if (encodedLocation == 0)
		{
			return null;
		}
		
		// Convert it to a MapLocation and return it.
		int goalX = encodedLocation % encoder;
		int goalY = encodedLocation / encoder;
		return new MapLocation(goalX, goalY);
	}
}
