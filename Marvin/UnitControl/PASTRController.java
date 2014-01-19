package Marvin.UnitControl;

import Marvin.RobotPlayer;
import Marvin.Util;
import Marvin.UnitActions.Communicate;
import battlecode.common.RobotController;
import battlecode.common.RobotType;

/**
 * This class contains methods for the control of a PASTR unit.
 */
public class PASTRController implements UnitController
{
	private RobotController rc = RobotPlayer.rc;
	
	/**
	 * This method executes one turn of the unit type's logic.
	 */
	public void run()
	{
		try
		{
			
			// If we are under attack we need to let people know.
			if (rc.getHealth() < RobotType.PASTR.maxHealth)
			{
				Communicate.writeLocation(Util.KILLMECHANNEL, rc.getLocation());
			}
		}
		catch (Exception e)
		{
			System.out.println("Caught PASTR Exception.");
			e.printStackTrace();
		}
	}
}
