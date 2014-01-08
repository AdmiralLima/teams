package OperationScreamingFist.actions;

import OperationScreamingFist.RobotPlayer;
import battlecode.common.*;

public class MapBuilder {
    
    private static RobotController rc = RobotPlayer.rc;
    public static Direction[] directions = {Direction.NORTH, Direction.NORTH_EAST, Direction.EAST, Direction.SOUTH_EAST, Direction.SOUTH, Direction.SOUTH_WEST, Direction.WEST, Direction.NORTH_WEST};
    public static int maxMapChannel = RobotPlayer.maxMapChannel;
    public static int mapHeight = RobotPlayer.mapHeight;
    public static int mapWidth = RobotPlayer.mapWidth;
    
    /**
     * contains the representation of the map, and methods for soldiers to broadcast new information to
     * the map representation, such as enemy Soldier/PASTR/HQ/Noisetower locations, 
     */
    
    /**
     * Map key:
     * empty = 0;
     * unknown = 1;
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
     * @throws GameActionException 
     * 
    */
    public static void hqBuildMap() throws GameActionException {
        int value = 1;
        for (int x = 0; x < mapWidth; x++) {
            for (int y = 0; y < mapHeight; y++) {
                MapLocation m = new MapLocation(x,y);
                TerrainTile tile = rc.senseTerrainTile(m);
                switch (tile) {
                case NORMAL : value = 0; break;
                case OFF_MAP : value = 2; break;
                case ROAD : value = 3; break;
                case VOID : value = 2; break;
                }
                Util.writeMap(m, value);
            }
        }
    }
    
    public static String stringMap() throws GameActionException {
        String[] map = new String[mapWidth];
        int terrain = 1;
        for (int y = 0; y < mapHeight; y++) {
            String row = "";
            for (int x = 0; x < mapWidth; x++) {
                MapLocation m = new MapLocation(x,y);
                terrain = Util.readMap(m);
                row = row.concat(String.valueOf(terrain));
            }
            map[y] = row;
        }
        String toPrint = "\n";
        for (String row : map) {
            toPrint = toPrint.concat(row);
            toPrint = toPrint.concat("\n");
        }
        return toPrint;
    }
    
}
