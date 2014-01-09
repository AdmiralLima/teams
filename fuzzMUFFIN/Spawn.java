package fuzzMUFFIN;

import battlecode.common.*;

public class Spawn 
{
	
	/**
	 * Contains methods relating to spawning units from the HQ.
	 */
	
	// This will save us from having to pass our RobotController every time we use a method here
	private static RobotController rc = RobotPlayer.rc;
	
    /**
     * Tries to spawn a soldier in the direction of the enemy HQ
     * 
     * @return boolean - Returns True is spawn attempt is successful, returns False otherwise.
     * @throws GameActionException
     */
    public static boolean spawnTowardEnemy() throws GameActionException 
    {
    	
    	// Makes sure we have not hit out unit cap.
        if (canSpawn()) 
        {
        	
        	// Find the direction of the enemy HQ and attempts to spawn in that direction
            Direction spawnDir = Sense.senseEnemyHQDirection();
            if (rc.senseObjectAtLocation(rc.getLocation().add(spawnDir)) == null) 
            {
                rc.spawn(spawnDir);
                return true;
            }
        }
        
        // If we cannot spawn in the direction of the enemy HQ we need to tell the caller.
        return false;
    }
    
    /**
     * Checks whether it is possible to spawn additional units.
     * 
     * @return boolean - Returns true if additional units are available.
     * @throws GameActionException
     */
    public static boolean canSpawn() throws GameActionException
    {
    	
    	// Checks the current number of units against the unit cap.
    	if (rc.senseRobotCount() < GameConstants.MAX_ROBOTS)
    	{
    		return true;
    	}
    	return false;
    }
}
