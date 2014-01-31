package team005.Strategy;

import battlecode.common.*;

/**
 * This interface contains methods for the execution of different strategies.
 * @author Nick
 *
 */
public interface Strategy 
{
	public static enum Strategies
	{
		TURTLE;
	}
	
	/**
	 * 
	 * @return
	 */
	public Strategies getStrategy();

	/**
	 * Executes one turn of logic for an HQ unit.
	 * 
	 * @param Robotcontroller - The unit to run.
	 * @throws GameActionException 
	 */
	public void runHQ() throws GameActionException;

	/**
	 * Executes one turn of logic for a SOLDIER unit.
	 * 
	 * @param Robotcontroller - The unit to run.
	 */
	public void runSOLDIER() throws GameActionException;
	
	/**
	 * Executes one turn of logic for a PASTR unit.
	 * 
	 * @param Robotcontroller - The unit to run.
	 */
	public void runPASTR() throws GameActionException;
	
	/**
	 * Executes one turn of logic for a NIOSETOWER unit.
	 * 
	 * @param Robotcontroller - The unit to run.
	 */
	public void runNOISETOWER() throws GameActionException;
}
