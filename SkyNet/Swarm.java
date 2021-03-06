package SkyNet;

import battlecode.common.*;

public class Swarm 
{

	// We need to store our HQ so we do not have to pass it every time we use a method here.
	private RobotController rc;
	
	// We will need to navigate and communicate.
	private Slug slugger;
	private Communicate comms;
	
	public Swarm(RobotController thisRC, Slug thisSlugger, Communicate thisComms)
	{
		rc = thisRC;
		slugger = thisSlugger;
		comms = thisComms;
	}
	
	/**
	 * This method causes the robot to swarm towards its companions.
	 * 
	 * @throws GameActionException
	 */
	public void swarm(MapLocation contingency) throws GameActionException
	{
		
		// Lets figure out where we need to go.
		MapLocation goHere = comms.listenGoalLocation(2);
		
		// If we do not have anywhere to go lets go to our backup.
		if (goHere == null)
		{
			slugger.slug(contingency);
		}
		
		// If we can go towards another robot, lets do that.
		else
		{
			slugger.slug(goHere);
		}
		
		// Now we need to broadcast our location.
		comms.yellGoalLocation(rc.getLocation(), 2);
	}
}
