/**
 * DataInterface - Interface for providing data sets to the system, ideally 
 * 		this will be the single point of contact that the System has with
 * 		the data, reducing the chance of issues if the code was distributed
 * 		throughout the system. 
 */
package bbk.pij.jsted02.data;

/**
 * @author Luke Stedman (jsted02), MSc CS Yr1 2012/13
 */
public class DataInterface {
	/**
	 * Filename to use to load and store data to and from disk.
	 */
	public final String fileName = "contacts.txt";
	
	/**
	 * Serialiser object which will be used to load and save data to/from disk.
	 */
	private DataSerialiser serialiser = new DataSerialiser(fileName);
	
	/**
	 * Data object, the data that is being updated by the system.
	 */
	private Object data;
	
	/**
	 * Initialise function to load data from disk and into memory.
	 * 
	 * @return null
	 */
	public void init()
	{
		data = serialiser.getData();
	}
	
	/**
	 * Flush function to save data to disk safely.
	 * 
	 * @return null
	 */
	public void flush()
	{
		
	}
}
