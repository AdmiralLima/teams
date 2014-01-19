package T_800;

import java.util.Random;

import battlecode.common.*;

public class RRT 
{
	private int acceptableGoalRadius = 16; 
	
	public static MapLocation[] getNextStep(MapLocation start, MapLocation goal)
	{
		Random rand = new Random();
		
		int hight = T_800.RobotPlayer.mapHeight;
		int width = T_800.RobotPlayer.mapWidth;
		
		while (true)
		{
			int rx = rand.nextInt(width);
			int ry = rand.nextInt(hight);
			
			if (rx > start.x)
			{
				boolean rightFromStart = true;
				boolean leftFromGoal = true;
				for (int i = start.x; i <= rx; i++)
				{
					if (T_800.MapBuilder.readMap(i, start.y) >= 2)
					{
						rightFromStart = false;
					}
					if (T_800.MapBuilder)
				}
			}
			
			
			return null;
		}
		
		
	}
}
