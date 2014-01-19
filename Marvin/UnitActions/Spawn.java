package Marvin.UnitActions;

import Marvin.RobotPlayer;
import battlecode.common.*;

/**
 * This class contains methods for spawning SOLDIER units from an HQ unit.
 */
public class Spawn 
{
	private static RobotController rc = RobotPlayer.rc;
	
	/**
	 * This method tries to spawn a unit in the direction of the enemy HQ unit.
	 * 
	 * @return boolean - Returns true if spawn was successful, false otherwise.
	 * @throws GameActionException
	 */
    public static boolean spawnDirectional(Direction spawnDirection) throws GameActionException 
    {
        if (rc.senseRobotCount() < GameConstants.MAX_ROBOTS) 
        {
        	MapLocation rcLocation = rc.getLocation();
        	
        	// We will try to spawn in each direction, starting with the given direction.
            if (rc.senseObjectAtLocation(rcLocation.add(spawnDirection)) == null) 
            {
                rc.spawn(spawnDirection);
                return true;
            }
            spawnDirection = spawnDirection.rotateRight();
            if (rc.senseObjectAtLocation(rcLocation.add(spawnDirection)) == null) 
            {
                rc.spawn(spawnDirection);
                return true;
            }
            spawnDirection = spawnDirection.rotateLeft().rotateLeft();
            if (rc.senseObjectAtLocation(rcLocation.add(spawnDirection)) == null) 
            {
                rc.spawn(spawnDirection);
                return true;
            }
            spawnDirection = spawnDirection.rotateRight().rotateRight().rotateRight();
            if (rc.senseObjectAtLocation(rcLocation.add(spawnDirection)) == null) 
            {
                rc.spawn(spawnDirection);
                return true;
            }
            spawnDirection = spawnDirection.rotateLeft().rotateLeft().rotateLeft().rotateLeft();
            if (rc.senseObjectAtLocation(rcLocation.add(spawnDirection)) == null) 
            {
                rc.spawn(spawnDirection);
                return true;
            }
            spawnDirection = spawnDirection.rotateRight().rotateRight().rotateRight().rotateRight().rotateRight();
            if (rc.senseObjectAtLocation(rcLocation.add(spawnDirection)) == null) 
            {
                rc.spawn(spawnDirection);
                return true;
            }
            spawnDirection = spawnDirection.rotateLeft().rotateLeft().rotateLeft().rotateLeft().rotateLeft().rotateLeft();
            if (rc.senseObjectAtLocation(rcLocation.add(spawnDirection)) == null) 
            {
                rc.spawn(spawnDirection);
                return true;
            }
            spawnDirection = spawnDirection.rotateLeft();
            if (rc.senseObjectAtLocation(rcLocation.add(spawnDirection)) == null) 
            {
                rc.spawn(spawnDirection);
                return true;
            }
        }
        return false;
    }
}
