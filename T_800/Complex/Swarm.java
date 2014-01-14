package T_800.Complex;

import T_800.*;
import battlecode.common.*;

/**
 * Contains methods for simple swarming techniques.
 */
public class Swarm 
{
	private static RobotController rc = RobotPlayer.rc;
	
	public static boolean swarm(Robot[] friends) throws GameActionException
	{
		MapLocation[] friendLocation = new MapLocation[friends.length];
		for (int i = 0; 0 < friends.length; i++)
		{
			if (rc.canSenseObject(friends[i]))
			{
				friendLocation[i] = rc.senseRobotInfo(friends[i]).location;
			}
		}
		int xTotal = 0;
		int yTotal = 0;
		int counter = 0;
		for (MapLocation loc : friendLocation)
		{
			if (loc != null)
			{
				xTotal += loc.x;
				yTotal += loc.y;
				counter ++;
			}
		}
		if (counter == 0)
		{
			return false;
		}
		MapLocation desiredLoc = new MapLocation(xTotal/counter, yTotal/counter);
		Direction desiredDir = rc.getLocation().directionTo(desiredLoc);
		
		return false;
	}
}
