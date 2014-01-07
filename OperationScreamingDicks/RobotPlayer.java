package OperationScreamingDicks;

import battlecode.common.*;
import java.util.*;

public class RobotPlayer 
{
	public static void run (RobotController rc)
	{
		Direction[] directions = {Direction.NORTH, Direction.NORTH_EAST, Direction.EAST, Direction.SOUTH_EAST, Direction.SOUTH, Direction.SOUTH_WEST, Direction.WEST, Direction.NORTH_WEST};
		while (true)
		{
			if (rc.isActive())
			{
					if (rc.getType() == RobotType.HQ) 
					{
						try 
						{
							Robot[] nearbyEnemies = rc.senseNearbyGameObjects(Robot.class,35);
							if (nearbyEnemies.length > 0) 
							{
								rc.attackSquare(rc.senseRobotInfo(nearbyEnemies[0]).location);
							}
							else if (rc.senseRobotCount() < 25) 
							{
								for (int i = 0; i < 8; i++)
								{
									if (rc.senseObjectAtLocation(rc.getLocation().add(directions[i])) == null) 
									{
										rc.spawn(directions[i]);
										break;
									}
								}
							}
						} catch (Exception e)
						{
							System.out.println("HQ Exception");
						}
					}
			}
			rc.yield();
		}
	}
}