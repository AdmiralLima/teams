package fuzzMUFFIN;

import java.util.ArrayList;
import battlecode.common.*;

public class RobotPlayer 
{
	
	// Storing our RobotController here allows its use by the framework.
    public static RobotController rc;
    public static RobotType rcType;
    public static int mapWidth;
    public static ArrayList<MapLocation> slug;
    
    // This is the method where everything happens.
    public static void run(RobotController thisRC) 
    {
    	
    	// Sets up our framework to run this robot.
    	rc = thisRC;
    	rcType = rc.getType();
    	mapWidth = rc.getMapHeight();
    	slug = new ArrayList<MapLocation>();
     	
    	// This loop controls the robot for the remainder of its life.
    	while (true)
    	{
    		
    		// No reason to run code if our robot cannot do anything.
    		if (rc.isActive())
    		{
    			
    			// Determines how our HQ will behave.
    			if (rcType.equals(RobotType.HQ))
    			{
    				try 
    				{
    					
    					// We need to update our SOLDIERs goal.
    					DeathToPASTRs.setGoal();
    					
    					// We try to attack nearby enemies.
    					if (!Attack.attackRandomEnemyNotHQ())
    					{
    						
    						// If there are none we spawn a new SOLDIER.
    						Spawn.spawnAtAllCosts();
    					}
    				} catch (Exception e) { System.out.println("Caught HQ Exception."); e.printStackTrace(); }
    			}
    			
    			// Determines how our SOLDIERs will behave.
    			if (rcType.equals(RobotType.SOLDIER))
    			{
    				try
    				{
    					
    					// We try to attack nearby enemies.
    					if (!Attack.attackRandomEnemyNotHQ())
    					{
    						Slug.slug(rc.senseEnemyHQLocation());
    					}
    				} catch (Exception e) { System.out.println("Caught Soldier Exception."); e.printStackTrace(); }
    			}
    		}
    		
    		// We have finished our turn so we yield.
    		rc.yield();
    	}
    }


}
