/**
 * DataInterface - Interface for providing data sets to the system, ideally 
 * 		this will be the single point of contact that the System has with
 * 		the data, reducing the chance of issues if the code was distributed
 * 		throughout the system. 
 */
package bbk.pij.jsted02.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import bbk.pij.jsted02.ContactImpl;
import bbk.pij.jsted02.interfaces.Contact;
import bbk.pij.jsted02.interfaces.FutureMeeting;
import bbk.pij.jsted02.interfaces.Meeting;
import bbk.pij.jsted02.meetings.FutureMeetingImpl;
import bbk.pij.jsted02.meetings.MeetingImpl;
import bbk.pij.jsted02.meetings.PastMeetingImpl;

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
	public final String FILE_NAME = "contacts.txt";
	
	/**
	 * Serialiser object which will be used to load and save data to/from disk.
	 */
	private DataSerialiser m_serialiser;

	/**
	 * Data store, hash containing enum reference to data and array list of
	 *  data objects.
	 */
	private HashMap<DataType, ArrayList<Object>> m_data;
	
	/**
	 * Constructs DataInterface, gets the data from the serialiser and stores
	 *  it for use by the ContactManager. 
	 */
	public DataInterface(boolean test)
	{
		m_serialiser =  new DataSerialiser(FILE_NAME, test);
		this.m_data = m_serialiser.getData();
	}

	
	// Contact interfaces to data.
	/**
	 * Get contacts method that returns a list of contacts based on the input
	 *  id's.
	 * 
	 * @param ids Integer id's of contacts to return. 
	 * @return List of Contacts.
	 */
	public List<ContactImpl> getContacts(int...ids)
	{
		// Create an empty list of contacts.
		List<ContactImpl> returnedContacts = new ArrayList<ContactImpl>();
		
		// Loop over each id, search for it in the contact store.
		for(int i = 0; i < ids.length;++i)
		{
			ContactImpl contact = this.getContact(ids[i]);
			// If the contact is present append to the list.
			if(contact != null)
			{
				returnedContacts.add(this.getContact(ids[i]));
			}
		}
		// Return list
		return returnedContacts;
	}
	
	/**
	 * Get contact method, returns the contact if it matches the id of a
	 *  contact in the data store, otherwise returns null.
	 *  
	 * @param id Integer id of contact to find.
	 * @return Contact object if present, null if not present.
	 */
	private ContactImpl getContact(int id)
	{
		// Loop over each contact in the data store.
		for(int i = 0; i < this.m_data.get(DataType.CONTACT).size(); ++i)
		{
			// If the contact's id matches the provided id, return it.
			if(((ContactImpl)this.m_data.get(DataType.CONTACT).get(i)).getId() == id)
				return (ContactImpl)this.m_data.get(DataType.CONTACT).get(i);
		}
		// Return null if not present.
		return null;
	}

	/**
	 * Get contacts method that returns a list of contacts based on the input
	 *  name.
	 * 
	 * @param List of strings (names) of contacts to return.
	 * @return List of Contacts.
	 */
	public List<ContactImpl> getContacts(String... names)
	{
		// Create an empty list of contacts.
		List<ContactImpl> returnedContacts = new ArrayList<ContactImpl>();
		
		// Loop over each id, search for it in the contact store.
		for(int i = 0; i < names.length;++i)
		{
			ContactImpl contact = this.getContact(names[i]);
			// If the contact is present append to the list.
			if(contact != null)
			{
				returnedContacts.add(this.getContact(names[i]));
			}
		}
		// Return list
		return returnedContacts;
	}
	
	public List<Contact> getAllContacts()
	{
		@SuppressWarnings("unchecked")
		List<Contact> contacts = new ArrayList<Contact>((Collection<? extends Contact>) this.m_data.get(DataType.CONTACT));
		return contacts;
	}
	
	/**
	 * Get contact method, returns the contact if it matches the name of a
	 *  contact in the data store, otherwise returns null.
	 *  
	 * @param name String name of the contact to find.
	 * @return Contact object if present, null if not present.
	 */
	private ContactImpl getContact(String name)
	{
		// Loop over each contact in the data store.
		for(int i = 0; i < this.m_data.get(DataType.CONTACT).size(); ++i)
		{
			// If the contact's name matches the provided name, return it.
			if(((ContactImpl)this.m_data.get(DataType.CONTACT).get(i)).getName().equals(name))
				return (ContactImpl)this.m_data.get(DataType.CONTACT).get(i);
		}
		// Return null if not present.
		return null;
	}

	/**
	 * Add Contact method, adds a contact to the data interface.
	 * 
	 * @param contact Contact to add.
	 */
	public void addContact(ContactImpl contact)
	{
		m_data.get(DataType.CONTACT).add(contact);
	}

	/**
	 * Add Meeting method, adds a meeting to the data interface.
	 * @param Meeting to add.
	 */
	public void addMeeting(Meeting meeting)
	{
		m_data.get(DataType.MEETING).add(meeting);
	}
	
	/**
	 * Flush function to save data to disk safely.
	 */
	public void flush()
	{
		m_serialiser.flush(this.m_data);
	}


	public Meeting getPastMeeting(int id) {
		return this.getMeeting(id);
	}


	public FutureMeeting getFutureMeeting(int id) {
		Meeting meeting = this.getMeeting(id);
		if(! (meeting instanceof FutureMeetingImpl))
		{
			return null;
		}
		return (FutureMeeting)meeting;
	}


	public Meeting getMeeting(int id) {
		// Loop over each contact in the data store.
		for(int i = 0; i < this.m_data.get(DataType.MEETING).size(); ++i)
		{
			// If the ID matches the provided id return
			if(((MeetingImpl)this.m_data.get(DataType.MEETING).get(i)).getId() == id)
				return (MeetingImpl)this.m_data.get(DataType.MEETING).get(i);
		}
		// Return null if not present.
		return null;
	}

	public ArrayList<Object> getAllMeetings() {
		return m_data.get(DataType.MEETING);
	}

	public List<Meeting> getAllFutureMeetings() {
		List<Object> meetings = getAllMeetings();
		List<Meeting> future_meetings = new ArrayList<Meeting>();
		for(Object meeting: meetings)
		{
			if(meeting instanceof FutureMeetingImpl)
			{
				future_meetings.add((Meeting) meeting);
			}
		}
		return future_meetings;
	}

	public void updMeeting(Meeting meeting) {
		// TODO Auto-generated method stub
		
	}


	public List<Meeting> getAllPastMeetings() {
		List<Object> meetings = getAllMeetings();
		List<Meeting> future_meetings = new ArrayList<Meeting>();
		for(Object meeting: meetings)
		{
			if(meeting instanceof PastMeetingImpl)
			{
				future_meetings.add((Meeting) meeting);
			}
		}
		return future_meetings;
	}
}
