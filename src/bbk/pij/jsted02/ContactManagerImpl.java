/**
 * ContactManagerImpl - implementation class of ContactManager Interface.
 */
package bbk.pij.jsted02;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

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
	 * @see bbk.pij.jsted02.interfaces.ContactManager#addFutureMeeting(java.util.Set, java.util.Calendar)
	 */
	@Override
	public int addFutureMeeting(Set<Contact> contacts, Calendar date) {
		// TODO Implement functionality
		return 0;
	}

	/**
	 * @see bbk.pij.jsted02.interfaces.ContactManager#getPastMeeting(int)
	 */
	@Override
	public PastMeeting getPastMeeting(int id) {
		// TODO Implement functionality
		return null;
	}

	/**
	 * @see bbk.pij.jsted02.interfaces.ContactManager#getFutureMeeting(int)
	 */
	@Override
	public FutureMeeting getFutureMeeting(int id) {
		// TODO Implement functionality
		return null;
	}

	/**
	 * @see bbk.pij.jsted02.interfaces.ContactManager#getMeeting(int)
	 */
	@Override
	public Meeting getMeeting(int id) {
		// TODO Implement functionality
		return null;
	}

	/**
	 * @see bbk.pij.jsted02.interfaces.ContactManager#getFutureMeetingList(bbk.pij.jsted02.interfaces.Contact)
	 */
	@Override
	public List<Meeting> getFutureMeetingList(Contact contact) {
		// TODO Implement functionality
		return null;
	}

	/**
	 * @see bbk.pij.jsted02.interfaces.ContactManager#getFutureMeetingList(java.util.Calendar)
	 */
	@Override
	public List<Meeting> getFutureMeetingList(Calendar date) {
		// TODO Implement functionality
		return null;
	}

	/**
	 * @see bbk.pij.jsted02.interfaces.ContactManager#getPastMeetingList(bbk.pij.jsted02.interfaces.Contact)
	 */
	@Override
	public List<PastMeeting> getPastMeetingList(Contact contact) {
		// TODO Implement functionality
		return null;
	}

	/**
	 * @see bbk.pij.jsted02.interfaces.ContactManager#addNewPastMeeting(java.util.Set, java.util.Calendar, java.lang.String)
	 */
	@Override
	public void addNewPastMeeting(Set<Contact> contacts, Calendar date,
			String text) {
		// TODO Implement functionality

	}

	/**
	 * @see bbk.pij.jsted02.interfaces.ContactManager#addMeetingNotes(int, java.lang.String)
	 */
	@Override
	public void addMeetingNotes(int id, String text) {
		// TODO Implement functionality

	}

	/**
	 * @see bbk.pij.jsted02.interfaces.ContactManager#addNewContact(java.lang.String, java.lang.String)
	 */
	@Override
	public void addNewContact(String name, String notes) {
		// TODO Implement functionality

	}

	/**
	 * @see bbk.pij.jsted02.interfaces.ContactManager#getContacts(int[])
	 */
	@Override
	public Set<Contact> getContacts(int... ids) {
		// TODO Implement functionality
		return null;
	}

	/**
	 * @see bbk.pij.jsted02.interfaces.ContactManager#getContacts(java.lang.String)
	 */
	@Override
	public Set<Contact> getContacts(String name) {
		// TODO Implement functionality
		return null;
	}

	/**
	 * @see bbk.pij.jsted02.interfaces.ContactManager#flush()
	 */
	@Override
	public void flush() {
		// TODO Implement functionality
	}
	
	/**
	 * Launch function for Contact Manager, runs the main loop for interacting with system.
	 */
	private static void launch()
	{
		List<Method> methods = new ArrayList<Method>();
		UserInterface ui = new UserInterface();
		
		//TODO Add all relevant methods to the list.
		try {
			methods.add(ui.getClass().getMethod("exit", null));
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		boolean running = true;
		do{
			running = ui.printInterface(methods);
		} while(running);
	}
	
	/**
	 * main Java function - entry point to program
	 */
	public static void main(String [] args){
		
		//TODO Initialise system (load contacts.txt and check all is good).
		//TODO If an exception occurred previously, would we find a corrupted contacts.txt file?
		launch();
		//TODO Clean-up system (save changes to contacts.txt and close gracefully).
		//TODO If an exception occurs, should we save the data that has been edited during the session?

	}

}
