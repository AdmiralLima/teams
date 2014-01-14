package SkyNet;


import battlecode.common.*;


/**
 * Contains methods for the control of our SOLDIERs.
 */
public class SOLDIERControl 
{

	// We need to keep track of the SOLDIER we are controlling.
	private RobotController rc;
	
	// We need a swarm if we want to swarm.
	private Swarm swarmer;
	private Communicate comms;
	private Attack attacker;
	private Slug slugger;
	
	/**
	 * This constructor just takes the robot we are controlling.
	 * 
	 * @param RobotController - Takes the robot we will direct.
	 */
	public SOLDIERControl (RobotController thisRC)
	{
		rc = thisRC;
		comms = new Communicate(rc);
		attacker = new Attack(rc);
		slugger = new Slug(rc, 100);
		swarmer = new Swarm(rc, slugger, comms);
	}
	
	/**
	 * This method runs the SOLDIER.
	 * 
	 * @throws GameActionException
	 */
	public void runSOLDIER() throws GameActionException
	{
		
		// If we are hurt we should run away.
		if (rc.getHealth() < RobotType.SOLDIER.maxHealth*0.15)
		{
			
			// Lets do a little setup.
			int attackRange = rc.senseRobotInfo(rc.getRobot()).type.attackRadiusMaxSquared;
			Class<Robot> robotClass = Robot.class;
			Team enemyTeam = rc.getTeam().opponent();
			
			// Now we can actually find the enemies that are within range.
			Robot[] nearbyEnemies = rc.senseNearbyGameObjects(robotClass, attackRange, enemyTeam);
			
			// Now lets run the **** away before we die.
			if (nearbyEnemies.length > 0)
			{
				MapLocation badDude = rc.senseLocationOf(nearbyEnemies[0]);
				slugger.retreat(badDude, rc.getLocation());
				
				// Now lets bring the swarm in for the kill.
				comms.yellGoalLocation(badDude, 1);
			}
			
		}
		
		// If we can attack something we should.
		else if (!attacker.attackRandomRobotNotEnemyHQ())
		{
			
			// If we have somewhere important to go we should go there.
			MapLocation goHere = comms.listenGoalLocation(1);
			if (!(goHere == null))
			{
				slugger.slug(goHere);
			}
			else
			{
				swarmer.swarm(rc.senseHQLocation());
			}
			
			// We need to tell everyone where we are.
			comms.yellGoalLocation(rc.getLocation(), 2);
		}
	}
}
