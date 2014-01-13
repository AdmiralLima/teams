package SkyNet.UnitControl;

import SkyNet.SimpleActions.*;
import battlecode.common.*;

/**
 * Contains methods governing the control of our HQ.
 */
public class ControllerHQ implements Controller
{
	private RobotController rc;
		private Attack attacker;
	private Spawn spawner;
	private Communicator comms;
	
	/**
	 * This constructor simply takes the HQ that we will be controlling.
	 * 
	 * @param RobotController - Takes our HQ.
	 */
	public ControllerHQ(RobotController thisRC)
	{
		rc = thisRC;
		
		// We need to set up the various actions we can take with our HQ.
		attacker = new Attack(rc);
		spawner = new Spawn(rc);
		comms = new Communicator(rc, 1, 2);
	}
	
	/**
	 * This method will run whatever strategy we have in place for our HQ.
	 */
	public void run()
	{
		try
		{
			if (rc.senseRobotCount() < 1)
			{
				spawner.spawnDirection(rc.getLocation().directionTo(rc.senseEnemyHQLocation()).opposite());
				return;
			}
			if (rc.senseRobotCount() < 2)
			{
				spawner.spawnDirection(rc.getLocation().directionTo(rc.senseEnemyHQLocation()).opposite().rotateRight());
				return;
			}
		
			// First lets attack anything if possible.
			if (!attacker.attackRandomRobotNotEnemyHQ())
			{
				spawner.spawn();
			}
		
			// Protect the HQ!
			Robot[] attacker = rc.senseNearbyGameObjects(Robot.class, RobotType.HQ.sensorRadiusSquared, rc.getTeam().opponent());
			if (attacker.length > 0)
			{
				comms.yellGoalLocation(rc.senseLocationOf(attacker[0]));
				return;
			}
		
			// Lets find enemy PASTRs to kill.
			MapLocation[] killThese = rc.sensePastrLocations(rc.getTeam().opponent());
		
			// We need to give our soldiers a goal.
			if (killThese.length > 0)
			{
				comms.yellGoalLocation(killThese[0]);
				return;
			}
		
			// Lets make sure we do not send the spawn too close to the enemy HQ.
			MapLocation currentGoal = comms.listenGoalLocation();
			if (currentGoal != null)
			{
				if (currentGoal.distanceSquaredTo(rc.senseEnemyHQLocation()) <= RobotType.HQ.attackRadiusMaxSquared + 25)
				{
					comms.yellGoalLocation(rc.getLocation());
				}
			}
		}
		catch (Exception e)
		{
			System.out.println("HQ Controller Exception.");
			e.printStackTrace();
		}
	}
}