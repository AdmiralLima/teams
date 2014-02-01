package team005.Tactic;

import team005.RobotPlayer;
import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import battlecode.common.Robot;
import battlecode.common.RobotController;
import battlecode.common.RobotType;

public class AggressiveMicro {
    
private static RobotController rc = RobotPlayer.rc;
    
    public static boolean execute() throws GameActionException
    {
        // Find friends and enemies
        Robot[] enemies = rc.senseNearbyGameObjects(Robot.class, RobotType.SOLDIER.sensorRadiusSquared, rc.getTeam().opponent());
        Robot[] friends = rc.senseNearbyGameObjects(Robot.class, RobotType.SOLDIER.sensorRadiusSquared, rc.getTeam());
        
        if (enemies.length == 0 && friends.length < 2)
        {
             // Maybe we should build a PASTR.
            MapLocation me = rc.getLocation();
                if (rc.senseCowGrowth()[me.x][me.y] > 0.99)
                {
                    MapLocation[] PASTRs = rc.sensePastrLocations(rc.getTeam());
                    if (PASTRs.length < 2 && (RobotPlayer.rand.nextInt(10) < 1)) {
                        rc.construct(RobotType.PASTR);
                        return true;
                    } else {
                        return false;
                    }
                }
        }
        
        // We need to know number of SOLDIER
        int enemyS = 0;
        int friendlyS = 1;
        for (Robot enemy : enemies)
        {
            if (rc.senseRobotInfo(enemy).type == RobotType.SOLDIER)
            {
                enemyS ++;
            }
        }
        for (Robot friend : friends)
        {
            if (rc.senseRobotInfo(friend).type == RobotType.SOLDIER)
            {
                friendlyS ++;
            }
        }       

        // Run away if injured
        if (rc.getHealth() < RobotType.SOLDIER.maxHealth*0.1)
        {
            if (enemies.length > 0)
            {
                if (team005.Basic.Move.move(rc.getLocation().directionTo(rc.senseRobotInfo(enemies[0]).location).opposite()))
                {
                    
                    return true;
                }
            }
        }
        
        // Fight or flight
        if (friendlyS >= enemyS - 1)
        {
            
            // Fight
            if (!team005.Complex.Attack.attackWeakest())
            {
                if (enemies.length > 0)
                {
                    if (team005.Basic.Move.move(rc.getLocation().directionTo(rc.senseRobotInfo(enemies[0]).location)))
                    {
                        return true;
                    }
                }
                return false;
            }

        }
        else
        {
            
            // Flight
            if (enemies.length > 0)
            {
                if (team005.Basic.Move.move(rc.getLocation().directionTo(rc.senseRobotInfo(enemies[0]).location).opposite()))
                {
                    return true;
                }
            }
        }
        return false;
    }

}
