package team005.Strategy;

import team005.Comm;
import team005.MapBuilder;
import team005.RRT;
import team005.Strategy.Strategy.Strategies;
import battlecode.common.*;

public class Aggressive implements Strategy {
    
    private RobotController rc;

    public Aggressive(RobotController rc) {
        this.rc = rc;
    }
    
    @Override
    public Strategies getStrategy() {
        return Strategies.AGGRESSIVE;
    }

    @Override
    public void runHQ() throws GameActionException {
        team005.Complex.Construct.needConstructs();
        //Comm.orderAllMove(rc.senseEnemyHQLocation());
        
        if (Comm.RRTReady(rc.getRobot()) && Clock.getRoundNum() > 200) {
            MapLocation[] pastrs = rc.sensePastrLocations(rc.getTeam().opponent());
            if (pastrs.length > 1) {
                Comm.orderAllMove(pastrs[pastrs.length-1]);
            }
        } else {
            MapLocation enemy = rc.senseEnemyHQLocation();
            Comm.orderAllMove(enemy);
        }

        // now spawn guys
        team005.Tactic.AttackAndSpawn.execute();
    }

    @Override
    public void runSOLDIER() throws GameActionException {
        if (!team005.Tactic.AggressiveMicro.execute()) {
            if (rc.isActive())
            {
                Comm.SoldierFollowOrders();
                rc.yield();
                if (rc.isActive()) {
                    team005.Complex.Swarm.swarm();
                }
            }
        }
    }

    @Override
    public void runPASTR() throws GameActionException {
     // chill my neezy
        Comm.PASTRReadMessages();
    }

    @Override
    public void runNOISETOWER() throws GameActionException {
        int max = (int) Math.sqrt(RobotType.NOISETOWER.attackRadiusMaxSquared);
        int min = (int) Math.sqrt(RobotType.NOISETOWER.attackRadiusMinSquared);
        MapLocation here = rc.getLocation();
        MapBuilder.buildMap();
        for (Direction dir : MapBuilder.directions) {
            for (int rad = max; rad >= 3; rad--) {
                MapLocation attackThis = here.add(dir, rad);
                MapLocation[] feasible = RRT.feasible(attackThis, here);
                if (rc.canAttackSquare(attackThis) && rc.isActive() && (feasible != null)) {
                    rc.attackSquare(attackThis);
                    //System.out.println(max + ", " + min);
                    rc.yield();
                    while (rc.getActionDelay() > 0) {}
                }
            }
        }
    }

}
