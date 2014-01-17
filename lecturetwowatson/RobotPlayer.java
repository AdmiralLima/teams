package lecturetwowatson;

//this is the code from the first Battlecode 2014 lecture
//paste this text into RobotPlayer.java in a package called bob
//this code is badly organized. We'll fix it in later lectures.
//you can use this as a reference for how to use certain methods.

import battlecode.common.*;

import java.util.*;

public class RobotPlayer{
    
    public static RobotController rc;
    static Random rand = new Random();
    static MapLocation myLoc;
    
    public static void run(RobotController rcin){
        rc = rcin;
        rand.setSeed(rc.getRobot().getID());
        while(true){
            myLoc = rc.getLocation();
            try {
                if(rc.getType()==RobotType.HQ){//if I'm a headquarters
                    //runHeadquarters();
                }else if(rc.getType()==RobotType.SOLDIER){
                    //runSoldier();
                }
            } catch (Exception e) {e.printStackTrace();}
            rc.yield();
        }
    }
    
    private static void runHeadquarters() throws GameActionException {
        Direction spawnDir = Direction.NORTH;
        if(rc.isActive()&&rc.canMove(spawnDir)&&rc.senseRobotCount()<GameConstants.MAX_ROBOTS){
            rc.spawn(Direction.NORTH);
        }
    }
    
    private static void runSoldier() throws GameActionException {
        int currentBytecode = Clock.getBytecodeNum();
        tryToShoot();
        rc.setIndicatorString(0, "used " + (Clock.getBytecodeNum() - currentBytecode) + " bc");
        // communication
        rc.setIndicatorString(0, "read ID: "+rc.readBroadcast(0));
        int runningTotal = rc.readBroadcast(0);
        rc.broadcast(0, runningTotal+1);
        rc.broadcast(0, locToInt(rc.getLocation()));
        Clock.getRoundNum();
        //movement
        Direction allDirections[] = Direction.values();
        Direction chosenDirection = allDirections[(int)(Math.random()*8)];
        if(rc.isActive()&&rc.canMove(chosenDirection)){
            rc.move(chosenDirection);
        }
    }
    
    private static MapLocation mladd(MapLocation m1, MapLocation m2) {
        return new MapLocation (m1.x+m2.x, m1.y+m2.y);
    }
    
    private static MapLocation mldivide(MapLocation bigM, int divisor) {
        return new MapLocation(bigM.x/divisor, bigM.y/divisor);
    }
    
    private static int locToInt(MapLocation m) {
        return (m.x*100 +m.y);
    }
    
    private static MapLocation intToLoc(int i) {
        return new MapLocation(i/100, i%100);
    }
    
    private static void tryToShoot() throws GameActionException {
        //shooting
        Robot[] enemyRobots = rc.senseNearbyGameObjects(Robot.class,10000,rc.getTeam().opponent());
        if(enemyRobots.length>0){//if there are enemies
            Robot anEnemy = enemyRobots[0];
            RobotInfo anEnemyInfo;
            anEnemyInfo = rc.senseRobotInfo(anEnemy);
            if(anEnemyInfo.location.distanceSquaredTo(rc.getLocation())<rc.getType().attackRadiusMaxSquared){
                if(rc.isActive()){
                    rc.attackSquare(anEnemyInfo.location);
                }
            }
        }else{//there are no enemies, so build a tower
            if(rand.nextDouble()<0.01 && rc.sensePastrLocations(rc.getTeam()).length < 5){
                if(rc.isActive()){
                    rc.construct(RobotType.PASTR);
                }
            }
        }
    }
}
