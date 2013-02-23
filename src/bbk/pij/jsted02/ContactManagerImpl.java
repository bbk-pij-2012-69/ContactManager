/**
 * ContactManagerImpl - implementation class of ContactManager Interface.
 */
package bbk.pij.jsted02;

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
import bbk.pij.jsted02.ui.UserInterface;

/**
 * @author Luke Stedman (jsted02), MSc CS Yr1 2012/13
 */
public class ContactManagerImpl implements ContactManager {
	/**
	 * initialised flag is set to true once the system has initialised and is
	 * ready to be used.
	 */
	private boolean m_initialised = false;
	/**
	 * DataInterface variable, this will provide the data used in the system.
	 */
	private DataInterface m_dataInterface = new DataInterface();

	/**
	 * Constructor, runs initialisation code to initialise the system.
	 */
	public ContactManagerImpl()
	{
		// Run the init function, this will load the require data from disk and
		// make sure that everything is behaving itself.
		this.init();
	}
	
	/**
	 * @see bbk.pij.jsted02.interfaces.ContactManager#addFutureMeeting(java.util.Set, java.util.Calendar)
	 */
	@Override
	public int addFutureMeeting(Set<Contact> contacts, Calendar date)
	{
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
		return this.m_dataInterface.getPastMeeting(id);
	}

	/**
	 * @see bbk.pij.jsted02.interfaces.ContactManager#getFutureMeeting(int)
	 */
	@Override
	public FutureMeeting getFutureMeeting(int id)
	{
		return this.m_dataInterface.getFutureMeeting(id);
	}

	/**
	 * @see bbk.pij.jsted02.interfaces.ContactManager#getMeeting(int)
	 */
	@Override
	public Meeting getMeeting(int id)
	{
		return this.m_dataInterface.getMeeting(id);
	}

	/**
	 * @see bbk.pij.jsted02.interfaces.ContactManager#getFutureMeetingList(bbk.pij.jsted02.interfaces.Contact)
	 */
	@Override
	public List<Meeting> getFutureMeetingList(Contact contact)
	{
		return this.m_dataInterface.getFutureMeetingList(contact);
	}

	/**
	 * @see bbk.pij.jsted02.interfaces.ContactManager#getFutureMeetingList(java.util.Calendar)
	 */
	@Override
	public List<Meeting> getFutureMeetingList(Calendar date)
	{
		return this.m_dataInterface.getFutureMeetingList(date);
	}

	/**
	 * @see bbk.pij.jsted02.interfaces.ContactManager#getPastMeetingList(bbk.pij.jsted02.interfaces.Contact)
	 */
	@Override
	public List<PastMeeting> getPastMeetingList(Contact contact)
	{
		return this.m_dataInterface.getPastMeetingList(contact);
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
		this.m_dataInterface.addMeeting(meeting);
	}

	/**
	 * @see bbk.pij.jsted02.interfaces.ContactManager#addMeetingNotes(int, java.lang.String)
	 */
	@Override
	public void addMeetingNotes(int id, String text)
	{
		MeetingImpl meeting = (MeetingImpl)this.m_dataInterface.getMeeting(id);
		meeting.addNotes(text);
		this.m_dataInterface.updMeeting(meeting);
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
		return new HashSet<Contact>((Collection<? extends Contact>) this.m_dataInterface.getContacts(ids));
	}

	/**
	 * @see bbk.pij.jsted02.interfaces.ContactManager#getContacts(java.lang.String)
	 */
	@Override
	public Set<Contact> getContacts(String name)
	{
		return new HashSet<Contact>((Collection<? extends Contact>) this.m_dataInterface.getContacts(name));
	}

	/**
	 * @see bbk.pij.jsted02.interfaces.ContactManager#flush()
	 */
	@Override
	public void flush() {
		m_dataInterface.flush();
	}
	
	/**
	 * Launch function for Contact Manager, runs the main loop for interacting with system.
	 * 
	 * @return null
	 */
	private void launch()
	{
		// Create user interface object and 
		UserInterface ui = new UserInterface(this);
		boolean running = true;
		do
		{
			running = ui.mainMenu();
		} while(running);
	}
	
	/**
	 * Initialise function, will load contacts data into memory and prepare
	 *  system for use.
	 *  
	 *  @return null
	 */
	private void init()
	{
		// Load data for the data interface object
		//dataInterface.init();
		//TODO If an exception occurred previously, would we find a corrupted contacts.txt file?
		this.m_initialised = true;
	}
	
	/**
	 * Finalise function, will call flush to save to disk and make sure that
	 *  everything is ok to shutdown the system.
	 *  
	 *  @return null
	 */
	private void finalise()
	{
		//TODO Clean-up system.
		// Flush data to disk
		this.flush();
		//TODO If an exception occurs, should we save the data that has been edited during the session?
		this.m_initialised = false;
	}
	
	/**
	 * main Java function - entry point to program
	 * 
	 * @return null
	 */
	public static void main(String [] args){
		
		// Create ContactManagerImpl object, check the system is initialised
		// and launch the system.
		ContactManagerImpl cmApp = new ContactManagerImpl();
		
		if(cmApp.m_initialised)
		{
			cmApp.launch();
		}
		
		// Only run the finalise code if we are initialised.
		if(cmApp.m_initialised)
		{
			cmApp.finalise();
		}
	}

}
