/**
 * ContactManagerImpl - implementation class of ContactManager Interface.
 */
package bbk.pij.jsted02;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
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
	/**
	 * initialised flag is set to true once the system has initialised and is
	 * ready to be used.
	 */
	
	private boolean m_initialised;
	/**
	 * DataInterface variable, this will provide the data used in the system.
	 */
	private DataInterface m_dataInterface;

	/**
	 * Constructor, runs initialisation code to initialise the system.
	 */
	public ContactManagerImpl()
	{
		init(false);
	}
	
	/**
	 * Constructor, runs initialisation code to initialise the system.
	 */
	public ContactManagerImpl(boolean test)
	{
		init(test);
	}
	
	/**
	 * Method to get an array of id's from the provided set of contacts.
	 * 
	 * @param list of contacts from which to get the id's
	 * @return int array containing id's of contacts.
	 */
	private int[] getContactIds(Set<Contact> contacts)
	{
		// Initialise a counter and array that will be returned
		int count = 0;
		int[] ids = new int[contacts.size()];
		
		// Iterate over each contact, add id to the array and increment the 
		//  count
		for(Contact contact : contacts)
		{
			ids[count++] = contact.getId();
		}
		
		return ids;
	}
	
	/**
	 * @see bbk.pij.jsted02.interfaces.ContactManager#addFutureMeeting(java.util.Set, java.util.Calendar)
	 */
	@Override
	public int addFutureMeeting(Set<Contact> contacts, Calendar date)
	{
		// Using the getContactId's method get all known contacts from the data
		Set<Contact> foundContacts = getContacts(getContactIds(contacts));
		
		// Check if the no. of contacts provided matches the number of contacts
		//  found in the data, if not throw an exception
		if(contacts.size() != foundContacts.size())
		{
			throw new IllegalArgumentException("Invalid contact - a contact supplied is not known.");
		}
		// Check the date provided, if in the past throw an exception.
		else if (date.compareTo(Calendar.getInstance()) <= 0)
		{
			throw new IllegalArgumentException("Invalid date - must be in the future.");
		}

		// Otherwise all is good and we can continue, create the meeting and 
		//  set the various attributes, add to the data and return the id
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
	public PastMeeting getPastMeeting(int id)
	{
		// Get the meeting from the data interface, check the meeting instance
		//  if a Future meeting then throw an exception
		Meeting meeting = m_dataInterface.getPastMeeting(id);
		if(meeting instanceof FutureMeetingImpl)
		{
			throw new IllegalArgumentException("Invalid meeting id - is associated with FutureMeeting.");
		}
		
		return (PastMeeting) meeting;
	}

	/**
	 * @see bbk.pij.jsted02.interfaces.ContactManager#getFutureMeeting(int)
	 */
	@Override
	public FutureMeeting getFutureMeeting(int id)
	{
		return m_dataInterface.getFutureMeeting(id);
	}

	/**
	 * @see bbk.pij.jsted02.interfaces.ContactManager#getMeeting(int)
	 */
	@Override
	public Meeting getMeeting(int id)
	{
		return m_dataInterface.getMeeting(id);
	}

	/**
	 * @see bbk.pij.jsted02.interfaces.ContactManager#getFutureMeetingList(bbk.pij.jsted02.interfaces.Contact)
	 */
	@Override
	public List<Meeting> getFutureMeetingList(Contact contact)
	{
		return m_dataInterface.getFutureMeetingList(contact);
	}

	/**
	 * @see bbk.pij.jsted02.interfaces.ContactManager#getFutureMeetingList(java.util.Calendar)
	 */
	@Override
	public List<Meeting> getFutureMeetingList(Calendar date)
	{
		return m_dataInterface.getFutureMeetingList(date);
	}

	/**
	 * @see bbk.pij.jsted02.interfaces.ContactManager#getPastMeetingList(bbk.pij.jsted02.interfaces.Contact)
	 */
	@Override
	public List<PastMeeting> getPastMeetingList(Contact contact)
	{
		// Create list to store meetings that the contact attended and get a
		//  generic list of all meeting objects.
		List<PastMeeting> meetings = new ArrayList<PastMeeting>();
		List<Object> all_meetings = m_dataInterface.getAllMeetings();
		
		// Iterate over all meetings and check if it is a PastMeeting and if
		//  the contact attended the meeting - if so then append to the list.
		for(Object meeting: all_meetings)
		{
			if(meeting instanceof PastMeetingImpl && ((Meeting) meeting).getContacts().contains(contact))
			{
				meetings.add((PastMeeting) meeting);
			}
		}
		
		return meetings;
	}

	/**
	 * @see bbk.pij.jsted02.interfaces.ContactManager#addNewPastMeeting(java.util.Set, java.util.Calendar, java.lang.String)
	 */
	@Override
	public void addNewPastMeeting(Set<Contact> contacts, Calendar date,
			String text)
	{
		PastMeetingImpl meeting = new PastMeetingImpl();
		meeting.setContacts(contacts);
		meeting.setDate(date);
		meeting.setNotes(text);
		m_dataInterface.addMeeting(meeting);
	}

	/**
	 * @see bbk.pij.jsted02.interfaces.ContactManager#addMeetingNotes(int, java.lang.String)
	 */
	@Override
	public void addMeetingNotes(int id, String text)
	{
		MeetingImpl meeting = (MeetingImpl)m_dataInterface.getMeeting(id);
		meeting.addNotes(text);
		m_dataInterface.updMeeting(meeting);
	}

	/**
	 * @see bbk.pij.jsted02.interfaces.ContactManager#addNewContact(java.lang.String, java.lang.String)
	 */
	@Override
	public void addNewContact(String name, String notes) {
		// Create contact, set name and notes and add contact to the
		//  data interface
		ContactImpl contact = new ContactImpl();
		contact.setName(name);
		contact.addNotes(notes);
		m_dataInterface.addContact(contact);
	}

	/**
	 * @see bbk.pij.jsted02.interfaces.ContactManager#getContacts(int[])
	 */
	@Override
	public Set<Contact> getContacts(int... ids)
	{
		return new HashSet<Contact>((Collection<? extends Contact>) m_dataInterface.getContacts(ids));
	}

	/**
	 * @see bbk.pij.jsted02.interfaces.ContactManager#getContacts(java.lang.String)
	 */
	@Override
	public Set<Contact> getContacts(String name)
	{
		return new HashSet<Contact>((Collection<? extends Contact>) m_dataInterface.getContacts(name));
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
	 *  system for use.
	 */
	private void init(boolean test)
	{
		m_initialised = false;
		m_dataInterface = new DataInterface();
		m_initialised = true;
	}
	
	/**
	 * Finalise function, will call flush to save to disk and make sure that
	 *  everything is ok to shutdown the system.
	 *  
	 * @throws Throwable 
	 */
	@Override
	protected void finalize() throws Throwable
	{
		flush();
		m_initialised = false;
		super.finalize();
	}

	public boolean getInitialised() {
		return m_initialised;
	}

}
