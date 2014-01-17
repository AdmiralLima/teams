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
        
        //MapLocation newGoal = new MapLocation(15,15);
        MapLocation newGoal = rc.senseEnemyHQLocation();
        T_800.RobotPlayer.goal = newGoal;
        
        /////////////////////////////////

        if (!oldGoal.equals(newGoal) || T_800.RobotPlayer.newUnits) {
            Comm.orderMove(RobotType.SOLDIER, newGoal);
        }
        /*
        // set new orders for team
            // if there is no pastr and/or noisetower near hq, get a soldier to build one
        Robot[] nearby = rc.senseNearbyGameObjects(Robot.class, 2, rc.getTeam());
                // check if there is a noisetower within squareradius of 1
        //for (Robot robot : nearby) {System.out.println(robot.toString());}
        Robot soldier = Util.getARobotOfType(RobotType.SOLDIER, nearby);
        
        if (Util.containsRobotOfType(RobotType.NOISETOWER, nearby)) {
                    // if so, check if there is a pastr within squareradius of 1
            if (Util.containsRobotOfType(RobotType.PASTR, nearby)) {
            } else {
                // if not, tell next soldier within sqrad=1 to construct pastr
                if (soldier != null) {
                    Comm.orderConstruct(soldier, RobotType.PASTR);
                }
            }
        } else {
            // if not, tell next soldier within sqrad=1 to construct noistwr
            if (soldier != null) {
                Comm.orderConstruct(soldier, RobotType.NOISETOWER);
            }
        }
        */
        
        // now spawn guys
        T_800.Tactic.AttackAndSpawn.execute();
    }

    @Override
    public void runSOLDIER() throws GameActionException {
        // TODO
        Comm.followOrders();
        
    }

    @Override
    public void runPASTR() throws GameActionException {
        // chill my neezy

    }

    @Override
    public void runNOISETOWER() throws GameActionException {
        // TODO
        // radially drag in cows to the PASTR
        int maxDistance = (int) Math.sqrt(RobotType.NOISETOWER.attackRadiusMaxSquared);
        int currentDistance = maxDistance;
        Direction direction = Direction.NORTH_EAST;
        
        MapLocation attackThis = rc.getLocation().add(direction, currentDistance);
        // How do we herd?
        if (currentDistance > 1)
        {
            if (rc.canAttackSquare(attackThis))
            {
                rc.attackSquare(attackThis);
            }
            currentDistance = currentDistance - 2;
            rc.yield();
            runNOISETOWER();
        }
        
        // Now we need to reset our herding.
        else 
        {
            currentDistance = maxDistance;
            direction = direction.rotateRight();
            rc.yield();
            this.runNOISETOWER();
        }
    }

}
