/**
 * ContactImpl class - concrete class for Contact object, contains name and
 *  notes for contacts in the contact management system.
 */
package bbk.pij.jsted02;

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
	private String m_notes = "";
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

	public void setId(int id)
	{
		this.m_id = id;
	}

	
	/* (non-Javadoc)
	 * @see bbk.pij.jsted02.interfaces.Contact#getNotes()
	 */
	@Override
	public String getNotes()
	{
		return this.m_notes;
	}

	/* (non-Javadoc)
	 * @see bbk.pij.jsted02.interfaces.Contact#addNotes(java.lang.String)
	 */
	@Override
	public void addNotes(String note)
	{
		// Append note to list.
		this.m_notes += "\n" + note;
	}
	public void setNotes(String note)
	{
		// Append note to list.
		this.m_notes += "\n" + note;
	}
	

	/**
	 * 
	 */
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("ID: " + this.m_id + "\n");
		sb.append("Name: " + this.m_name + "\n");
		if(this.m_notes.length() > 0)
		{
			sb.append("\n");
			sb.append("Notes:");
			sb.append(this.m_notes);
			sb.append("\n");
		}
		return sb.toString();
	}

}
