package OperationScreamingFist;

import battlecode.common.*;
import OperationScreamingFist.actions.*;
import java.util.*;

public class RobotPlayer {
    
    public static RobotController rc;
    public static Random rand = new Random();
    public static int mapWidth;
    public static int mapHeight;
    public static int maxMapChannel;
    
    public static void run(RobotController rcin) {
        ///////// initialise stuff /////////
        rc = rcin;
        rand.setSeed(rc.getRobot().getID());
        mapWidth = rc.getMapWidth();
        mapHeight = rc.getMapHeight();
        maxMapChannel = mapWidth * mapHeight;
        ////////////////////////////////////
        
        if (rc.getType() == RobotType.HQ) {
            try {
                MapBuilder.hqBuildMap();
                System.out.println(MapBuilder.stringMap());
            } catch (GameActionException e) {e.printStackTrace();}
        }
        
        while (true) {
            if (rc.isActive()) {
                if (rc.getType() == RobotType.HQ) { // if robot is the HQ
                    try {
                        Tactic.hqSpawnAndAttack();
                    } catch (GameActionException e1) {e1.printStackTrace(); System.out.println("HQ Exception");}
                }
                else if (rc.getType() == RobotType.SOLDIER) { // if robot is a soldier
                    try {
                        Tactic.soldierPASTRmassacre();
                    } catch (GameActionException e) {e.printStackTrace(); System.out.println("Soldier exception");}
                }
            }
            rc.yield();
        }
        
    }
}
