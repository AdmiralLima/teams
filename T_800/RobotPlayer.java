package T_800;

import T_800.Strategy.*;
import battlecode.common.*;

/**
 * This class is called by the Battle Code framework to control a unit for it's lifetime.
 */
public class RobotPlayer 
{
	public static RobotController rc;
	private static Strategy currentStrategy;
	
	/**
	 * This method is executed at the beginning of a unit's life and 
	 * governs their actions until the game ends or they die.
	 *      
	 * @param RobotController - Takes this unit's controller.
	 */
	public static void run(RobotController thisRC) 
	{
		rc = thisRC;
		RobotType ourType = thisRC.getType();
	    currentStrategy = new Turtle(thisRC);
	    		
	    // This loop runs for the duration of the unit's life.
	    while (true)
	    {
	    	if (thisRC.isActive())
	    	{
	    		switch (ourType)
	    		{
	    			case HQ:
	    			{
	    				try
	    				{
	    					currentStrategy.runHQ();
	    				}
	    				catch (Exception e)
	    				{
	    					System.out.println("Caught HQ Exception.");
	    					e.printStackTrace();
	    				}
	    			}
	    			case SOLDIER:
	    			{
	    				try
	    				{
	    					currentStrategy.runSOLDIER();
	    				}
	    				catch (Exception e)
	    				{
	    					System.out.println("Caught SOLDIER Exception.");
	    					e.printStackTrace();
	    				}
	    			}
	    			case PASTR:
	    			{
	    				try
	    				{
	    					currentStrategy.runPASTR();
	    				}
	    				catch (Exception e)
	    				{
	    					System.out.println("Caught PASTR Exception.");
	    					e.printStackTrace();
	    				}
	    			}
	    			case NOISETOWER:
	    			{
	    				try
	    				{
	    					currentStrategy.runNOISETOWER();
	    				}
	    				catch (Exception e)
	    				{
	    					System.out.println("Caught NOISETOWER Exception.");
	    					e.printStackTrace();
	    				}
	    			}
	    		}
	    	}
	    	thisRC.yield();
	    }
	}
}