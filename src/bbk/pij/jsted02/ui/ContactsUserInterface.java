/**
 * Separate ContactsInterface for Contact specific context menu, will use the
 *  ContactManager class to read write/data.
 */
package bbk.pij.jsted02.ui;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import bbk.pij.jsted02.interfaces.Contact;
import bbk.pij.jsted02.interfaces.ContactManager;
/**
 * @author Luke Stedman (jsted02), MSc CS Yr1 2012/13
 */
public class ContactsUserInterface
{
	/**
	 * Static/void function to handle the input and appending of a contact to 
	 *  the app. 
	 * 
	 * @param cmApp Contact Manager App to add contact to.
	 * @throws IOException
	 */
	public static void addNewContact(ContactManager cmApp) throws IOException
	{
		// Get the name and notes
		String name = Utils.getStringInput("Please enter the contacts name");
		String notes = Utils.getStringInput("Please enter any notes associated with the contact");

		// Add a new contact via the ContactManage app
		cmApp.addNewContact(name, notes);
	}

	private static Set<Contact> getContacts(ContactManager cmApp, String searchValue)
	{
		String[] splitValue = searchValue.split(" ");

		boolean integer = false;
		boolean string  = false;

		int[] contactIds = new int[splitValue.length];

		for(int i = 0; i < splitValue.length; ++i)
		{
			try
			{
				contactIds[i] = Integer.parseInt(splitValue[i]);
				integer = true;
			}
			catch(NumberFormatException nfe)
			{
				string = true;
			}
		}

		if(string)
		{
			return cmApp.getContacts(searchValue);
		}
		else if(integer)
		{
			return cmApp.getContacts(contactIds);
		}
		return new HashSet<Contact>();

	}

	public static void listContacts(ContactManager cmApp) throws IOException
	{

		// Retrieve the search value from the user and split it.
		String searchValue = Utils.getStringInput("Please enter name or ID's (multiple ID's should be a space seperated list) to seach for");
		Set<Contact> contacts = ContactsUserInterface.getContacts(cmApp, searchValue);
		if(contacts.size() == 0)
		{
			System.out.println("No contacts found matching your input");
		}
		else
		{
			System.out.println("Contact(s): ");
			for(Contact contact : contacts)
			{
				System.out.println(contact.toString());

			}
		}
	}





	public static void updContact(ContactManager cmApp) {
		// TODO Auto-generated method stub

	}
}
