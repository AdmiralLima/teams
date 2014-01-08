package OperationScreamingFist;

import battlecode.common.*;
import OperationScreamingFist.actions.*;
import java.util.*;

public class RobotPlayer {
    
    public static RobotController rc;
    public static Random rand = new Random();
    public static int maxMapChannel;
    public static int mapHeight;
    
    public static void run(RobotController rcin) {
        ///////// initialise stuff /////////
        rc = rcin;
        rand.setSeed(rc.getRobot().getID());
        maxMapChannel = rc.getMapWidth();
        mapHeight = rc.getMapHeight();
        ////////////////////////////////////
        
        //int width = rc.getMapWidth();
        //int height = rc.getMapHeight();
        //int area = width*height;
        
        
        while (true) {
            if (rc.isActive()) {
                if (rc.getType() == RobotType.HQ) { // if robot is the HQ
                    try {
                        Tactic.hqSpawnAndAttack();
                    } catch (GameActionException e1) {e1.printStackTrace(); System.out.println("HQ Exception");}
                }
                else if (rc.getType() == RobotType.SOLDIER) { // if robot is a soldier
                    try {
                        Tactic.soldierRandom();
                    } catch (GameActionException e) {e.printStackTrace(); System.out.println("Soldier exception");}
                }
            }
            rc.yield();
        }
        
    }
}
