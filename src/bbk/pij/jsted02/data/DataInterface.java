/**
 * DataInterface - Interface for providing data sets to the system, ideally 
 * 		this will be the single point of contact that the System has with
 * 		the data, reducing the chance of issues if the code was distributed
 * 		throughout the system. 
 */
package bbk.pij.jsted02.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import bbk.pij.jsted02.ContactImpl;
import bbk.pij.jsted02.interfaces.Contact;
import bbk.pij.jsted02.interfaces.Meeting;

/**
 * @author Luke Stedman (jsted02), MSc CS Yr1 2012/13
 */
public class DataInterface {
	
	// Members
	/**
	 * DataType enum, allows the data to be referenced using a static set
	 * of identifiers - MEETING and CONTACT.
	 */
	public static enum DataType {
		MEETING, CONTACT;
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
	 * Data store, hash containing enum reference to data and array list of data
	 * objects.
	 */
	private HashMap<DataType, ArrayList<Object>> m_data;

	/**
	 * Constructs DataInterface, gets the data from the serialiser and stores it
	 * for use by the ContactManager.
	 * 
	 * @param test
	 * 			Boolean to indicate whether to run in test mode or not.
	 */
	public DataInterface(boolean test) {
		m_serialiser = new DataSerialiser(FILE_NAME, test);
		this.m_data = m_serialiser.getData();
	}

	// Private methods
	/**
	 * Get contact method, returns the contact if it matches the id of a contact
	 * in the data store, otherwise returns null.
	 * 
	 * @param id
	 *            Integer id of contact to find.
	 * @return Contact object if present, null if not present.
	 */
	private ContactImpl getContact(int id) {
		// Loop over each contact in the data store.
		for (int i = 0; i < this.m_data.get(DataType.CONTACT).size(); ++i) {
			// If the contact's id matches the provided id, return it.
			if (((ContactImpl) this.m_data.get(DataType.CONTACT).get(i))
					.getId() == id)
				return (ContactImpl) this.m_data.get(DataType.CONTACT).get(i);
		}
		// Return null if not present.
		return null;
	}

	
	// Public interfaces.
	/**
	 * Get contacts method, returns a list of contacts based on the input
	 * id's.
	 * 
	 * @param ids
	 *            Integer array of id's of contacts to return.
	 * @return List of Contacts.
	 */
	public List<ContactImpl> getContacts(int... ids) {
		// Create an empty list of contacts.
		List<ContactImpl> returnedContacts = new ArrayList<ContactImpl>();

		// Loop over each id, search for it in the contact store.
		for (int i = 0; i < ids.length; ++i) {
			ContactImpl contact = this.getContact(ids[i]);
			// If the contact is present append to the list.
			if (contact != null) {
				returnedContacts.add(this.getContact(ids[i]));
			}
		}
		// Return list
		return returnedContacts;
	}

	/**`
	 * Method to get all contacts in data store.
	 * @return List of all Contact objects in the data store.
	 */
	public List<Contact> getAllContacts() {
		List<Contact> contacts = new ArrayList<Contact>();
		List<Object> returnedContacts = m_data.get(DataType.CONTACT);
		for (Object contact : returnedContacts) {
			contacts.add((Contact) contact);
		}
		return contacts;
	}

	/**
	 * Add Contact method, adds a contact to the data interface.
	 * 
	 * @param contact
	 *            Contact object to add.
	 */
	public void addContact(ContactImpl contact) {
		m_data.get(DataType.CONTACT).add(contact);
	}

	/**
	 * Add Meeting method, adds a meeting to the data interface.
	 * 
	 * @param Meeting
	 *            Meeting object to add.
	 */
	public void addMeeting(Meeting meeting) {
		m_data.get(DataType.MEETING).add(meeting);
	}

	/**
	 * Flush function to save data to disk safely.
	 */
	public void flush() {
		m_serialiser.flush(this.m_data);
	}

	/**
	 * Method to get meeting object associated with specific id.
	 * @param id
	 * @return
	 */
	public Meeting getMeeting(int id) {
		// Loop over each contact in the data store.
		for (int i = 0; i < this.m_data.get(DataType.MEETING).size(); ++i) {
			// If the ID matches the provided id return
			if (((Meeting) this.m_data.get(DataType.MEETING).get(i)).getId() == id)
				return (Meeting) this.m_data.get(DataType.MEETING).get(i);
		}
		// Return null if not present.
		return null;
	}

	public ArrayList<Object> getAllMeetings() {
		return m_data.get(DataType.MEETING);
	}

}
