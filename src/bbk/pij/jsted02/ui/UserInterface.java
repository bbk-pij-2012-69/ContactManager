/**
 * UserInterface - Implements a basic console interface for interaction with
 * 		the ContactManagerImpl, prints the options, handles input and invokes
 * 		the desired action.
 */

package bbk.pij.jsted02.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


import bbk.pij.jsted02.utils.UIMethod;

/**
 * @author Luke Stedman (jsted02), MSc CS Yr1 2012/13
 */
public class UserInterface {

	private boolean running = true;
	/**
	 * exit() method, always appended to the end of existing options to allow the 
	 * 	user to exit, once invoked it sets the running flag to false.
	 * 
	 * @return null
	 */
	@UIMethod(name="Exit")
	public void exit()
	{
		// If exit is called then set running instance variable to false.
		// This will then be returned at the end of the printInterface method.
		this.running = false;
	}

	/**
	 * printEntry() method, called for each action that the user can take, 
	 * 	prints the entry to screen with the associated number id that the
	 *  user should input to perform action.
	 * 
	 * @param option, number id relating to the user choice.
	 * @param method, action to invoke.
	 * 
	 * @return null
	 */
	private void printEntry(int option, Method action)
	{
		// Print the output in the format #.	<NAME>
		System.out.print(option + ".\t");
		System.out.println(action.getAnnotation(UIMethod.class).name());
	}

	/**
	 * printInterface() method, called to print the user interface and handles
	 * 	the input choice made by the user.
	 * 
	 * @param method list, list of action methods that can be invoked. 
	 *  
	 * @return true if to continue running or false to quit.
	 */	public boolean printInterface(List<Method> actions)
	{
		// Initialise a map of options to methods, iterate over inputs and
		// append each method to the map while incrementing the option no.
		Map<Integer, Method> methodMap = new HashMap<Integer, Method>();
		
		for(int x = 0; x < actions.size();++x){
			methodMap.put(x+1, actions.get(x));
		}

		// Create iterator object and iterate over each MapEntry, for each 
		// entry split the key and value, pass them to the printEntry method
		// to be printed to console.
		Iterator<?> i = methodMap.entrySet().iterator();
		while(i.hasNext()){
			Map.Entry uiMapEntry = (Map.Entry)i.next();
			printEntry((int) uiMapEntry.getKey(), (Method) uiMapEntry.getValue());
		}

		try {
			System.out.print("\nPlease enter action: ");
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String selectedOption = br.readLine();
			methodMap.get(Integer.parseInt(selectedOption)).invoke(this, null);
			// TODO Process selectedOption
		}
		catch (IOException ioe) {
			System.out.println("Unknown error trying to read option");
			System.exit(1);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Return true/false if still running.
		return this.running;
	}


}
