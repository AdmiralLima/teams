package Marvin.PathFinding;

import Marvin.RobotPlayer;
import battlecode.common.*;
/**
 * this class contains methods for Slug navigation (bug navigation
 * with short-term memory).
 */
public class Slug 
{

	// This will save us from having to pass our RobotController every time we use a method here
	private RobotController rc = RobotPlayer.rc;
	
	// We need access to our robot's slug memory.
	private SlugMemory memory;
	
	
	/**
	 * Creates a new slug.
	 * 
	 * @param RobotController - Takes the robot the slug will navigate for.
	 * @param int - Takes the length of the slug's memory
	 */
	public Slug(int memoryLength)
	{
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
		if (!goalDirection.equals(Direction.OMNI) && rc.isActive())
		{		
            if (rc.canMove(goalDirection) && !memory.visited(currentLocation.add(goalDirection))) 
            {
                memory.remember(currentLocation.add(goalDirection));
                rc.move(goalDirection);
                return;
            }
            goalDirection = goalDirection.rotateRight();
            if (rc.canMove(goalDirection) && !memory.visited(currentLocation.add(goalDirection))) 
            {
                memory.remember(currentLocation.add(goalDirection));
                rc.move(goalDirection);
                return;
            }
            goalDirection = goalDirection.rotateLeft().rotateLeft();
            if (rc.canMove(goalDirection) && !memory.visited(currentLocation.add(goalDirection))) 
            {                
            	memory.remember(currentLocation.add(goalDirection));
                rc.move(goalDirection);
                return;
            }
            goalDirection = goalDirection.rotateRight().rotateRight().rotateRight();
            if (rc.canMove(goalDirection) && !memory.visited(currentLocation.add(goalDirection))) 
            {
                memory.remember(currentLocation.add(goalDirection));
                rc.move(goalDirection);
                return;
            }
            goalDirection = goalDirection.rotateLeft().rotateLeft().rotateLeft().rotateLeft();
            if (rc.canMove(goalDirection) && !memory.visited(currentLocation.add(goalDirection))) 
            {
                memory.remember(currentLocation.add(goalDirection));
                rc.move(goalDirection);
                return;
            }
            goalDirection = goalDirection.rotateRight().rotateRight().rotateRight().rotateRight().rotateRight();
            if (rc.canMove(goalDirection) && !memory.visited(currentLocation.add(goalDirection))) 
            {
                memory.remember(currentLocation.add(goalDirection));
                rc.move(goalDirection);
                return;
            }
            goalDirection = goalDirection.rotateLeft().rotateLeft().rotateLeft().rotateLeft().rotateLeft().rotateLeft();
            if (rc.canMove(goalDirection) && !memory.visited(currentLocation.add(goalDirection))) 
            {
                memory.remember(currentLocation.add(goalDirection));
                rc.move(goalDirection);
                return;
            }
            goalDirection = goalDirection.rotateLeft();
            if (rc.canMove(goalDirection) && !memory.visited(currentLocation.add(goalDirection))) 
            {
                memory.remember(currentLocation.add(goalDirection));
                rc.move(goalDirection);
                return;
            }
		}
		memory.clear();
	}
	
	public void newGoal()
	{
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
