package T_800;

import T_800.*;
import battlecode.common.*;

public class Nav {
    
    private static RobotController rc = T_800.RobotPlayer.rc;
    
    public static MapLocation[] getPath(MapLocation start, MapLocation goal) {
        // use RRT
        return RRT.getPath(start, goal);
    }
    
    public static void followPath(MapLocation[] path) throws GameActionException {
        for (int i = 0; i < path.length && rc.isActive(); i++) {
            Direction next = rc.getLocation().directionTo(path[i]);
            rc.setIndicatorString(0, "next dir is " + next.toString());
            T_800.Basic.Move.move(next);
            rc.yield();
        }
    }

}
