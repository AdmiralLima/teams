package T_800.Complex;

import T_800.RobotPlayer;
import battlecode.common.*;

/**
 * Contains methods for cleverly retreating.
 */
public class Retreat 
{
	private static RobotController rc = RobotPlayer.rc;

	public static boolean broadCastHurtSOLDIER() throws GameActionException
	{
		if (rc.getHealth() < RobotType.SOLDIER.maxHealth*0.8)
		{
			
		}
		return false;
	}
}
