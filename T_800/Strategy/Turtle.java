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
        T_800.Complex.Construct.needConstructs();
        //Comm.orderAllMove(rc.senseEnemyHQLocation());
        
        if (Clock.getRoundNum() > 120) {
            MapLocation[] pastrs = rc.sensePastrLocations(rc.getTeam().opponent());
            if (pastrs.length > 0) {
                Comm.orderAllMove(pastrs[0]);
            }
        }

        // now spawn guys
        T_800.Tactic.AttackAndSpawn.execute();
    }

    @Override
    public void runSOLDIER() throws GameActionException {
        // TODO
        if (!T_800.Tactic.Micro.execute()) {
        	if (rc.isActive())
        	{
        		Comm.SoldierFollowOrders();
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
        // TODO
        int max = (int) Math.sqrt(RobotType.NOISETOWER.attackRadiusMaxSquared);
        int min = (int) Math.sqrt(RobotType.NOISETOWER.attackRadiusMinSquared);
        MapLocation here = rc.getLocation();
        MapBuilder.buildMap();
        for (Direction dir : MapBuilder.directions) {
            for (int rad = max; rad >= 0; rad--) {
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
