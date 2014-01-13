package team005;

import java.util.ArrayList;

import battlecode.common.*;

public class Slug 
{

	/**
	 * this class contains methods for Slug navigation (bug navigation
	 * with short-term memory).
	 */
	
	// This will save us from having to pass our RobotController every time we use a method here
	private static RobotController rc = RobotPlayer.rc;
	
	// We need access to our robot's slug memory.
	private static ArrayList<MapLocation> slug = RobotPlayer.slug;
	
	/**
	 * Robot will slug towards the given goal. 
	 * 
	 * @param MapLocation - Takes the goal location.
	 * @throws GameActionException
	 */
	public static void slug(MapLocation goal) throws GameActionException
	{
		// We start by getting our current location
		MapLocation current = rc.getLocation();
		
		// Lets get the direction of our goal.
		Direction goalDir = current.directionTo(goal);
		int goalIndex = Util.getDirectionIndex(goalDir);
		
		MapLocation possibleMove;
		boolean shouldMove;
		
		// Now we need to find a plausible direction to travel.
		for (int inc : Util.dLooks)
		{
			
			// We will look back and forth until we find somewhere we can move.
			goalDir = Util.directions[Math.abs(goalIndex + inc) % 8];
			possibleMove = current.add(goalDir);
			
			// If we cannot move there there is no point in looking.
			if (rc.canMove(goalDir))
			{
				shouldMove = true;
				
				// We need to make sure we do not get stuck.
				for (MapLocation old : slug)
				{
					if (old.equals(possibleMove))
					{
						shouldMove = false;
					}
				}
			
				// If we can move then we should.
				if (shouldMove && rc.canMove(goalDir))
				{
					
					// We need to update our slug.
					slug.add(current);
					
					// Now we can actually move.
					rc.move(goalDir);
					break;
				}
			}
		}
	}
}
