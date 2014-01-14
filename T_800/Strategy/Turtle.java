package T_800.Strategy;

import T_800.Util;
import battlecode.common.*;

public class Turtle implements Strategy {
    
    private RobotController rc;
    
    public Turtle(RobotController rc) {
        this.rc = rc;
    }

    @Override
    public Strategies getStrategy() {
        return Strategies.TURTLE;
    }

    @Override
    public void runHQ() throws GameActionException {
        // TODO
        // set new orders for team
            // if there is no pastr and/or noisetower near hq, get a soldier to build one
        Robot[] nearby = rc.senseNearbyGameObjects(Robot.class, 1, rc.getTeam().opponent());
                // check if there is a noisetower within squareradius of 1
        if (Util.containsRobotOfType(RobotType.NOISETOWER, nearby)) {
                    // if so, check if there is a pastr within squareradius of 1
            if (Util.containsRobotOfType(RobotType.PASTR, nearby)) {
            } else {
                        // if not, tell next soldier within sqrad=1 to construct pastr
            }
        } else {
                    // if not, tell next soldier within sqrad=1 to construct noistwr
        }
        // then, set new goal location for swarm

    }

    @Override
    public void runSOLDIER() {
        // TODO
        // read broadcasted orders from HQ
        
        // if within 1 square of HQ
            // check if hq needs noistwr or pastr
                // if so, construct
        // else go to goal location with swarm

    }

    @Override
    public void runPASTR() {
        // chill my neezy

    }

    @Override
    public void runNOISETOWER() {
        // TODO
        // radially drag in cows to the PASTR

    }

}
