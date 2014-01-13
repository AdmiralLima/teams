package OperationScreamingFist.actions;

import battlecode.common.*;
import OperationScreamingFist.RobotPlayer;

public class Intercept {

    public static RobotController rc = RobotPlayer.rc;
    
    public static void intercept(Robot enemy) throws GameActionException {
        // get enemy's trajectory
        RobotInfo info = rc.senseRobotInfo(enemy);
        MapLocation enemyLocPrev = info.location;
        // get my location
        MapLocation myLoc = rc.getLocation();
        // get future time at which this robot could reach enemy
        int timeToEnemy = (int) Math.sqrt(myLoc.distanceSquaredTo(enemyLocPrev))-1; // distance as the crow flies
        ////int timeToEnemy = Math.abs(myLoc.y - enemyLoc.y) + Math.abs(myLoc.x - enemyLoc.x); // manhattan distance
        // move towards enemy
        System.out.println("Distance^2 to enemy is " + myLoc.distanceSquaredTo(enemyLocPrev));
        Soldier.move(myLoc.directionTo(enemyLocPrev));
        
        rc.yield();
        
        if (rc.isActive()) {

            MapLocation enemyLoc = enemyLocPrev;
            Direction enemyTraj = myLoc.directionTo(rc.senseEnemyHQLocation());

            while (info.health > 0 && rc.canSenseObject(enemy)) {
                if (rc.isActive()) {
                    if (rc.canSenseObject(enemy)) {
                        // get enemy robot info
                        info = rc.senseRobotInfo(enemy);
                        enemyLoc = info.location;
                        enemyTraj = enemyLocPrev.directionTo(enemyLoc);
                        //System.out.println("Spotted enemy at " + enemyLoc.toString() + " facing " + enemyTraj.toString());
                        if (rc.canAttackSquare(enemyLoc)) {
                            Attacks.attackLocation(enemyLoc);
                        }
                    } else { 
                        // get my loc
                        myLoc = rc.getLocation();
                        // if the enemy hasn't moved, head straight for them
                        if (enemyTraj.equals(Direction.OMNI) || enemyTraj.equals(Direction.NONE)) {
                            System.out.println("Following " + info.type.toString() + " with health " + info.health);
                            System.out.println(myLoc.directionTo(enemyLoc).toString());
                            Soldier.move(myLoc.directionTo(enemyLoc));
                        } else { 
                            // else predict enemies future position at future time
                            MapLocation goal = enemyLoc;
                            for (int i = timeToEnemy; i > 0; i--) {
                                goal.add(enemyTraj);
                            }
                            // get direction to future position
                            Direction moveTo = goal.directionTo(myLoc);
                            // move toward it
                            System.out.println("Moving toward mobile enemy in direction " + moveTo);
                            Soldier.move(moveTo);
                        }
                    }
                    enemyLocPrev = enemyLoc;
                    rc.yield();
                }
            }
        }
    }
    
    

}
