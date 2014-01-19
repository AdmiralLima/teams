package Marvin;

import battlecode.common.*;
import Marvin.UnitControl.*;

/**
 * An instance of this class is created to run each unit.
 */
public class RobotPlayer 
{
	
	public static RobotController rc;
	
	/**
	 * This method is called once to run a robot over its lifetime. 
	 * 
	 * @param RobotController - The unit to be controlled.
	 */
    public static void run(RobotController thisRC) 
    {
    	rc = thisRC;
    	UnitController controller;
    	
    	// This is the initial controller setup.
    	switch (thisRC.getType())
    	{
    		case HQ:
    		{
    			controller = new HQController();
    			break;
    		}
    		case SOLDIER:
    		{
    			controller = new SOLDIERController();
    			break;
    		}
    		case NOISETOWER:
    		{
    			controller = new NOISETOWERController(); 
    			break;
    		}
    		case PASTR:
    		{
    			controller = new PASTRController();
    			break;
    		}
    		default:
    		{
    			controller = null;
    			break;
    		}
    	}
    	
    	// This executes the robot logic for the remainder of its life.
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