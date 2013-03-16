package bbk.pij.jsted02.ui;

import bbk.pij.jsted02.ui.interfaces.MenuOption;
/**
 * @author Luke Stedman (jsted02), MSc CS Yr1 2012/13
 */
public class MenuOptions {

	public enum MainMenuOption implements MenuOption
	{
		NULL(""),
		CONTACT("Add/Query Contacts"),
		MEETING("Add/Query Meetings"),
		SAVE("Save Data"),
		EXIT("Exit");
		
		private final String value;
		
		MainMenuOption(String value) {
	        this.value = value;
	    }

	    public String toString() {
	        return value;
	    }
	}

	public enum ContactOption implements MenuOption
	{
		NULL(""),
		ADD_CONTACT("Add a Contact"),
		GET_CONTACT("List Contacts"),
		UPD_CONTACT("Add a Note to a Contact"),
		QUIT("Quit to Main Menu"),
		EXIT("Exit");
		
		private final String value;
		
		ContactOption(String value) {
	        this.value = value;
	    }

	    public String toString() {
	        return value;
	    }		
	}

	public enum MeetingOption implements MenuOption
	{
		NULL(""),
		ADD_MEETING("Add a Meeting"),
		GET_MEETING("Get a Meeting"),
		UPD_MEETING("Update a Meeting"),
		QUIT("Quit to Main Menu"),
		EXIT("Exit");

		private final String value;
		
		MeetingOption(String value) {
	        this.value = value;
	    }

	    public String toString() {
	        return value;
	    }		
	}
}
