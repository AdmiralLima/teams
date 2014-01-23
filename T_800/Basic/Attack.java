package T_800.Basic;

import T_800.*;
import battlecode.common.*;

/**
 * Contains very simple methods for robot attacking.
 */
public class Attack 
{
	private static RobotController rc = RobotPlayer.rc;
	
	/**
	 * Tries to attack the given location.
	 * 
	 * @param MapLocation - Takes the location to be attacks.
	 * @return boolean - Returns true if the robot executed an attack, false otherwise.
	 * @throws GameActionException
	 */
	public static boolean attackLocation(MapLocation attackThis) throws GameActionException
	{
		
		// If we can attack the location we do.
		if (rc.canAttackSquare(attackThis))
		{
			rc.attackSquare(attackThis);
			return true;
		}
		return false;
	}
	
	public static boolean suicide(MapLocation killThis) throws GameActionException
	{
		if (rc.getLocation().distanceSquaredTo(killThis) < 3)
		{
			rc.selfDestruct();
			return true;
		}
		return false;
	}
}
