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
                    // if not, tell next soldier within sqrad=1 to construct noistwr
                    // if so, check if there is a pastr within squareradius of 1
                        // if not, tell next soldier within sqrad=1 to construct pastr
            // else, set new goal location for swarm

    }

    @Override
    public void runSOLDIER() {
        // read broadcasted orders from HQ
        // if within 1 square of HQ
            // check if hq needs noistwr or pastr
                // if so, construct
        // else go to goal location

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
