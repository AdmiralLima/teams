package OperationScreamingFist.actions;

import java.util.Random;
import OperationScreamingFist.RobotPlayer;
import battlecode.common.*;

public class Soldier {
    
    private static RobotController rc = RobotPlayer.rc;
    private static Random rand = RobotPlayer.rand;
    private static Direction[] directions = RobotPlayer.directions;
    
    /**
     * attacks a random nearby enemy
     * 
     * @throws GameActionException
     */
    public static void attackRandom() throws GameActionException {
        Robot[] nearbyEnemies = rc.senseNearbyGameObjects(Robot.class, 10, rc.getTeam().opponent());
        if (nearbyEnemies.length > 0) {
            RobotInfo robotInfo;
            robotInfo = rc.senseRobotInfo(nearbyEnemies[0]);
            rc.attackSquare(robotInfo.location);
        }
    }
    
    /**
     * constructs a PASTR at current location
     * 
     * @throws GameActionException
     */
    public static void construct() throws GameActionException {
        if (rc.getLocation().distanceSquaredTo(rc.senseHQLocation()) > 25) {
            rc.construct(RobotType.PASTR);
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
        Direction moveDirection = directions[rand.nextInt(8)];
        if (rc.canMove(moveDirection)) {
            rc.move(moveDirection);
        }
    }

}
