package T_800;

import java.util.Random;

import T_800.Strategy.*;
import battlecode.common.*;

/**
 * This class is called by the Battle Code framework to control a unit for it's lifetime.
 */
public class RobotPlayer 
{
	public static RobotController rc;
	
	public static Random rand = new Random();
    public static int mapWidth;
    public static int mapHeight;
    public static MapLocation goal;
    public static boolean newUnits;
    public static boolean mapReady;
    
    
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
		rand.setSeed(rc.getRobot().getID());
        mapWidth = rc.getMapWidth();
        mapHeight = rc.getMapHeight();
        mapReady = false;
        goal = new MapLocation(14,15);
        newUnits = false;
        ///////
		RobotType ourType = thisRC.getType();
	    currentStrategy = new Turtle(thisRC);
	    ///////
	    Protocol.init();
	    
	    // build map representation
        try {
            int bc = Clock.getBytecodeNum();
            int round = Clock.getRoundNum();
            MapBuilder.buildMap();
            System.out.println("MapBuilder.buildMap() used " + ((Clock.getRoundNum() - round)*10000 + (Clock.getBytecodeNum() - bc)) + " bc");
            //String map = MapBuilder.stringMap();
            //System.out.println(map);
        } catch (GameActionException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

	    
	    if (ourType == RobotType.HQ) {
	        try {
                rc.spawn(Direction.EAST);
            } catch (GameActionException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
	    }
	    		
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
	    				break;
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
	    				break;
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
	    				break;
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
