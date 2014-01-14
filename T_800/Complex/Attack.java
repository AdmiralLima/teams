package T_800.Complex;

import T_800.RobotPlayer;
import battlecode.common.*;

public class Attack 
{
	private static RobotController rc = RobotPlayer.rc;
	
	public static boolean attackWithPriority(RobotType firstChoice, RobotType secondChoice) throws GameActionException
	{
		Robot[] nearbyEnemies = rc.senseNearbyGameObjects(Robot.class, rc.getType().sensorRadiusSquared, rc.getTeam().opponent());
		
		// First we try to attack our first choice type.
		for (Robot badGuy : nearbyEnemies)
		{
			if (rc.senseRobotInfo(badGuy).type == firstChoice)
			{
				if (T_800.Basic.Attack.attackLocation(rc.senseRobotInfo(badGuy).location))
				{
					return true;
				}
			}
		}
		
		// Next we try to attack our third choice type.
		for (Robot badGuy : nearbyEnemies)
		{
			if (rc.senseRobotInfo(badGuy).type == secondChoice)
			{
				if (T_800.Basic.Attack.attackLocation(rc.senseRobotInfo(badGuy).location))
				{
					return true;
				}
			}
		}
		
		// Or we just attack anything.
		for (Robot badGuy : nearbyEnemies)
		{
			if (rc.senseRobotInfo(badGuy).type != RobotType.HQ)
			{
				if (T_800.Basic.Attack.attackLocation(rc.senseRobotInfo(badGuy).location))
				{
					return true;
				}
			}
		}
		
		// If we cannot attack anything we need to let the caller know.
		return false;
	}
}
