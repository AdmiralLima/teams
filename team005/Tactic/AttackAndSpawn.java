package team005.Tactic;

import battlecode.common.*;

/**
 * Contains methods for the tactics of having an HQ try to attack nearby enemy units and spawn SOLDIERS.
 */
public class AttackAndSpawn 
{
	
	/**
	 * Causes the HQ to try to attack or spawn.
	 * 
	 * @throws GameActionException
	 */
	public static void execute() throws GameActionException
	{
		// Try to attack nearby enemies.
		if (!team005.Complex.Attack.splashAttack())
		{		
			// Spawn a SOLDIER if we did not attack.
			team005.Complex.Spawn.spawn();
		}
	}
}
