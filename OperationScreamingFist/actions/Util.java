package OperationScreamingFist.actions;

import OperationScreamingFist.RobotPlayer;
import battlecode.common.*;

public class Util {

    /**
     * Util contains very basic utility methods and other miscellaneous things that 
     * don't fit well elsewhere
     */
    
    public static int locToInt(MapLocation m) {
        return (m.x*100 +m.y);
    }
    
    public static MapLocation intToLoc(int i) {
        return new MapLocation(i/100, i%100);
    }
    
}
