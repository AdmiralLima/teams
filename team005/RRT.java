package team005;

import java.util.Random;

import battlecode.common.*;

public class RRT {
    
    private static RobotController rc = RobotPlayer.rc;
    public static Random rand = RobotPlayer.rand;
    public static int[][] gameMap = MapBuilder.gameMap;
    public static int mapHeight = RobotPlayer.mapHeight;
    public static int mapWidth = RobotPlayer.mapWidth;
    
    public MapLocation start;
    
    public MapLocation[] vertices; // a list of vertices in order of added
    public MapLocation[][] getParent; // getParent[x][y] returns the MapLocation that is the parent of (x,y)
    public int numVertices = 0;
    public Path[][] pathTo = new Path[mapWidth][mapHeight];
    
    public static MapLocation[] openLocs = MapBuilder.openLocs;
    public static MapLocation[] roadLocs = MapBuilder.roadLocs;
    
    public RRT(MapLocation start, int iterations) {
        this.start = start;
        
        vertices = new MapLocation[mapWidth*mapHeight];
        getParent = new MapLocation[mapWidth][mapHeight];
        vertices[0] = start;
        numVertices = 1;
        
        buildTree(this, iterations);
    }
    
    public static RRT buildTree(RRT tree, int iterations) {
        int openLen = openLocs.length;
        
        int count = 0;
        
        MapLocation newLoc = tree.start;
        
        while (count < iterations) {
            MapLocation randomLoc = openLocs[rand.nextInt(openLen)];
            // TODO: check that randomLoc is not in an obstacle
            MapLocation near = tree.nearestVertex(randomLoc);
            MapLocation[] feasible = feasible(near, randomLoc);
            
            if (feasible != null && !near.equals(randomLoc)) {
                newLoc = randomLoc;
                tree.add(newLoc, near, feasible);       
                count++;
            }
        }
        
        return tree;
    }
    
//    public static MapLocation[] getPath(MapLocation start, MapLocation goal) {
//        return getPath(start, goal, 5);
//    }
    
    public static MapLocation[] getPath(RRT tree, MapLocation start, MapLocation goal) {
        //System.out.println("start is: " + start.toString());
        // generate goal branch
        MapLocation[] goalBranch = new MapLocation[100];
        goalBranch[0] = goal;
        MapLocation point = tree.nearestVertex(goal);
        // need to check that this is feasible
        int goalcount = 1;
        while (point != null) {
            goalBranch[goalcount] = point;
            point = tree.getParent[point.x][point.y];
            goalcount++;
        }
        goalBranch = Util.trimNullPoints(goalBranch, goalcount);
        
        // initialise waypoints and get first vertex
        MapLocation[] waypoints = new MapLocation[100];
        int count = 0;
        MapLocation nearest = tree.nearestVertex(start);
        MapLocation[] feasible = feasible(nearest, start);
        
        if (feasible != null) {
            //System.out.println("nearest is " + nearest.toString() + " and is feasible");
            waypoints[count] = nearest;
            count++;
        } else {
            //System.out.println("RRT.getPath(): nearest vertex can't be reached");
        }
        
        MapLocation near = nearest;    
        while (near!=null) {
            // add near to waypoints
            waypoints[count] = near;
            count++;
            
            // get nearest vertex on goal branch
            int nearGoalIndex = tree.nearestVertexIndex(goalBranch, near);
            MapLocation nearGoal = goalBranch[nearGoalIndex];
            //System.out.println("trying nearGoal: " + nearGoal.toString());
            
            // check if it can be reached
            MapLocation[] crossBranchFeasible = feasible(near, nearGoal);
            if (crossBranchFeasible != null) { // can be reached, so add it to the waypoints
                //System.out.println("success!");
                waypoints[count] = nearGoal;
                count++;
                for (int i = nearGoalIndex; i >= 0 ; i--) { // then add the rest of the vertices on the branch leading up to the goal
                    waypoints[count] = goalBranch[i];
                    count++;
                }
                break;
            } else {
                // set new near further back up current branch
                near = tree.getParent[near.x][near.y];
                //System.out.println("failure. trying near: " + near.toString());
            }
        }
        
        return Util.trimNullPoints(waypoints, count);
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
     * Gets the nearest vertex to a given maplocation, on a given branch
     */
    public MapLocation nearestVertex(MapLocation[] branch, MapLocation child) {
      //System.out.println("numVertices = " + numVertices + " and vertices = " + vertices[0] + " " + vertices[1]);
        MapLocation nearest = branch[0];
        int nearestDist = nearest.distanceSquaredTo(child);
        for (MapLocation next : branch) {
            int nextDist = next.distanceSquaredTo(child);
            if (nextDist < nearestDist) {
                nearest = next;
                nearestDist = nextDist;
            }
        }
        return nearest;
    }
    public int nearestVertexIndex(MapLocation[] branch, MapLocation child) {
        //System.out.println("numVertices = " + numVertices + " and vertices = " + vertices[0] + " " + vertices[1]);
          int nearest = 0;
          int nearestDist = branch[nearest].distanceSquaredTo(child);
          for (int i = 1; i < branch.length; i++) {
              MapLocation next = branch[i];
              int nextDist = next.distanceSquaredTo(child);
              if (nextDist < nearestDist) {
                  nearest = i;
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
        if (parent.equals(child)) {
            return new MapLocation[] {parent};
        }
        //TODO: this seems to be allowing paths that pass through obstacles
        int guessLength = 100;
        
        MapLocation next = parent;
        MapLocation[] path = new MapLocation[guessLength];
        
        boolean canMove = true;
        
        for (int i = 0; i < guessLength && canMove; i++) {
            
            next = next.add(next.directionTo(child));

            int nextInt = MapBuilder.readMap(next);
            // check that the next location is either empty or a road
            canMove = ((nextInt == 0) || (nextInt == 3));
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
