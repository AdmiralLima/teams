package T_800;

import java.util.HashMap;

import battlecode.common.*;

public class Protocol {

    // add string messages that you want to use here
    private static String[] messages = new String[] {
        "go to location",
        "enemy spotted at location",
        "something else"
        };
    
    
    // messaging methods
    public static void broadcastToRobot(Robot robot, String message) throws GameActionException {
        rc.broadcast(robot.getID(), encode(message));
    }
    public static void broadcastToRobot(Robot robot, String message, MapLocation m) {
        rc.broadcast(robot.getID(), encode(message, m));
    }
    
    public static void broadcastToRobotsOfType(RobotType type, String message) throws GameActionException {
        int code = encode(message);
        Robot[] robots = rc.senseNearbyGameObjects(Robot.class, 10000, rc.getTeam());
        for (Robot robot : robots) {
            RobotType thistype = rc.senseRobotInfo(robot).type;
            if (type.equals(thistype)) {
                rc.broadcast(robot.getID(), code);
            }
        }
    }
    public static void broadcastToRobotsOfType(RobotType type, int code) {
        Robot[] robots = rc.senseNearbyGameObjects(Robot.class, 10000, rc.getTeam());
        for (Robot robot : robots) {
            RobotType thistype = rc.senseRobotInfo(robot).type;
            if (type.equals(thistype)) {
                rc.broadcast(robot.getID(), code);
            }
        }
    }
    
    private static RobotController rc = RobotPlayer.rc;
    private static int msgsLength = messages.length;
    public static HashMap<String,Integer> ints = new HashMap<String,Integer>(msgsLength);
    
    public static void init() {
        for (int i = 0; i < msgsLength; i++) {
            ints.put(messages[i], i);
        }
    }
    
    public static int encode(String message) {
        // TODO: could optimise this by just computing dictPos, no need to make default be (0,0) since it naturally defaults to that anyway
        // default location is (0,0)
        MapLocation m = new MapLocation(0, 0);
        int code = encode(message, m);
        return code;
    }
    
    public static int encode(String message, MapLocation m) {
        int dictPos = ints.get(message).intValue();
        int loc = Util.locToInt(m);
        int code = dictPos + msgsLength*loc;
        return code;
    }
    
    /**
     * 
     * @param code
     * @return a String array of length 3 as follows: ["message", x location, y location]
     */
    public static String[] decode(int code) {
        int loc = code/msgsLength;
        int dictPos = code%msgsLength;
        MapLocation m = Util.intToLoc(loc);
        return new String[] {messages[dictPos], String.valueOf(m.x), String.valueOf(m.y)};
    }
    
}