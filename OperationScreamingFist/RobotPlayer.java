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
        

        MapLocation lastSquare = new MapLocation(0,0);
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
                    	System.out.println(GameConstants.MILK_GAIN_FACTOR);
                    	MapLocation currentSquare = rc.getLocation();
                        MapLocation[] killThese = rc.sensePastrLocations(rc.getTeam().opponent());
                        if(Attacks.attackRandomNotHQ()){}
                        else{
                        if (!killThese[0].equals(null)){
                        	if (Soldier.move(rc.getLocation().directionTo(killThese[0]))){}
                        	else {
                        		int dir = 0;
                        		for (int i = 1; i < 8; i++)
                        		{
                        			if (rc.getLocation().directionTo(killThese[0]).equals(Util.directions[i]))
                        			{
                        				dir = i;
                        			}
                        		}
                        		dir = (dir + 1)%8;
                        		int count = 1;
                        		while(!Soldier.move(Util.directions[dir]))
                        		{
                        			dir = (dir+1)%8;
                        			if (count == 7)
                        			{
                        				break;
                        			}
                        			count ++;
                        		}
                        	}
                        }
                        }
                    } catch (Exception e) {e.printStackTrace(); System.out.println("Soldier exception");}
                }
            }
            rc.yield();
        }
        
    }
}
