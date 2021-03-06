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
		
		if (enemies.length == 0)
		{
		     // Maybe we should build a PASTR.
	            if (rc.senseCowGrowth()[rc.getLocation().x][rc.getLocation().y] > 0.9)
	            {
	                MapLocation[] PASTRs = rc.sensePastrLocations(rc.getTeam());
	                if (PASTRs.length < 2) {
	                    rc.construct(RobotType.PASTR);
	                    return true;
	                } else {
	                    return false;
	                }
	            }
		}
		
		// We need to know number of SOLDIER
		int enemyS = 0;
		int friendlyS = 1;
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
				if (T_800.Basic.Move.move(rc.getLocation().directionTo(rc.senseRobotInfo(enemies[0]).location).opposite()))
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
				if (enemies.length > 0)
				{
					if (T_800.Basic.Move.move(rc.getLocation().directionTo(rc.senseRobotInfo(enemies[0]).location)))
					{
						return true;
					}
				}
				return false;
			}

		}
		else
		{
			
			// Flight
			if (enemies.length > 0)
			{
				if (T_800.Basic.Move.move(rc.getLocation().directionTo(rc.senseRobotInfo(enemies[0]).location).opposite()))
				{
					return true;
				}
			}
		}
		return false;
	}
}
