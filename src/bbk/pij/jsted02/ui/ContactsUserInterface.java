/**
 * Separate ContactsInterface for Contact specific context menu, will use the
 *  ContactManager class to read write/data.
 */
package bbk.pij.jsted02.ui;

import java.io.IOException;

import bbk.pij.jsted02.ContactManagerImpl;
import bbk.pij.jsted02.interfaces.ContactManager;

public class ContactsUserInterface
{
	static void addNewContact(ContactManager cmApp) throws IOException
	{
		String name = Utils.getStringInput("Please enter the contacts name");
		String notes = Utils.getStringInput("Please enter any notes associated with the contact");;
		cmApp.addNewContact(name, notes);
	}

	public static void getContact(ContactManagerImpl m_cmApp) throws IOException
	{
		String searchValue = Utils.getStringInput("Please enter name or ID's (multiple ID's should be a space seperated list)");
		String[] splitValue = searchValue.split(" ");
		
		
	}

	public static void updContact(ContactManagerImpl m_cmApp) {
		// TODO Auto-generated method stub
		
	}
}
