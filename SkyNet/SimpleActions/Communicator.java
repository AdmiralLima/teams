package SkyNet.SimpleActions;

import battlecode.common.*;

/**
 * Contains methods for robot communication.
 */
public class Communicator
{
	private RobotController rc;
	private static final int encoder = 200;
	private int strategyChannel;
	private int goalChannel;
	private int helpChannel;
	
	/**
	 * The constructor takes the controller of the unit that will be communicating.
	 * It additionally requires the channel that will be used for setting goal location
	 * and the channel that will be used to call for help.
	 * 
	 * @param RobotController - The robot that wants to communicate.
	 * @param int - The channel where goal location will be stored.
	 * @param int - The channel where the location of units in duress will be stored.
	 */
	public Communicator (RobotController thisRC, int thisStrategyChannel, int thisGoalChannel, int thisHelpChannel)
	{
		rc = thisRC;
		strategyChannel = thisStrategyChannel;
		goalChannel = thisGoalChannel;
		helpChannel = thisHelpChannel;
	}
	
	/**
	 * Broadcasts the given location to the goal channel.
	 * 
	 * @param MapLocation - Takes the location of the new goal.
	 * @throws GameActionException
	 */
	public void yellGoalLocation(MapLocation newGoal) throws GameActionException
	{
		rc.broadcast(goalChannel, newGoal.x + encoder*newGoal.y);
	}
	
	/**
	 * Gets the encoded location stored at the goal channel.
	 * 
	 * @return MapLocation - Returns the location that was encoded in the given channel.
	 * @throws GameActionException
	 */
	public MapLocation listenGoalLocation() throws GameActionException
	{
		int encodedLocation = rc.readBroadcast(goalChannel);
		if (encodedLocation == 0)
		{
			return null;
		}
		int goalX = encodedLocation % encoder;
		int goalY = encodedLocation / encoder;
		return new MapLocation(goalX, goalY);
	}
	
	/**
	 * Broadcasts the given location to the help channel.
	 * 
	 * @param MapLocation - Takes the location where help is required.
	 * @throws GameActionException
	 */
	public void yellHelpLocation(MapLocation helpHere) throws GameActionException
	{
		rc.broadcast(helpChannel, helpHere.x + encoder*helpHere.y);
	}
	
	/**
	 * Gets the encoded location stored at the help channel.
	 * 
	 * @return MapLocation - Returns the location stored in the help channel.
	 * @throws GameActionException
	 */
	public MapLocation listenHelpLocation() throws GameActionException
	{
		int encodedLocation = rc.readBroadcast(helpChannel);
		if (encodedLocation == 0)
		{
			return null;
		}
		int goalX = encodedLocation % encoder;
		int goalY = encodedLocation / encoder;
		return new MapLocation(goalX, goalY);
	}
}
