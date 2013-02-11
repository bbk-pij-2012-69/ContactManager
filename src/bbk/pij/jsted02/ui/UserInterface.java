/**
 * UserInterface - Implements a basic console interface for interaction with
 * 		the ContactManagerImpl, prints the options, handles input and invokes
 * 		the desired action.
 */

package bbk.pij.jsted02.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import bbk.pij.jsted02.ContactManagerImpl;

/**
 * @author Luke Stedman (jsted02), MSc CS Yr1 2012/13
 */
public class UserInterface {

	/**
	 * ContactManagerImpl object, used to call the specific method from the 
	 * 	ContactManager interface/class.
	 */
	private ContactManagerImpl m_cmApp;
	/**
	 * Constant Exit string, avoids having too many magic words floating in the
	 * 	code.
	 */
	public final String EXIT = "Exit"; 
	/**
	 * Member string Array containing options for user interface, blank member 
	 *  to ensure options and numbers are consistant with each other.
	 */
	private String[] m_options = {"", "Add New Contact", EXIT};

	/**
	 * UserInterface class - contains logic to interact with the user.
	 * 
	 * @param cmApp - ContactManagerImpl object that contains all the methods 
	 * 	to run
	 */
	public UserInterface(ContactManagerImpl cmApp)
	{
		this.m_cmApp = cmApp;
	}

	/**
	 * Method to print main options to screen
	 * 
	 * @return null
	 */
	private void printMainMenu()
	{
		// Initialise incremental to 1 to keep interface/lookup consistent.
		for(int i = 1; i < m_options.length; ++i)
		{
			System.out.println(i + ".\t" + m_options[i]);
		}

		System.out.println("");
		System.out.print("Please enter action: ");
	}

	/**
	 * Method to print the Contact Add Menu, options are read from the user and
	 *  the method then called on the ContactManagerImpl object.
	 * @throws IOException
	 */
	private void addContactMenu() throws IOException
	{

		// Open new buffer read, print menu screen and read input.
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		System.out.println("");
		System.out.println("---Add new Contact---");
		System.out.println("");
		
		// First name and surname read seperatley and then combined.
		System.out.print("Please enter the contacts first name: ");
		String forename = br.readLine();

		System.out.print("Please enter the contacts surname: ");
		String surname = br.readLine();
		
		String name = forename + " " + surname;
		
		// Ask the user for any initial notes.
		System.out.print("Please enter any notes with regards this Contact: ");
		String notes = br.readLine();
		
		// Add contact to the contact manager.
		m_cmApp.addNewContact(name, notes);
	}

	/**
	 * Method to handle user on main Menu input
	 * 
	 * @return true if still running or false to exit.
	 */
	public boolean userInput()
	{
		// Print the menu to screen
		this.printMainMenu();

		// Initialise selected option to blank string
		int selectedOption = 0;

		// Read input
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		// Parse the selected option to an int, if the option entered matches
		//  the EXIT option then return false.
		try {
			selectedOption = Integer.parseInt(br.readLine());
			if (m_options[selectedOption] == this.EXIT)
			{
				System.out.println("Exiting...");
				return false;
			}
			
			// If option was not to exit then print context specific menu.
			switch(selectedOption)
			{
			case 1:
				this.addContactMenu();
				break;
			default:
				System.out.println("Unknown option, please try again...");
			}
		}
		catch (IOException ioe) {
			System.out.println("Unknown error trying to get user input.");
		}
		catch (NumberFormatException nfe) {
			System.out.println("Please enter the number associated with the option.");
		}
		
		// Print blank line and continue running.
		System.out.println("");
		return true;
	}
}
