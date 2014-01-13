package T_800.Strategy;

import battlecode.common.RobotController;

public class Turtle implements Strategy {

    @Override
    public Strategies getStrategy() {
        return Strategies.TURTLE;
    }

    @Override
    public void runHQ(RobotController runThis) {
        // set new goal location for the swarm

    }

    @Override
    public void runSOLDIER(RobotController runThis) {
        // TODO Auto-generated method stub

    }

    @Override
    public void runPASTR(RobotController runThis) {
        // TODO Auto-generated method stub

    }

    @Override
    public void runNOISETOWER(RobotController runThis) {
        // TODO Auto-generated method stub

    }

}
