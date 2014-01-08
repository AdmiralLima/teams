package OperationScreamingFist.actions;

import java.util.Random;

import OperationScreamingFist.RobotPlayer;
import battlecode.common.*;

public class Attacks 
{
    private static RobotController rc = RobotPlayer.rc;
    private static Random rand = RobotPlayer.rand;
    public static Direction[] directions = {Direction.NORTH, Direction.NORTH_EAST, Direction.EAST, Direction.SOUTH_EAST, Direction.SOUTH, Direction.SOUTH_WEST, Direction.WEST, Direction.NORTH_WEST};
    
    /**
     * attack an enemy within range of the HQ
     * 
     * @return boolean - successful attack
     * @throws GameActionException
     */
    public static boolean attackRandom() throws GameActionException 
    {
        Robot[] nearbyEnemies = rc.senseNearbyGameObjects(Robot.class,rc.senseRobotInfo(rc.getRobot()).type.attackRadiusMaxSquared,rc.getTeam().opponent());
        if (nearbyEnemies.length > 0) 
        {
            rc.attackSquare(rc.senseRobotInfo(nearbyEnemies[0]).location);
            return true;
        }
        return false;
    }
    
    /**
     * attacks the given location
     * 
     * @return boolean - successful attack
     * @throws GameActionException
     */
    public static boolean attackLocation(MapLocation attackLoc) throws GameActionException
    {
    	if (rc.getLocation().distanceSquaredTo(attackLoc) <= rc.senseRobotInfo(rc.getRobot()).type.attackRadiusMaxSquared)
    	{
    		rc.attackSquare(attackLoc);
    		return true;
    	}
    	return false;
    }
    
    /**
     * attacks the random tile selected within range with the most cows
     * 
     * @return boolean - successful attack
     * @throws GameActionException
     */
    public static boolean cowMassacre() throws GameActionException
    {
    	int maxCow = 0;
    	MapLocation maxLoc = new MapLocation(0,0);
    	for (int i = 0; i < 4; i++)
    	{
    		int dir = rand.nextInt(8);
    		MapLocation Loc = rc.getLocation().add(directions[dir]).add(directions[dir]);
    		int cows = (int) rc.senseCowsAtLocation(Loc);
    		if (cows > maxCow)
    		{
    			maxCow = cows;
    			maxLoc = Loc;
    		}
    	}
    	if (maxCow > 0)
    	{
    		return attackLocation(maxLoc);
    	}
    	return false;
    }
    
    /**
     * kills the cows around an enemy pasture
     * 
     * @returns boolean - successful attack
     * @throws GameActionsException
     */
    public static boolean cowDenial() throws GameActionException
    {
    	
    }
}