package Marvin.UnitActions;

import Marvin.RobotPlayer;
import battlecode.common.*;

/**
 * This class contains methods for executing robot attacks.
 */
public class Attack 
{
	private static RobotController rc = RobotPlayer.rc;

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
	
	/**
	 * Attacks the nearby enemy with the least health.
	 * 
	 * @return boolean - Returns true if a successful attack was executed, false otherwise.
	 * @throws GameActionException
	 */
	public static boolean attackWeakestEnemy() throws GameActionException
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
				if (rc.canAttackSquare(weakestLocation))
				{
					rc.attackSquare(weakestLocation);
				}
			}
		}
		return false;
	}
	
	public static boolean suicide(MapLocation killThis) throws GameActionException
	{
		if (rc.getLocation().distanceSquaredTo(killThis) < 3)
		{
			rc.selfDestruct();
			return true;
		}
		
		return false;
	}
}
