package SkyNet.Strategy;

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
	 */
	public void runHQ(RobotController runThis);

	/**
	 * Executes one turn of logic for a SOLDIER unit.
	 * 
	 * @param Robotcontroller - The unit to run.
	 */
	public void runSOLDIER(RobotController runThis);
	
	/**
	 * Executes one turn of logic for a PASTR unit.
	 * 
	 * @param Robotcontroller - The unit to run.
	 */
	public void runPASTR(RobotController runThis);
	
	/**
	 * Executes one turn of logic for a NIOSETOWER unit.
	 * 
	 * @param Robotcontroller - The unit to run.
	 */
	public void runNOISETOWER(RobotController runThis);
}
