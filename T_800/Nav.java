package T_800;

import java.util.HashMap;

import T_800.*;
import battlecode.common.*;

public class Nav {
    
    private static RobotController rc = T_800.RobotPlayer.rc;
    
    private static HashMap<Robot, MapLocation[]> paths = new HashMap<Robot, MapLocation[]>(30);
    private static HashMap<Robot, Integer> steps = new HashMap<Robot, Integer>(30);
    
    public static void addRobot(Robot soldier, MapLocation[] waypoints) {
        paths.put(soldier, waypoints);
        steps.put(soldier, Integer.valueOf(0));
    }
    
    public static MapLocation[] getWaypoints(MapLocation current, MapLocation goal) {
        return RRT.getPath(RobotPlayer.tree, current, goal);
    }
    
    /**
     * PASTR uses this to give the next waypoint to a soldier
     * @param soldier
     * @throws GameActionException 
     */
    public static MapLocation nextWaypoint(Robot soldier) throws GameActionException {
        MapLocation[] waypoints = paths.get(soldier);
        Integer stepInt = steps.get(soldier);
        if (stepInt == null) {
            return null;
        }
        int step = stepInt.intValue();
        if (step < waypoints.length-1) {
            step++;
            steps.put(soldier, Integer.valueOf(step));
            return waypoints[step]; 
        } else {
            Protocol.broadcastToRobot(soldier, 0, "reached destination");
            return null;
        }
    }

}
