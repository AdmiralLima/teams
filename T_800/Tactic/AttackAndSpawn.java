package T_800.Tactic;

import T_800.RobotPlayer;
import battlecode.common.*;

/**
 * Contains methods for the tactics of having an HQ try to attack nearby enemy units and spawn SOLDIERS.
 */
public class AttackAndSpawn 
{
	private RobotController rc = RobotPlayer.rc;
	
	/**
	 * Causes the HQ to try to attack or spawn.
	 * 
	 * @throws GameActionException
	 */
	public static void execute() throws GameActionException
	{
		
		// Try to attack nearby enemies.
		if (!T_800.Complex.Attack.AttackWithPriority(RobotType.SOLDIER, RobotType.NOISETOWER))
		{
			
			// If we did not attack anything we should spawn.
			// TODO: this doesn't spawn yet.
		}
	}
}
