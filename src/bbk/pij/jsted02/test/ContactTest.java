package bbk.pij.jsted02.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import bbk.pij.jsted02.interfaces.Contact;
import bbk.pij.jsted02.test.utils.*;

public class ContactTest {

	static List<Contact> m_contacts;

	/**
	 * Initialisation method, sets up a list of contacts to use in the tests.
	 */
	@BeforeClass
	public void setUp()
	{
		m_contacts = TestHelper.generateContacts(10, 100);
	}

	/**
	 * Finalise method, tears down anything that does not need to persist.
	 */
	@AfterClass
	public void tearDown()
	{
	}

	/**
	 * checkId test, tests the uniqueness of id's on creation
	 */
	@Test
	public void checkId()
	{
		List<Integer> ids = new ArrayList<Integer>();
		for(Contact contact : m_contacts)
		{
			int id = contact.getId();
			if(ids.contains(id))
			{
				fail(id + " is already present against a contact");
			}
			ids.add(id);
		}
	}

	/**
	 * checkName test, tests that Names have been created for the correct Contact
	 */
	@Test
	public void checkName()
	{
		for(Contact contact : m_contacts)
		{
			int id = contact.getId();
			assertTrue("Name incorrect for Contact: " + id, contact.getName().equals(id + "_name"));
		}
	}
	
	/**
	 * checkNote test, tests that Notes have been created for the correct
	 * Contact, notes have not been created for other and that notes can 
	 * be added.
	 */
	@Test
	public void checkNote()
	{
		for(Contact contact : m_contacts)
		{
			int id = contact.getId();

			if(id % 2 == 0)
			{
				assertTrue("Notes incorrect for Contact: " + id, contact.getNotes().equals(id + "_note"));
			}
			else if(id % 1 == 0)
			{
				assertTrue("Notes incorrect for Contact: " + id, contact.getNotes().equals(""));
			}
			contact.addNotes(id + "_new_note");
		}

		for(Contact contact : m_contacts)
		{
			int id = contact.getId();

			if(id % 2 == 0)
			{
				assertTrue("New notes incorrect for Contact: " + id, contact.getNotes().equals(id + "_note\n" + id + "_new_note"));
			}
			else if(id % 1 == 0)
			{
				assertTrue("New notes incorrect for Contact: " + id, contact.getNotes().equals(id + "_new_note"));
			}
		}
	}

}
