package team005;

import battlecode.common.*;

/**
 * Contains methods related to executing attacks with a RobotController.
 */
public class Attack 
{
	
	// We need to store the robot that will be executing attacks.
	private RobotController rc;
	
	/**
	 * Sets the robot controller in order to execute future attacks.  
	 * 
	 * @param Robotcontroller - Takes the robot controller that will execute future attacks.
	 */
	public Attack(RobotController thisRC)
	{
		rc = thisRC;
	}

	/**
	 * Attempts to attack the given robot known to be in range.
	 * 
	 * @param Robot - Takes the robot to be attacked.
	 * @throws GameActionException
	 */
	public void attackRobot(Robot killThis) throws GameActionException
	{
		
		// First we need to know were the Robot is.
		MapLocation killThisLocation = rc.senseLocationOf(killThis);
		
		// Now we try to attack the location of the given robot.
		rc.attackSquare(killThisLocation);
	}

	/**
	* Attacks an enemy of the given type if any are within range.
	* 
	* @param RobotType - Takes the type of enemy we want to try to attack.
	* @return boolean - Returns true if we execute an attack, false otherwise.
	* @throws GameActionException
	*/
	public boolean attackType(RobotType killThisType) throws GameActionException
	{
			
		// First we need some information.
		int attackRange = rc.senseRobotInfo(rc.getRobot()).type.attackRadiusMaxSquared;
		Class<Robot> robotClass = Robot.class;
		Team enemyTeam = rc.getTeam().opponent();
		
		// Now we can actually find the enemies that are within range.
		Robot[] nearbyEnemies = rc.senseNearbyGameObjects(robotClass, attackRange, enemyTeam);
		
		// We need to check if we actually found any enemies.
		if (nearbyEnemies.length > 0) 
		{
			
			// This loop will only run until we grab an enemy of the desired type.
			for (Robot enemy : nearbyEnemies) 
			{
				
				// We need to know the enemy robot type.
				RobotType enemyType = rc.senseRobotInfo(enemy).type;
				
				// We now check if it is the desired type.
				if (enemyType.equals(killThisType)) 
				{
					
					// First we need to know were the robot is.
					MapLocation killThisLocation = rc.senseLocationOf(enemy);
					
					// Now we try to attack the location of the enemy.
					rc.attackSquare(killThisLocation);
					return true;
				}
			}
		}
		
		// If we did not find any enemies of the correct type we need to let the caller know.
		return false;
	}

	/**
	 * Attacks a random enemy in range.
	 * 
	 * @return boolean - Returns true 
	 * @throws GameActionException
	 */
	public boolean attackRandomRobot() throws GameActionException
	{
		
		// First we need some information.
		int attackRange = rc.senseRobotInfo(rc.getRobot()).type.attackRadiusMaxSquared;
		Class<Robot> robotClass = Robot.class;
		Team enemyTeam = rc.getTeam().opponent();
		
		// Now we can actually find the enemies that are within range.
		Robot[] nearbyEnemies = rc.senseNearbyGameObjects(robotClass, attackRange, enemyTeam);
		
		// Now we check if we actually found any nearby enemies.
		if (nearbyEnemies.length > 0)
		{
			
			// First we need to know were the robot is.
			MapLocation killThisLocation = rc.senseLocationOf(nearbyEnemies[0]);
			
			// Now we try to attack the location of the robot.
			rc.attackSquare(killThisLocation);
			return true;
		}
		
		// If we did not we need to let the caller know.
		return false;
	}
	
	/**
	 * Attacks a random nearby enemy but not the enemy HQ (prevents pointless attacking of enemy HQ).
	 * 
	 * @return boolean - Returns true if the robot attacks, false if it does not.
	 * @throws GameActionException
	 */
	public boolean attackRandomRobotNotEnemyHQ() throws GameActionException 
	{
		
		// First we need some information.
		int attackRange = rc.senseRobotInfo(rc.getRobot()).type.attackRadiusMaxSquared;
		Class<Robot> robotClass = Robot.class;
		Team enemyTeam = rc.getTeam().opponent();
		
		// Now we can actually find the enemies that are within range.
		Robot[] nearbyEnemies = rc.senseNearbyGameObjects(robotClass, attackRange, enemyTeam);
		
		// We need to check if we actually found any enemies.
		if (nearbyEnemies.length > 0) 
		{
			
			// This loop will only run until we grab an enemy that is not the enemy HQ.
			for (Robot enemy : nearbyEnemies) 
			{
				
				// We need to know the enemy robot type.
				RobotType enemyType = rc.senseRobotInfo(enemy).type;
				
				// We now check if it is the enemy HQ.
				if (!enemyType.equals(RobotType.HQ)) 
				{
					
					// First we need to know were the Robot is.
					MapLocation killThisLocation = rc.senseLocationOf(enemy);
					
					// Now we try to attack the location of the given robot.
					rc.attackSquare(killThisLocation);
					return true;
				}
			}
		}
		
		// If we cannot find anything to attack we need to let the caller know. 
		return false;
	}
	
	/**
	 * Attacks a random nearby enemy, trying to attack an enemy in the given priority order.
	 * This method will not attack the enemy HQ (preventing useless attacking).
	 * 
	 * @param RobotType - Takes the robot type we most want to attack.
	 * @param RoboyType - Takes our second preference of robot type to attack.
	 * @return boolean - Returns true if an attack was executed, false otherwise.
	 * @throws GameActionException
	 */
	public boolean attackWithPriority(RobotType firstChoice, RobotType secondChoice) throws GameActionException
	{
		
		// First we need some information.
		int attackRange = rc.senseRobotInfo(rc.getRobot()).type.attackRadiusMaxSquared;
		Class<Robot> robotClass = Robot.class;
		Team enemyTeam = rc.getTeam().opponent();
		
		// Now we can actually find the enemies that are within range.
		Robot[] nearbyEnemies = rc.senseNearbyGameObjects(robotClass, attackRange, enemyTeam);
		
		// We need to check if we actually found any enemies.
		if (nearbyEnemies.length > 0) 
		{
			
			// First we try to attack our first choice.
			for (Robot enemy : nearbyEnemies) 
			{
				// We need to know the enemy robot type.
				RobotType enemyType = rc.senseRobotInfo(enemy).type;
				
				// We now check if it is the desired type.
				if (enemyType.equals(firstChoice)) 
				{
					
					// First we need to know were the Robot is.
					MapLocation killThisLocation = rc.senseLocationOf(enemy);
					
					// Now we try to attack the location of the given robot.
					rc.attackSquare(killThisLocation);
					return true;
				}
			}
			
			// Now we need to try to attack our second choice.
			for (Robot enemy : nearbyEnemies) 
			{
				// We need to know the enemy robot type.
				RobotType enemyType = rc.senseRobotInfo(enemy).type;
				
				// We now check if it is the desired type.
				if (enemyType.equals(secondChoice)) 
				{
					
					// First we need to know were the Robot is.
					MapLocation killThisLocation = rc.senseLocationOf(enemy);
					
					// Now we try to attack the location of the given robot.
					rc.attackSquare(killThisLocation);
					return true;
				}
			}
			
			// At this point we just want to attack anything useful.
			for (Robot enemy : nearbyEnemies) 
			{
				// We need to know the enemy robot type.
				RobotType enemyType = rc.senseRobotInfo(enemy).type;
				
				// We now check if it is the desired type.
				if (!enemyType.equals(RobotType.HQ)) 
				{
					
					// First we need to know were the Robot is.
					MapLocation killThisLocation = rc.senseLocationOf(enemy);
					
					// Now we try to attack the location of the given robot.
					rc.attackSquare(killThisLocation);
					return true;
				}
			}
		}
		
		// If we did not find any enemies to attack we need to let the caller know.
		return false;
	}
}
