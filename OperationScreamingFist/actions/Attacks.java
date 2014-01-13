package OperationScreamingFist.actions;

import java.util.Random;
import OperationScreamingFist.RobotPlayer;
import battlecode.common.*;

public class Attacks {
	private static RobotController rc = RobotPlayer.rc;
	private static Random rand = RobotPlayer.rand;

	/**
	 * attack an enemy within range
	 * 
	 * @return boolean - successful attack
	 * @throws GameActionException
	 */
	public static boolean attackRandom() throws GameActionException {
		Robot[] nearbyEnemies = rc.senseNearbyGameObjects(Robot.class,
				rc.senseRobotInfo(rc.getRobot()).type.attackRadiusMaxSquared,
				rc.getTeam().opponent());
		if (nearbyEnemies.length > 0) {
			rc.attackSquare(rc.senseRobotInfo(nearbyEnemies[0]).location);
			return true;
		}
		return false;
	}
	
	public static boolean interceptAndAttack() throws GameActionException {
	    Robot[] nearbyEnemies = rc.senseNearbyGameObjects(Robot.class,
                rc.getType().sensorRadiusSquared,
                rc.getTeam().opponent());
	    if (nearbyEnemies.length > 0) {
	        Intercept.intercept(nearbyEnemies[0]);
        }
        return false;
	}

	/**
	 * attack a random enemy but not the enemy HQ
	 * 
	 * @return boolean - successful attack
	 * @throws GameActionException
	 */
	public static boolean attackRandomNotHQ() throws GameActionException {
		Robot[] nearbyEnemies = rc.senseNearbyGameObjects(Robot.class,
				rc.senseRobotInfo(rc.getRobot()).type.attackRadiusMaxSquared,
				rc.getTeam().opponent());
		if (nearbyEnemies.length > 0) {
			for (Robot bad : nearbyEnemies) {
				if (!rc.senseRobotInfo(bad).type.equals(RobotType.HQ)) {
					rc.attackSquare(rc.senseRobotInfo(nearbyEnemies[0]).location);
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * attack a random enemy but not the enemy HQ
	 * 
	 * @param Robot
	 *            [] nearbyEnemies
	 * @return boolean - successful attack
	 * @throws GameActionException
	 */
	public static boolean attackRandomNotHQ(Robot[] nearbyEnemies)
			throws GameActionException {
		if (nearbyEnemies.length > 0) {
			for (Robot bad : nearbyEnemies) {
				if (!rc.senseRobotInfo(bad).type.equals(RobotType.HQ)) {
					rc.attackSquare(rc.senseRobotInfo(nearbyEnemies[0]).location);
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * attack an enemy soldier within range
	 * 
	 * @return boolean - successful attack
	 * @throws GameActionException
	 */
	public static boolean attackSOLDIER() throws GameActionException {
		Robot[] nearbyEnemies = rc.senseNearbyGameObjects(Robot.class,
				rc.senseRobotInfo(rc.getRobot()).type.attackRadiusMaxSquared,
				rc.getTeam().opponent());
		if (nearbyEnemies.length > 0) {
			for (Robot bad : nearbyEnemies) {
				if (rc.senseRobotInfo(bad).type.equals(RobotType.SOLDIER)) {
					rc.attackSquare(rc.senseRobotInfo(nearbyEnemies[0]).location);
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * attacks an enemy pasture within range
	 * 
	 * @return boolean - successful attack
	 * @throws GameActionException
	 */
	public static boolean attackPASTR() throws GameActionException {
		Robot[] nearbyEnemies = rc.senseNearbyGameObjects(Robot.class,
				rc.senseRobotInfo(rc.getRobot()).type.attackRadiusMaxSquared,
				rc.getTeam().opponent());
		if (nearbyEnemies.length > 0) {
			for (Robot bad : nearbyEnemies) {
				if (rc.senseRobotInfo(bad).type.equals(RobotType.PASTR)) {
					rc.attackSquare(rc.senseRobotInfo(nearbyEnemies[0]).location);
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * attacks an enemy noise tower within range
	 * 
	 * @return boolean - successful attack
	 * @throws GameActionException
	 */
	public static boolean attackNOISE() throws GameActionException {
		Robot[] nearbyEnemies = rc.senseNearbyGameObjects(Robot.class,
				rc.senseRobotInfo(rc.getRobot()).type.attackRadiusMaxSquared,
				rc.getTeam().opponent());
		if (nearbyEnemies.length > 0) {
			for (Robot bad : nearbyEnemies) {
				if (rc.senseRobotInfo(bad).type.equals(RobotType.NOISETOWER)) {
					rc.attackSquare(rc.senseRobotInfo(nearbyEnemies[0]).location);
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * attacks an enemy of the given type
	 * 
	 * @param RobotType
	 *            killType
	 * @return boolean - successful attack
	 * @throws GameActionException
	 */
	public static boolean attackType(RobotType killType)
			throws GameActionException {
		Robot[] nearbyEnemies = rc.senseNearbyGameObjects(Robot.class,
				rc.senseRobotInfo(rc.getRobot()).type.attackRadiusMaxSquared,
				rc.getTeam().opponent());
		if (nearbyEnemies.length > 0) {
			for (Robot bad : nearbyEnemies) {
				if (rc.senseRobotInfo(bad).type.equals(killType)) {
					rc.attackSquare(rc.senseRobotInfo(nearbyEnemies[0]).location);
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * attacks the given location
	 * 
	 * @return boolean - successful attack
	 * @throws GameActionException
	 */
	public static boolean attackLocation(MapLocation attackLoc)
			throws GameActionException {
	    // potentially could use this method to exclude robots from attacking enemy hq's location
		if (rc.getLocation().distanceSquaredTo(attackLoc) <= rc
				.senseRobotInfo(rc.getRobot()).type.attackRadiusMaxSquared) {
			rc.attackSquare(attackLoc);
			return true;
		}
		return false;
	}

	/**
	 * attacks the random tile selected within range with the most cows
	 * 
	 * @param int intensity - how many squares to compare
	 * @return boolean - successful attack
	 * @throws GameActionException
	 */
	public static boolean cowMassacre(int intensity) throws GameActionException {
		int maxCow = 0;
		MapLocation maxLoc = new MapLocation(0, 0);
		for (int i = 0; i <= intensity; i++) {
			int dir = rand.nextInt(8);
			int dis = rand.nextInt((int) Math.sqrt(rc.senseRobotInfo(rc
					.getRobot()).type.attackRadiusMaxSquared)) + 1;
			MapLocation Loc = rc.getLocation();
			for (int j = 1; j <= dis; i++) {
				Loc = Loc.add(Util.directions[dir]);
			}
			int cows = (int) rc.senseCowsAtLocation(Loc);
			if (cows > maxCow) {
				maxCow = cows;
				maxLoc = Loc;
			}
		}
		if (maxCow > 0) {
			return attackLocation(maxLoc);
		}
		return false;
	}
}
