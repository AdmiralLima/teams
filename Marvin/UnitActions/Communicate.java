package Marvin.UnitActions;

import Marvin.RobotPlayer;
import Marvin.Util;
import battlecode.common.*;

/**
 * This class contains methods for robots to interact with the team communication array.
 */
public class Communicate 
{
	private static RobotController rc = RobotPlayer.rc;
	
	/**
	 * This method broadcasts the given location to the given channel.
	 * 
	 * @param int - Takes the channel to broadcast to.
	 * @param MapLocation - Takes the location to broadcast.
	 * @throws GameActionException
	 */
	public static void writeLocation(int channel, MapLocation location) throws GameActionException
	{
		rc.broadcast(channel, location.x + Util.LOCATIONENCODER*location.y);
	}
	
	/**
	 * This method reads a location from the given channel.
	 * 
	 * @param int - Takes the channel to read from.
	 * @return MapLocation - Returns the location encoded in the channel.
	 * @throws GameActionException
	 */
	public static MapLocation readLocation(int channel) throws GameActionException
	{
		int encodedLocation = rc.readBroadcast(channel);
		return new MapLocation(encodedLocation % Util.LOCATIONENCODER, encodedLocation / Util.LOCATIONENCODER);
	}
	
	public static void resetCommunications() throws GameActionException
	{
		
		// Reset our PASTR denial channel.
		int reset = rc.readBroadcast(Util.KILLMECHANNEL);
		MapLocation[] good = rc.sensePastrLocations(rc.getTeam());
		if (reset != 0)
		{
			MapLocation goodGoal = Util.integerLocation(reset);
			boolean shouldReset = true;
			for (MapLocation existing : good)
			{
				if (existing.equals(goodGoal))
				{
					shouldReset = false;
				}
			}
			if (shouldReset)
			{
				rc.broadcast(Util.KILLMECHANNEL, 0);
			}
		}

		
		// Reset our enemy PASTR killing channel.
		reset = rc.readBroadcast(Util.KILLENEMYCHANNEL);
		MapLocation[] bad = rc.sensePastrLocations(rc.getTeam().opponent());
		boolean shouldResetThis = true;
		if (reset != 0)
		{
			MapLocation badGoal = Util.integerLocation(reset);
			for (MapLocation badGuy : bad)
			{
				if (badGuy.equals(badGoal))
				{
					shouldResetThis = false;
				}
			}
		}
		if (shouldResetThis && bad.length > 0)
		{
			rc.broadcast(Util.KILLENEMYCHANNEL, Util.locationInteger(bad[0]));
			return;
		}
		if (shouldResetThis)
		{
			rc.broadcast(Util.KILLENEMYCHANNEL, 0);
		}
	}
}
