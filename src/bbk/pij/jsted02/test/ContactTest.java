package bbk.pij.jsted02.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import bbk.pij.jsted02.ContactImpl;
import bbk.pij.jsted02.interfaces.Contact;

public class ContactTest {

	List<ContactImpl> m_contacts;

	/**
	 * Initialisation method, sets up a list of contacts to use in the tests.
	 */
	@Before
	public void setUp()
	{
		Random rand = new Random();
		int min = 10;
		int max = 100;
		int random = rand.nextInt(max - min + 1) + min;
		m_contacts = new ArrayList<ContactImpl>();
		for(int i = 0; i < random; ++i)
		{
			ContactImpl contact = new ContactImpl();
			int id = contact.getId();
			contact.setName(id + "_name");
			if(id % 2 == 0)
			{
				contact.setNotes(id + "_note");
			}
			m_contacts.add(contact);
		}
	}

	/**
	 * Finalise method, tears down anything that does not need to persist.
	 */
	@After
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
