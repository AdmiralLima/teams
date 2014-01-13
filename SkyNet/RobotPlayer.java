package SkyNet;

import SkyNet.PathFinding.Slug;
import SkyNet.SimpleActions.Attack;
import SkyNet.SimpleActions.Spawn;
import battlecode.common.*;

public class RobotPlayer 
{

    public static Spawn spawner;
    public static Slug slugger;
    public static Attack attacker;
    
    // This is the method where everything happens.
    public static void run(RobotController thisRC) 
    {
    	try
    	{
	
    		// What type of robot is this?
    		RobotType thisRCType = thisRC.senseRobotInfo(thisRC.getRobot()).type;
    		
    		// We will need an attacker.
    		attacker = new Attack(thisRC);

    		// If we are an HQ we need a spawner.
    		if (thisRCType.equals(RobotType.HQ))
    		{
    			spawner = new Spawn(thisRC);
    		}
    		
    		// If we are a SOLDIER we need a slug for navigation.
    		if (thisRCType.equals(RobotType.SOLDIER))
    		{
    			slugger = new Slug(thisRC, 50);
    		}
    		    		
    		// Now we need to decide what to do for the rest of our lives.
    		while (true)
    		{
    			
    			// We should not try to do anything unless we can.
    			if (thisRC.isActive())
    			{
    			
    				// What will we do if we are the HQ?
    				if (thisRCType.equals(RobotType.HQ))
    				{
    	
    					// We should try to attack.
    					if (!attacker.attackRandomRobotNotEnemyHQ())
    					{
    			
    						// If not we should try to spawn.
    						spawner.spawn();
    					}
    				}
    				
    				// What will we do if we are a SOLDIER.
    				if (thisRCType.equals(RobotType.SOLDIER))
    				{
    					if (!attacker.attackRandomRobotNotEnemyHQ())
    					{
    						if (!(thisRC.sensePastrLocations(thisRC.getTeam()).length > 0))
    						{
    							if (thisRC.getLocation().distanceSquaredTo(thisRC.senseHQLocation()) > 100)
    							{
    								thisRC.construct(RobotType.PASTR);
    							}
    							else
    							{
    								slugger.slug(thisRC.sensePastrLocations(thisRC.getTeam().opponent())[0]);
    							}
    						}
    						else if (thisRC.sensePastrLocations(thisRC.getTeam().opponent()).length > 0)
    							{
    								slugger.slug(thisRC.sensePastrLocations(thisRC.getTeam().opponent())[0]);
    							}
    						
    				}
    			}
    			thisRC.yield();
    		}

		}
		catch (GameActionException e)
		{
			System.out.println("YOU DONE FUCKED UP.");
			e.printStackTrace();
		}
 
    }


}
