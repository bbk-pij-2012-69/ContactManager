package bbk.pij.jsted02.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import bbk.pij.jsted02.interfaces.Contact;
import bbk.pij.jsted02.interfaces.Meeting;
import bbk.pij.jsted02.meetings.MeetingImpl;
import bbk.pij.jsted02.test.utils.TestHelper;

public class FutureMeetingTest {

	static List<MeetingImpl> m_meetings;
	static List<Contact> m_contacts;

	/**
	 * Initialisation method, sets up a list of contacts and meetings to use in the tests.
	 */
	@BeforeClass
	public static void setUp()
	{
		m_meetings = TestHelper.generateFutureMeetings(10, 100);
		m_contacts = TestHelper.generateContacts(m_meetings.size());
		for(MeetingImpl meeting : m_meetings)
		{
			meeting.setContacts(m_contacts.subList(0, meeting.getId()));
		}
	}
	
	/**
	 * Finalise method, tears down anything that does not need to persist.
	 */
	@AfterClass
	public static void tearDown()
	{
	}

	/**
	 * checkId test, tests the uniqueness of id's on creation
	 */
	@Test
	public void checkId()
	{
		List<Integer> ids = new ArrayList<Integer>();
		for(Meeting meeting : m_meetings)
		{
			int id = meeting.getId();
			if(ids.contains(id))
			{
				fail(id + " is already present against a meeting");
			}
			ids.add(id);
		}
	}

	/**
	 * checkContacts test, tests that the correct number of contacts are associated with the meeting
	 */
	@Test
	public void checkContactCount()
	{
		for(Meeting meeting : m_meetings)
		{
			Set<Contact> contacts = meeting.getContacts();
			int id = meeting.getId();
			if(id != contacts.size())
			{
				fail(id + " does not return the correct number of contacts: " + contacts.size() + " v. " + id);
			}
		}
	}
	

	/**
	 * checkContacts test, tests that the correct contacts are associated with the meeting
	 */
	@Test
	public void checkContacts()
	{
		for(Meeting meeting : m_meetings)
		{
			Set<Contact> contacts = meeting.getContacts();
			int id = meeting.getId();
			for(Contact contact : contacts)
			{
				if(id <= contact.getId())
				{
					fail(id + " does not contain a correct contact: " + contact.getId() + " v. " + id);
				}
				
			}
		}
	}	

}
