package team005;

import java.util.Random;

import team005.Complex.Slug;
import team005.Strategy.*;

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
    public static Slug slugger;
    
    // milk projections
    public static double ourMilk;
    public static double theirMilk;
    public static double ourMilkNew;
    public static double theirMilkNew;
    public static double ourWinRound;
    public static double theirWinRound;
    
    public static RRT tree;
    
    
	public static Strategy currentStrategy;
	public static Turtle turtle;
	public static Aggressive aggr;
	
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
		turtle = new Turtle(thisRC);
		aggr = new Aggressive(thisRC);
	    currentStrategy = turtle;
	    ///////
	    Protocol.init();	    
	    
	    // build map representation
	    if (ourType == RobotType.PASTR) {
	        try {
	            int bc = Clock.getBytecodeNum();
	            int round = Clock.getRoundNum();
	            MapBuilder.buildMap();
	            
	            double openFrac = MapBuilder.openCount * 1.0 / (mapWidth * mapHeight);
	            //System.out.println("MapBuilder.buildMap() used " + ((Clock.getRoundNum() - round)*10000 + (Clock.getBytecodeNum() - bc)) + " bc");
	            System.out.println("open fraction is " + openFrac);
	            if (openFrac > 0.5) {
	                System.out.println("using RRT");
                    tree = new RRT(rc.senseHQLocation(), 200*((int) (1.5-openFrac))); // uses between 100 & 200 RRT points
                    Comm.RRTReady(); // tell HQ that RRT is ready 
                    Comm.useRRT(); // tell soldiers to use RRT
	                
	            //String rrt = RRT.stringPath(tree.vertices);
	            //System.out.println(rrt);
	            
//	            MapLocation[] waypoints = Nav.getWaypoints(MapBuilder.openLocs[RobotPlayer.rand.nextInt(MapBuilder.openLocs.length)], rc.senseEnemyHQLocation());
//	            for (MapLocation waypoint : waypoints) {
//	                System.out.println("waypoint : " + waypoint.toString());
//	            }
//	            String paywoints = RRT.stringPath(waypoints);
//	            System.out.println(paywoints);
	               
	            }

	        } catch (GameActionException e1) {e1.printStackTrace();}
	    }

	    
	    if (ourType == RobotType.HQ) {
	        try {
	            if (!team005.Basic.Spawn.spawnDirection(rc.getLocation().directionTo(rc.senseEnemyHQLocation()).opposite())) {
	                team005.Complex.Spawn.spawn();
	            }
                rc.yield();
                Robot[] nearby = rc.senseNearbyGameObjects(Robot.class, 2, rc.getTeam());
                Robot soldier = Util.getARobotOfType(RobotType.SOLDIER, nearby);
                if (soldier != null) {
                    Comm.orderConstruct(soldier, RobotType.PASTR);
                }
                // HQ must have no action delay and be active at the same time
                while (rc.getActionDelay() > 0 || !rc.isActive()) {} // wait
                team005.Complex.Spawn.spawn();     
                rc.yield();
                    nearby = rc.senseNearbyGameObjects(Robot.class, 2, rc.getTeam());
                    soldier = Util.getARobotOfType(RobotType.SOLDIER, nearby);
                    if (soldier != null) {
                        Comm.orderConstruct(soldier, RobotType.NOISETOWER);
                    }
                
                
                ourMilk = rc.senseTeamMilkQuantity(rc.getTeam());
                theirMilk = rc.senseTeamMilkQuantity(rc.getTeam().opponent());
                
            } catch (GameActionException e) {e.printStackTrace();}
	    }
	    
	    if (ourType == RobotType.SOLDIER) {
	        slugger = new Slug(rc,100);
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
	    				    ourMilkNew = rc.senseTeamMilkQuantity(rc.getTeam());
	    				    theirMilkNew = rc.senseTeamMilkQuantity(rc.getTeam().opponent());
	    				    
	    				    // calculation of ourMilkNew and theirMilkNew
	    				    ourWinRound = (GameConstants.WIN_QTY - ourMilkNew)/(ourMilkNew - ourMilk);
	    				    theirWinRound = (GameConstants.WIN_QTY - theirMilkNew)/(theirMilkNew - theirMilk);
	    				    
	    				    if (theirWinRound > ourWinRound) {
	    				        currentStrategy = aggr;
	    				    } else if (ourWinRound >= theirWinRound) {
	    				        currentStrategy = turtle;
	    				    }
	    				    // broadcast new strat to soldiers
	    				    Comm.changeStrategy(currentStrategy);
	    				    rc.setIndicatorString(1, currentStrategy.toString());
	    				    
	    				    ourMilk = ourMilkNew;
	    				    theirMilk = theirMilkNew;
	    				    
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
	    				    // update strategy
	    				    Comm.checkStrategy(thisRC.getRobot());
	    				    
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
