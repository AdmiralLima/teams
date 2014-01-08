package examplefuncsplayer;

import battlecode.common.*;

import java.util.*;

public class RobotPlayer {
	static Random rand;
	
	public static void run(RobotController rc) {
		rand = new Random();
		Direction[] directions = {Direction.NORTH, Direction.NORTH_EAST, Direction.EAST, Direction.SOUTH_EAST, Direction.SOUTH, Direction.SOUTH_WEST, Direction.WEST, Direction.NORTH_WEST};
		
		while(true) {
			if (rc.getType() == RobotType.HQ) {
				try {	
				    Robot[] nearbyEnemies = rc.senseNearbyGameObjects(Robot.class,rc.senseRobotInfo(rc.getRobot()).type.attackRadiusMaxSquared,rc.getTeam().opponent());
				    if (rc.isActive() && nearbyEnemies.length > 0) {
				        rc.attackSquare(rc.senseRobotInfo(nearbyEnemies[0]).location);
				    }
					//Check if a robot is spawnable and spawn one if it is
				    else if (rc.isActive() && rc.senseRobotCount() < 25) {
						Direction toEnemy = rc.getLocation().directionTo(rc.senseEnemyHQLocation());
						if (rc.senseObjectAtLocation(rc.getLocation().add(toEnemy)) == null) {
							rc.spawn(toEnemy);
						}
					}
				} catch (Exception e) {e.printStackTrace(); System.out.println("HQ Exception");}
			}
			
			if (rc.getType() == RobotType.SOLDIER) {
				try {
					if (rc.isActive()) {
						int action = (rc.getRobot().getID()*rand.nextInt(101) + 50)%101;
						//Construct a PASTR
						if (action < 1 && rc.getLocation().distanceSquaredTo(rc.senseHQLocation()) > 2) {
							rc.construct(RobotType.PASTR);
						//Attack a random nearby enemy
						} else if (action < 30) {
							Robot[] nearbyEnemies = rc.senseNearbyGameObjects(Robot.class,10,rc.getTeam().opponent());
							if (nearbyEnemies.length > 0) {
								RobotInfo robotInfo = rc.senseRobotInfo(nearbyEnemies[0]);
								rc.attackSquare(robotInfo.location);
							}
						//Move in a random direction
						} else if (action < 80) {
							Direction moveDirection = directions[rand.nextInt(8)];
							if (rc.canMove(moveDirection)) {
								rc.move(moveDirection);
							}
						//Sneak towards the enemy
						} else {
							Direction toEnemy = rc.getLocation().directionTo(rc.senseEnemyHQLocation());
							if (rc.canMove(toEnemy)) {
								rc.sneak(toEnemy);
							}
						}
					}
				} catch (Exception e) {
					System.out.println("Soldier Exception");
				}
			}
			
			rc.yield();
		}
	}
}
