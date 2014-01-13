package T_800.Strategy;

import battlecode.common.RobotController;

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
    public void runHQ() {
        // TODO: set new orders for team
            // if there is no pastr and/or noisetower near hq, get a soldier to build one
                // check if there is a noisetower within squareradius of 1
                    // if not, tell next soldier built to make one
                    // if so, check if there is a pastr within squareradius of 1
                        // if not, tell next soldier built to make one
            // else, set new goal location for swarm

    }

    @Override
    public void runSOLDIER() {
        // read broadcasted orders from HQ

    }

    @Override
    public void runPASTR() {
        // TODO Auto-generated method stub

    }

    @Override
    public void runNOISETOWER() {
        // TODO Auto-generated method stub

    }

}
