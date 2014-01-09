package OperationScreamingFist.actions;

import java.util.Random;

import OperationScreamingFist.RobotPlayer;
import battlecode.common.*;

public class Bugging {

	private static RobotController rc = RobotPlayer.rc;
	private static MapLocation[] visited = RobotPlayer.visited;
    public static Random rand = new Random();

	public static Direction Bug(MapLocation Goal) throws GameActionException {
						
		// are we close enough to our goal?
		if (rc.getLocation().distanceSquaredTo(Goal) <= rc.senseRobotInfo(rc.getRobot()).type.attackRadiusMaxSquared)
		{
			return null;
		}
		
		Direction goalDirection = rc.getLocation().directionTo(Goal);
		
		// if not can we move towards our goal?
		if (rc.canMove(goalDirection))
		{
			boolean shouldMove = true;
			for (MapLocation prev : visited)
			{
				if (prev.equals(rc.getLocation().add(goalDirection)))
				{
					shouldMove = false;
				}
			}
			if (shouldMove) {
				return goalDirection;
			}
		}
		
		// if we cannot move towards our goal we will try to move to the right
		int dirCounter = 0;
		for (int i = 0; i < 8; i++) {
			if (Util.directions[i].equals(goalDirection)) {
				dirCounter = i;
			}
		}
		for (int j = 1; j < 8; j++) {
			if (rc.canMove(Util.directions[j]))
			{
				boolean shouldMove = true;
				for (MapLocation prev : visited)
				{
					if (prev.equals(rc.getLocation().add(Util.directions[j])))
					{
						shouldMove = false;
					}
				}
				if (shouldMove) {
					return Util.directions[j];
				}
			}
		}

		return null;
	}
}
