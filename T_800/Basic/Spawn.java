package T_800.Basic;

import battlecode.common.*;
import T_800.*;

/**
 * Contains very simple methods for spawning with an HQ unit.
 */
public class Spawn 
{
	private static RobotController rc = RobotPlayer.rc;
	
	/**
	 * Attempts to spawn a unit in the given direction.
	 * 
	 * @param Direction - Takes the direction to spawn in.
	 * @return boolean - Returns true if a unit was spawned, false otherwise.
	 * @throws GameActionException
	 */
	public static boolean spawnDirection(Direction tryThis) throws GameActionException
	{
		
		// We try to spawn in the given location.
		if (rc.senseObjectAtLocation(rc.getLocation().add(tryThis)) == null)
		{
			rc.spawn(tryThis);
			T_800.RobotPlayer.newUnits = true;
			return true;
		}
		return false;
	}
}
