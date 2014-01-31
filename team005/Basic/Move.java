package team005.Basic;

import team005.RobotPlayer;
import team005.Util;
import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.RobotController;

public class Move {

private static RobotController rc = RobotPlayer.rc;
private static int[] looks = Util.directionalLooks;
    
    /**
     * Tries to move in given direction
     * 
     * @param Direction to be moved in
     * @return boolean - Returns true if the robot moved, false otherwise.
     * @throws GameActionException
     */
    public static boolean move(Direction dir) throws GameActionException
    {        
        if (dir.equals(Direction.NONE) || dir.equals(Direction.OMNI)) {
            return false;
        }
        for (int look : looks) {
            Direction movedir = dir;
            // If we can move we do
            switch (look) {
            case 0 : {movedir = dir; break;}
            case 1 : {movedir = dir.rotateRight(); break;}
            case -1 : {movedir = dir.rotateLeft(); break;}
            case 2 : {movedir = dir.rotateRight().rotateRight(); break;}
            case -2 : {movedir = dir.rotateLeft().rotateLeft(); break;}
            default : {return false; }
            }
            if (rc.canMove(movedir)) {
                rc.move(movedir);
                return true;
            }
        }
        return false;
    }
    
}
