package team005.Complex;

import battlecode.common.*;
/**
 * this class contains methods for Slug navigation (bug navigation
 * with short-term memory).
 */
public class Slug 
{

	// This will save us from having to pass our RobotController every time we use a method here
	private RobotController rc;
	
	// We need access to our robot's slug memory.
	private SlugMemory memory;
	
	// We need some way to order our search for valid movement.
	private static final int[] directionalLook = {0, 1, -1, -2, 2, -3, 3, 4};
	
	/**
	 * Creates a new slug.
	 * 
	 * @param RobotController - Takes the robot the slug will navigate for.
	 * @param int - Takes the length of the slug's memory
	 */
	public Slug(RobotController thisRC, int memoryLength)
	{
		rc = thisRC;
		memory = new SlugMemory(memoryLength);
	}
	
	/**
	 * Robot will slug towards the given location. 
	 * 
	 * @param MapLocation - Takes the goal location.
	 * @throws GameActionException
	 */
	public void slug(MapLocation goal) throws GameActionException
	{
		// We start by getting our current location
		MapLocation currentLocation = rc.getLocation();
		
		// Lets get the direction of our goal.
		Direction goalDirection = currentLocation.directionTo(goal);
		
		// If we are on our goal we do not need to move.
		if (!goalDirection.equals(Direction.OMNI))
		{
			Direction possibleDirection;
		
			// Now we need to find a plausible direction to travel.
			for (int inc : directionalLook)
			{
				possibleDirection = goalDirection;
			
				// We will look back and forth until we find somewhere we can move.
				if (inc < 0)
				{
					for (int i = 0; i > inc; i--)
					{
						possibleDirection = possibleDirection.rotateLeft();
					}
				}
				if (inc > 0)
				{
					for (int i = 0; i < inc; i++)
				{
					possibleDirection = possibleDirection.rotateRight();
				}
			}
			
				// If we cannot move there there is no point in looking.
				if (rc.canMove(possibleDirection))
				{
					if (!memory.visited(currentLocation.add(possibleDirection)))
					{						
						memory.remember(currentLocation);
						rc.move(possibleDirection);
						return;
					}
				}
			}
		}
		memory.clear();
	}
	
	/**
	 * The robot slugs away from the given location.
	 * 
	 * @param current
	 */
	public void retreat(MapLocation enemy, MapLocation current) throws GameActionException
	{
		Direction flee = enemy.directionTo(current);
		memory.clear();
		slug(current.add(flee));
	}
}
