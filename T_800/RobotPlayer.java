package T_800;

import battlecode.common.*

/**
 * This class is called by the Battle Code framework to control a unit for it's lifetime.
 */
public class RobotPlayer 
{
	private Strategy currentStrategy;
	
	/**
	 * This method is executed at the beginning of a unit's life and 
	 * governs their actions until the game ends or they die.
	 *      
	 * @param RobotController - Takes this unit's controller.
	 */
	public static void run(RobotController thisRC) 
	{
	    		RobotType ourType = thisRC.getType();
	    		currentStrategy = new Turtle();
	    		
	    		// The unit controller is called for the duration of the unit's life.
	    		while (true)
	    		{
	    			if (thisRC.isActive())
	    			{
	    				switch (ourType)
	    				{
	    					case HQ:
	    					{
	    					
	    					}
	    					case SOLDIER:
	    					{
	    						
	    					}
	    					case PASTR:
	    					{
	    						
	    					}
	    					case NOISETOWER:
	    					{
	    						
	    					}
	    			}
	    			thisRC.yield();
	    		}
	    }
	}

}
