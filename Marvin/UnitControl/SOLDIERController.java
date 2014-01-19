package Marvin.UnitControl;

import Marvin.RobotPlayer;
import Marvin.Util;
import Marvin.PathFinding.Slug;
import battlecode.common.Robot;
import battlecode.common.RobotController;
import battlecode.common.RobotType;

/**
 * This class contains methods for the control of a SOLDIER unit.
 */
public class SOLDIERController implements UnitController
{
	private RobotController rc = RobotPlayer.rc;
	private Slug slugger= new Slug(200);
	private int oldGoal = 0;
	
	/**
	 * This method executes one turn of logic for the given unit type.
	 */
	public void run()
	{
		try
		{
			
			// We should try to save ourselves if possible.
			if (rc.getHealth() < RobotType.SOLDIER.maxHealth*0.3)
			{
				
				// Lets run away.
				Robot[] badGuys = rc.senseNearbyGameObjects(Robot.class, RobotType.SOLDIER.attackRadiusMaxSquared, rc.getTeam().opponent());
				if (badGuys.length > 0)
				{
					slugger.newGoal();
					slugger.retreat(rc.senseRobotInfo(badGuys[0]).location, rc.getLocation());
					return;
				}
			}
			
			// Maybe we can our friends to help us.
			if (rc.getHealth() < RobotType.SOLDIER.maxHealth*0.8)
			{
				
				Marvin.UnitActions.Communicate.writeLocation(Util.SAVEMECHANNEL, rc.getLocation());
			}
			
			// Next lets see if we can attack anything.
			if (Marvin.UnitActions.Attack.attackWeakestEnemy())
			{
				return;
			}
			
			 // First lets get our updated communications.
			int friendlyGoal = rc.readBroadcast(Util.KILLMECHANNEL);
			int enemyGoal = rc.readBroadcast(Util.KILLENEMYCHANNEL);
			int save = rc.readBroadcast(Util.SAVEMECHANNEL);
			int newGoal = 0;
			
			// Now we need to figure out what to do.
			if (friendlyGoal != 0)
			{
				newGoal = friendlyGoal;
				if (newGoal != oldGoal)
				{
					slugger.newGoal();
				}
				slugger.slug(Util.integerLocation(newGoal));
				oldGoal = newGoal;
				return;
			}
			else if (enemyGoal != 0)
			{
				newGoal = enemyGoal;
				if (newGoal != oldGoal)
				{
					slugger.newGoal();
				}
				slugger.slug(Util.integerLocation(newGoal));
				oldGoal = newGoal;
				return;
			}
			else if (save != 0)
			{
				newGoal = save;
				if (newGoal != oldGoal)
				{
					slugger.newGoal();
				}
				slugger.slug(Util.integerLocation(newGoal));
				oldGoal = newGoal;
				return;
			}
			slugger.slug(rc.senseEnemyHQLocation());
		}
		catch (Exception e)
		{
			System.out.println("Caught SOLDIER Exception");
			e.printStackTrace();
		}
	}
}
