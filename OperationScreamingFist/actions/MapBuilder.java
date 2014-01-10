package OperationScreamingFist.actions;

import OperationScreamingFist.RobotPlayer;
import battlecode.common.*;
import OperationScreamingFist.datatypes.*;

public class MapBuilder {
    
    private static RobotController rc = RobotPlayer.rc;
    public static Direction[] directions = {Direction.NORTH, Direction.NORTH_EAST, Direction.EAST, Direction.SOUTH_EAST, Direction.SOUTH, Direction.SOUTH_WEST, Direction.WEST, Direction.NORTH_WEST};
    public static int maxMapChannel = RobotPlayer.maxMapChannel;
    public static int mapHeight = RobotPlayer.mapHeight;
    public static int mapWidth = RobotPlayer.mapWidth;
    
    public static int[][] gameMap = new int[mapWidth][mapHeight];
    public static RectangleList rectangles = new RectangleList();
    private static int freeArea = mapWidth*mapHeight;
    
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
     * this uses way too many channels. fuck this
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
                case OFF_MAP : value = 2; freeArea -= 1; break;
                case ROAD : value = 3; break;
                case VOID : value = 2; freeArea -= 1; break;
                }
                writeMap(m, value);
            }
        }
    }
    
    public static void generateMesh() {
        int area = 0;
        while (area < freeArea) {
            System.out.println(freeArea - area);
            int bc = Clock.getBytecodeNum();
            MapLocation newLoc = Util.randomLoc();
            Rectangle r = new Rectangle(newLoc);
            for (Direction dir : directions) {
                boolean expanding = true;
                while (expanding) {
                    //System.out.println("expanding in " + dir.toString() + " with " + r.toString());
                    expanding = r.expand(dir, rectangles);
                }
            }
            area += r.area();
            System.out.println("finished " + r.toString());
            rectangles.add(r);  
            System.out.println("Used " + (Clock.getBytecodeNum() - bc) +" bc");
        }
    }
    
    public static String stringMesh() {
        String[] map = new String[mapWidth];
        int c = -1;
        for (int y = 0; y < mapHeight; y++) {
            String row = "";
            for (int x = 0; x < mapWidth; x++) {
                //MapLocation m = new MapLocation(x,y);
                //terrain = readMap(m);
                c = (char) rectangles.whichRectContains(x, y) + 100;
                row = row.concat(String.valueOf(c));
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
    
    public static String stringMap() throws GameActionException {
        String[] map = new String[mapWidth];
        int terrain = 1;
        for (int y = 0; y < mapHeight; y++) {
            String row = "";
            for (int x = 0; x < mapWidth; x++) {
                //MapLocation m = new MapLocation(x,y);
                //terrain = readMap(m);
                terrain = readMap(x,y);
                String c = "";
                switch (terrain) {
                case 0 : c = " "; break;
                case 1 : c = ""; break;
                case 2 : c = "X"; break;
                case 3 : c = "-"; break;
                }
                row = row.concat(String.valueOf(c));
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
    
    public static int readMap(MapLocation m) {
        if (0 <= m.x && m.x < mapWidth && 0 <= m.y && m.y < mapHeight) {
            return gameMap[m.x][m.y];
        }
        return 2; // 2 = obstacle
    }
    public static int readMap(int x, int y) {
        if (0 <= x && x < mapWidth && 0 <= y && y < mapHeight) {
            return gameMap[x][y];
        }
        return 2; // 2 = obstacle
    }
    
    public static void writeMap(MapLocation m, int msg) {
        gameMap[m.x][m.y] = msg;
    }
    public static void writeMap(int x, int y, int msg) {
        gameMap[x][y] = msg;
    }
    
}
