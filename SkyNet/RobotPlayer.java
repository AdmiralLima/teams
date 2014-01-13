package T_;

import battlecode.common.*;

/**
 * This class is used by the BattleCode framework to run each of this
 * team's units for the duration of their lives.
 */
public class RobotPlayer 
{
    private static Controller controller;
    
    /**
     * This method is executed at the beginning of a unit's life and 
     * governs their actions until the game ends or they die.
     * 
     * @param RobotController - Takes this unit's controller.
     */
    public static void run(RobotController thisRC) 
    {
    		RobotType ourType = thisRC.getType();
    		
    		// The setup is slightly different for each unit type.    		
    		switch (ourType)
    		{
    			case HQ:
    			{
    				controller = new ControllerHQ(thisRC);
    			}
    			case SOLDIER:
    			{
    				controller = new ControllerSOLDIER(thisRC);
    			}
    			case NOISETOWER:
    			{
    				controller = new ControllerNOISETOWER(thisRC);
    			}
    			case PASTR:
    			{
    				controller = new ControllerPASTR(thisRC);
    			}
    		}
    		
    		// The unit controller is called for the duration of the unit's life.
    		while (true)
    		{
    			if (thisRC.isActive())
    			{
    				controller.run();
    			}
    			thisRC.yield();
    		}
    }
}
