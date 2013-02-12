/**
 * ContactImpl class - concrete class for Contact object, contains name and
 *  notes for contacts in the contact management system.
 */
package bbk.pij.jsted02;

import java.util.ArrayList;
import java.util.List;

import bbk.pij.jsted02.interfaces.Contact;

/**
 * @author Luke Stedman (jsted02), MSc CS Yr1 2012/13
 */
public class ContactImpl implements Contact {

	/**
	 * Static ID increments when each Contact is created.
	 */
	private static int contact_count = 0;
	/**
	 * List of notes associated with Contact.
	 */
	private List<String> m_notes = new ArrayList<String>();
	/**
	 * Name of contact.
	 */
	private String m_name;
	
	/**
	 * Set the instance id to the value of the incremental count
	 */
	private int m_id = ContactImpl.contact_count++;
	
	/**
	 * Empty constructor for serialisation
	 */
	public ContactImpl()
	{
	}

	/* (non-Javadoc)
	 * @see bbk.pij.jsted02.interfaces.Contact#getId()
	 */
	@Override
	public int getId() {
		return this.m_id;
	}
	
	/* (non-Javadoc)
	 * @see bbk.pij.jsted02.interfaces.Contact#getName()
	 */
	@Override
	public String getName()
	{
		 return this.m_name;
	}

	/**
	 * Sets the name of the contact
	 * 
	 * @param name Name of Contact
	 * 
	 * @return null
	 */
	public void setName(String name)
	{
		this.m_name = name;
	}
	
	/* (non-Javadoc)
	 * @see bbk.pij.jsted02.interfaces.Contact#getNotes()
	 */
	@Override
	public String getNotes()
	{
		// Create string builder object, iterate over notes and create string
		//  with newline separating the notes
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < this.m_notes.size();++i)
			sb.append(this.m_notes.get(i) + "\n");
		// Return string of notes.
		return sb.toString();
	}

	/* (non-Javadoc)
	 * @see bbk.pij.jsted02.interfaces.Contact#addNotes(java.lang.String)
	 */
	@Override
	public void addNotes(String note)
	{
		// Append note to list.
		this.m_notes.add(note);
	}

}
