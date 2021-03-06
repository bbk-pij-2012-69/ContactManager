/**
 * ContactManagerImpl - implementation class of ContactManager Interface.
 */
package bbk.pij.jsted02;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import bbk.pij.jsted02.data.DataInterface;
import bbk.pij.jsted02.interfaces.Contact;
import bbk.pij.jsted02.interfaces.ContactManager;
import bbk.pij.jsted02.interfaces.FutureMeeting;
import bbk.pij.jsted02.interfaces.Meeting;
import bbk.pij.jsted02.interfaces.PastMeeting;
import bbk.pij.jsted02.meetings.FutureMeetingImpl;
import bbk.pij.jsted02.meetings.MeetingImpl;
import bbk.pij.jsted02.meetings.PastMeetingImpl;

/**
 * @author Luke Stedman (jsted02), MSc CS Yr1 2012/13
 */
public class ContactManagerImpl implements ContactManager {
    
    // Members
    /**
     * Initialised flag is set to true once the system has initialised and is
     * ready to be used.
     */
    private boolean       m_initialised;
    
    /**
     * DataInterface variable, this will provide the data that is used in the
     * system.
     */
    private DataInterface m_dataInterface;
    
    // Constructors
    /**
     * Constructor, runs initialisation code to initialise the system.
     */
    public ContactManagerImpl() {
        init(false, false);
    }
    
    /**
     * Constructor, runs initialisation code to initialise the system.
     * 
     * @param test
     *            Boolean to indicate whether testing or not.
     */
    public ContactManagerImpl(boolean test) {
        init(test, false);
    }
    
    /**
     * Constructor, runs initialisation code to initialise the system.
     * 
     * @param test
     *            Boolean to indicate whether testing or not.
     * @param wipe
     *            Boolean to indicate whether to wipe the existing data or not.
     */
    public ContactManagerImpl(boolean test, boolean wipe) {
        init(test, wipe);
    }
    
    // Getters/Setters
    /**
     * Gets the initialised value, can only be set locally.
     * 
     * @return Boolean value indicating if the system is initialised
     */
    public boolean getInitialised() {
        return m_initialised;
    }
    
    // Private Methods
    /**
     * Checks if an individual contact is present in the data set, if not it
     * throws an IllegalArgumentException.
     * 
     * @param contact
     *            Contact to check
     * @throws IllegalArgumentException
     */
    private void checkContact(Contact contact) throws IllegalArgumentException {
        // If the size of the contacts list returned when we pass in the
        // contact id is 0 then the contact must not exist and we throw
        if (getContacts(contact.getId()).size() == 0) {
            throw new IllegalArgumentException(
                    "Invalid contact - a contact supplied is not known.");
        }
    }
    
    /**
     * Initialise function, will load contacts and meeting data into memory and
     * prepare system for use.
     * 
     * @param test
     *            Boolean indicating whether in test mode.
     * @param wipe
     *            Boolean indicating whether to wipe the data
     */
    private void init(boolean test, boolean wipe) {
        m_initialised = false;
        m_dataInterface = new DataInterface(test, wipe);
        m_initialised = true;
    }
    
    // Public Interface
    /**
     * @see bbk.pij.jsted02.interfaces.ContactManager#addFutureMeeting(java.util.Set,
     *      java.util.Calendar)
     */
    @Override
    public int addFutureMeeting(Set<Contact> contacts, Calendar date) {
        // Using the getContact's method get all known contacts from the data
        // interface, if a contact doesn't exist it will throw an exception
        int[] contactIds = new int[contacts.size()];
        int contactCount = 0;
        for (Contact contact : contacts) {
            contactIds[contactCount++] = contact.getId();
        }
        
        Set<Contact> foundContacts = getContacts(contactIds);
        
        // Check the date provided, if in the past throw an exception.
        if (date.compareTo(Calendar.getInstance()) <= 0) {
            throw new IllegalArgumentException(
                    "Invalid date - must be in the future.");
        }
        
        // All is good and we can continue, create the meeting and set the
        // various attributes, add to the data and return the id
        FutureMeetingImpl meeting = new FutureMeetingImpl();
        
        meeting.setId(m_dataInterface.getAllMeetings().size());
        meeting.setDate(date);
        meeting.setContacts(foundContacts);
        m_dataInterface.addMeeting(meeting);
        
        return meeting.getId();
    }
    
    /**
     * @see bbk.pij.jsted02.interfaces.ContactManager#getPastMeeting(int)
     */
    @Override
    public PastMeeting getPastMeeting(int id) {
        // Get the meeting from the data interface, check the meeting instance
        // if a Future meeting then throw an exception
        Meeting meeting = m_dataInterface.getMeeting(id);
        if (meeting instanceof FutureMeetingImpl) {
            throw new IllegalArgumentException(
                    "Invalid meeting id - is associated with FutureMeeting.");
        }
        
        return (PastMeeting) meeting;
    }
    
    /**
     * @see bbk.pij.jsted02.interfaces.ContactManager#getFutureMeeting(int)
     */
    @Override
    public FutureMeeting getFutureMeeting(int id) {
        return (FutureMeeting) m_dataInterface.getMeeting(id);
    }
    
    /**
     * @see bbk.pij.jsted02.interfaces.ContactManager#getMeeting(int)
     */
    @Override
    public Meeting getMeeting(int id) {
        return m_dataInterface.getMeeting(id);
    }
    
    /**
     * @see bbk.pij.jsted02.interfaces.ContactManager#getFutureMeetingList(bbk.pij.jsted02.interfaces.Contact)
     */
    @Override
    public List<Meeting> getFutureMeetingList(Contact contact) {
        // Call checkContact method, throws and IllegalArgument if the contact
        // does not exist
        checkContact(contact);
        
        // Get the full list of future meetings and create a new list to store
        // the filtered meetings
        List<Meeting> allMeetings = m_dataInterface.getAllMeetings();
        List<Meeting> returnedMeetings = new ArrayList<Meeting>();
        
        // Iterate over the meetings and check the contacts exist for that
        // meeting, if he does then append the meeting to the filtered list
        for (Object meeting : allMeetings) {
            if (meeting instanceof FutureMeeting
                    && ((Meeting) meeting).getContacts().contains(contact)) {
                returnedMeetings.add((Meeting) meeting);
            }
        }
        
        // Sort using the Meeting date ascending comparator and return
        Collections.sort(returnedMeetings, MeetingImpl.COMPARATOR_DATE_ASC);
        
        return returnedMeetings;
    }
    
    /**
     * @see bbk.pij.jsted02.interfaces.ContactManager#getFutureMeetingList(java.util.Calendar)
     */
    @Override
    public List<Meeting> getFutureMeetingList(Calendar date) {
        
        // Get the full list of future meetings and create a new list to store
        // the filtered meetings
        List<Meeting> allMeetings = m_dataInterface.getAllMeetings();
        List<Meeting> returnedMeetings = new ArrayList<Meeting>();
        
        // Create a calendar instance to store the date specified (the
        // specification requires meetings on the same day to be returned, as
        // such we need to ignore the hours and minutes)
        Calendar compareDate = Calendar.getInstance();
        compareDate.clear();
        compareDate.set(date.get(Calendar.YEAR), date.get(Calendar.MONTH),
                date.get(Calendar.DATE));
        
        // Iterate over each meeting and create a Calendar value based on the
        // date (again excluding hours and minutes).
        // If the date matches the date supplied then append to the list of
        // meetings to return.
        for (Object meeting : allMeetings) {
            Calendar meetingDate = Calendar.getInstance();
            meetingDate.clear();
            meetingDate.set(((Meeting) meeting).getDate().get(Calendar.YEAR),
                    ((Meeting) meeting).getDate().get(Calendar.MONTH),
                    ((Meeting) meeting).getDate().get(Calendar.DATE));
            if (compareDate.compareTo(meetingDate) == 0) {
                returnedMeetings.add((Meeting) meeting);
            }
        }
        
        // If the date is in the future then sort the meetings in ascending
        // order (nearest first), if earlier than now sort in descending order
        // (again nearest first)
        if (Calendar.getInstance().compareTo(compareDate) == -1) {
            Collections.sort(returnedMeetings, MeetingImpl.COMPARATOR_DATE_ASC);
        } else {
            Collections.sort(returnedMeetings, MeetingImpl.COMPARATOR_DATE_DSC);
        }
        
        return returnedMeetings;
    }
    
    /**
     * @see bbk.pij.jsted02.interfaces.ContactManager#getPastMeetingList(bbk.pij.jsted02.interfaces.Contact)
     */
    @Override
    public List<PastMeeting> getPastMeetingList(Contact contact) {
        
        // Call checkContact method, throws an IllegalArgument if the contact
        // does not exist
        checkContact(contact);
        
        // Get the full list of past meetings and create a new list to store
        // the filtered meetings
        List<Meeting> allMeetings = m_dataInterface.getAllMeetings();
        List<PastMeeting> returnedMeetings = new ArrayList<PastMeeting>();
        
        // Iterate over the meetings and check the contacts exist for that
        // meeting, if he does then append the meeting to the filtered list
        for (Object meeting : allMeetings) {
            if (meeting instanceof PastMeeting
                    && ((Meeting) meeting).getContacts().contains(contact)) {
                returnedMeetings.add((PastMeeting) meeting);
            }
        }
        
        // Sort using the Meeting date descending comparator and return
        Collections.sort(returnedMeetings, MeetingImpl.COMPARATOR_DATE_DSC);
        
        return returnedMeetings;
    }
    
    /**
     * @see bbk.pij.jsted02.interfaces.ContactManager#addNewPastMeeting(java.util.Set,
     *      java.util.Calendar, java.lang.String)
     */
    @Override
    public void addNewPastMeeting(Set<Contact> contacts, Calendar date,
            String text) {
        // Check if any of the inputs are null
        if (contacts == null || date == null || text == null) {
            throw new NullPointerException("None of the values should be null");
        }
        // Check if the contacts set is empty
        else if (contacts.size() == 0) {
            throw new IllegalArgumentException("Contacts should not be empty");
        }
        
        // Run checkContacts on the set of contacts
        for (Contact contact : contacts) {
            checkContact(contact);
        }
        
        // Create a new meeting, set the various attributes and add to the data
        // interface
        PastMeetingImpl meeting = new PastMeetingImpl();
        meeting.setId(m_dataInterface.getAllMeetings().size());
        meeting.setContacts(contacts);
        meeting.setDate(date);
        meeting.setNotes(text);
        m_dataInterface.addMeeting(meeting);
    }
    
    /**
     * @see bbk.pij.jsted02.interfaces.ContactManager#addMeetingNotes(int,
     *      java.lang.String)
     */
    @Override
    public void addMeetingNotes(int id, String text) {
        
        // Get the meeting and initialise a variable for the new note
        Meeting meeting = m_dataInterface.getMeeting(id);
        
        // Perform Exception checks - are the text and meeting valid?
        if (text == null) {
            throw new NullPointerException(
                    "Text must be set to a non-null value.");
        }
        
        if (meeting == null) {
            throw new IllegalArgumentException(
                    "Invalid meeting id supplied, meeting does not exist.");
        }
        
        // True/False if meeting is in the future.
        boolean isFutureMeeting = meeting.getDate().compareTo(
                Calendar.getInstance()) > 0;
        
        // Exception check - future meetings cannot have notes added as the
        // meeting hasn't taken place.
        if (meeting instanceof FutureMeetingImpl && isFutureMeeting) {
            throw new IllegalStateException("Meeting must be in the past.");
        } else if (isFutureMeeting) {
            throw new IllegalStateException(
                    "Meeting is in the future and not a FutureMeeting, invalid state.");
        }
        
        // If the meeting is a past meeting then just append the notes,
        // otherwise we need to convert the meeting to a past meeting, update
        // the data store and append the notes.
        if (meeting instanceof PastMeetingImpl) {
            String newNote = ((PastMeeting) meeting).getNotes();
            newNote += "\n" + text;
            ((PastMeetingImpl) meeting).setNotes(newNote);
        } else {
            PastMeetingImpl newMeeting = new PastMeetingImpl(meeting);
            newMeeting.setNotes(text);
            m_dataInterface.updMeeting(newMeeting);
        }
        
    }
    
    /**
     * @see bbk.pij.jsted02.interfaces.ContactManager#addNewContact(java.lang.String,
     *      java.lang.String)
     */
    @Override
    public void addNewContact(String name, String notes) {
        // Check name and notes are non-null values.
        if (name == null || notes == null) {
            throw new NullPointerException(
                    "Both name and notes must be set, null value found");
        }
        
        // Create contact, set name and notes and add contact to the
        // data interface
        ContactImpl contact = new ContactImpl();
        contact.setId(m_dataInterface.getAllContacts().size());
        contact.setName(name);
        contact.addNotes(notes);
        m_dataInterface.addContact(contact);
    }
    
    /**
     * @see bbk.pij.jsted02.interfaces.ContactManager#getContacts(int[])
     */
    @Override
    public Set<Contact> getContacts(int... ids) {
        // Create list to return containing the contacts
        Set<Contact> returnContacts = new HashSet<Contact>();
        
        // Iterate over each id provided and get the contact
        for (int i = 0; i < ids.length; ++i) {
            Contact contact = m_dataInterface.getContact(ids[i]);
            // If the contact is null then throw, otherwise append to the
            // returned list
            if (contact == null) {
                throw new IllegalArgumentException("Contact (" + ids[i]
                        + ") does not exist.");
            } else {
                returnContacts.add(contact);
            }
        }
        
        return returnContacts;
    }
    
    /**
     * @see bbk.pij.jsted02.interfaces.ContactManager#getContacts(java.lang.String)
     */
    @Override
    public Set<Contact> getContacts(String name) {
        
        // Create list of all contacts and a new set for the returned contacts
        List<Contact> contactsList = m_dataInterface.getAllContacts();
        Set<Contact> returnContacts = new HashSet<Contact>();
        
        // Iterate over each contact
        for (Contact contact : contactsList) {
            // Check if the name contains the string specified
            // Will naturally thrown if null
            if (contact.getName().contains(name)) {
                returnContacts.add(contact);
            }
        }
        return returnContacts;
    }
    
    /**
     * @see bbk.pij.jsted02.interfaces.ContactManager#flush()
     */
    @Override
    public void flush() {
        m_dataInterface.flush();
    }
}
