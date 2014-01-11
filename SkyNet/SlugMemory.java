package SkyNet;

import battlecode.common.*;

/**
 * This class is a data type for use with bug navigation / slug navigation.
 */
public class SlugMemory 
{

	// We need somewhere to store locations that we have visited.
	private MapLocation[] memory;
	
	/**
	 * This constructor creates a new instance of this data type.
	 * 
	 * @param int - The number of locations we want to store in memory.
	 */
	public SlugMemory(int length)
	{
		memory = new MapLocation[length];
	}
	
	/**
	 * This method adds a new location to memory and eliminates the last location.
	 * 
	 * @param MapLocation - Takes the new location we want to remember.
	 */
	public void remember(MapLocation rememberThis)
	{
		
		// First we need to shift each location in our memory.
		for (int i = memory.length - 1; i > 0; i++)
		{
			memory[i] = memory[i - 1];
		}
		
		// Now that we have made room we store the new location.
		memory[0] = rememberThis;
	}
	
	/**
	 * Checks if the given location is in memory.
	 * 
	 * @param MapLocation - takes the location to be checked.
	 * @return boolean - Returns true if the location is in memory, false otherwise.
	 */
	public boolean visited(MapLocation maybeVisited)
	{
		
		// We will have to check every location in memory.
		for (MapLocation visited : memory)
		{
			if (visited.equals(maybeVisited))
			{
				return true;
			}
		}
		return false;
	}
	
	
	public boolean visitedAdjacent(MapLocation maybeVisitedClose)
	{
		Direction startWithThis = Direction.NORTH;
		
		for (int i = 0; i < 8; i++)
		{
			
			
			startWithThis = startWithThis.rotateRight();
		}
		
		return false;
	}
}
