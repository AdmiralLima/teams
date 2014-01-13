package team005;

import battlecode.common.*;

/**
 * Contains methods governing the control of our HQ.
 */
public class HQControl 
{

	// We need to store our HQ so we do not have to pass it every time we use a method here.
	private RobotController rc;
	
	// We will need our various objects for controlling our HQ.
	private Attack attacker;
	private Spawn spawner;
	private Communicate communicator;
	
	/**
	 * This constructor simply takes the HQ that we will be controlling.
	 * 
	 * @param RobotController - Takes our HQ.
	 */
	public HQControl(RobotController thisRC)
	{
		rc = thisRC;
		
		// We need to set up the various actions we can take with our HQ.
		attacker = new Attack(rc);
		spawner = new Spawn(rc);
		communicator = new Communicate(rc);
	}
	
	/**
	 * This method will run whatever strategy we have in place for our HQ.
	 * 
	 * @throws GameActionException
	 */
	public void runHQ() throws GameActionException
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
			communicator.yellGoalLocation(rc.senseLocationOf(attacker[0]), 1);
			return;
		}
		
		// Lets find enemy PASTRs to kill.
		MapLocation[] killThese = rc.sensePastrLocations(rc.getTeam().opponent());
		
		// We need to give our soldiers a goal.
		if (killThese.length > 0)
		{
			communicator.yellGoalLocation(killThese[0], 1);
			return;
		}
		
		// Lets make sure we do not send the spawn too close to the enemy HQ.
		MapLocation currentGoal = communicator.listenGoalLocation(1);
		if (currentGoal != null)
		{
			if (currentGoal.distanceSquaredTo(rc.senseEnemyHQLocation()) <= RobotType.HQ.attackRadiusMaxSquared + 25)
			{
				communicator.yellGoalLocation(rc.getLocation(), 1);
			}
		}
	}
}
