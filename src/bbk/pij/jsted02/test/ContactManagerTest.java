package bbk.pij.jsted02.test;

import static org.junit.Assert.*;

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
	public void checkGetMeeting()
	{
		// I know I have created 4 meetings - 3 Future, 1 Past
		assertTrue(m_cmApp.getMeeting(0) instanceof FutureMeetingImpl);
		assertTrue(m_cmApp.getMeeting(1) instanceof PastMeetingImpl);
		assertTrue(m_cmApp.getMeeting(2) instanceof FutureMeetingImpl);
		assertTrue(m_cmApp.getMeeting(3) instanceof FutureMeetingImpl);
		assertTrue(m_cmApp.getMeeting(4) == null);
	}
	
	@Test
	public void checkFutureMeetingListOnContact()
	{
		Set<Contact> contacts = m_cmApp.getContacts("Test1"); 
		List<Meeting> meetings = m_cmApp.getFutureMeetingList(contacts.iterator().next());
		
		// Check only 3 meetings returned
		assertTrue("Incorrect no. meetings returned (" + meetings.size() + "), expected 3", meetings.size() == 3);
		
		// Check they are in chronological order
		assertTrue(meetings.get(0).getDate().compareTo(meetings.get(1).getDate()) < 0);
		assertTrue(meetings.get(1).getDate().compareTo(meetings.get(2).getDate()) < 0);
		
		// Check there are no duplicates
		//TODO: Clarify this
		
		// Check no meetings setup for Test2.
		meetings = m_cmApp.getFutureMeetingList(m_cmApp.getContacts("Test2").iterator().next());
		assertTrue(meetings.size() == 0);
	
	}
	
}
