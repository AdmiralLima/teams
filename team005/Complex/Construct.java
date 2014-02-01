package team005.Complex;

import team005.Comm;
import team005.RobotPlayer;
import team005.Util;
import battlecode.common.*;

public class Construct {
    
    private static RobotController rc = RobotPlayer.rc;

    public static boolean needConstructs() throws GameActionException {
        Robot[] nearby = rc.senseNearbyGameObjects(Robot.class, 4, rc.getTeam());
        Robot soldier = Util.getARobotOfType(RobotType.SOLDIER, nearby);
        
        if (!Util.containsRobotOfType(RobotType.PASTR, nearby)) {
            if (soldier != null) {
                Comm.orderConstruct(soldier, RobotType.PASTR);
                return true; 
            }
        } else if (!Util.containsRobotOfType(RobotType.NOISETOWER, nearby)) {
            if (soldier != null) {
                System.out.println("ordering noisetower");
                Comm.orderConstruct(soldier, RobotType.NOISETOWER);
                return true;
            }
        }
            
        return false;
    }
    
}
