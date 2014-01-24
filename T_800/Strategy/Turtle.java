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
