package OperationScreamingFist;

import battlecode.common.*;

import java.util.*;

public class RobotPlayer {
    static Random rand;
    
    public static void run(RobotController rc) {
        rand = new Random();
        Direction[] directions = {Direction.NORTH, Direction.NORTH_EAST, Direction.EAST, Direction.SOUTH_EAST, Direction.SOUTH, Direction.SOUTH_WEST, Direction.WEST, Direction.NORTH_WEST};
        
        int width = rc.getMapWidth();
        int height = rc.getMapHeight();
        int area = width*height;
        
        while (true) {
            if (rc.getType() == RobotType.HQ) { // if robot is the HQ
                if (rc.isActive() && rc.senseRobotCount() < GameConstants.MAX_ROBOTS) {
                    Direction spawnDir = Direction.EAST;
                    try {
                        if (rc.senseObjectAtLocation(rc.getLocation().add(spawnDir)) == null) {
                            rc.spawn(spawnDir);
                        }
                    } catch (GameActionException e) {e.printStackTrace(); System.out.println("HQ exception");}
                }
            }
            else if (rc.getType() == RobotType.SOLDIER) { // if robot is a soldier
                if (rc.isActive()) { // check that soldier is still active
                    int action = (rc.getRobot().getID()*rand.nextInt(1001) + 500)%1001;
                    // construct PASTR
                    if (action < 5 && rc.getLocation().distanceSquaredTo(rc.senseHQLocation()) > 25) {
                        try {
                            rc.construct(RobotType.PASTR);
                        } catch (GameActionException e) {e.printStackTrace(); System.out.println("Soldier construct exception");}
                    }
                    // attack nearby cunts
                    else if (action < 300) {
                        Robot[] nearbyEnemies = rc.senseNearbyGameObjects(Robot.class, 10, rc.getTeam().opponent());
                        if (nearbyEnemies.length > 0) {
                            RobotInfo robotInfo;
                            try {
                                robotInfo = rc.senseRobotInfo(nearbyEnemies[0]);
                                rc.attackSquare(robotInfo.location);
                            } catch (GameActionException e) {e.printStackTrace(); System.out.println("Soldier attack exception");}
                        }
                    }
                    // move in a random direction
                    else if (action < 600) {
                        Direction moveDirection = directions[rand.nextInt(8)];
                        if (rc.canMove(moveDirection)) {
                            try {
                                rc.move(moveDirection);
                            } catch (GameActionException e) {e.printStackTrace(); System.out.println("Soldier move exception");}
                        }
                    }
                    // move away from the friendly HQ
                    else if (action < (800 + area/10)) {
                        Direction fromHQ = rc.getLocation().directionTo(rc.senseHQLocation()).opposite();
                        if (rc.canMove(fromHQ)) {
                            try {
                                rc.move(fromHQ);
                            } catch (GameActionException e) {e.printStackTrace(); System.out.println("Soldier move from HQ exception");}
                        }
                    }
                    //Sneak towards the enemy
                    else {
                        Direction toEnemy = rc.getLocation().directionTo(rc.senseEnemyHQLocation());
                        if (rc.canMove(toEnemy)) {
                            try {
                                rc.sneak(toEnemy);
                            } catch (GameActionException e) {e.printStackTrace(); System.out.println("Soldier sneak to enemy HQ exception");}
                        }
                    }
                }
            }
            rc.yield();
        }
        
    }
}
