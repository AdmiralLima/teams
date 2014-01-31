package team005.Complex;

import team005.*;
import battlecode.common.*;

/**
 * Contains well thought out methods for spawning units from an HQ.
 */
public class Spawn 
{
	private static RobotController rc = RobotPlayer.rc;
	
	/**
	 * Spawns a unit in any available direction, starting with the direction of the enemy HQ.
	 * 
	 * @return boolean - Returns true if a successful spawn in executed, false otherwise.
	 * @throws GameActionException
	 */
	public static boolean spawn() throws GameActionException
	{
		Direction desired = rc.getLocation().directionTo(rc.senseEnemyHQLocation());
		for (int i = 0; i < 8; i++)
		{
			if (team005.Basic.Spawn.spawnDirection(desired))
			{
				return true;
			}
			desired = desired.rotateRight();
		}
		return false;
	}
}
