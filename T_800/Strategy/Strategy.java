package T_800.Strategy;

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
	public void runHQ();

	/**
	 * Executes one turn of logic for a SOLDIER unit.
	 * 
	 * @param Robotcontroller - The unit to run.
	 */
	public void runSOLDIER();
	
	/**
	 * Executes one turn of logic for a PASTR unit.
	 * 
	 * @param Robotcontroller - The unit to run.
	 */
	public void runPASTR();
	
	/**
	 * Executes one turn of logic for a NIOSETOWER unit.
	 * 
	 * @param Robotcontroller - The unit to run.
	 */
	public void runNOISETOWER();
}
