package T_800.Tactic;

import T_800.RobotPlayer;
import battlecode.common.*;
/**
 * This class contains the tactics of having a soldier retreat when in danger.
 */
public class SaveYourself 
{
	private RobotController rc = RobotPlayer.rc;
	
	public static void execute() throws GameActionException
	{
		if (T_800.Basic.Retreat.shouldRetreat())
		{
			//TODO: make the robot call for help and retreat
		}
		else if (T_800.Basic.Retreat.shouldCallHelp())
		{
			//TODO: make the robot retreat
		}
	}
}
