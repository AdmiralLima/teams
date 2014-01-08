package OperationScreamingFist.actions;

import OperationScreamingFist.RobotPlayer;
import battlecode.common.*;

public class Communication 
{
    private static RobotController rc = RobotPlayer.rc;
	
	/**
	 * broadcasts the robot's current location into the next empty channel
	 * 
	 */
	public static void updateLocation()
	{
		MapLocation loc = rc.getLocation();
	}
}
