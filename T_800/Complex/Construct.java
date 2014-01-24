package T_800.Complex;

import T_800.Comm;
import T_800.RobotPlayer;
import T_800.Util;
import battlecode.common.*;

public class Construct {
    
    private static RobotController rc = RobotPlayer.rc;

    public static boolean needConstructs() throws GameActionException {
        Robot[] nearby = rc.senseNearbyGameObjects(Robot.class, 2, rc.getTeam());
        Robot soldier = Util.getARobotOfType(RobotType.SOLDIER, nearby);
        
        if (Util.containsRobotOfType(RobotType.NOISETOWER, nearby)) {
                    // if so, check if there is a pastr within squareradius of 1
            if (Util.containsRobotOfType(RobotType.PASTR, nearby)) {
            } else {
                // if not, tell next soldier within sqrad=1 to construct pastr
                if (soldier != null) {
                    Comm.orderConstruct(soldier, RobotType.PASTR);
                    return true;
                }
            }
        } else {
            // if not, tell next soldier within sqrad=1 to construct noistwr
            if (soldier != null) {
                Comm.orderConstruct(soldier, RobotType.NOISETOWER);
                return true;
            }
        }
        return false;
    }
    
}
