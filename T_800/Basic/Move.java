package T_800.Basic;

import T_800.RobotPlayer;
import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.RobotController;

public class Move {

private static RobotController rc = RobotPlayer.rc;
    
    /**
     * Tries to move in given direction
     * 
     * @param Direction to be moved in
     * @return boolean - Returns true if the robot moved, false otherwise.
     * @throws GameActionException
     */
    public static boolean move(Direction dir) throws GameActionException
    {        
        // If we can attack the location we do.
        if (rc.canMove(dir))
        {
            rc.move(dir);
            return true;
        }
        return false;
    }
    
}
