package T_800.Complex;

import T_800.*;
import battlecode.common.*;

/**
 * Contains well thought out methods for robot attacking.
 */
public class Attack 
{
	private static RobotController rc = RobotPlayer.rc;
	
	/**
	 * Attacks a nearby enemy taking into account attack preferences.
	 * 
	 * @param RobotType - Takes the robot type you most want to attack.
	 * @param RobotType - Takes the robot type you would be OK with attacking.
	 * @return boolean - Returns true if a successful attack was executed, false otherwise.
	 * @throws GameActionException
	 */
	public static boolean attackWithPriority(RobotType firstChoice, RobotType secondChoice) throws GameActionException
	{
		Robot[] nearbyEnemies = rc.senseNearbyGameObjects(Robot.class, rc.getType().attackRadiusMaxSquared, rc.getTeam().opponent());
		if (nearbyEnemies.length > 0)
		{
			RobotInfo enemyInfo;
			
			// First we try to attack our first choice type.
			for (Robot badGuy : nearbyEnemies)
			{
				enemyInfo = rc.senseRobotInfo(badGuy);
				if (enemyInfo.type == firstChoice)
				{
					if (T_800.Basic.Attack.attackLocation(enemyInfo.location))
					{
						return true;
					}
				}
			}
			
			// Next we try to attack our third choice type.
			for (Robot badGuy : nearbyEnemies)
			{
				enemyInfo = rc.senseRobotInfo(badGuy);
				if (enemyInfo.type == secondChoice)
				{
					if (T_800.Basic.Attack.attackLocation(enemyInfo.location))
					{
						return true;
					}
				}
			}
			
			// Or we just attack anything.
			for (Robot badGuy : nearbyEnemies)
			{
				enemyInfo = rc.senseRobotInfo(badGuy);
				if (enemyInfo.type != RobotType.HQ)
				{
					if (T_800.Basic.Attack.attackLocation(enemyInfo.location))
					{
						return true;
					}
				}
			}
		}
		
		// If we cannot attack anything we need to let the caller know.
		return false;
	}
	
	/**
	 * Attacks the nearby enemy with the least health.
	 * 
	 * @return boolean - Returns true if a successful attack was executed, false otherwise.
	 * @throws GameActionException
	 */
	public static boolean attackWeakest() throws GameActionException
	{
		Robot[] nearbyEnemies = rc.senseNearbyGameObjects(Robot.class, rc.getType().attackRadiusMaxSquared, rc.getTeam().opponent());

		if (nearbyEnemies.length > 0)
		{
			// We need to find the weakest enemy.
			MapLocation weakestLocation = null;
			RobotInfo enemyInfo;
			double minHealthFraction = 1.1;
			double healthFraction;
			for (Robot possible : nearbyEnemies)
			{
				enemyInfo = rc.senseRobotInfo(possible);
				healthFraction = enemyInfo.health / enemyInfo.type.maxHealth;
				if (healthFraction < minHealthFraction && enemyInfo.type != RobotType.HQ)
				{
					minHealthFraction = healthFraction;
					weakestLocation = enemyInfo.location;
				}
			}
		
			// If we found one lets try to kill it.
			if (minHealthFraction < 1.1)
			{
				return T_800.Basic.Attack.attackLocation(weakestLocation);
			}
		}
		return false;
	}
	
	/**
	 * Attack a random enemy but not the enemy HQ.
	 * 
	 * @return boolean - Returns true if a successful attack was executed, false otherwise.
	 * @throws GameActionException
	 */
	public static boolean attackRandomNotHQ() throws GameActionException
	{
		Robot[] nearbyEnemies = rc.senseNearbyGameObjects(Robot.class, rc.getType().attackRadiusMaxSquared, rc.getTeam().opponent());
		
		// We only want to try to attack if we found enemies.
		if (nearbyEnemies.length > 0)
		{
			RobotInfo enemyInfo;
			for (Robot badGuy : nearbyEnemies)
			{
				enemyInfo = rc.senseRobotInfo(badGuy);
				if (enemyInfo.type != RobotType.HQ)
				{
					return T_800.Basic.Attack.attackLocation(enemyInfo.location);
				}
			}
		}
		return false;
	}
}
