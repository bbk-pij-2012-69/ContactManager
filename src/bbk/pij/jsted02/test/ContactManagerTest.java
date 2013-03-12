package bbk.pij.jsted02.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.AfterClass;
import org.junit.Before;
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

public class ContactManagerTest {

	static ContactManager m_cmApp;
	static Set<Contact> m_contacts;
	private static Calendar m_date_2001 = Calendar.getInstance();
	private static Calendar m_date_2003 = Calendar.getInstance();
	private static Calendar m_date_2010 = Calendar.getInstance();
	private static Calendar m_date_2015 = Calendar.getInstance();
	private static Calendar m_date_2017 = Calendar.getInstance();
	private static Calendar m_date_2020 = Calendar.getInstance();

	@Before
	public void setUpBefore()
	{


	}

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		m_cmApp = new ContactManagerImpl();
		m_contacts = new HashSet<Contact>(TestHelper.generateContacts(10));

		m_cmApp.addNewContact("Test1", "Test contact 1");
		m_cmApp.addNewContact("Test2", "Test contact 2");

		Set<Contact> meeting_contacts = m_cmApp.getContacts("Test1"); 

		// Add a future meeting
		m_date_2001.set(Calendar.YEAR, 2001);
		m_date_2003.set(Calendar.YEAR, 2003);
		m_date_2010.set(Calendar.YEAR, 2010);
		m_date_2015.set(Calendar.YEAR, 2015);
		m_date_2017.set(Calendar.YEAR, 2017);
		m_date_2020.set(Calendar.YEAR, 2020);

		m_cmApp.addFutureMeeting(meeting_contacts, m_date_2020);

		// Add a meeting in the past
		m_cmApp.addNewPastMeeting(meeting_contacts, m_date_2001, "Some notes...");

		// Add a second future meeting
		m_cmApp.addFutureMeeting(meeting_contacts, m_date_2015);

		// Add a third future meeting
		m_cmApp.addFutureMeeting(meeting_contacts , m_date_2017);

		// Add a second meeting in the past
		m_cmApp.addNewPastMeeting(meeting_contacts, m_date_2003, "Some notes...");

		// Add a third meeting in the past
		m_cmApp.addNewPastMeeting(meeting_contacts, m_date_2010, "Some notes...");

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test(expected=IllegalArgumentException.class)
	public void addFutureMeetingCheckDate() {
		m_cmApp.addFutureMeeting(m_contacts, m_date_2001);
	}

	@Test(expected=IllegalArgumentException.class)
	public void addFutureMeetingCheckContacts() {
		ContactImpl contact = new ContactImpl();
		contact.setName("RANDOM_CONTACT");

		Set<Contact> contacts = new HashSet<Contact>();
		contacts.add(contact);

		m_cmApp.addFutureMeeting(contacts, m_date_2020);
	}

	@Test
	public void checkFutureMeeting()
	{
		assertFalse(m_cmApp.getFutureMeeting(0) == null);
		assertTrue(m_cmApp.getFutureMeeting(27) == null);
	}

	@Test(expected=IllegalArgumentException.class)
	public void getPastMeetingInTheFuture() {
		m_cmApp.getPastMeeting(3);
	}

	@Test
	public void checkPastMeeting()
	{
		Set<Contact> contacts = m_cmApp.getContacts("Test1");

		List<PastMeeting> meetings = m_cmApp.getPastMeetingList(contacts.iterator().next());

		int other_id = 0;

		for(PastMeeting meeting : meetings)
		{
			assertFalse(m_cmApp.getPastMeeting(meeting.getId()) == null);
			other_id += meeting.getId();
		}

		assertTrue(m_cmApp.getPastMeeting(other_id * 100 + 1) == null);
	}

	@Test
	public void checkPastMeetingList()
	{
		Set<Contact> contacts = m_cmApp.getContacts("Test1");

		List<PastMeeting> meetings = m_cmApp.getPastMeetingList(contacts.iterator().next());

		int other_id = 0;

		for(PastMeeting meeting : meetings)
		{
			assertFalse(m_cmApp.getPastMeeting(meeting.getId()) == null);
			other_id += meeting.getId();
		}

		assertTrue(m_cmApp.getPastMeeting(other_id * 100 + 1) == null);
	}

	@Test
	public void checkGetMeeting()
	{
		// I know I have created 4 meetings - 3 Future, 1 Past
		assertTrue(m_cmApp.getMeeting(0) instanceof FutureMeetingImpl);
		assertTrue(m_cmApp.getMeeting(1) instanceof PastMeetingImpl);
		assertTrue(m_cmApp.getMeeting(2) instanceof FutureMeetingImpl);
		assertTrue(m_cmApp.getMeeting(3) instanceof FutureMeetingImpl);
		assertTrue(m_cmApp.getMeeting(4) instanceof PastMeetingImpl);
		assertTrue(m_cmApp.getMeeting(5) instanceof PastMeetingImpl);
		assertTrue(m_cmApp.getMeeting(25) == null);
	}

	@Test
	public void checkFutureMeetingListOnContact()
	{
		Set<Contact> contacts = m_cmApp.getContacts("Test1"); 
		List<Meeting> meetings = m_cmApp.getFutureMeetingList(contacts.iterator().next());

		// Check only 3 meetings returned
		assertTrue("Incorrect no. meetings returned (" + meetings.size() + "), expected 3", meetings.size() == 3);

		// Check they are in chronological order (ascending nearest first)
		assertTrue(meetings.get(0).getDate().compareTo(meetings.get(1).getDate()) < 0);
		assertTrue(meetings.get(1).getDate().compareTo(meetings.get(2).getDate()) < 0);

		// Check there are no duplicates
		//TODO: Clarify this

		// Check no meetings setup for Test2.
		meetings = m_cmApp.getFutureMeetingList(m_cmApp.getContacts("Test2").iterator().next());
		assertTrue(meetings.size() == 0);
	}

	@Test(expected=IllegalArgumentException.class)
	public void checkFutureMeetingListUnknowContact()
	{
		ContactImpl contact = new ContactImpl();
		contact.setName("RANDOM_CONTACT");
		m_cmApp.getFutureMeetingList(contact);
	}

	@Test
	public void checkPastMeetingListOnContact()
	{
		Set<Contact> contacts = m_cmApp.getContacts("Test1"); 
		List<PastMeeting> meetings = m_cmApp.getPastMeetingList(contacts.iterator().next());

		// Check only 3 meetings returned
		assertTrue("Incorrect no. meetings returned (" + meetings.size() + "), expected 3", meetings.size() == 3);

		// Check they are in chronological order (descending, most recent first)
		assertTrue(meetings.get(0).getDate().compareTo(meetings.get(1).getDate()) > 0);
		assertTrue(meetings.get(1).getDate().compareTo(meetings.get(2).getDate()) > 0);

		// Check there are no duplicates
		//TODO: Clarify this

		// Check no meetings setup for Test2.
		meetings = m_cmApp.getPastMeetingList(m_cmApp.getContacts("Test2").iterator().next());
		assertTrue(meetings.size() == 0);
	}	

	@Test(expected=IllegalArgumentException.class)
	public void checkPastMeetingListUnknowContact()
	{
		ContactImpl contact = new ContactImpl();
		contact.setName("RANDOM_CONTACT");
		m_cmApp.getPastMeetingList(contact);
	}

	@Test(expected=IllegalArgumentException.class)
	public void addNewPastMeetingListUnknowContact()
	{
		ContactImpl contact = new ContactImpl();
		contact.setName("RANDOM_CONTACT");
		Set<Contact> contacts = m_cmApp.getContacts("Test1"); 
		contacts.add(contact);
		Calendar date = Calendar.getInstance();
		date.set(Calendar.YEAR, 2008);
		m_cmApp.addNewPastMeeting(contacts, date, "some notes...");
	}

	@Test(expected=IllegalArgumentException.class)
	public void addNewPastMeetingListEmptyContactsList()
	{
		Set<Contact> contacts = new HashSet<Contact>(); 
		Calendar date = Calendar.getInstance();
		date.set(Calendar.YEAR, 2008);
		m_cmApp.addNewPastMeeting(contacts, date, "some notes...");
	}

	@Test(expected=NullPointerException.class)
	public void addNewPastMeetingListNullContact()
	{
		Calendar date = Calendar.getInstance();
		date.set(Calendar.YEAR, 2008);
		m_cmApp.addNewPastMeeting(null, date, "some notes...");
	}
	@Test(expected=NullPointerException.class)
	public void addNewPastMeetingListNullDate()
	{
		Set<Contact> contacts = m_cmApp.getContacts("Test1"); 
		m_cmApp.addNewPastMeeting(contacts, null, "some notes...");
	}
	@Test(expected=NullPointerException.class)
	public void addNewPastMeetingListNullNotes()
	{
		Set<Contact> contacts = m_cmApp.getContacts("Test1"); 
		Calendar date = Calendar.getInstance();
		date.set(Calendar.YEAR, 2008);
		m_cmApp.addNewPastMeeting(contacts, date, null);
	}
	
	@Test
	public void checkGetContactsName()
	{
		assertTrue(m_cmApp.getContacts("Test").size() == 2);
		assertTrue(m_cmApp.getContacts("Test1").size() == 1);
		assertTrue(m_cmApp.getContacts("RANDOM1").size() == 0);
		}
	
	@Test(expected=NullPointerException.class)
	public void getContactsNameNull()
	{
		String name = null;
		m_cmApp.getContacts(name);
	}
	
	
	@Test
	public void checkGetContactsID()
	{
		Set<Contact> contacts = m_cmApp.getContacts("Test");
		int[] ids = new int[contacts.size()];
		for(Contact contact: contacts)
		{
			assertTrue(m_cmApp.getContacts(contact.getId()).size() == 1);
			assertTrue(m_cmApp.getContacts(contact.getId()).iterator().next().getId() == contact.getId());
		}
			
		int count =0;
		for(Contact contact: contacts)
		{
			ids[count++] = contact.getId();
			
		}	
	
		assertTrue(m_cmApp.getContacts(ids).size() == ids.length);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void getContactsIDNotPresent()
	{
		m_cmApp.getContacts(5);
	}
	
	@Test
	public void checkNewContactAdded()
	{
		m_cmApp.addNewContact("NewContact1", "New Contact 1");
		assertTrue(m_cmApp.getContacts("NewContact").size() == 1);
		
	}
	
	@Test(expected=NullPointerException.class)
	public void addContactNullFields()
	{
		String name = null;
		String notes = null;
		m_cmApp.addNewContact(name, notes);
	}
	
	
	@Test(expected=NullPointerException.class)
	public void addContactNullNameField()
	{
		String name = null;
		String notes = "SomeNotes";
		m_cmApp.addNewContact(name, notes);
	}
	
	@Test(expected=NullPointerException.class)
	public void addContactNullNoteFields()
	{
		String name = "SomeName";
		String notes = null;
		m_cmApp.addNewContact(name, notes);
	}
	
	@Test
	public void checkGetFutureMeetingListDate()
	{
		Calendar setDate = Calendar.getInstance();
		setDate.set(2025, 8, 31, 12, 0);

		Set<Contact> contacts = m_cmApp.getContacts("Test");
		
		m_cmApp.addFutureMeeting(contacts, setDate);
		
		Calendar checkDate = Calendar.getInstance();
		checkDate.set(2025, 8, 31, 0, 0);
		List<Meeting> meetings = m_cmApp.getFutureMeetingList(checkDate);
		assertTrue("Expected 1 meeting, got " + meetings.size(), meetings.size() == 1);
	}
	
	@Test
	public void checkGetFutureMeetingListDateFutOrder()
	{
		Set<Contact> contacts = m_cmApp.getContacts("Test");

		Calendar setDate = Calendar.getInstance();
		setDate.set(2025, 5, 15, 11, 30);
		m_cmApp.addFutureMeeting(contacts, (Calendar) setDate.clone());

		setDate.set(2025, 5, 15, 13, 30);
		m_cmApp.addFutureMeeting(contacts, (Calendar) setDate.clone());

		setDate.set(2025, 5, 15, 15, 30);
		m_cmApp.addFutureMeeting(contacts, (Calendar) setDate.clone());

		Calendar checkDate = Calendar.getInstance();
		checkDate.set(2025, 5, 15, 0, 0);

		List<Meeting> meetings =m_cmApp.getFutureMeetingList(checkDate);
		assertTrue(meetings.size() == 3);

		assertTrue(meetings.get(0).getDate().compareTo(meetings.get(1).getDate()) < 0 );
		assertTrue(meetings.get(1).getDate().compareTo(meetings.get(2).getDate()) < 0 );
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void checkAddMeetingNotesInvalidId()
	{
		String text = "Test Notes";
		m_cmApp.addMeetingNotes(54321, text);
	}
	
	@Test(expected=IllegalStateException.class)
	public void checkAddMeetingNotesFutureMeeting()
	{
		Set<Contact> contacts = m_cmApp.getContacts("Test1");

		List<Meeting> meetings = m_cmApp.getFutureMeetingList(contacts.iterator().next());

		int id = meetings.get(0).getId();
		String text = "Test Notes";
		m_cmApp.addMeetingNotes(id, text);
	}
	

	@Test(expected=NullPointerException.class)
	public void checkAddMeetingNotesNullNotes()
	{
		Set<Contact> contacts = m_cmApp.getContacts("Test1");

		List<PastMeeting> meetings = m_cmApp.getPastMeetingList(contacts.iterator().next());

		int id = meetings.get(0).getId();
		String text = null;
		m_cmApp.addMeetingNotes(id, text);
	}
	
	
}
