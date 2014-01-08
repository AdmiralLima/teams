package OperationScreamingFist.actions;

import OperationScreamingFist.RobotPlayer;
import battlecode.common.*;

import java.util.Random;

public class Tactic {
    
    private static RobotController rc = RobotPlayer.rc;
    private static Random rand = RobotPlayer.rand;
    public static Direction[] directions = {Direction.NORTH, Direction.NORTH_EAST, Direction.EAST, Direction.SOUTH_EAST, Direction.SOUTH, Direction.SOUTH_WEST, Direction.WEST, Direction.NORTH_WEST};
    
    public static void soldierSwarm() {
        
    }
    
    /**
     * a non-deterministic strategy based on the original example player
     * 
     * @throws GameActionException
     */
    public static void soldierRandom() throws GameActionException {
        int action = (rc.getRobot().getID()*rand.nextInt(1001) + 500)%1001;
        // construct PASTR
        if (action < 5) {
            Soldier.constructPastr();
        }
        // attack nearby cunts
        else if (action < 300) {
            Attacks.attackRandom();
        }
        // move in a random direction
        else if (action < 600) {
            Soldier.randomMove();
        }
        // suicide bomb enemy PASTRs
        else if (action < 700) {
            Soldier.suicideBomb();
        }
        // move away from the friendly HQ
        else if (action < (800)) {
            Soldier.moveAwayFromHQ();
        }
        //Sneak towards the enemy
        else {
            Soldier.sneakTowardEnemyHQ();
        }
    }
    

}
