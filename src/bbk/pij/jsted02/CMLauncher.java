/**
 * CMLauncher - Launcher class for Command Line app that runs the ContactManager
 */
package bbk.pij.jsted02;

import bbk.pij.jsted02.ui.UserInterface;

/**
 * @author Luke Stedman (jsted02), MSc CS Yr1 2012/13
 */
public class CMLauncher {
    
    /**
     * main Java function - entry point to program
     * 
     * @param args
     *            String array of arguments passed in at run time.
     */
    public static void main(String[] args) {
        // Local vars to keep the system running and flag whether the system is
        // in testing mode.
        boolean running = true;
        boolean testing = false;
        
        // Iterate over arguments and check if testing argument is present
        for (int i = 0; i < args.length; ++i) {
            String arg = args[i];
            if (!testing && (arg.equals("--test") || arg.equals("-t"))) {
                testing = true;
            }
        }
        
        do {
            // Create ContactManagerImpl object, check the system is initialised
            // and launch the system.
            ContactManagerImpl cmApp = new ContactManagerImpl(testing);
            
            if (cmApp.getInitialised()) {
                // Create user interface object and
                UserInterface ui = new UserInterface(cmApp);
                do {
                    running = ui.mainMenu();
                } while (running);
                cmApp.flush();
            }
        } while (running);
    }
    
}
