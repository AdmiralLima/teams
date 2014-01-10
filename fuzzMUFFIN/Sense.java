package fuzzMUFFIN;

import battlecode.common.*;

public class Sense 
{
	
	/**
	 * Contains information related to sensing the in game environment.
	 */

	// This will save us from having to pass our RobotController every time we use a method here
	private static RobotController rc = RobotPlayer.rc;
	
	/**
	 * Senses the location of enemy units within range.
	 * 
	 * @return Robot[] - Returns an array of enemies within the robot's range.
	 * @throws GameActionException
	 */
	public static Robot[] senseEnemiesInRange() throws GameActionException
	{
		// We need to gather information on our robot to correctly sense the enemies within range.
		int attackRange = rc.senseRobotInfo(rc.getRobot()).type.attackRadiusMaxSquared;
		Class<Robot> robotClass = Robot.class;
		Team enemyTeam = rc.getTeam().opponent();
		
		// Now we can actually find the enemies that are within range.
		return rc.senseNearbyGameObjects(robotClass, attackRange, enemyTeam);
	}
	
	/**
	 * Senses the type of the given robot.  This method will throw an exception if the given
	 * robot is out of the current units range.
	 * 
	 * @param Robot - Takes unit whose type is unknown.
	 * @return RobotType - Returns the type of the given robot.
	 * @throws GameActionException
	 */
	public static RobotType senseRobotType(Robot unknownType) throws GameActionException
	{
		return rc.senseRobotInfo(unknownType).type;
	}
	
	/**
	 * Senses if the given robot is an HQ.
	 * 
	 * @param Robot - Takes the unit that might be an HQ.
	 * @return boolean - Returns True if the passed unit is an HQ, returns False otherwise.
	 * @throws GameActionException
	 */
	public static boolean senseIfIsHQ(Robot unknownType) throws GameActionException
	{
		return senseRobotType(unknownType).equals(RobotType.HQ);
	}
	
	/**
	 * Senses the given robot's location.
	 * 
	 * @param Robot - Takes the robot whose location is in question.
	 * @return MapLocation - Returns the location of the given robot.
	 * @throws GameActionException
	 */
	public static MapLocation senseRobotLocation(Robot whereIs) throws GameActionException
	{
		return rc.senseRobotInfo(whereIs).location;	
	}
	
	/**
	 * Senses the direction to the given location.
	 * 
	 * @param MapLocation - the location to find the direction to.
	 * @returns Direction - Returns the direction to the given location.
	 * @throws GameActionException
	 */
	public static Direction senseLocationDirection(MapLocation toThis) throws GameActionException
	{
		return rc.getLocation().directionTo(toThis);
	}
	
	/**
	 * Senses the direction from the current robot to the enemy HQ.
	 * 
	 * @returns Direction - Returns the direction to the enemy HQ.
	 * @throws GameActionException
	 */
	public static Direction senseEnemyHQDirection() throws GameActionException
	{
		return senseLocationDirection(rc.senseEnemyHQLocation());
	}
	
	/**
	 * Senses if there are close by enemies.
	 * 
	 * @return boolean - Returns True if there are enemies within sensor range.
	 * @throws GameActionException
	 */
	public static boolean senseEnemies() throws GameActionException
	{
		// We need to gather information on our robot to correctly sense the enemies within range.
		int attackRange = rc.senseRobotInfo(rc.getRobot()).type.sensorRadiusSquared;
		Class<Robot> robotClass = Robot.class;
		Team enemyTeam = rc.getTeam().opponent();
		
		// Now we can actually find the enemies that are within range.
		Robot[] close = rc.senseNearbyGameObjects(robotClass, attackRange, enemyTeam);
		// Did we actually find anything?
		if (!close.equals(null))
		{
			return true;
		}
		return false;
	}
}
