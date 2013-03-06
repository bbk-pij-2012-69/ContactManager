package bbk.pij.jsted02.test.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import bbk.pij.jsted02.ContactImpl;
import bbk.pij.jsted02.interfaces.Contact;

public class TestHelper {
	
	/**
	 * Helper function to generate a random number of contacts based on the
	 *  input seed, every contact with an even id will have a note added.
	 * 
	 * @param min - minimum number of contacts to create
	 * @param max - maximum number of contacts to create
	 * 
	 * @return List of Contacts equal to the number provided
	 */
	public static List<Contact> generateRandomContacts(int min, int max)
	{
		List<Contact> contacts = new ArrayList<Contact>();
		
		Random rand = new Random();
		int random = rand.nextInt(max - min + 1) + min;
		for(int i = 0; i < random; ++i)
		{
			ContactImpl contact = new ContactImpl();
			int id = contact.getId();
			contact.setName(id + "_name");
			if(id % 2 == 0)
			{
				contact.setNotes(id + "_note");
			}
			
			contacts.add(contact);
		}
		
		return contacts;
	}
}
