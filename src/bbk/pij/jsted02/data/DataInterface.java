/**
 * DataInterface - Interface for providing data sets to the system, ideally 
 * 		this will be the single point of contact that the System has with
 * 		the data, reducing the chance of issues if the code was distributed
 * 		throughout the system. 
 */
package bbk.pij.jsted02.data;

import java.util.ArrayList;
import java.util.HashMap;

import bbk.pij.jsted02.ContactImpl;

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

	private HashMap<String, ArrayList<Object>> m_data;
	
	/**
	 * Initialise function to load data from disk and into memory.
	 * 
	 * @return null
	 */
	public void init()
	{
		this.m_data = serialiser.getData();
	}

	
	public void addContact(ContactImpl contact)
	{
		m_data.get("contacts").add(contact);
	}
	
	/**
	 * Flush function to save data to disk safely.
	 * 
	 * @return null
	 */
	public void flush()
	{
		//serialiser.flush(this);
	}
}
