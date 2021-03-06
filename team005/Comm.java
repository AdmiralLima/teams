package team005;

import team005.Strategy.Strategy;
import team005.Strategy.Strategy.Strategies;
import battlecode.common.*;

public class Comm {
    
    private static RobotController rc = team005.RobotPlayer.rc;
    public static boolean done = false;
    
    /**
     * method for soldiers to follow orders
     * @throws GameActionException 
     */
    public static void SoldierFollowOrders() throws GameActionException {
        Robot soldier = rc.getRobot();
        
     // read broadcasted orders from HQ
        Pair orders = Protocol.readMessage(rc.getRobot());
        MapLocation m = orders.location;
        String order = orders.message;
        
        rc.setIndicatorString(0, order);
        rc.setIndicatorString(1, m.toString());
        
        int orderNum = 0;
        if (order.equals("go to waypoint")) {orderNum = 1;}
        else if (order.equals("construct PASTR")) {orderNum = 2;}
        else if (order.equals("construct Noisetower")) {orderNum = 3;}
        else if (order.equals("go to location")) {orderNum = 4;}
        
        switch (orderNum) {
        case 0 : break;// be ready to attack enemies
        case 1 : // move toward waypoint
            MapLocation currentLoc = rc.getLocation(); 
            if (currentLoc.distanceSquaredTo(m) < 2) { 
                reachedWaypoint(soldier,m);
                //Protocol.broadcastToRobot(soldier, "default");
                //System.out.println("reached waypoint " + m.toString());
            } else {
                RobotPlayer.slugger.slug(m);
            }
            // and swarm?
//            rc.yield();
//            if (rc.isActive()) {T_800.Complex.Swarm.swarm();}
            break;
        case 2 : // construct PASTR
            rc.setIndicatorString(0, "constructing PASTR");
            rc.construct(RobotType.PASTR);
            break;
        case 3 : // construct Noisetower
            rc.setIndicatorString(0, "constructing NOISETOWER");
            rc.construct(RobotType.NOISETOWER);
            break;
        case 4 : // get path to location
            if (useRRT(soldier)) {
                requestPath(soldier, m);
                //System.out.println("Soldier requesting path to " + m.toString());
                Protocol.broadcastToRobot(soldier, "default");
            } else {
                rc.setIndicatorString(0, "slugging");
                RobotPlayer.slugger.slug(m);
                rc.yield();
                if (rc.isActive()) {team005.Complex.Swarm.swarm();}
            }
            break;
        default : break; // chill
        }
    }
    
    /**
     * method for HQ to order a soldier to construct a PASTR or Noisetower
     * @param soldier
     * @param constructType
     * @throws GameActionException
     */
    public static void orderConstruct(Robot soldier, RobotType constructType) throws GameActionException {
        if (constructType.equals(RobotType.PASTR)) {
            Protocol.broadcastToRobot(soldier, "construct PASTR");
        }
        else if (constructType.equals(RobotType.NOISETOWER)) {
            Protocol.broadcastToRobot(soldier, "construct Noisetower");
        }
        else {
            System.out.println("Comm.orderConstruct: can't construct that type");
        }
    }
    
    /**
     * method for HQ to order an individual soldier to go to a specific location
     * @param soldier
     * @param m
     * @throws GameActionException
     */
    public static void orderMove(Robot soldier, MapLocation m) throws GameActionException {
        Protocol.broadcastToRobot(soldier, "go to location", m);
    }
    
    /**
     * method for HQ to order all soldiers of a certain type to go to a specific location
     * @param type
     * @param m
     * @throws GameActionException
     */
    public static void orderAllMove(MapLocation m) throws GameActionException {
        Protocol.broadcastToRobotsOfType(RobotType.SOLDIER, "go to location", m);
    }
    
    /**
     * method for HQ to tell soldiers which strategy to use
     * @throws GameActionException 
     */
    public static void changeStrategy(Strategy strat) throws GameActionException {
        if (strat.getStrategy() == Strategies.TURTLE) {
            Protocol.broadcastToRobotsOfType(RobotType.SOLDIER, 4, "change strategy: turtle");
        } else if (strat.getStrategy() == Strategies.AGGRESSIVE) {
            Protocol.broadcastToRobotsOfType(RobotType.SOLDIER, 4, "change strategy: aggressive");
        }
    }
    /**
     * method for soldiers to check which strategy to use
     * @throws GameActionException 
     */
    public static void checkStrategy(Robot soldier) throws GameActionException {
        Pair shit = Protocol.readMessage(soldier, 4);
        if (shit.message.equals("change strategy: turtle")) {
            RobotPlayer.currentStrategy = RobotPlayer.turtle;
        } else if (shit.message.equals("change strategy: aggressive")) {
            RobotPlayer.currentStrategy = RobotPlayer.aggr;
        }
    }
    
    /**
     * method for soldiers to request a path from the NavPASTR (broadcasts to soldier's relay #1)
     * @param soldier
     * @param m
     * @throws GameActionException
     */
    public static void requestPath(Robot soldier, MapLocation current, MapLocation goal) throws GameActionException {
        Protocol.broadcastToRobot(soldier, 1, "request path to location", goal);
        Protocol.broadcastToRobot(soldier, 2, "current location", current);
    }
    public static void requestPath(Robot soldier, MapLocation goal) throws GameActionException {
        requestPath(soldier, rc.getLocation(), goal);
    }
    public static void reachedWaypoint(Robot soldier, MapLocation m) throws GameActionException {
        Protocol.broadcastToRobot(soldier, 1, "reached waypoint", m);
    }
    
    /** method for PASTR to send waypoints to a soldier
     * 
     * @param soldier
     * @param waypoint
     * @throws GameActionException
     */
    public static void sendWaypoint(Robot soldier, MapLocation waypoint) throws GameActionException {
        Protocol.broadcastToRobot(soldier, 0, "go to waypoint", waypoint);
    }
    
    /** method for PASTR to indicate to HQ that RRT is ready
     * 
     */
    public static void RRTReady() throws GameActionException {
        if (rc.canSenseSquare(rc.senseHQLocation())) {
            Robot HQ = (Robot) rc.senseObjectAtLocation(rc.senseHQLocation());
            Protocol.broadcastToRobot(HQ, 3, "RRT ready");
        }
    }
    /** method for HQ to check that RRT is ready
     * @throws GameActionException 
     * 
     */
    public static boolean RRTReady(Robot hq) throws GameActionException {
        Pair ready = Protocol.readMessage(hq, 3);
        return ready.message.equals("RRT ready");
    }
    
    /**
     * method for PASTR to indicate to soldiers that its map is ready
     * @throws GameActionException
     */
    public static void useRRT() throws GameActionException {
        Protocol.broadcastToRobotsOfType(RobotType.SOLDIER, 3, "use RRT");
    }
    /**
     * method for Soldiers to tell whether they should use RRT calls
     * @throws GameActionException
     */
    public static boolean useRRT(Robot soldier) throws GameActionException {
        Pair use = Protocol.readMessage(soldier, 3);
        return use.message.equals("use RRT");
    }
    
    public static void PASTRReadMessages() throws GameActionException {
        Robot[] soldiers = rc.senseBroadcastingRobots();
        Team friend = rc.getTeam();
        for (Robot soldier : soldiers) {
            if (soldier.getTeam().equals(friend)) {
                Pair shit = Protocol.readMessage(soldier, 1);
                String message = shit.message;
                MapLocation m = shit.location;
                
                rc.setIndicatorString(0, message);
                rc.setIndicatorString(1, m.toString());
                
                if (message.equals("reached waypoint")) {
                    MapLocation next = Nav.nextWaypoint(soldier);
                    // give the soldier his next waypoint (null if at destination)
                    if (next != null) {
                        sendWaypoint(soldier,next);
                    }
                }
                else if (message.equals("request path to location")) {
                    // compute path and send first waypoint
                    Pair current = Protocol.readMessage(soldier, 2);
                    if (current.message.equals("current location")) {
                        MapLocation currentLoc = current.location;
                        // get path from RRT
                        MapLocation[] path = Nav.getWaypoints(currentLoc, m);
                        //System.out.println(RRT.stringPath(path));
                        // store robot and path
                        Nav.addRobot(soldier, path);
                        // get next waypoint. defaults to start of path. shouldnt be null
                        MapLocation next = Nav.nextWaypoint(soldier);
                        sendWaypoint(soldier, next);
                    }
                }
                else {
                    //Protocol.broadcastToRobot(soldier, "default");
                }
            }
        }
    }

}
