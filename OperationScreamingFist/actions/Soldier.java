package OperationScreamingFist.actions;

import java.util.Random;
import OperationScreamingFist.RobotPlayer;
import battlecode.common.*;

public class Soldier {
    
    /**
     * Soldier contains basic actions that only Soldiers can undertake, i.e. moving,
     * sneaking, constructing PASTRs and Noisetowers, self-destructing
     */
    
    private static RobotController rc = RobotPlayer.rc;
    private static Random rand = RobotPlayer.rand;
    
    public static void broadcastLocation() throws GameActionException {
        rc.setIndicatorString(0, "read ID: "+rc.readBroadcast(0));
        rc.broadcast(0, Util.locToInt(rc.getLocation()));
    }
    
    /**
     * constructs a PASTR at current location
     * 
     * @throws GameActionException
     */
    public static void constructPastr() throws GameActionException {
        if (rc.getLocation().distanceSquaredTo(rc.senseHQLocation()) > 25) {
            rc.construct(RobotType.PASTR);
        }
    }
    
    public static void constructNoisetower() throws GameActionException {
        rc.construct(RobotType.NOISETOWER);
    }
    
    /**
     * moves in dir if possible
     * 
     * @param dir
     * @throws GameActionException
     */
    public static void move(Direction dir) throws GameActionException {
        if (rc.canMove(dir)) 
        {
            rc.move(dir);
        }
        else
        {
        	for (int i = 0; i < 8; i++)
        	{
        		int randDir = rand.nextInt(8);
                if (rc.canMove(Util.directions[randDir])) 
                {
                    rc.move(Util.directions[randDir]);
                    break;
                }
        	}
        }
    }
    
    /**
     * moves away from the friendly HQ
     * 
     * @throws GameActionException
     */
    public static void moveAwayFromHQ() throws GameActionException {
        Direction fromHQ = rc.getLocation().directionTo(rc.senseHQLocation()).opposite();
        if (rc.canMove(fromHQ)) {
            rc.move(fromHQ);
        }
    }
    
    /**
     * sneaks towards the enemy HQ
     * 
     * @throws GameActionException
     */
    public static void sneakTowardEnemyHQ() throws GameActionException {
        Direction toEnemy = rc.getLocation().directionTo(rc.senseEnemyHQLocation());
        if (rc.canMove(toEnemy)) {
            rc.sneak(toEnemy);
        }
    }
    
    /**
     * moves in a random direction
     * 
     * @throws GameActionException
     */
    public static void randomMove() throws GameActionException {
        Direction moveDirection = Util.directions[rand.nextInt(8)];
        if (rc.canMove(moveDirection)) {
            rc.move(moveDirection);
        }
    }
    
    public static void suicideBomb() throws GameActionException {
        MapLocation[] pastrs = rc.sensePastrLocations(rc.getTeam().opponent());
        for (MapLocation pastr : pastrs) {
            if (rc.getLocation().isAdjacentTo(pastr)) {
                rc.selfDestruct();
            }
        }
        if (pastrs.length > 0) {
            Soldier.move(rc.getLocation().directionTo(pastrs[0]));
        }
    }

}
