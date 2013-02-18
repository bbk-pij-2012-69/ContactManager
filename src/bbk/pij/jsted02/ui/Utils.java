/**
 * Utils class for interface, contains common code used to read input from the
 *  user.
 */

package bbk.pij.jsted02.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Utils {

	public static int getIntInput(String prompt) throws NumberFormatException, IOException
	{
		return Integer.parseInt(Utils.getInput(prompt));
	}

	public static String getStringInput(String prompt) throws IOException
	{
		return Utils.getInput(prompt);
	}
	
	private static String getInput(String prompt) throws IOException
	{
		System.out.print(prompt + ": ");
		// Open new buffer and read input
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String input = br.readLine();
		return input;
	}
	
}
