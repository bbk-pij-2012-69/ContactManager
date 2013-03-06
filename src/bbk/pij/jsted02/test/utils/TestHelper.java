package bbk.pij.jsted02.test.utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import bbk.pij.jsted02.ContactImpl;
import bbk.pij.jsted02.FutureMeetingImpl;
import bbk.pij.jsted02.interfaces.Contact;
import bbk.pij.jsted02.interfaces.Meeting;

public class TestHelper {
	
	
	private static int generateRandomNum(int min, int max)
	{
		Random rand = new Random();
		return rand.nextInt(max - min + 1) + min;
	}
	
	/**
	 * Helper function to generate a random number of contacts based on the
	 *  input seed, every contact with an even id will have a note added.
	 * 
	 * @param min - minimum number of contacts to create
	 * @param max - maximum number of contacts to create
	 * 
	 * @return List of Contacts between the numbers provided
	 */
	public static List<Contact> generateContacts(int min, int max)
	{
		List<Contact> contacts = new ArrayList<Contact>();
		
		for(int i = 0; i < generateRandomNum(min, max); ++i)
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
	
	/**
	 * Helper function to generate a number of contacts equal to the
	 *  input seed, every contact with an even id will have a note added.
	 * 
	 * @param number - number of contacts to create
	 * 
	 * @return List of Contacts equal to the number provided
	 */
	public static List<Contact> generateContacts(int number)
	{
		return TestHelper.generateContacts(number, number);
	}
	
	/**
	 * Helper function to generate a number of Future meetings based on the input
	 *  seed, each meeting will be set on an incremental date and the 
	 *  contacts will be generate randomly based on the id.
	 *  
	 * @param min - minimum number of meeting.
	 * @param max - maximum number of meetings
	 * 
	 * @return List of Meetings between the numbers provided
	 */
	public static List<Meeting> generateFutureMeetings(int min, int max)
	{
		List<Meeting> Meeting = new ArrayList<Meeting>();
		Calendar cal = Calendar.getInstance();
		
		for(int i = 0; i < generateRandomNum(min, max); ++i)
		{
			FutureMeetingImpl meeting = new FutureMeetingImpl();
			int id = meeting.getId();
			
			meeting.setDate(cal);
			meeting.setContacts(generateContacts(id));
			Meeting.add(meeting);
			
			// Increment the day by 1
			cal.add(Calendar.DATE, 1);
		}
		
		return Meeting;
	}
}
