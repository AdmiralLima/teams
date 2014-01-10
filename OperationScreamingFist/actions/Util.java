package OperationScreamingFist.actions;

import java.util.Random;

import OperationScreamingFist.RobotPlayer;
import battlecode.common.*;

public class Util {
    
    public static RobotController rc = RobotPlayer.rc;
    private static Random rand = RobotPlayer.rand;
    public static int mapWidth = RobotPlayer.mapWidth;
    public static int mapHeight = RobotPlayer.mapHeight;
    
    /**
     * Util contains very basic utility methods, constants and other miscellaneous things that 
     * don't fit well elsewhere
     * @throws GameActionException 
     */
    
    ///////METHODS/////////
    
    public static boolean isRobot(GameObject g) {
        return g.getClass().equals(Robot.class);
    }
    
    public static boolean sameClass(GameObject g1, GameObject g2) {
        return g1.getClass().equals(g2.getClass());
    }
    
    public static MapLocation randomLoc() {
        return new MapLocation(rand.nextInt(mapWidth), rand.nextInt(mapHeight));
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
