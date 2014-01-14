package SkyNet;

import battlecode.common.*;

/**
 * This class is a data type for use with bug navigation / slug navigation.
 */
public class SlugMemory 
{

	// We need somewhere to store locations that we have visited.
	private MapLocation[] memory;
	
	// We can speed up by remembering how many locations we need to check
	private int counter;
	
	/**
	 * This constructor creates a new instance of this data type.
	 * 
	 * @param int - The number of locations we want to store in memory.
	 */
	public SlugMemory(int length)
	{
		memory = new MapLocation[length];
		counter = 0;
	}
	
	/**
	 * This method adds a new location to memory and eliminates the last location.
	 * 
	 * @param MapLocation - Takes the new location we want to remember.
	 */
	public void remember(MapLocation rememberThis)
	{

		// First we need to shift each location in our memory.
		for (int i = counter; i > 0; i--)
		{
			memory[i] = memory[i-1];
		}
		
		// Now that we have made room we store the new location.
		memory[0] = rememberThis;
		counter ++;
		if (counter == 15)
		{
			counter = 14;
		}
	}
	
	/**
	 * Clears the memory.
	 */
	public void clear()
	{
		for (int i = 0; i < memory.length; i++)
		{
			memory[i] = null;
		}
		counter = 0;
	}
	
	/**
	 * Removes the last element in the memory.
	 */
	public void forget()
	{
		if (counter > 0)
		{
			memory[counter - 1] = null;
			counter --;
		}
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
		for (int i = 0; i < counter; i++)
		{
				if (memory[i].equals(maybeVisited))
				{
					return true;
				}
		}
		return false;
	}
	
	/**
	 * Returns the previous location of this robot.
	 * 
	 * @return MapLocation - Returns the previous location of this robot.
	 */
	public MapLocation previous()
	{
		return memory[0];
	}
	
}
