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
        MapLocation oldGoal = T_800.RobotPlayer.goal;
        
        /////// set new goal here ///////
        
        MapLocation newGoal = new MapLocation(15,15);
        T_800.RobotPlayer.goal = newGoal;
        
        /////////////////////////////////

        if (!oldGoal.equals(newGoal) || T_800.RobotPlayer.newUnits) {
            //System.out.println("HQ broadcasting new goal at " + newGoal.toString());
            // set new goal location for swarm (set to [15,15] for now)
            //int bc = Clock.getBytecodeNum();
            Protocol.broadcastToRobotsOfType(RobotType.SOLDIER, "go to location", newGoal);
            //System.out.println("used " + (Clock.getBytecodeNum() - bc) + " bc");
        }
        
        // set new orders for team
            // if there is no pastr and/or noisetower near hq, get a soldier to build one
        Robot[] nearby = rc.senseNearbyGameObjects(Robot.class, 2, rc.getTeam());
                // check if there is a noisetower within squareradius of 1
        //for (Robot robot : nearby) {System.out.println(robot.toString());}
        Robot soldier = Util.getARobotOfType(RobotType.SOLDIER, nearby);
//        
//        if (soldier != null) {
//            System.out.println("ordering soldier " + soldier.toString());
//        }
        
        if (Util.containsRobotOfType(RobotType.NOISETOWER, nearby)) {
                    // if so, check if there is a pastr within squareradius of 1
            if (Util.containsRobotOfType(RobotType.PASTR, nearby)) {
            } else {
                System.out.println("need PASTR");
                // if not, tell next soldier within sqrad=1 to construct pastr
                if (soldier != null) {
                    System.out.println(soldier.toString() + " could construct PASTR");
                    Protocol.broadcastToRobot(soldier, "construct PASTR");
                }
            }
        } else {
            System.out.println("need Noisetower");
            // if not, tell next soldier within sqrad=1 to construct noistwr
            if (soldier != null) {
                Protocol.broadcastToRobot(soldier, "construct Noisetower");
            }
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
        
//        if (!(orderNum==1)) {
//            System.out.println("Received order: " + order + " " + m + " (order num " + orderNum + ")");
//        }
        
        switch (orderNum) {
        case 0 : {break;}// be ready to attack enemies
        case 1 : { // move toward goal location and swarm
            Direction dir = rc.getLocation().directionTo(m);
            //System.out.println("Moving toward " + m.toString() + " in direction " + dir.toString());
            boolean succeeded = T_800.Basic.Move.move(dir);
            //System.out.println("Succeeded? " + succeeded);
//            rc.yield();
//            if (rc.isActive()) {T_800.Complex.Swarm.swarm();}
            break;
        }
        case 2 : { // construct PASTR
            System.out.println("Constructing PASTR!");
            rc.construct(RobotType.PASTR);
            break;
        }
        case 3 : { // construct Noisetower
            System.out.println("Constructing Noisetower!");
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
