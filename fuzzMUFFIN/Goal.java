package fuzzMUFFIN;

import battlecode.common.*;

public class Goal 
{

	/**
	 * Contains methods for setting the goal location of our swarm.
	 */
	
	// This will save us from having to pass our RobotController every time we use a method here
	private static RobotController rc = RobotPlayer.rc;
	
	/**
	 * Finds an enemy pasture and broadcasts its location to network.
	 * 
	 * @throws GameActionException
	 */
	public static void goToEnemyPasture() throws GameActionException
	{
		// Get location of enemy PASTRs.
		MapLocation[] PASTRs = rc.sensePastrLocations(rc.getTeam().opponent());
		
		// Broadcasts first PASTR location.
		if (PASTRs.length > 0)
		{
			Communicate.yellGoalLocation(PASTRs[0], 1);
			return;
		}
		contingencyGoal();
	}
	
	/**
	 * Sets the middle of the map as the swarm goal.
	 * 
	 * @throws GameActionException
	 */
	public static void goToMapMiddle() throws GameActionException
	{
		
		// First we find the middle of the map.
		int xMid = rc.getMapWidth()/2;
		int yMid = rc.getMapHeight()/2;
		
		// Then we broadcast it.
		Communicate.yellGoalLocation(new MapLocation(xMid,yMid), 1);
	}
	
	/**
	 * Sets the swarm goal to the enemy HQ.
	 * 
	 * @throws GameActionException
	 */
	public static void goToEnemyHQ() throws GameActionException
	{
		Communicate.yellGoalLocation(rc.senseEnemyHQLocation(), 1);
	}
	
	/**
	 * Sets the swarm goal to the friendly HQ.
	 * 
	 * @throws GameActionException
	 */
	public static void goToFriendlyHQ() throws GameActionException
	{
		Communicate.yellGoalLocation(rc.senseHQLocation(), 1);
	}
	
	/**
	 * Sets the goal if no other good goals exist.
	 * 
	 * @throws GameActionException
	 */
	public static void contingencyGoal() throws GameActionException
	{
		MapLocation emergency = Util.integerToLocation(rc.readBroadcast(3));
		
		if (!(emergency.x == 0 && emergency.y == 0))
		{
			Communicate.yellGoalLocation(emergency, 1);
		}
	}
}
