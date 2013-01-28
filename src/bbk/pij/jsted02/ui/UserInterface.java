package bbk.pij.jsted02.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import bbk.pij.jsted02.utils.Tuple;

public class UserInterface {

	/**
	 * Exit method, always append to the end of existing options to allow the user to exit.
	 * 
	 * @return boolean false 
	 */
	public static boolean exit()
	{
		return false;
	}

	/**
	 * Static method to print the user interface.
	 */
	public static boolean printInterface(List<Tuple<String,Object>> actions)
	{
		// Initialise a map of options to methods, iterate over inputs and
		// append an exit method.
		Map<Object, Tuple<String, Object>> methodMap = new HashMap<Object, Tuple<String, Object>>();

		for(int x = 0; x < actions.size();++x){
			methodMap.put(x+1, actions.get(x));
		}
		methodMap.put(actions.size()+1, new Tuple<String, Object>("Exit", UserInterface.exit()));

		// Initialise selected option to blank string
		String selectedOption = "";
		
		// TODO Print available options
		Iterator<?> i = methodMap.entrySet().iterator();
		while(i.hasNext()){
			Map.Entry me = (Map.Entry)i.next();
			System.out.print(me.getKey() + ".\t");
			Tuple<String, Object> value = (Tuple<String, Object>)me.getValue();
			System.out.println(value.x);
		}

		System.out.print("\nPlease enter action: ");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		try {
			selectedOption = br.readLine();
			System.out.println("selected: " + selectedOption);
			System.out.println(methodMap.get(Integer.parseInt(selectedOption)));
		}
		catch (IOException ioe) {
			System.out.println("Unknown error trying to read option");
			System.exit(1);
		}

		return true;
		// TODO Process selectedOption
	}


}
