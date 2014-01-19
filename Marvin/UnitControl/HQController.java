package Marvin.UnitControl;

import Marvin.RobotPlayer;
import Marvin.UnitActions.*;
import battlecode.common.*;

/**
 * This class contains methods for the control of an HQ unit.
 */
public class HQController implements UnitController
{
	private RobotController rc = RobotPlayer.rc;
	
	/**
	 * The method executes the unit type's logic for one round.
	 */
	public void run()
	{
		try
		{
			
			// First we update our communications.
			Communicate.resetCommunications();

			// Next we try to attack.
			if (Attack.splashAttack())
			{
				return;
			}
			
			// If we cannot attack, we try to spawn.
			if (Spawn.spawnDirectional(rc.getLocation().directionTo(rc.senseEnemyHQLocation())))
			{
				return;
			}
		}
		catch (Exception e)
		{
			System.out.println("Caught HQ Exception.");
			e.printStackTrace();
		}
	}
}
