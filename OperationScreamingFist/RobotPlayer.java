package OperationScreamingFist;

import battlecode.common.*;
import OperationScreamingFist.actions.*;
import java.util.*;

public class RobotPlayer {
    
    public static RobotController rc;
    public static Random rand = new Random();
    public static Direction[] directions = {Direction.NORTH, Direction.NORTH_EAST, Direction.EAST, Direction.SOUTH_EAST, Direction.SOUTH, Direction.SOUTH_WEST, Direction.WEST, Direction.NORTH_WEST};
    
    public static void run(RobotController rcin) {
        ///////// initialise stuff /////////
        rc = rcin;
        rand.setSeed(rc.getRobot().getID());
        ////////////////////////////////////
        
        //int width = rc.getMapWidth();
        //int height = rc.getMapHeight();
        //int area = width*height;
        
        
        while (true) {
            if (rc.getType() == RobotType.HQ) { // if robot is the HQ
                try {
                    if (rc.isActive()) {
                        Attacks.attackRandom(); // try to attack
                        HQ.spawnTowardEnemy(); // try to spawn
                    }
                } catch (GameActionException e1) {e1.printStackTrace(); System.out.println("HQ Exception");}
                
            }
            else if (rc.getType() == RobotType.SOLDIER) { // if robot is a soldier
                if (rc.isActive()) { // check that soldier is still active
                    try {
                        Tactic.random();
                    } catch (GameActionException e) {e.printStackTrace(); System.out.println("Soldier exception");}
                }
            }
            rc.yield();
        }
        
    }
}
