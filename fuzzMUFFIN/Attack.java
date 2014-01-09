package fuzzMUFFIN;

import battlecode.common.*;

public class Attack 
{
	
	/**
	 * Contains methods related to attacking enemy units and cows.
	 */
	
	// This will save us from having to pass our RobotController every time we use a method here
	private static RobotController rc = RobotPlayer.rc;

	/**
	 * Attacks a random nearby enemy but not the enemy HQ (prevents pointless attacking of enemy HQ).
	 * 
	 * @return boolean - Returns True if the robot attacks, False if it does not.
	 * @throws GameActionException
	 */
	public static boolean attackRandomEnemyNotHQ() throws GameActionException 
	{
		
		// We first need to sense nearby enemies.
		Robot[] nearbyEnemies = Sense.senseEnemiesInRange();
		
		// We do not want to throw an exception, and we will if we check what type the enemy is
		// and there are not actually enemies, thus we must check if we found any enemies.
		if (nearbyEnemies.length > 0) 
		{
			
			// This loop will only run until we grab an enemy that is not the enemy HQ.
			for (Robot badGuy : nearbyEnemies) 
			{
				if (!Sense.senseIfIsHQ(badGuy)) 
				{
					
					// If we find an enemy that is no the enemy HQ we attack it.
					rc.attackSquare(Sense.senseRobotLocation(badGuy));
					return true;
				}
			}
		}
		
		// If we cannot find anything to attack we need to let the caller know. 
		return false;
	}
	
}
