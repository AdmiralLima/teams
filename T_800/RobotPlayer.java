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
    
    public static RRT tree;
    
    
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
	    if (ourType == RobotType.PASTR) {
	        try {
	            int bc = Clock.getBytecodeNum();
	            int round = Clock.getRoundNum();
	            MapBuilder.buildMap();
	            //System.out.println("MapBuilder.buildMap() used " + ((Clock.getRoundNum() - round)*10000 + (Clock.getBytecodeNum() - bc)) + " bc");

	            tree = new RRT(rc.senseHQLocation(), 100);
	            //String rrt = RRT.stringPath(tree.vertices);
	            //System.out.println(rrt);
	            
//	            MapLocation[] waypoints = Nav.getWaypoints(MapBuilder.openLocs[RobotPlayer.rand.nextInt(MapBuilder.openLocs.length)], rc.senseEnemyHQLocation());
//	            for (MapLocation waypoint : waypoints) {
//	                System.out.println("waypoint : " + waypoint.toString());
//	            }
//	            String paywoints = RRT.stringPath(waypoints);
//	            System.out.println(paywoints);

	        } catch (GameActionException e1) {e1.printStackTrace();}
	    }

	    
	    if (ourType == RobotType.HQ) {
	        try {
	            if (!T_800.Basic.Spawn.spawnDirection(rc.getLocation().directionTo(rc.senseEnemyHQLocation()).opposite())) {
	                T_800.Complex.Spawn.spawn();
	            }
                rc.yield();
                Robot[] nearby = rc.senseNearbyGameObjects(Robot.class, 2, rc.getTeam());
                Robot soldier = Util.getARobotOfType(RobotType.SOLDIER, nearby);
                if (soldier != null) {
                    Comm.orderConstruct(soldier, RobotType.PASTR);
                }
                // HQ must have no action delay and be active at the same time
                while (rc.getActionDelay() > 0 || !rc.isActive()) {} // wait
                T_800.Complex.Spawn.spawn();
                
                while (rc.getActionDelay() > 0 || !rc.isActive() || Clock.getRoundNum() < 150) {} // wait
                nearby = rc.senseNearbyGameObjects(Robot.class, 2, rc.getTeam());
                soldier = Util.getARobotOfType(RobotType.SOLDIER, nearby);
                if (soldier != null) {
                    System.out.println("ordering soldier to move");
                    //Comm.orderMove(soldier, rc.senseEnemyHQLocation());
                    Comm.orderMove(soldier, new MapLocation(31,26));
                }
                
                
                
            } catch (GameActionException e) {e.printStackTrace();}
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
