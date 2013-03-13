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
	 * initialised flag is set to true once the system has initialised and is
	 * ready to be used.
	 */
	private boolean m_initialised;

	/**
	 * DataInterface variable, this will provide the data used in the system.
	 */
	private DataInterface m_dataInterface;

	// Constructors
	/**
	 * Constructor, runs initialisation code to initialise the system.
	 */
	public ContactManagerImpl() {
		init(false);
	}

	/**
	 * Constructor, runs initialisation code to initialise the system.
	 * 
	 * @param flag
	 *            to indicate whether testing or not, if testing then data is
	 *            stored in a different file.
	 */
	public ContactManagerImpl(boolean test) {
		init(test);
	}

	// Getters/Setters
	/**
	 * Gets the initialised value, can only be set locally.
	 * 
	 * @return
	 */
	public boolean getInitialised() {
		return m_initialised;
	}

	// Private Methods
	/**
	 * Method to get an array of id's from the provided set of contacts.
	 * 
	 * @param list
	 *            of contacts from which to get the id's
	 * @return int array containing id's of contacts.
	 */
	private int[] getContactIds(Set<Contact> contacts) {
		// Initialise a counter and array that will be returned
		int count = 0;
		int[] ids = new int[contacts.size()];

		// Iterate over each contact, add id to the array and increment the
		// count
		for (Contact contact : contacts) {
			ids[count++] = contact.getId();
		}

		return ids;
	}

	/**
	 * Checks if all contacts are present in the data set, if not it throws an
	 * IllegalArgumentException.
	 * 
	 * @param set
	 *            of contacts to check
	 * @throws IllegalArgumentException
	 */
	private void checkContacts(Set<Contact> contacts)
			throws IllegalArgumentException {
		// Iterate over contacts and call checkContact on each one
		for (Contact contact : contacts) {
			checkContact(contact);
		}
	}

	/**
	 * Checks if an individual contact is present in the data set, if not it
	 * throws an IllegalArgumentException.
	 * 
	 * @param contact
	 *            to check
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

	// Public Interface
	/**
	 * @see bbk.pij.jsted02.interfaces.ContactManager#addFutureMeeting(java.util.Set,
	 *      java.util.Calendar)
	 */
	@Override
	public int addFutureMeeting(Set<Contact> contacts, Calendar date) {
		// Using the getContactId's method get all known contacts from the data
		Set<Contact> foundContacts = getContacts(getContactIds(contacts));

		// Check if the no. of contacts provided matches the number of contacts
		// found in the data, if not throw an exception
		if (contacts.size() != foundContacts.size()) {
			throw new IllegalArgumentException(
					"Invalid contact - a contact supplied is not known.");
		}
		// Check the date provided, if in the past throw an exception.
		else if (date.compareTo(Calendar.getInstance()) <= 0) {
			throw new IllegalArgumentException(
					"Invalid date - must be in the future.");
		}

		// Otherwise all is good and we can continue, create the meeting and
		// set the various attributes, add to the data and return the id
		FutureMeetingImpl meeting = new FutureMeetingImpl();
		meeting.setDate(date);
		meeting.setContacts(contacts);
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
		List<Meeting> allMeetings = m_dataInterface.getAllMeetings();
		List<Meeting> returnedMeetings = new ArrayList<Meeting>();
		Calendar compareDate = Calendar.getInstance();
		compareDate.clear();
		compareDate.set(date.get(Calendar.YEAR), date.get(Calendar.MONTH),
				date.get(Calendar.DATE));

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

		// Call checkContact method, throws and IllegalArgument if the contact
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
		checkContacts(contacts);

		// Create a new meeting, set the various attributes and add to the data
		// interface
		PastMeetingImpl meeting = new PastMeetingImpl();
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
		if (name == null || notes == null) {
			throw new NullPointerException(
					"Both name and notes must be set, null value found");
		}

		// Create contact, set name and notes and add contact to the
		// data interface
		ContactImpl contact = new ContactImpl();
		contact.setName(name);
		contact.addNotes(notes);
		m_dataInterface.addContact(contact);
	}

	/**
	 * @see bbk.pij.jsted02.interfaces.ContactManager#getContacts(int[])
	 */
	@Override
	public Set<Contact> getContacts(int... ids) {
		// Create list of all contacts and a new set for the returned contacts
		List<Contact> contactsList = m_dataInterface.getAllContacts();

		Set<Contact> returnContacts = new HashSet<Contact>();

		for (int i = 0; i < ids.length; ++i) {
			boolean missing = true;
			for (Contact contact : contactsList) {

				if (ids[i] == contact.getId()) {
					missing = false;
					returnContacts.add(contact);
					break;
				}
			}

			if (missing) {
				throw new IllegalArgumentException("Contact does not exist.");
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

	/**
	 * Initialise function, will load contacts data into memory and prepare
	 * system for use.
	 */
	private void init(boolean test) {
		m_initialised = false;
		m_dataInterface = new DataInterface(test);
		m_initialised = true;
	}

	/**
	 * Finalise function, calls flush on data set when the object is cleaned up
	 * by the GC
	 * 
	 * @throws Throwable
	 */
	@Override
	protected void finalize() throws Throwable {
		flush();
		m_initialised = false;
		super.finalize();
	}
}
