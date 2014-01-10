package fuzzMUFFIN;

import java.util.ArrayList;

import battlecode.common.*;

public class RobotPlayer 
{
	
	// Storing our RobotController here allows its use by the framework.
    public static RobotController rc;
    public static RobotType rcType;

    public static ArrayList<MapLocation> slug;
    public static int goal;
    public static MapLocation enemyHQ;
    public static MapLocation friendlyHQ;
    
    // This is the method where everything happens.
    public static void run(RobotController thisRC) 
    {
    	
    	// Sets up our framework to run this robot.
    	rc = thisRC;
    	rcType = rc.getType();
    	slug = new ArrayList<MapLocation>();
    	enemyHQ = rc.senseEnemyHQLocation();
    	friendlyHQ = rc.senseHQLocation();
    	     	
    	if (rcType.equals(RobotType.HQ))
		{
    		try 
    		{
    			Goal.goToFriendlyHQ();
    		} catch (Exception e) { System.out.println("Caught HQ Setup Exception."); e.printStackTrace(); }
		}
    	
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

    					Goal.goToEnemyPasture();
    					
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
    						int newgoal = rc.readBroadcast(1);
    						if (goal != newgoal)
    						{
    							slug.clear();
    							goal = newgoal;
    						}
    						Swarm.swarm();
    					}
    				} catch (Exception e) { System.out.println("Caught Soldier Exception."); e.printStackTrace(); }
    			}
    		}
    		
    		// We have finished our turn so we yield.
    		rc.yield();
    	}
    }


}
