package T_800;

import java.util.Random;

import battlecode.common.*;

public class RRT {
    
    private static RobotController rc = RobotPlayer.rc;
    public static Random rand = RobotPlayer.rand;
    public static int[][] gameMap = MapBuilder.gameMap;
    public static int mapHeight = RobotPlayer.mapHeight;
    public static int mapWidth = RobotPlayer.mapWidth;
    
    private MapLocation start;
    private MapLocation goal;
    
    private MapLocation[] vertices; // a list of vertices in order of added
    private MapLocation[][] getParent; // getParent[x][y] returns the MapLocation that is the parent of (x,y)
    private int numVertices = 0;
    private Path[][] pathTo = new Path[mapWidth][mapHeight];
    
    private static MapLocation[] openLocs = MapBuilder.openLocs;
    private static MapLocation[] roadLocs = MapBuilder.roadLocs;
    
    public RRT(MapLocation start, MapLocation goal) {
        this.start = start;
        this.goal = goal;
        
        vertices = new MapLocation[mapWidth*mapHeight];
        getParent = new MapLocation[mapWidth][mapHeight];
        vertices[0] = start;
        numVertices = 1;
    }
    
    // TODO: make a way of prettily printing out the path that the RRT gets for debugging purposes
    
    public static MapLocation[] getPath(MapLocation start, MapLocation goal) {
        return getPath(start, goal, 5);
    }
    
    public static MapLocation[] getPath(MapLocation start, MapLocation goal, int range) {
        RRT tree = new RRT(start, goal);
        
        int openLen = openLocs.length;
        
        MapLocation newLoc = start;
        
        while (newLoc.distanceSquaredTo(goal) > range) {
            MapLocation randomLoc;
            int spread = 20;
            int threshold = 5;
            int loc = rand.nextInt(100);
            if (loc < 30) { // near goal
                randomLoc = new MapLocation(goal.x + (rand.nextInt(spread) - rand.nextInt(spread)), goal.y + rand.nextInt(spread) - rand.nextInt(spread));
            } else if (loc < 30) { // near start
                randomLoc = new MapLocation(start.x + (rand.nextInt(spread) - rand.nextInt(spread)), start.y + rand.nextInt(spread) - rand.nextInt(spread));
            } else { // random
                //int bc = Clock.getBytecodeNum();
                randomLoc = openLocs[rand.nextInt(openLen)];
                //System.out.println("openLocs[rand.nextInt(openLen)] used " + (Clock.getBytecodeNum() - bc) + " bc");
                //randomLoc = Util.randomLoc(); // why does this line work better than choosing an openLoc?????
                //System.out.println("Util.randomLoc() used " + (Clock.getBytecodeNum() - bc) + " bc");
            }
            // TODO: check that randomLoc is not in an obstacle
            MapLocation near = tree.nearestVertex(randomLoc);
            MapLocation[] feasible = feasible(near, randomLoc);
            
            if (feasible != null && !near.equals(randomLoc)) {
                newLoc = randomLoc;
                tree.add(newLoc, near, feasible);                
            }
        }
        // after the loop breaks newLoc is equal to the location within range of the goal
        MapLocation next = newLoc;
        MapLocation[] path = tree.pathTo[newLoc.x][newLoc.y].path;
        MapLocation parent = tree.getParent[newLoc.x][newLoc.y];
        MapLocation[] nextPath = tree.pathTo[parent.x][parent.y].path; 
        
        while (parent != null && !parent.equals(start)) {
            nextPath = tree.pathTo[parent.x][parent.y].path;
            path = Util.join(nextPath, path);
            next = parent;
            parent = tree.getParent[next.x][next.y];
        }
        
        return path;
    }
    
    /**
     * Sets pathTo, isVertex and isParent appropriately.
     * @param child - the new map location to be added
     * @param parent - must already be a vertex
     */
    public void add(MapLocation child, MapLocation parent, MapLocation[] path) {
        // adds to vertices and increments numVertices
        vertices[numVertices] = child;
        numVertices += 1;
        int x = child.x;
        int y = child.y;
        // adds parent
        getParent[x][y] = parent;
        // adds path from parent to child
        pathTo[x][y] = new Path(path);
    }
    
    /**
     * Gets the nearest vertex to a given maplocation
     */
    public MapLocation nearestVertex(MapLocation child) {
        //System.out.println("numVertices = " + numVertices + " and vertices = " + vertices[0] + " " + vertices[1]);
        MapLocation nearest = vertices[numVertices-1];
        int nearestDist = nearest.distanceSquaredTo(child);
        for (int i = numVertices-2; i >= 0; i--) {
            MapLocation next = vertices[i];
            int nextDist = next.distanceSquaredTo(child);
            if (nextDist < nearestDist) {
                nearest = next;
                nearestDist = nextDist;
            }
        }
        return nearest;
    }
    
    /**
     * returns the beeline path from the parent to the child as a list of MapLocations.
     * If there is no path it returns null.
     * 
     * @param parent
     * @param child
     * @return
     */
    public static MapLocation[] feasible(MapLocation parent, MapLocation child) {
        //TODO: this seems to be allowing paths that pass through obstacles
        int guessLength = 200;
        
        MapLocation next = parent;
        MapLocation[] path = new MapLocation[guessLength];
        
        boolean canMove = true;
        
        for (int i = 0; i < guessLength && canMove; i++) {
            
            next = next.add(next.directionTo(child));

            int nextInt = MapBuilder.readMap(next);
            // check that the next location is either empty or a road
            canMove = (nextInt == 0 || nextInt == 3);
            
            if (canMove) {
                path[i] = next;
                if (next.equals(child)) {
                    path = Util.trimNullPoints(path, i+1);
                    return path;
                }
            }
            

        }
        return null;
    }
    
//    public static MapLocation nextLoc() {
//        MapLocation next = new MapLocation(rand.nextInt(mapWidth), rand.nextInt(mapHeight));
//        return next;
//    }
    
    public static String stringPath(MapLocation[] path) {
        int[][] gamemap = gameMap.clone();
        for (MapLocation point : path) {
            //System.out.println(point != null);
            if (point != null) {
                gamemap[point.x][point.y] = -1;
            }
        }
        
        String[] map = new String[mapWidth];
        int terrain = 1;
        for (int y = 0; y < mapHeight; y++) {
            String row = "";
            for (int x = 0; x < mapWidth; x++) {
                //MapLocation m = new MapLocation(x,y);
                //terrain = readMap(m);
                terrain = gamemap[x][y];
                String c = "";
                switch (terrain) {
                case -1 : c ="*"; break;
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
    
    private class Path {
        
        public MapLocation[] path;
        
        public Path(MapLocation[] path) {
            this.path = path;
        }        
    }
    
    
}
