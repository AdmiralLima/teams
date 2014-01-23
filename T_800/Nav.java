package T_800;

import java.util.HashMap;

import T_800.*;
import battlecode.common.*;

public class Nav {
    
    private static RobotController rc = T_800.RobotPlayer.rc;
    
    private static HashMap<Robot, MapLocation[]> paths = new HashMap<Robot, MapLocation[]>(20);
    
    public static void addRobot(Robot soldier, MapLocation[] waypoints) {
        paths.put(soldier, waypoints);
    }
    
    public static MapLocation[] getWaypoints(MapLocation current, MapLocation goal) {
        return RRT.getPath(RobotPlayer.tree, current, goal);
    }
    
    public static MapLocation[] getPath(MapLocation start, MapLocation goal) {
        // use RRT
        //return RRT.getPath(start, goal);
        return new MapLocation[1];
    }
    
    public static void followPath(MapLocation[] path) throws GameActionException {
        int i =0;
        while (i < path.length) {
            if (rc.isActive()) {
                //System.out.println("At step " + i + " of " + path.length + " going to " + path[i].toString());
            Direction next = rc.getLocation().directionTo(path[i]);
            rc.setIndicatorString(0, "next dir is " + next.toString());
            T_800.Basic.Move.move(next);
            i++;
            rc.yield();
            }
        }
    }

}
