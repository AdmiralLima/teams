package T_800;

import battlecode.common.*;

public class Comm {
    
    private static RobotController rc = T_800.RobotPlayer.rc;
    
    /**
     * method for soldiers to follow orders
     * @throws GameActionException 
     */
    public static void followOrders() throws GameActionException {
     // read broadcasted orders from HQ
        Pair orders = Protocol.readMessage(rc.getRobot());
        MapLocation m = orders.location;
        String order = orders.message;
        
        int orderNum = 0;
        if (order.equals("go to location")) {orderNum = 1;}
        else if (order.equals("construct PASTR")) {orderNum = 2;}
        else if (order.equals("construct Noisetower")) {orderNum = 3;}
        
        switch (orderNum) {
        case 0 : {break;}// be ready to attack enemies
        case 1 : { // move toward goal location and swarm
            Direction dir = rc.getLocation().directionTo(m);
            T_800.Basic.Move.move(dir);
            rc.yield();
            if (rc.isActive()) {T_800.Complex.Swarm.swarm();}
            break;
        }
        case 2 : { // construct PASTR
            rc.construct(RobotType.PASTR);
            break;
        }
        case 3 : { // construct Noisetower
            rc.construct(RobotType.NOISETOWER);
            break;
        }
        default : {break;} // chill
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
    public static void orderMove(RobotType type, MapLocation m) throws GameActionException {
        Protocol.broadcastToRobotsOfType(type, "go to location", m);
    }

}
