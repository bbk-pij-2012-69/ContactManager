package bbk.pij.jsted02.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import bbk.pij.jsted02.interfaces.Contact;
import bbk.pij.jsted02.interfaces.Meeting;
import bbk.pij.jsted02.test.utils.TestHelper;

public class FutureMeetingTest {

	List<Meeting> m_meetings;
	List<Contact> m_contacts;
	
	@Before
	public void setUp() throws Exception
	{
		m_meetings = new ArrayList<Meeting>();
		m_contacts = TestHelper.generateContacts(m_meetings.size());
	}

	@After
	public void tearDown() throws Exception
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
	 * checkContacts test, tests that the correct contacts are associated with the meeting
	 */
	@Test
	public void checkContacts()
	{
		List<Integer> ids = new ArrayList<Integer>();
		for(Meeting meeting : m_meetings)
		{
			meeting.getContacts();
			int id = meeting.getId();
			if(ids.contains(id))
			{
				fail(id + " is already present against a meeting");
			}
			ids.add(id);
		}
	}

}
