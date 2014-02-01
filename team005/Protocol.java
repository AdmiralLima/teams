package team005;

import java.util.HashMap;

import battlecode.common.*;

public class Protocol {

    // TODO: add string messages that you want to use here. Don't delete or move default.
    public static String[] messages = new String[] {
        "default",
        "go to location",
        "enemy spotted at location",
        "current location",
        "construct PASTR",
        "construct Noisetower",
        "request path to location",
        "go to waypoint",
        "reached waypoint",
        "reached destination",
        "use RRT",
        "RRT ready",
        "change strategy: turtle",
        "change strategy: aggressive",
        "wear a hat"
        };
    
    // channel methods
    // channel 0 is used for communications TO the soldier
    // channel 1 is used for communications FROM the soldier (i.e. to the HQ or PASTR)
    // channel 2 is used for the soldier to indicate their current location
    // channel 3 is used by the PASTR to tell the soldier which Nav algorithm to use
    // channel 4 is used to change strategy
    /**
     * 
     * @param robot
     * @param relay can be 0-4
     * @return
     */
    public static int getChannel(Robot robot, int relay) {
        return robot.getID()*5+relay;
    }
    public static int getChannel(Robot robot) {
        return getChannel(robot,0);
    }
//    public static Robot getRobot(int channel) {
//        return rc.channel%10;
//    }
    
    // reading methods
    /**
     * 
     * @param robot
     * @param relay can be 0 (to soldier), 1 (from soldier), 2 (soldier's location), 3 (use RRT?), 4 (shared info)
     * @return
     * @throws GameActionException
     */
    public static Pair readMessage(Robot robot, int relay) throws GameActionException {
        String[] message = decode(rc.readBroadcast(getChannel(robot, relay)));
        MapLocation m = new MapLocation(Integer.parseInt(message[1]),Integer.parseInt(message[2]));
        return new Pair(message[0], m);
    }
    public static Pair readMessage(Robot robot) throws GameActionException {
        return readMessage(robot, 0);
    }
    
    // messaging methods
    /**
     * sends a message, maplocation to an individual robot
     * @param robot
     * @param message
     * @throws GameActionException
     */
    public static void broadcastToRobot(Robot robot, String message) throws GameActionException {
        rc.broadcast(getChannel(robot), encode(message));
    }
    public static void broadcastToRobot(Robot robot, String message, MapLocation m) throws GameActionException {
        rc.broadcast(getChannel(robot), encode(message, m));
    }
    public static void broadcastToRobot(Robot robot, int relay, String message, MapLocation m) throws GameActionException {
        rc.broadcast(getChannel(robot, relay), encode(message, m));
    }
    public static void broadcastToRobot(Robot robot, int relay, String message) throws GameActionException {
        rc.broadcast(getChannel(robot, relay), encode(message));
    }
    
    /**
     * sends a message to all robots of type on their ID channel
     * @param type
     * @param message, maplocation
     * @throws GameActionException
     */
    public static void broadcastToRobotsOfType(RobotType type, String message) throws GameActionException {
        int code = encode(message);
        broadcastToRobotsOfType(type, code);
    }
    public static void broadcastToRobotsOfType(RobotType type, String message, MapLocation m) throws GameActionException {
        int code = encode(message,m);
        broadcastToRobotsOfType(type, code);
    }
    public static void broadcastToRobotsOfType(RobotType type, int relay, String message, MapLocation m) throws GameActionException {
        int code = encode(message,m);
        broadcastToRobotsOfType(type, relay, code);
    }
    public static void broadcastToRobotsOfType(RobotType type, int relay, String message) throws GameActionException {
        int code = encode(message);
        broadcastToRobotsOfType(type, relay, code);
    }
    public static void broadcastToRobotsOfType(RobotType type, int code) throws GameActionException {
        broadcastToRobotsOfType(type, 0, code);
    }
    public static void broadcastToRobotsOfType(RobotType type, int relay, int code) throws GameActionException {
        Robot[] robots = rc.senseNearbyGameObjects(Robot.class, 10000, rc.getTeam());
        for (Robot robot : robots) {
            RobotType thistype = rc.senseRobotInfo(robot).type;
            if (type.equals(thistype)) {
                rc.broadcast(getChannel(robot, relay), code);
            }
        }
    }
    
    private static RobotController rc = team005.RobotPlayer.rc;
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
    
    /**
     * turns a string and a location into an integer to be broadcast
     * @param message
     * @param m
     * @return
     */
    public static int encode(String message, MapLocation m) {
        int dictPos = ints.get(message).intValue();
        int loc = Util.locToInt(m);
        int code = dictPos + msgsLength*loc;
        return code;
    }
    
    /**
     * turns an int from the broadcast array back into a string and a location
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
