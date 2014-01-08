package OperationScreamingFist.actions;

import OperationScreamingFist.RobotPlayer;
import battlecode.common.*;

public class Util {
    
    public static RobotController rc = RobotPlayer.rc;
    public static int mapWidth = RobotPlayer.mapWidth;
    
    /**
     * Util contains very basic utility methods, constants and other miscellaneous things that 
     * don't fit well elsewhere
     * @throws GameActionException 
     */
    
    ///////METHODS/////////
    
    public static int readMap(MapLocation m) throws GameActionException {
        return rc.readBroadcast(locToInt(m));
    }
    
    public static void writeMap(MapLocation m, int msg) throws GameActionException {
        rc.broadcast(locToInt(m), msg);
    }
    
    public static boolean isRobot(GameObject g) {
        return g.getClass().equals(Robot.class);
    }
    
    public static boolean sameClass(GameObject g1, GameObject g2) {
        return g1.getClass().equals(g2.getClass());
    }
    
    public static int locToInt(MapLocation m) {
        return (m.x + mapWidth*m.y);
    }
    
    public static MapLocation intToLoc(int i) {
        return new MapLocation(i%mapWidth, i/mapWidth);
    }
    
    ///////CONSTANTS///////
    
    public static final Direction[] directions = {Direction.NORTH, Direction.NORTH_EAST, Direction.EAST, Direction.SOUTH_EAST, Direction.SOUTH, Direction.SOUTH_WEST, Direction.WEST, Direction.NORTH_WEST};
    
}
