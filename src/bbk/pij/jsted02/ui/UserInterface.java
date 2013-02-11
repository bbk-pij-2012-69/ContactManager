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

	private ContactManagerImpl m_cmApp;
	public final String EXIT = "Exit"; 
	/**
	 * Member string Array containing options for user interface.
	 */
	private String[] m_options = {"", EXIT};

	
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
	 * Method to print options to screen
	 * 
	 * @return null
	 */
	private void printMenu()
	{
		// Initialise incremental to 1 to keep interface/lookup consistent.
		for(int i = 1; i < m_options.length; ++i)
		{
			System.out.println(i + "\t" + m_options[i]);
		}
				
		System.out.println("");
		System.out.print("Please enter action: ");
	}
	
	/**
	 * Method to handle user input
	 * 
	 * @return true if still running or false to exit.
	 */
	public boolean userInput()
	{
		// Print the menu to screen
		this.printMenu();
		
		// Initialise selected option to blank string
		int selectedOption = 0;

		// Read input
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		try {
			selectedOption = Integer.parseInt(br.readLine());
			if (m_options[selectedOption] == this.EXIT)
			{
				System.out.println("Exiting...");
				return false;
			}
			
			switch(selectedOption)
			{
				default:
					System.out.println("Unknown option, please try again...");
			}
		}
		catch (IOException ioe) {
			System.out.println("Unknown error trying to read option");
		}
		catch (NumberFormatException nfe) {
			System.out.println("Please enter the number associated with the option.");
		}
		System.out.println("");

		return true;
	}
}
