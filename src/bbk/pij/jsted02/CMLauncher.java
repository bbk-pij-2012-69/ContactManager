package bbk.pij.jsted02;

import bbk.pij.jsted02.ui.UserInterface;

public class CMLauncher {


	/**
	 * main Java function - entry point to program
	 * 
	 * @return null
	 */
	public static void main(String [] args){
		boolean running = true;
		do
		{
			// Create ContactManagerImpl object, check the system is initialised
			// and launch the system.
			ContactManagerImpl cmApp = new ContactManagerImpl();

			if(cmApp.getInitialised())
			{
				// Create user interface object and 
				UserInterface ui = new UserInterface(cmApp);
				do
				{
					running = ui.mainMenu();
				} while(running);
				cmApp.flush();
			}
		}while(running);
	}


}
