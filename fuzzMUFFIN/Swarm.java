package fuzzMUFFIN;

import battlecode.common.*;

public class Swarm 
{
	/**
	 *	 Contains methods relating to the control of swarm behavior.
	 */
	
	// This will save us from having to pass our RobotController every time we use a method here
	private static RobotController rc = RobotPlayer.rc;
	
	/**
	 * Causes the robot to swarm.
	 * 
	 * @throws GameActionException
	 */
	public static void swarm() throws GameActionException
	{
		
		// First we listen for the relevant communications.
		MapLocation goal = Communicate.listenGoalLocation(1);
		MapLocation friend = Communicate.listenGoalLocation(2);
				
		int decision = Util.rand.nextInt(100) + 1;
		
		if (decision < 60)
		{
			Slug.slug(goal);
		}
		else
		{
			Slug.slug(friend);
		}
		
		// If we are having an emergency we want the swarm to save us.
		if (rc.getHealth() < RobotType.SOLDIER.maxHealth*0.75)
		{
			Communicate.yellPosition(3);
		}
		
		// Now we need to tell the swarm where we are.
		Communicate.yellPosition(2);
	}
}
