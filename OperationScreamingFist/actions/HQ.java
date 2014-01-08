package OperationScreamingFist.actions;

import java.util.Random;
import OperationScreamingFist.RobotPlayer;
import battlecode.common.*;

public class HQ {
    
    private static RobotController rc = RobotPlayer.rc;
    private static Random rand = RobotPlayer.rand;
    private static Direction[] directions = RobotPlayer.directions;

    /**
     * attacks enemies within range of the HQ
     * 
     * @param rc
     * @throws GameActionException
     */
    public static void attackRandom() throws GameActionException {
        Robot[] nearbyEnemies = rc.senseNearbyGameObjects(Robot.class,rc.senseRobotInfo(rc.getRobot()).type.attackRadiusMaxSquared,rc.getTeam().opponent());
        if (nearbyEnemies.length > 0) {
            rc.attackSquare(rc.senseRobotInfo(nearbyEnemies[0]).location);
        }
    }
    
    /**
     * spawns soldiers near HQ
     * @param rc
     * @throws GameActionException
     */
    public static void spawnTowardEnemy() throws GameActionException {
        if (rc.senseRobotCount() < GameConstants.MAX_ROBOTS) {
            Direction spawnDir = rc.getLocation().directionTo(rc.senseEnemyHQLocation());
            if (rc.senseObjectAtLocation(rc.getLocation().add(spawnDir)) == null) {
                rc.spawn(spawnDir);
            }
        }
    }
    
}
