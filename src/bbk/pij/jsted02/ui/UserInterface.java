/**
 * UserInterface - Implements a basic console interface for interaction with
 * 		the ContactManagerImpl, prints the options, handles input and invokes
 * 		the desired action.
 */

package bbk.pij.jsted02.ui;

import java.io.IOException;
import bbk.pij.jsted02.ContactManagerImpl;
import bbk.pij.jsted02.interfaces.MenuOption;
import bbk.pij.jsted02.ui.MenuOptions.*;

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
	 * Member string Array containing options for user interface, blank member 
	 *  to ensure options and numbers are consistent with each other.
	 */
	private MainMenuOption[] m_mainOptions;
	private ContactOption[] m_contactOptions;
	private MeetingOption[] m_meetingOptions;

	/**
	 * UserInterface class - contains logic to interact with the user.
	 * 
	 * @param cmApp - ContactManagerImpl object that contains all the methods 
	 * 	to run
	 */
	public UserInterface(ContactManagerImpl cmApp)
	{
		this.m_cmApp = cmApp;
		this.m_mainOptions = MainMenuOption.values();
		this.m_contactOptions = ContactOption.values();
		this.m_meetingOptions = MeetingOption.values();
	}

	/**
	 * Method to print main menu.
	 * 
	 * @return boolean True or False if program is still running.
	 */
	public boolean mainMenu()
	{
		this.printMenu("Main Menu", this.m_mainOptions);
		return this.processMenuInput(this.m_mainOptions);
	}

	/**
	 * Method to print meeting context menu.
	 * 
	 * @return boolean True or False if program is still running.
	 */
	private boolean meetingMenu()
	{
		this.printMenu("Meeting Menu", this.m_meetingOptions);
		return this.processMenuInput(this.m_meetingOptions);
	}

	/**
	 * Method to print contact context menu.
	 * 
	 * @return boolean True or False if program is still running.
	 */
	private boolean contactMenu()
	{
		this.printMenu("Contact Menu", this.m_contactOptions);
		return this.processMenuInput(this.m_contactOptions);
	}

	/**
	 * Generic method to print options to screen.
	 * 
	 * @param menuName Name of menu to print in header.
	 * @param options Array of Menu Options to print.
	 */
	private void printMenu(String menuName, MenuOption[] options)
	{
		// Print Menu header.
		System.out.println("");
		System.out.println("---" + menuName + "---");
		System.out.println("");

		// Initialise incremental to 1 to keep interface/lookup consistent.
		for(int i = 1; i < options.length; ++i)
		{
			System.out.println(i + ".\t" + options[i]);
		}

		System.out.println("");
	}

	/**
	 * Generic method to process user input of options.
	 * 
	 * @param options Array of MenuOptions printed to screen.
	 * 
	 * @return boolean True or False if program is still running.
	 */
	private boolean processMenuInput(MenuOption[] options)
	{
		// Initialises local variable to represent whether we are running 
		//  still
		boolean running = true;
		// Initialise local to check if the input was known.
		boolean unknown = false;
		do
		{
			// re-initialise to catch subsequent passes in the loop.
			unknown = false;			
			try {
				MenuOption selectedOption = options[Utils.getIntInput("Please enter action")];
				
				// Check which instance the options are and cast to the enum
				if(options instanceof MainMenuOption[])
				{
					// Check main menu options
					switch((MainMenuOption)selectedOption)
					{
					case CONTACT:
						return this.contactMenu();
					case MEETING:
						return this.meetingMenu();
					case SAVE:
						this.m_cmApp.flush();
						break;
					case EXIT:
						running = false;
						break;
					default:
						unknown = true;
						break;
					}
				}
				else if(options instanceof ContactOption[])
				{
					// Check contact menu options
					switch((ContactOption)selectedOption)
					{
					case ADD_CONTACT:
						ContactsUserInterface.addNewContact(m_cmApp);
						break;
					case GET_CONTACT:
						ContactsUserInterface.listContacts(m_cmApp);
						break;
					case UPD_CONTACT:
						ContactsUserInterface.updContact(m_cmApp);
						break;
					case QUIT:
						break;
					case EXIT:
						running = false;
						break;
					default:
						unknown = true;
						break;
					}
				}
				else if(options instanceof MeetingOption[])
				{
					// Check meeting menu options
					switch((MeetingOption)selectedOption)
					{
					case ADD_MEETING:
						break;
					case UPD_MEETING:
						break;
					case GET_MEETING:
						break;
					case QUIT:
						break;
					case EXIT:
						running = false;
						break;
					default:
						unknown = true;
						break;
					}
				}
				else
				{
					// Somehow the options passed to us were unknown.
					System.out.println("Unknown options provided, exiting.");
					running = false;
				}
			}
			catch (IOException ioe)
			{
				System.out.println("Error trying to get user input: " + ioe.getMessage());
				ioe.printStackTrace();
			}
			catch (NumberFormatException nfe)
			{
				// Known exception - someone entered a character/string.
				System.out.println("Please enter the number associated with the option.");
			}
			catch (ArrayIndexOutOfBoundsException abe)
			{
				// Known exception - someone entered a character/string.
				System.out.println("Unknown option provided, please enter the number associated with one of the options above.");
				unknown = true;
			}

		}while(unknown);

		// Return if exit was selected.
		return running;
	}

}
