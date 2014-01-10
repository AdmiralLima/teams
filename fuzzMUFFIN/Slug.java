package fuzzMUFFIN;

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
		
		// Next we find the direction we would really like to go.
		Direction desired = current.directionTo(goal);
		Direction tryDir = desired;
		
		// Lets find our the location we want to go.
		MapLocation want = current;
		MapLocation tryLoc = want;
		
		// Lets try moving in each direction.
		for (int i = 0; i < 8; i++)
		{
			
			// Rotate so we do not try the same direction twice.
			for (int j = 0; j < i; j++)
			{
				tryDir = desired.rotateLeft();
			}
			
			// Find the new location we might want to go to.
			tryLoc = want.add(tryDir);
			
			// No point doing extra computation if there is something in the way.
			if (rc.canMove(tryDir))
			{
				boolean shouldMove = true;
				
				// We do not want to go somewhere we have already been.
				MapLocation old;
				for (int k = 0; k < 5 && k < slug.size(); k++)
				{
					old = slug.get(k);
					if (old.equals(tryLoc) && !old.equals(slug.get(0)))
					{
						shouldMove = false;
					}
				}
				
				// We might actually get to move.
				if (shouldMove)
				{
					slug.add(current);
					rc.move(tryDir);
					break;
				}
			}
		}
	}
}
