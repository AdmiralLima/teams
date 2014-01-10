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
    public static MapLocation[] visited;
    
    public static void run(RobotController rcin) {
        ///////// initialise stuff /////////
        rc = rcin;
        rand.setSeed(rc.getRobot().getID());
        mapWidth = rc.getMapWidth();
        mapHeight = rc.getMapHeight();
        maxMapChannel = mapWidth * mapHeight;
        ////////////////////////////////////
        

        //MapLocation lastSquare = new MapLocation(0,0);
        if (rc.getType() == RobotType.HQ) {
            try {
                MapBuilder.hqBuildMap();
                MapBuilder.generateMesh();
                System.out.println(MapBuilder.stringMesh());
            } catch (GameActionException e) {e.printStackTrace();}
        }
        
        visited = new MapLocation[1];
        visited[0] = rc.getLocation();
        
        while (true) {
        	
        	
        	
            if (rc.isActive()) {
                if (rc.getType() == RobotType.HQ) { // if robot is the HQ
                    try {
                        //Tactic.hqSpawnAndAttack();
                    } catch (Exception e) {e.printStackTrace(); System.out.println("HQ Exception");}
                }
                else if (rc.getType() == RobotType.SOLDIER) { // if robot is a soldier
                    try {
//                    	MapLocation currentSquare = rc.getLocation();
//                        MapLocation[] killThese = rc.sensePastrLocations(rc.getTeam().opponent());
//                        if(Attacks.attackRandomNotHQ()){}
//                        else{
//                        if (killThese.length > 0){
//                        	if (Soldier.move(rc.getLocation().directionTo(killThese[0]))){}
//                        	else {
//                        		int dir = 0;
//                        		for (int i = 1; i < 8; i++)
//                        		{
//                        			if (rc.getLocation().directionTo(killThese[0]).equals(Util.directions[i]))
//                        			{
//                        				dir = i;
//                        			}
//                        		}
//                        		dir = (dir + 1)%8;
//                        		int count = 1;
//                        		while(!Soldier.move(Util.directions[dir]))
//                        		{
//                        			dir = (dir+1)%8;
//                        			if (count == 7)
//                        			{
//                        				break;
//                        			}
//                        			count ++;
//                        		}
//                        	}
//                        }
//                        }


//                    	Direction direc = Bugging.Bug(rc.senseEnemyHQLocation());
//                    	if (rc.canMove(direc)) {
//                    		rc.move(direc);
//                    	}

                    } catch (Exception e) {e.printStackTrace(); System.out.println("Soldier exception");}
                }
            }
            visited[0] = rc.getLocation();
            rc.yield();
        }
        
    }
}
