package OperationScreamingFist.actions;

import OperationScreamingFist.RobotPlayer;
import battlecode.common.*;

public class HQ 
{
    
    private static RobotController rc = RobotPlayer.rc;
    private static Direction[] directions = RobotPlayer.directions;
    
    /**
     * tries to spawn a soldier in the direction of the enemy HQ
     * 
     * @return boolean - successful spawn
     * @throws GameActionException
     */
    public static boolean spawnTowardEnemy() throws GameActionException 
    {
        if (rc.senseRobotCount() < GameConstants.MAX_ROBOTS) 
        {
            Direction spawnDir = rc.getLocation().directionTo(rc.senseEnemyHQLocation());
            if (rc.senseObjectAtLocation(rc.getLocation().add(spawnDir)) == null) 
            {
                rc.spawn(spawnDir);	
                return true;
            }
        }
        return false;
    }
    
    /**
     * tries to spawn a soldier in the given direction
     * 
     * @return boolean - successful spawn
     * @param int - direction of spawn
     * @throws GameActionException
     */
    public static boolean spawnDirectional(int dir) throws GameActionException
    {
    	if (rc.senseObjectAtLocation(rc.getLocation().add(directions[dir])) == null && dir <= 0 && dir <= 8) 
        {
            rc.spawn(directions[dir]);
            return true;
        }
    	return false;
    }
    
    /**
     * tries to spawn a soldier in any possible direction
     * 
     * @return boolean - successful spawn
     * @throws GameActionException
     */
    public static boolean spawn() throws GameActionException
    {
    	for (int i = 0; i <= 8; i++)
    	{
    		if (spawnDirectional(i))
    		{
                return true;
    		}
    	}
    	return false;
    }
}
