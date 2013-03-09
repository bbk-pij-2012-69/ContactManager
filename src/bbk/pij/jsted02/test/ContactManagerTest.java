package bbk.pij.jsted02.test;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import bbk.pij.jsted02.ContactImpl;
import bbk.pij.jsted02.ContactManagerImpl;
import bbk.pij.jsted02.interfaces.Contact;
import bbk.pij.jsted02.interfaces.ContactManager;
import bbk.pij.jsted02.test.utils.TestHelper;

public class ContactManagerTest {

	static ContactManager m_cmApp;
	static Set<Contact> m_contacts;
	static Calendar m_date;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		m_cmApp = new ContactManagerImpl();
		m_contacts = new HashSet<Contact>(TestHelper.generateContacts(10));
		m_date = Calendar.getInstance();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test(expected=IllegalArgumentException.class)
	public void addFutureMeetingCheckDate() {
		m_date.set(Calendar.YEAR, 2001);
		m_cmApp.addFutureMeeting(m_contacts, m_date);
	}

	@Test(expected=IllegalArgumentException.class)
	public void addFutureMeetingCheckContacts() {
		ContactImpl contact = new ContactImpl();
		contact.setName("RANDOM_CONTACT");

		Set<Contact> contacts = new HashSet<Contact>();
		contacts.add(contact);
		
		m_date.set(Calendar.YEAR, 2020);
		m_cmApp.addFutureMeeting(contacts, m_date);
	}

	@Test
	public void checkFutureMeeting()
	{
		m_cmApp.addNewContact("Test1", "Test contact 1");
		Set<Contact> contacts = m_cmApp.getContacts("Test1");
		
		m_date.set(Calendar.YEAR, 2020);
		int meeting_id = m_cmApp.addFutureMeeting(contacts, m_date);
		
		assertFalse(m_cmApp.getFutureMeeting(meeting_id) == null);
		assertTrue(m_cmApp.getFutureMeeting(meeting_id * 100) == null);
		
		
	}
	
}
