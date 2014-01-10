package testshitout;

import OperationScreamingFist.actions.MapBuilder;
import battlecode.common.*;
import java.util.*;

public class RobotPlayer {
        
    public static void run(RobotController rc) {
        
//        int bc = Clock.getBytecodeNum();
//        MapLocation m = new MapLocation(1,1);
//        System.out.println("Used " + (Clock.getBytecodeNum() - bc) +" bc");
//        
//        int bc2 = Clock.getBytecodeNum();
//        int x = 1;
//        int y = 1;
//        System.out.println("Used " + (Clock.getBytecodeNum() - bc2) +" bc");        
//        
                
        while(true) {
            if (rc.getType() == RobotType.HQ) {
                try {   

                } catch (Exception e) {e.printStackTrace(); System.out.println("HQ Exception");}
            }
            
            if (rc.getType() == RobotType.SOLDIER) {
                try {
                    
                } catch (Exception e) {System.out.println("Soldier Exception");}
            }
            
            rc.yield();
        }
    }
}