package SkyNet.UnitControl;

import SkyNet.SimpleActions.Communicator;
import battlecode.common.*;

/**
 * Contains methods for the control of a NOISETOWER unit.
 */
public class ControllerNOISETOWER implements Controller
{	
	private RobotController rc;
	private Communicator comms;
	private int maxDistance;
	private int currentDistance;
	private Direction direction;
	
	/**
	 * This constructor simply takes the NOISETOWER to be controlled.
	 * 
	 * @param RobotController - Takes the unit to be controlled.
	 */
	public ControllerNOISETOWER (RobotController thisRC)
	{
		rc = thisRC;
		comms = new Communicator(thisRC, 1, 2);
		maxDistance = (int) Math.sqrt(RobotType.NOISETOWER.attackRadiusMaxSquared);
		currentDistance = maxDistance;
		direction = Direction.NORTH_EAST;
	}
	
	/**
	 * This method executed one round of logic for a NOISETOWER.
	 */
	public void run()
	{
		try
		{
			
			// Calls for help if under attack.
			if (rc.getHealth() <= RobotType.NOISETOWER.maxHealth)
			{
				comms.yellHelpLocation(rc.getLocation());
			}
			MapLocation attackThis = rc.getLocation().add(direction, currentDistance);
		
			// Executes noise attacks getting closer to the tower.
			if (currentDistance > 1)
			{
				if (rc.canAttackSquare(attackThis))
				{
					rc.attackSquare(attackThis);
				}
				currentDistance = currentDistance - 2;
			}
		
			// The execution must be reset to a new direction.
			else 
			{
				currentDistance = maxDistance;
				direction = direction.rotateRight();
			}
		}
		catch (Exception e)
		{
			System.out.println("NOISETOWER Controller Error.");
			e.printStackTrace();
		}
	}
}