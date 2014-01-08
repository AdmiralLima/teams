package OperationScreamingFist.actions;

import OperationScreamingFist.RobotPlayer;
import battlecode.common.*;

public class MapBuilder {
    
    private static RobotController rc = RobotPlayer.rc;
    public static Direction[] directions = {Direction.NORTH, Direction.NORTH_EAST, Direction.EAST, Direction.SOUTH_EAST, Direction.SOUTH, Direction.SOUTH_WEST, Direction.WEST, Direction.NORTH_WEST};
    public static int maxMapChannel = RobotPlayer.maxMapChannel;
    public static int mapHeight = RobotPlayer.mapHeight;
    
    /**
     * contains the representation of the map, and methods for soldiers to broadcast new information to
     * the map representation, such as enemy Soldier/PASTR/HQ/Noisetower locations, 
     */
    
    /**
     * Map key:
     * empty/unknown/other = 1;
     * obstacle = 2;
     * road = 3;
     * friendlysoldier = 4;
     * enemysoldier = 5;
     * enemyPASTR = 6;
     * enemyNoisetower = 7;
     * mediumCows = 8;
     * bigCows = 9;
     */
    
    /**
     * 
     * @param objects
     * @throws GameActionException
     */
    public static void observeGameObjects(GameObject[] objects) throws GameActionException {
        int[] map = new int[maxMapChannel];
        int row = 0;
        for (GameObject obj : objects) {
            MapLocation loc = rc.senseLocationOf(obj);
            row = rc.readBroadcast(loc.x);
            if (Util.isRobot(obj)) {
                
            }
        }
    }
    
    public static void updateMap(int row) throws GameActionException {
        rc.broadcast(rc.getLocation().x, row);
    }
    
    public static int[] readRow(int x) throws GameActionException {
        int row = rc.readBroadcast(x);
        int[] digits = new int[mapHeight];
        for (int i = mapHeight; i >= 0; i--) {
            digits[i] = row % 10;
            row = row/10;
        }
        return digits;
    }

    public static int readMap(int x, int y) throws GameActionException {
        int[] digs = readRow(x);
        return digs[y];
    }
}
