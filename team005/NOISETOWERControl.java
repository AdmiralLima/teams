package team005;

import battlecode.common.*;

/**
 * Contains methods for the control of our NOISETOWERs.
 */
public class NOISETOWERControl 
{
	
	// We need to store our NOISETOWER so we do not have to pass it every time we use a method here.
	private RobotController rc;
	
	// We need some way to accomplish our herding.
	private int maxDistance;
	private int currentDistance;
	private Direction direction;
	
	/**
	 * This constructor simply takes the NOISETOWER we are controlling.
	 */
	public NOISETOWERControl (RobotController thisRC)
	{
		rc = thisRC;
		maxDistance = (int) Math.sqrt(RobotType.NOISETOWER.attackRadiusMaxSquared);
		currentDistance = maxDistance;
		direction = Direction.NORTH_EAST;
	}
	
	public void runNOISETOWER() throws GameActionException
	{
		MapLocation attackThis = rc.getLocation().add(direction, currentDistance);

		// How do we herd?
		if (currentDistance > 1)
		{
			if (rc.canAttackSquare(attackThis))
			{
				rc.attackSquare(attackThis);
			}
			currentDistance = currentDistance - 2;
			rc.yield();
			this.runNOISETOWER();
		}
		
		// Now we need to reset our herding.
		else 
		{
			currentDistance = maxDistance;
			direction = direction.rotateRight();
			rc.yield();
			this.runNOISETOWER();
		}
	}
}
