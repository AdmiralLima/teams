package T_800;

import java.util.Random;
import battlecode.common.*;

public class Util {
    
    public static RobotController rc = T_800.RobotPlayer.rc;
    private static Random rand = T_800.RobotPlayer.rand;
    public static int mapWidth = T_800.RobotPlayer.mapWidth;
    public static int mapHeight = T_800.RobotPlayer.mapHeight;
    
    /**
     * Util contains very basic utility methods, constants and other miscellaneous things that 
     * don't fit well elsewhere
     * @throws GameActionException 
     */
    
    ///////METHODS/////////
    
    public static boolean containsRobotOfType(RobotType type, Robot[] robots) throws GameActionException {
        for (Robot robot : robots) {
            RobotType thistype = rc.senseRobotInfo(robot).type;
            if (type.equals(thistype)) {
                return true;
            }
        }
        return false;
    }
    
    public static Robot getARobotOfType(RobotType type, Robot[] robots) throws GameActionException {
        for (Robot robot : robots) {
            RobotType thistype = rc.senseRobotInfo(robot).type;
            if (type.equals(thistype)) {
                return robot;
            }
        }
        return null;
    }
    
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
    
    /** 
     * 
     * @param patha
     * @param pathb
     * @return path with patha appended to pathb
     */
    public static MapLocation[] join(MapLocation[] patha, MapLocation[] pathb) {
        
        int alen = patha.length;
        int blen = pathb.length;
        MapLocation[] path = new MapLocation[alen + blen];
        System.arraycopy(patha, 0, path, 0, alen);
        System.arraycopy(pathb, 0, path, alen, blen);
        
        return path;
    }
    
    ///////CONSTANTS///////
    
    public static final Direction[] directions = {Direction.NORTH, Direction.NORTH_EAST, Direction.EAST, Direction.SOUTH_EAST, Direction.SOUTH, Direction.SOUTH_WEST, Direction.WEST, Direction.NORTH_WEST};
    public static final int[] directionalLooks = {0,1,-1,2,-2,3,-3,4};
    
}
