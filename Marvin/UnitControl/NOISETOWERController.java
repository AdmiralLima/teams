package Marvin.UnitControl;

import Marvin.RobotPlayer;
import battlecode.common.*;
/**
 * This class contains methods for the control of a NOISETOWER unit.
 */
public class NOISETOWERController implements UnitController
{
	
	// This is really a recursive method so we need to set up our recursion.
	private RobotController rc = RobotPlayer.rc;
	private Direction direction = Direction.NORTH;
	private final int maxDistance = (int) Math.sqrt(RobotType.NOISETOWER.attackRadiusMaxSquared);
	private int currentDistance = maxDistance;
	
	/**
	 * This method executes the unit type's logic for one turn.
	 */
	public void run()
	{
		try
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
				return;
			}
			
			// Now we need to reset our herding.
			else 
			{
				currentDistance = maxDistance;
				direction = direction.rotateRight();
				return;
			}
		}
		catch (Exception e)
		{
			System.out.println("Caught NOISETOWER Exception.");
			e.printStackTrace();
		}
	}
}
