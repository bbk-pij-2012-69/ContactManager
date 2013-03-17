/**
 * DataInterface - Interface for providing data sets to the system, ideally this
 * will be the single point of contact that the System has with the data,
 * reducing the chance of issues if the code was distributed throughout the
 * system.
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
     * DataType enum, allows the data to be referenced using a static set of
     * identifiers - MEETING and CONTACT.
     */
    public static enum DataType {
        MEETING, CONTACT;
    }
    
    /**
     * Filename to use to load and store data to and from disk.
     */
    public final String                          FILE_NAME = "contacts.txt";
    
    /**
     * Serialiser object which will be used to load and save data to/from disk.
     */
    private DataSerialiser                       m_serialiser;
    
    /**
     * Data store, hash containing enum reference to data and array list of data
     * objects.
     */
    private HashMap<DataType, ArrayList<Object>> m_data;
    
    /**
     * Constructs DataInterface, gets the data from the serialiser and stores it
     * for use by the ContactManager. Defaults test and wipe to false.
     */
    public DataInterface() {
        init(false, false);
    }
    
    /**
     * Constructs DataInterface, gets the data from the serialiser and stores it
     * for use by the ContactManager. Defaults wipe to false.
     * 
     * @param test
     *            Boolean to indicate whether to run in test mode or not.
     */
    public DataInterface(boolean test) {
        init(test, false);
    }
    
    /**
     * Constructs DataInterface, gets the data from the serialiser and stores it
     * for use by the ContactManager.
     * 
     * @param test
     *            Boolean to indicate whether to run in test mode or not.
     * @param wipe
     *            Boolean to indicate whether to wipe the data and start afresh.
     */
    public DataInterface(boolean test, boolean wipe) {
        init(test, wipe);
    }
    
    // Private methods
    /**
     * Initialiser function - initialises interfaces based on the parameters
     * supplied to the constructor
     */
    private void init(boolean test, boolean wipe) {
        m_serialiser = new DataSerialiser(FILE_NAME, test, wipe);
        m_data = m_serialiser.getData();
    }
    
    // Public interfaces.
    /**
     * Get contact method, returns the contact if it matches the id of a contact
     * in the data store, otherwise returns null.
     * 
     * @param id
     *            Integer id of contact to find.
     * @return Contact object if present, null if not present.
     */
    public Contact getContact(int id) {
        // Loop over each contact in the data store.
        for (int i = 0; i < m_data.get(DataType.CONTACT).size(); ++i) {
            // If the contact's id matches the provided id, return it.
            if (((Contact) m_data.get(DataType.CONTACT).get(i)).getId() == id)
                return (Contact) m_data.get(DataType.CONTACT).get(i);
        }
        // Return null if not present.
        return null;
    }
    
    /**
     * Get contacts method, returns a list of contacts based on the input id's.
     * 
     * @param ids
     *            Integer array of id's of contacts to return.
     * @return List of Contacts.
     */
    public List<Contact> getContacts(int... ids) {
        // Create an empty list of contacts.
        List<Contact> returnedContacts = new ArrayList<Contact>();
        
        // Loop over each id, search for it in the contact store.
        for (int i = 0; i < ids.length; ++i) {
            Contact contact = getContact(ids[i]);
            // If the contact is present append to the list.
            if (contact != null) {
                returnedContacts.add(getContact(ids[i]));
            }
        }
        // Return list
        return returnedContacts;
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
        m_serialiser.flush(m_data);
    }
    
    /**
     * Method to get meeting object associated with specific id.
     * 
     * @param id
     *            integer id of meeting to return
     * @return Meeting object associated with id or null if id not present.
     */
    public Meeting getMeeting(int id) {
        // Loop over each contact in the data store.
        for (int i = 0; i < m_data.get(DataType.MEETING).size(); ++i) {
            // If the ID matches the provided id return
            if (((Meeting) m_data.get(DataType.MEETING).get(i)).getId() == id)
                return (Meeting) m_data.get(DataType.MEETING).get(i);
        }
        // Return null if not present.
        return null;
    }
    
    /**
     * Method to get all meetings in data store.
     * 
     * @return List of all Meeting objects in the data store.
     */
    public List<Meeting> getAllMeetings() {
        List<Meeting> meetings = new ArrayList<Meeting>();
        List<Object> returnedContacts = m_data.get(DataType.MEETING);
        for (Object contact : returnedContacts) {
            meetings.add((Meeting) contact);
        }
        return meetings;
    }
    
    /**
     * Method to get all contacts in data store.
     * 
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
     * Method to update a meeting in the data store, takes a meeting object to
     * update, uses the meeting ID to find the meeting, removes the meeting and
     * inserts the new meeting.
     * 
     * @param meeting
     *            Meeting object to update.
     * @throws IllegalArgumentException
     */
    public void updMeeting(Meeting meeting) {
        
        int meetingLocation = -1;
        List<Object> storedMeetings = m_data.get(DataType.MEETING);
        for (int i = 0; i < storedMeetings.size(); ++i) {
            if (((Meeting) storedMeetings.get(i)).getId() == meeting.getId()) {
                meetingLocation = i;
                break;
            }
        }
        
        if (meetingLocation == -1) {
            throw new IllegalArgumentException("Meeting (" + meeting.getId()
                    + ") does not exist in data store.");
        }
        storedMeetings.remove(meetingLocation);
        
        storedMeetings.add(meeting);
    }
}
