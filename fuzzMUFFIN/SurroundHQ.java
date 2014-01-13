package fuzzMUFFIN;

import battlecode.common.*;

public class SurroundHQ 
{

	/**
	 * Contains methods relating to the tactics of surrounding the enemy HQ.
	 */
	
	// This will save us from having to pass our RobotController every time we use a method here
	private static RobotController rc = RobotPlayer.rc;
	private static MapLocation enemyHQ = RobotPlayer.enemyHQ;
	
	/**
	 * The robot will stay outside of the enemy HQ's range while staying close to it.
	 * 
	 * @throws GameActionException
	 */
	public static void surroundEnemyHQ() throws GameActionException
	{
		
		// We need to know where we are
		MapLocation myLoc = rc.getLocation();
		
		// We start by checking how far away the enemy HQ is.
		double toHQ = myLoc.distanceSquaredTo(enemyHQ);
		
		// If we are too close we need to move away!
		if (toHQ < RobotType.HQ.attackRadiusMaxSquared + 10)
		{
			Direction away = myLoc.directionTo(enemyHQ).opposite();
			Slug.slug(myLoc.add(away));
		}
		
		// If we are far away lets get closer!
		else if (toHQ > 35)
		{
			Slug.slug(enemyHQ);
		}
	}
}
