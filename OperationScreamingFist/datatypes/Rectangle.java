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
        this.xMax = m.x;
        this.yMin = m.y;
        this.yMax = m.y;
    }
    public Rectangle(int xMin, int xMax, int yMin, int yMax) {
        this.xMin = xMin;
        this.xMax = xMax;
        this.yMin = yMin;
        this.yMax = yMax;
    }
    
    public boolean expand(Direction dir, RectangleList rectangles) {
        int axisMin=0; int axisMax=0; int extreme=0; int delta=0;
        switch (dir) {
        case NORTH :  axisMin = xMin; axisMax = xMax; extreme = yMin; delta = -1; break;
        case NORTH_EAST : if (canAddLoc(xMax+1, yMin-1, rectangles)) {
            if (expand(Direction.NORTH, rectangles) && expand(Direction.EAST, rectangles)) {return true;} else {return false;}
        } else {return false;}
        case EAST : axisMin = yMin; axisMax = yMax; extreme = xMax; delta = 1; break;
        case SOUTH_EAST : if (canAddLoc(xMax+1, yMax+1, rectangles)) {
            if (expand(Direction.SOUTH, rectangles) && expand(Direction.EAST, rectangles)) {return true;} else {return false;}
        } else {return false;}
        case SOUTH : axisMin = xMin; axisMax = xMax; extreme = yMax; delta = 1; break;
        case SOUTH_WEST : if (canAddLoc(xMin-1, yMax+1, rectangles)) {
            if (expand(Direction.SOUTH, rectangles) && expand(Direction.WEST, rectangles)) {return true;} else {return false;}
        } else {return false;}
        case WEST : axisMin = yMin; axisMax = yMax; extreme = xMin; delta = -1; break;
        case NORTH_WEST : if (canAddLoc(xMin-1, yMin-1, rectangles)) {
            if (expand(Direction.NORTH, rectangles) && expand(Direction.WEST, rectangles)) {return true;} else {return false;}
        } else {return false;}
        }
        
        boolean canAdd = true;
        
        for (int i = axisMin; i <= axisMax; i++) {
            canAdd = canAdd && canAddLoc(i,extreme + delta, rectangles);
        }
        if (canAdd) {
            switch (dir) {
            case NORTH : this.yMin = extreme + delta; break;
            case EAST : this.xMax = extreme + delta; break;
            case SOUTH : this.yMax = extreme + delta; break;
            case WEST : this.xMin = extreme + delta; break;
            default: System.out.println("can't add that direction");
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
        boolean obstacle = MapBuilder.readMap(m)==2;
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
        boolean obstacle = MapBuilder.readMap(x,y)==2;
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
        return (xMin <= m.x && m.x <= xMax && yMin <= m.y && m.y <= yMax);
    }
    public boolean contains(int x, int y) {
        return (xMin <= x && x <= xMax && yMin <= y && y <= yMax);
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
