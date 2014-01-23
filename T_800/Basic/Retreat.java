package T_800.Basic;

import T_800.RobotPlayer;
import battlecode.common.*;

/**
 * Contains methods for deciding to retreat and call for help.
 */
public class Retreat 
{
	private static RobotController rc = RobotPlayer.rc;
	private static double retreatHealth = 0.4;
	private static double callHelpHealth = 0.8;

	/**
	 * Determines if the robot should retreat.
	 * 
	 * @return boolean - Returns true if the robot should retreat.
	 * @throws GameActionException
	 */
	public static boolean shouldRetreat() throws GameActionException
	{
		return rc.getHealth() < RobotType.SOLDIER.maxHealth*retreatHealth;
	}
	
	/**
	 * Determines if the robot should call for help.
	 * 
	 * @return boolean - Returns true if the robot should call for help.
	 * @throws GameActionException
	 */
	public static boolean shouldCallHelp() throws GameActionException
	{
		return rc.getHealth() < rc.getType().maxHealth*callHelpHealth;
	}
}
