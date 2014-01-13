package SkyNet.UnitControl;

import SkyNet.SimpleActions.Communicator;
import SkyNet.Strategy.Strategy;
import battlecode.common.*;

public class ControllerPASTR implements Controller
{
	private Strategy currentStrategy;
	private RobotController rc;
	private Communicator comms;
	
	
	/**
	 * This constructor creates a controller for a PASTR.
	 * 
	 * @param RobotController - Takes the unit to be controlled.
	 */
	public ControllerPASTR (RobotController thisRC)
	{
		rc = thisRC;
		comms = new Communicator(rc, 1, 2, 3);
	}
	
	/**
	 * This method executes one turn of PASTR logic.
	 */
	public void run() 
	{
		try
		{
			switch (currentStrategy.getStrategy())
			{
				case TURTLE:
				{
					
				}
			}
			
		}
		catch (Exception e)
		{
			System.out.println("PASTR Controller Error.");
			e.printStackTrace();
		}
	}
}
