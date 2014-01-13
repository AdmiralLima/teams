package SkyNet.SimpleActions;

import battlecode.common.*;

/**
 * Contains methods relating to spawning units from the HQ.
 */
public class Spawn 
{
	
	// We need to store the robot that will be spawning.
	private RobotController rc;
	
	/**
	 * Sets up spawning for the current RobotPlayer.
	 * 
	 * @param RobotController - Takes the robot that will be spawning.
	 */
	public Spawn(RobotController thisRC)
	{
		rc = thisRC;
	}
	
	/**
	 * Tries to spawn a soldier in the given direction.
	 * 
	 * @param Direction - The direction we want to spawn in.
	 * @return boolean - Returns true if we spawn, false otherwise.
	 * @throws GameActionException
	 */
	public boolean spawnDirection(Direction thisDirection) throws GameActionException
	{
		
		// First we need to make sure we have not hit our unit cap.
		if (rc.senseRobotCount() < GameConstants.MAX_ROBOTS)
		{
			
			// Now we need to check if we can spawn in the given direction.
			if (rc.senseObjectAtLocation(rc.getLocation().add(thisDirection)) == null)
			{
				rc.spawn(thisDirection);
			}
		}
		
		// If we cannot spawn in the given direction we need to let the caller know.
		return false;
	}
	
    /**
     * Tries to spawn a soldier in the direction of the enemy HQ.
     * 
     * @return boolean - Returns True is spawn attempt is successful, returns False otherwise.
     * @throws GameActionException
     */
    public boolean spawnDirectionOfEnemy() throws GameActionException 
    {
    	
    	// Makes sure we have not hit out unit cap.
        if (rc.senseRobotCount() < GameConstants.MAX_ROBOTS) 
        {
        	
        	// We need the direction of the enemy HQ.
            Direction spawnDirection = rc.getLocation().directionTo(rc.senseEnemyHQLocation());
            
            
            // Now we try to spawn in that direction
            if (rc.senseObjectAtLocation(rc.getLocation().add(spawnDirection)) == null) 
            {
                rc.spawn(spawnDirection);
                return true;
            }
        }
        
        // If we cannot spawn in the direction of the enemy HQ we need to tell the caller.
        return false;
    }
    
    /**
     * Spawns a unit in any available direction.  Tries to spawn in direction 
     * of enemy HQ first.
     * 
     * @return boolean - Returns True if spawn attempt is successful, returns False otherwise.
     * @throws GameActionException
     */
    public boolean spawn() throws GameActionException
    {
    	// First we make sure we are not at our unit cap.
    	if (rc.senseRobotCount() < GameConstants.MAX_ROBOTS)
    	{
    		
    		// If we can spawn we try to spawn towards the enemy.
            Direction spawnDirection = rc.getLocation().directionTo(rc.senseEnemyHQLocation());
    		if (rc.senseObjectAtLocation(rc.getLocation().add(spawnDirection)) == null)
    		{
                rc.spawn(spawnDirection);
                return true;
    		}
    		
    		// If we cannot spawn towards the enemy we go in any available direction.
    		for (int i = 0; i < 7; i++)
    		{
    			spawnDirection = spawnDirection.rotateRight();
                if (rc.senseObjectAtLocation(rc.getLocation().add(spawnDirection)) == null) 
                {
                    rc.spawn(spawnDirection);
                    return true;
                }
    		}
    	}
    	return false;
    }
    
}
