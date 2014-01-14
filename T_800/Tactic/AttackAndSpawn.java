package T_800.Tactic;

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
	    int bc = Clock.getBytecodeNum();
		if (!T_800.Complex.Attack.attackRandomNotHQ())
		{
		    //System.out.println("Attack.attackRandomNotHQ used " + (Clock.getBytecodeNum() - bc) + " bc");
			
			// Spawn a SOLDIER if we did not attack.
		    bc = Clock.getBytecodeNum();
			T_800.Complex.Spawn.spawn();
			//System.out.println("Spawn.spawn used " + (Clock.getBytecodeNum() - bc) + " bc");
		}
	}
}
