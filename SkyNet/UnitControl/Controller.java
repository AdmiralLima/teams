package SkyNet.UnitControl;

import SkyNet.Strategy.*;
import battlecode.common.*;

/**
 * This interface provides methods for the control of different unit types.
 */
public interface Controller 
{
	
	/**
	 * The run method executes a units logic for a single turn.
	 * 
	 * @throws GameActionException
	 */
	public void run();
	
	/**
	 * Sets the new strategy to be employed.
	 *
	 * @param Strategy - The new strategy.
	 */
	public void setStrategy(Strategy newStrategy);
}
