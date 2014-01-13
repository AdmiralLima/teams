package fuzzMUFFIN;

import battlecode.common.*;

public class DeathToPASTRs 
{

	/**
	 * Contains methods related to the strategy of killing enemy PASTRs.
	 */
	
	// This will save us from having to pass our RobotController every time we use a method here
	private static RobotController rc = RobotPlayer.rc;
	
	/**
	 * Finds an enemy pasture and broadcasts its location to network.  If no PASTR exists
	 * to kill, sets the enemy HQ as the goal (to blockade).
	 * 
	 * @throws GameActionException
	 */
	public static void setGoal() throws GameActionException
	{
		// Get location of enemy PASTRs.
		MapLocation[] PASTRs = rc.sensePastrLocations(rc.getTeam().opponent());
		
		// Broadcasts first PASTR location.
		if (PASTRs.length > 0)
		{
			Communicate.yellGoalLocation(PASTRs[0], 1);
		}
		
		// If there are no PASTRs we send our soldiers to the enemy HQ.
		else
		{
			Communicate.yellGoalLocation(rc.senseEnemyHQLocation(), 1);
		}
	}
}
