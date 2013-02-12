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
	public enum DataType
	{
		MEETING,
		CONTACT;
	}
	
	
	/**
	 * Filename to use to load and store data to and from disk.
	 */
	public final String fileName = "contacts.txt";
	
	/**
	 * Serialise object which will be used to load and save data to/from disk.
	 */
	private DataSerialiser serialiser = new DataSerialiser(fileName);

	private HashMap<DataType, ArrayList<Object>> m_data;
	
	public DataInterface()
	{
		this.m_data = serialiser.getData();
	}

	public ArrayList<Object> getContacts()
	{
		return this.m_data.get(DataType.CONTACT);
	}
	
	
	public void addContact(ContactImpl contact)
	{
		m_data.get(DataType.CONTACT).add(contact);
	}
	
	/**
	 * Flush function to save data to disk safely.
	 * 
	 * @return null
	 */
	public void flush()
	{
		serialiser.flush(this.m_data);
	}
}
