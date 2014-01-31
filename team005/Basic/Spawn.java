package team005.Basic;

import team005.*;
import battlecode.common.*;

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
		MapLocation newLocation = rc.getLocation().add(tryThis);
		TerrainTile newTerrain = rc.senseTerrainTile(newLocation);
		
		// check that we are not at robot limit
		if (rc.senseRobotCount() >= GameConstants.MAX_ROBOTS) {
		    return false;
		}
		
		// We try to spawn in the given location.
		if (rc.senseObjectAtLocation(newLocation) == null && newTerrain != TerrainTile.OFF_MAP && newTerrain != TerrainTile.VOID)
		{
			rc.spawn(tryThis);
			team005.RobotPlayer.newUnits = true;
			return true;
			
		}
		return false;
	}
}
