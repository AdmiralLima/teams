package T_800.Strategy;

import T_800.*;
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
        // set new goal location for swarm (set to enemy HQ for now)
        Protocol.broadcastToRobotsOfType(RobotType.SOLDIER, "go to location", new MapLocation(15,15));
        // set new orders for team
            // if there is no pastr and/or noisetower near hq, get a soldier to build one
        Robot[] nearby = rc.senseNearbyGameObjects(Robot.class, 1, rc.getTeam().opponent());
                // check if there is a noisetower within squareradius of 1
        Robot soldier = Util.getARobotOfType(RobotType.SOLDIER, nearby);
        if (Util.containsRobotOfType(RobotType.NOISETOWER, nearby)) {
                    // if so, check if there is a pastr within squareradius of 1
            if (Util.containsRobotOfType(RobotType.PASTR, nearby)) {
            } else {
                        // if not, tell next soldier within sqrad=1 to construct pastr
                Protocol.broadcastToRobot(soldier, "construct Noisetower");
            }
        } else {
                    // if not, tell next soldier within sqrad=1 to construct noistwr
            Protocol.broadcastToRobot(soldier, "construct PASTR");
        }     
    }

    @Override
    public void runSOLDIER() throws GameActionException {
        // TODO
        // read broadcasted orders from HQ
        Pair orders = Protocol.readMessage(rc.getRobot());
        MapLocation m = orders.location;
        String order = orders.message;
        int orderNum = 0;
        if (order.equals("go to location")) {orderNum = 1;}
        else if (order.equals("construct PASTR")) {orderNum = 2;}
        else if (order.equals("construct Noisetower")) {orderNum = 3;}
        
        switch (orderNum) {
        case 0 : {break;}// be ready to attack enemies
        case 1 : { // move toward goal location and swarm
            Direction dir = rc.getLocation().directionTo(m);
            T_800.Basic.Move.move(dir);
            rc.yield();
            if (rc.isActive()) {T_800.Complex.Swarm.swarm();}
            break;
        }
        case 2 : { // construct PASTR
            rc.construct(RobotType.PASTR);
            break;
        }
        case 3 : { // construct Noisetower
            rc.construct(RobotType.NOISETOWER);
            break;
        }
        default : {break;} // chill
        }
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
