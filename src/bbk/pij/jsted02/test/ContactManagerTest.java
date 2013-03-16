/**
 * Test class for testing of core contact manager application
 */

package bbk.pij.jsted02.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import bbk.pij.jsted02.ContactImpl;
import bbk.pij.jsted02.ContactManagerImpl;
import bbk.pij.jsted02.interfaces.Contact;
import bbk.pij.jsted02.interfaces.ContactManager;
import bbk.pij.jsted02.interfaces.Meeting;
import bbk.pij.jsted02.interfaces.PastMeeting;
import bbk.pij.jsted02.meetings.FutureMeetingImpl;
import bbk.pij.jsted02.meetings.PastMeetingImpl;
import bbk.pij.jsted02.test.utils.TestHelper;
/**
 * @author Luke Stedman (jsted02), MSc CS Yr1 2012/13
 */
public class ContactManagerTest {

	// Static members - used to help test the app.
	static ContactManager cmApp;
	static Set<Contact> contacts;
	private static Calendar date1 = Calendar.getInstance();
	private static Calendar date2 = Calendar.getInstance();
	private static Calendar date3 = Calendar.getInstance();
	private static Calendar date4 = Calendar.getInstance();
	private static Calendar date5 = Calendar.getInstance();
	private static Calendar date6 = Calendar.getInstance();


	/**
	 * Initialize a set of meetings and contacts before the class is started
	 * 
	 * @throws Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		cmApp = new ContactManagerImpl();
		contacts = new HashSet<Contact>(TestHelper.generateContacts(10));

		cmApp.addNewContact("Test1", "Test contact 1");
		cmApp.addNewContact("Test2", "Test contact 2");

		Set<Contact> meetingContacts = cmApp.getContacts("Test1");

		// Add a future meeting
		date1.set(Calendar.YEAR, 2001);
		date2.set(Calendar.YEAR, 2003);
		date3.set(Calendar.YEAR, 2010);
		date4.set(Calendar.YEAR, 2015);
		date5.set(Calendar.YEAR, 2017);
		date6.set(Calendar.YEAR, 2020);

		cmApp.addFutureMeeting(meetingContacts, date6);

		// Add a meeting in the past
		cmApp.addNewPastMeeting(meetingContacts, date1,
				"Some notes...");

		// Add a second future meeting
		cmApp.addFutureMeeting(meetingContacts, date4);

		// Add a third future meeting
		cmApp.addFutureMeeting(meetingContacts, date5);

		// Add a second meeting in the past
		cmApp.addNewPastMeeting(meetingContacts, date2,
				"Some notes...");

		// Add a third meeting in the past
		cmApp.addNewPastMeeting(meetingContacts, date3,
				"Some notes...");

	}

	/**
	 * Test future meeting throwing if the date is in the past
	 */
	@Test(expected = IllegalArgumentException.class)
	public void addFutureMeetingCheckDate() {
		cmApp.addFutureMeeting(contacts, date1);
	}

	/**
	 * Test future meeting throwing if the contact is not in the system
	 */
	@Test(expected = IllegalArgumentException.class)
	public void addFutureMeetingCheckContacts() {
		ContactImpl contact = new ContactImpl();
		contact.setName("RANDOM_CONTACT");

		Set<Contact> contacts = new HashSet<Contact>();
		contacts.add(contact);

		cmApp.addFutureMeeting(contacts, date6);
	}
	
	/**
	 * Tests that nulls are returned for an invalid index
	 * Tests that nulls are NOT returned for valid index 
	 */
	@Test
	public void checkFutureMeeting() {
		assertFalse(cmApp.getFutureMeeting(0) == null);
		assertTrue(cmApp.getFutureMeeting(27) == null);
	}

	/**
	 * Test that getting a past meeting that is a future meeting throws
	 */
	@Test(expected = IllegalArgumentException.class)
	public void getPastMeetingInTheFuture() {
		cmApp.getPastMeeting(3);
	}

	/**
	 * Checks that past meetings are returned from past meeting list
	 * That their id's do not return nulls.
	 * That a random number does return null. 
	 */
	@Test
	public void checkPastMeeting() {
		Set<Contact> contacts = cmApp.getContacts("Test1");

		List<PastMeeting> meetings = cmApp.getPastMeetingList(contacts
				.iterator().next());
		for (PastMeeting meeting : meetings) {
			assertFalse(cmApp.getPastMeeting(meeting.getId()) == null);
		}

		assertTrue(cmApp.getPastMeeting(12547) == null);
	}

	@Test
	public void checkPastMeetingList() {
		Set<Contact> contacts = cmApp.getContacts("Test1");

		List<PastMeeting> meetings = cmApp.getPastMeetingList(contacts
				.iterator().next());

		int other_id = 0;

		for (PastMeeting meeting : meetings) {
			assertFalse(cmApp.getPastMeeting(meeting.getId()) == null);
			other_id += meeting.getId();
		}

		assertTrue(cmApp.getPastMeeting(other_id * 100 + 1) == null);
	}

	@Test
	public void checkGetMeeting() {
		// I know I have created 4 meetings - 3 Future, 1 Past
		assertTrue(cmApp.getMeeting(0) instanceof FutureMeetingImpl);
		assertTrue(cmApp.getMeeting(1) instanceof PastMeetingImpl);
		assertTrue(cmApp.getMeeting(2) instanceof FutureMeetingImpl);
		assertTrue(cmApp.getMeeting(3) instanceof FutureMeetingImpl);
		assertTrue(cmApp.getMeeting(4) instanceof PastMeetingImpl);
		assertTrue(cmApp.getMeeting(5) instanceof PastMeetingImpl);
		assertTrue(cmApp.getMeeting(25) == null);
	}

	@Test
	public void checkFutureMeetingListOnContact() {
		Set<Contact> contacts = cmApp.getContacts("Test1");
		List<Meeting> meetings = cmApp.getFutureMeetingList(contacts
				.iterator().next());

		// Check only 3 meetings returned
		assertTrue("Incorrect no. meetings returned (" + meetings.size()
				+ "), expected 3", meetings.size() == 3);

		// Check they are in chronological order (ascending nearest first)
		assertTrue(meetings.get(0).getDate()
				.compareTo(meetings.get(1).getDate()) < 0);
		assertTrue(meetings.get(1).getDate()
				.compareTo(meetings.get(2).getDate()) < 0);

		// Check there are no duplicates
		// TODO: Clarify this

		// Check no meetings setup for Test2.
		meetings = cmApp.getFutureMeetingList(cmApp.getContacts("Test2")
				.iterator().next());
		assertTrue(meetings.size() == 0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void checkFutureMeetingListUnknowContact() {
		ContactImpl contact = new ContactImpl();
		contact.setName("RANDOM_CONTACT");
		cmApp.getFutureMeetingList(contact);
	}

	@Test
	public void checkPastMeetingListOnContact() {
		Set<Contact> contacts = cmApp.getContacts("Test1");
		List<PastMeeting> meetings = cmApp.getPastMeetingList(contacts
				.iterator().next());

		// Check only 3 meetings returned
		assertTrue("Incorrect no. meetings returned (" + meetings.size()
				+ "), expected 3", meetings.size() == 3);

		// Check they are in chronological order (descending, most recent first)
		assertTrue(meetings.get(0).getDate()
				.compareTo(meetings.get(1).getDate()) > 0);
		assertTrue(meetings.get(1).getDate()
				.compareTo(meetings.get(2).getDate()) > 0);

		// Check there are no duplicates
		// TODO: Clarify this

		// Check no meetings setup for Test2.
		meetings = cmApp.getPastMeetingList(cmApp.getContacts("Test2")
				.iterator().next());
		assertTrue(meetings.size() == 0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void checkPastMeetingListUnknowContact() {
		ContactImpl contact = new ContactImpl();
		contact.setName("RANDOM_CONTACT");
		cmApp.getPastMeetingList(contact);
	}

	@Test(expected = IllegalArgumentException.class)
	public void addNewPastMeetingListUnknowContact() {
		ContactImpl contact = new ContactImpl();
		contact.setName("RANDOM_CONTACT");
		Set<Contact> contacts = cmApp.getContacts("Test1");
		contacts.add(contact);
		Calendar date = Calendar.getInstance();
		date.set(Calendar.YEAR, 2008);
		cmApp.addNewPastMeeting(contacts, date, "some notes...");
	}

	@Test(expected = IllegalArgumentException.class)
	public void addNewPastMeetingListEmptyContactsList() {
		Set<Contact> contacts = new HashSet<Contact>();
		Calendar date = Calendar.getInstance();
		date.set(Calendar.YEAR, 2008);
		cmApp.addNewPastMeeting(contacts, date, "some notes...");
	}

	@Test(expected = NullPointerException.class)
	public void addNewPastMeetingListNullContact() {
		Calendar date = Calendar.getInstance();
		date.set(Calendar.YEAR, 2008);
		cmApp.addNewPastMeeting(null, date, "some notes...");
	}

	@Test(expected = NullPointerException.class)
	public void addNewPastMeetingListNullDate() {
		Set<Contact> contacts = cmApp.getContacts("Test1");
		cmApp.addNewPastMeeting(contacts, null, "some notes...");
	}

	@Test(expected = NullPointerException.class)
	public void addNewPastMeetingListNullNotes() {
		Set<Contact> contacts = cmApp.getContacts("Test1");
		Calendar date = Calendar.getInstance();
		date.set(Calendar.YEAR, 2008);
		cmApp.addNewPastMeeting(contacts, date, null);
	}

	@Test
	public void checkGetContactsName() {
		assertTrue(cmApp.getContacts("Test").size() == 2);
		assertTrue(cmApp.getContacts("Test1").size() == 1);
		assertTrue(cmApp.getContacts("RANDOM1").size() == 0);
	}

	@Test(expected = NullPointerException.class)
	public void getContactsNameNull() {
		String name = null;
		cmApp.getContacts(name);
	}

	@Test
	public void checkGetContactsID() {
		Set<Contact> contacts = cmApp.getContacts("Test");
		int[] ids = new int[contacts.size()];
		for (Contact contact : contacts) {
			assertTrue(cmApp.getContacts(contact.getId()).size() == 1);
			assertTrue(cmApp.getContacts(contact.getId()).iterator().next()
					.getId() == contact.getId());
		}

		int count = 0;
		for (Contact contact : contacts) {
			ids[count++] = contact.getId();

		}

		assertTrue(cmApp.getContacts(ids).size() == ids.length);
	}

	@Test(expected = IllegalArgumentException.class)
	public void getContactsIDNotPresent() {
		cmApp.getContacts(5);
	}

	@Test
	public void checkNewContactAdded() {
		cmApp.addNewContact("NewContact1", "New Contact 1");
		assertTrue(cmApp.getContacts("NewContact").size() == 1);

	}

	@Test(expected = NullPointerException.class)
	public void addContactNullFields() {
		String name = null;
		String notes = null;
		cmApp.addNewContact(name, notes);
	}

	@Test(expected = NullPointerException.class)
	public void addContactNullNameField() {
		String name = null;
		String notes = "SomeNotes";
		cmApp.addNewContact(name, notes);
	}

	@Test(expected = NullPointerException.class)
	public void addContactNullNoteFields() {
		String name = "SomeName";
		String notes = null;
		cmApp.addNewContact(name, notes);
	}

	@Test
	public void checkGetFutureMeetingListDate() {
		Calendar setDate = Calendar.getInstance();
		setDate.set(2025, 8, 31, 12, 0);

		Set<Contact> contacts = cmApp.getContacts("Test");

		cmApp.addFutureMeeting(contacts, setDate);

		Calendar checkDate = Calendar.getInstance();
		checkDate.set(2025, 8, 31, 0, 0);
		List<Meeting> meetings = cmApp.getFutureMeetingList(checkDate);
		assertTrue("Expected 1 meeting, got " + meetings.size(),
				meetings.size() == 1);
	}

	@Test
	public void checkGetFutureMeetingListDateFutOrder() {
		Set<Contact> contacts = cmApp.getContacts("Test");

		Calendar setDate = Calendar.getInstance();
		setDate.set(2025, 5, 15, 11, 30);
		cmApp.addFutureMeeting(contacts, (Calendar) setDate.clone());

		setDate.set(2025, 5, 15, 13, 30);
		cmApp.addFutureMeeting(contacts, (Calendar) setDate.clone());

		setDate.set(2025, 5, 15, 15, 30);
		cmApp.addFutureMeeting(contacts, (Calendar) setDate.clone());

		Calendar checkDate = Calendar.getInstance();
		checkDate.set(2025, 5, 15, 0, 0);

		List<Meeting> meetings = cmApp.getFutureMeetingList(checkDate);
		assertTrue(meetings.size() == 3);

		assertTrue(meetings.get(0).getDate()
				.compareTo(meetings.get(1).getDate()) < 0);
		assertTrue(meetings.get(1).getDate()
				.compareTo(meetings.get(2).getDate()) < 0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void checkAddMeetingNotesInvalidId() {
		String text = "Test Notes";
		cmApp.addMeetingNotes(54321, text);
	}

	@Test(expected = IllegalStateException.class)
	public void checkAddMeetingNotesFutureMeeting() {
		Set<Contact> contacts = cmApp.getContacts("Test1");
		Calendar date = Calendar.getInstance();
		date.add(Calendar.HOUR, 5);
		int meeting_id = cmApp.addFutureMeeting(contacts, date);
		String text = "Test Notes";
		cmApp.addMeetingNotes(meeting_id, text);
	}

	@Test(expected = NullPointerException.class)
	public void checkAddMeetingNotesNullNotes() {
		Set<Contact> contacts = cmApp.getContacts("Test1");

		List<PastMeeting> meetings = cmApp.getPastMeetingList(contacts
				.iterator().next());

		int id = meetings.get(0).getId();
		String text = null;
		cmApp.addMeetingNotes(id, text);
	}

	@Test
	public void checkAddMeetingNotesFutureConversion() {
		Set<Contact> contacts = cmApp.getContacts("Test1");
		Calendar date = Calendar.getInstance();
		date.add(Calendar.HOUR, 1);
		int meeting_id = cmApp.addFutureMeeting(contacts, date);

		date.add(Calendar.HOUR, -2);

		cmApp.addMeetingNotes(meeting_id, "Meeting completed...");

		assertTrue(cmApp.getMeeting(meeting_id) instanceof PastMeetingImpl);
	}

	@Test
	public void checkAddMeetingNotes() {
		Set<Contact> contacts = cmApp.getContacts("Test1");
		Calendar date = Calendar.getInstance();
		date.add(Calendar.DATE, -20);
		int meeting_id = cmApp.getPastMeetingList(contacts.iterator().next())
				.get(0).getId();
		PastMeeting meeting = (PastMeeting) cmApp.getMeeting(meeting_id);

		assertTrue(meeting instanceof PastMeetingImpl);

		String old_notes = meeting.getNotes();
		cmApp.addMeetingNotes(meeting_id, "Meeting completed...");
		meeting = (PastMeeting) cmApp.getMeeting(meeting_id);

		assertFalse(old_notes.equals(meeting.getNotes()));

		String newNote = "...really, it has been completed";
		String newNotes = meeting.getNotes() + "\n" + newNote;
		cmApp.addMeetingNotes(meeting_id, newNote);

		assertTrue("meeting notes (" + meeting.getNotes()
				+ ") do not match expected notes (" + newNotes + ")",
				newNotes.equals(meeting.getNotes()));
	}

}
