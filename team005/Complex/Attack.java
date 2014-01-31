package team005.Complex;

import team005.*;
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
					if (team005.Basic.Attack.attackLocation(enemyInfo.location))
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
					if (team005.Basic.Attack.attackLocation(enemyInfo.location))
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
					if (team005.Basic.Attack.attackLocation(enemyInfo.location))
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
				return team005.Basic.Attack.attackLocation(weakestLocation);
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
					return team005.Basic.Attack.attackLocation(enemyInfo.location);
				}
			}
		}
		return false;
	}
	
	/**
	 * This method will attack a random enemy within range.  If there are not enemies within 
	 * range it will attempt to deal splash damage to units just outside of range.  FOR USE
	 * BY HQ ONLY.
	 * 
	 * @return boolean - Returns true if a successful attack was made.
	 * @throws GameActionException
	 */
	public static boolean splashAttack() throws GameActionException
	{
		Robot[] enemies = rc.senseNearbyGameObjects(Robot.class, RobotType.HQ.sensorRadiusSquared, rc.getTeam().opponent());
		if (enemies.length > 0)
		{
			MapLocation rcLocation = rc.getLocation();
			MapLocation enemyLocation;
			
			// First we try to attack enemies within range.
			for (Robot enemy : enemies)
			{
				enemyLocation = rc.senseRobotInfo(enemy).location;
				if (rcLocation.distanceSquaredTo(enemyLocation) < RobotType.HQ.attackRadiusMaxSquared)
				{
					if (rc.canAttackSquare(enemyLocation))
					{
						rc.attackSquare(enemyLocation);
						return true;
					}
				}
			}
			MapLocation splashLocation;
			
			// Next we try to deal splash damage.
			for (Robot enemy : enemies)
			{
				enemyLocation = rc.senseRobotInfo(enemy).location;
				splashLocation = enemyLocation.subtract(rcLocation.directionTo(enemyLocation));
				if (rcLocation.distanceSquaredTo(splashLocation) < RobotType.HQ.attackRadiusMaxSquared)
				{
					if (rc.canAttackSquare(splashLocation))
					{
						rc.attackSquare(splashLocation);
						return true;
					}
				}
			}
		}
		return false;
	}
}
