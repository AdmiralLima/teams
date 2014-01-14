package SkyNet;

import battlecode.common.*;

public class RobotPlayer 
{

	// We will need to 
    private static HQControl HQ;
    private static SOLDIERControl SOLDIER;
    private static NOISETOWERControl NT;
    
    // This is the method where everything happens.
    public static void run(RobotController thisRC) 
    {
    	try
    	{
    		
    		// We need to know what we are dealing with.
    		RobotType ourType = thisRC.getType();
    		
    		// We need to set up our HQ.
    		if (ourType.equals(RobotType.HQ))
    		{
    			HQ = new HQControl(thisRC);
    		}
    		
    		// We need to set up our SOLDIER.
    		if (ourType.equals(RobotType.SOLDIER))
    		{
    			
    			// We may need to build a NOISETOWER.
    			if (thisRC.senseRobotCount() < 2)
    			{
    				thisRC.construct(RobotType.NOISETOWER);
    			}
    			
    			// We may need to build a PASTR.
    			else if (thisRC.senseRobotCount() < 3)
    			{
    				thisRC.construct(RobotType.PASTR);
    				thisRC.yield();
    			}
    			
    			//If not we will SOLDIER on.
    			SOLDIER = new SOLDIERControl(thisRC);
    		}
    		
    		// We need to set up our tower.
    		if (ourType.equals(RobotType.NOISETOWER))
    		{
    			NT = new NOISETOWERControl(thisRC);
    		}
    		
    		// This governs what the robot will do until it dies.
    		while (true)
    		{
    			
    			// We should not try to do anything if we are not active.
    			if (thisRC.isActive())
    			{
    				
    				// Lets run our HQ.
    				if (ourType.equals(RobotType.HQ))
    				{
    					HQ.runHQ();
    				}
    				
    				// Lets run our SOLDIERs.
    				if (ourType.equals(RobotType.SOLDIER))
    				{
    					SOLDIER.runSOLDIER();
    				}
    				
    				// Lets run our NOISETOWERs.
    				if (ourType.equals(RobotType.NOISETOWER))
    				{
    					NT.runNOISETOWER();
    				}
    			}
    			
    			// Do not forget to yield or you will hate yourself forever.
    			thisRC.yield();
    		}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
    }
}
