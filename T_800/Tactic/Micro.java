package T_800.Tactic;

import battlecode.common.*;
import T_800.RobotPlayer;

public class Micro 
{
	private static RobotController rc = RobotPlayer.rc;
	
	public static boolean execute() throws GameActionException
	{

		// Find friends and enemies
		Robot[] enemies = rc.senseNearbyGameObjects(Robot.class, RobotType.SOLDIER.sensorRadiusSquared, rc.getTeam().opponent());
		Robot[] friends = rc.senseNearbyGameObjects(Robot.class, RobotType.SOLDIER.sensorRadiusSquared, rc.getTeam());
		
		// We need to know number of SOLDIER
		int enemyS = 0;
		int friendlyS = 0;
		for (Robot enemy : enemies)
		{
			if (rc.senseRobotInfo(enemy).type == RobotType.SOLDIER)
			{
				enemyS ++;
			}
		}
		for (Robot friend : friends)
		{
			if (rc.senseRobotInfo(friend).type == RobotType.SOLDIER)
			{
				friendlyS ++;
			}
		}
		
		// Run away if injured
		if (rc.getHealth() < RobotType.SOLDIER.maxHealth*0.25)
		{
			if (enemies.length > 0)
			{
				if (T_800.Basic.Move.move(rc.senseRobotInfo(enemies[0]).direction.opposite()))
				{
					return true;
				}
			}
		}
		
		// Fight or flight
		if (friendlyS >= enemyS)
		{
			
			// Fight
			if (!T_800.Complex.Attack.attackWeakest())
			{
				return false;
			}
			return true;
		}
		else
		{
			
			// Flight
			if (T_800.Basic.Move.move(rc.senseRobotInfo(enemies[0]).direction.opposite()))
			{
				return true;
			}
		}
		return false;
	}
}
