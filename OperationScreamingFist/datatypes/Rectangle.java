package OperationScreamingFist.datatypes;

import OperationScreamingFist.RobotPlayer;
import battlecode.common.*;
import OperationScreamingFist.actions.*;

public class Rectangle {
    
    public static Direction[] directions = Util.directions;
    
    public int xMin;
    public int xMax;
    public int yMin;
    public int yMax;
    
    
    public Rectangle(MapLocation m) {
        this.xMin = m.x;
        this.xMax = m.x+1;
        this.yMin = m.y;
        this.yMax = m.y+1;
    }
    public Rectangle(int xMin, int xMax, int yMin, int yMax) {
        this.xMin = xMin;
        this.xMax = xMax;
        this.yMin = yMin;
        this.yMax = yMax;
    }
    
    /**
     * 
     * @param dir
     * @param rectangles
     * @return true if successfully expanded, false if not
     */
    public boolean expand(Direction dir, RectangleList rectangles) {
        int axisMin=0; int axisMax=0; int extreme=0; int delta=0; boolean swap = false;
        switch (dir) {
        case NORTH :  axisMin = xMin; axisMax = xMax; extreme = yMin; delta = -1; break;
        case EAST : axisMin = yMin; axisMax = yMax; extreme = xMax; delta = 1; swap = true; break;
        case SOUTH : axisMin = xMin; axisMax = xMax; extreme = yMax; delta = 1; break;
        case WEST : axisMin = yMin; axisMax = yMax; extreme = xMin; delta = -1; swap = true; break;
        default: System.out.println("Rectangle.expand(): Must be one of NESW"); break;
        }

        boolean canAdd = true;

        for (int i = axisMin; i < axisMax; i++) {
            if (swap) { // if expanding in an x direction
                canAdd = canAdd && canAddLoc(extreme + delta, i, rectangles);
            }
            else { // if expanding in a y direction
                canAdd = canAdd && canAddLoc(i,extreme + delta, rectangles);
            }
        }
        
        if (canAdd) {
            switch (dir) {
            case NORTH : this.yMin += delta; break;
            case EAST : this.xMax  += delta; break;
            case SOUTH : this.yMax += delta; break;
            case WEST : this.xMin  += delta; break;
            default: System.out.println("Rectangle.expand(): can't add that direction");
            }
        } else {
            return false;
        }

        return true;
    }
    
    /**
     * returns true if the given map location does not correspond to an obstacle
     * @param m
     * @return
     * @throws GameActionException 
     */
    public static boolean canAddLoc(MapLocation m, RectangleList rects) {
        boolean obstacle = (MapBuilder.readMap(m)==2);
        boolean canAdd = true;
        if (obstacle) {
            return false;
        } else {
            for (int i = 0; i < rects.size(); i++) {
                canAdd = canAdd && !(rects.get(i).contains(m));
                if (!canAdd) {
                    break;
                }
            }
        }
        return canAdd;
    }
    public static boolean canAddLoc(int x, int y, RectangleList rects) {
        boolean obstacle = (MapBuilder.readMap(x,y)==2);
        boolean canAdd = true;
        if (obstacle) {
            return false;
        } else {
            for (int i = 0; i < rects.size(); i++) {
                canAdd = canAdd && !(rects.get(i).contains(x,y));
                if (!canAdd) {
                    break;
                }
            }
        }
        return canAdd;
    }
    
    public boolean contains(MapLocation m) {
        return (xMin <= m.x && m.x < xMax && yMin <= m.y && m.y < yMax);
    }
    public boolean contains(int x, int y) {
        return (xMin <= x && x < xMax && yMin <= y && y < yMax);
    }
        
    public MapLocation center() {
        return new MapLocation((xMin + xMax)/2, (yMin + yMax)/2);
    }
    public int area() {
        return ((xMax-xMin)*(yMax-yMin));
    }
    
    @Override
    public String toString() {
        return "Rectangle [xMin=" + xMin + ", xMax=" + xMax + ", yMin=" + yMin
                + ", yMax=" + yMax + "]";
    }
    
    

}
