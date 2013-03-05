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
	private int m_id;

	/**
	 * Empty constructor for serialisation
	 */
	public ContactImpl()
	{
		setId(ContactImpl.contact_count++);
	}

	/* (non-Javadoc)
	 * @see bbk.pij.jsted02.interfaces.Contact#getId()
	 */
	@Override
	public int getId() {
		return m_id;
	}

	/* (non-Javadoc)
	 * @see bbk.pij.jsted02.interfaces.Contact#getName()
	 */
	@Override
	public String getName()
	{
		return m_name;
	}

	/**
	 * Sets the name of the contact
	 * 
	 * @param name Name of Contact
	 */
	public void setName(String name)
	{
		m_name = name;
	}

	/**
	 * Sets the id of the contact
	 * 
	 * @param integer id of Contact
	 */
	private void setId(int id)
	{
		m_id = id;
	}

	
	/* (non-Javadoc)
	 * @see bbk.pij.jsted02.interfaces.Contact#getNotes()
	 */
	@Override
	public String getNotes()
	{
		return m_notes;
	}

	/* (non-Javadoc)
	 * @see bbk.pij.jsted02.interfaces.Contact#addNotes(java.lang.String)
	 */
	@Override
	public void addNotes(String note)
	{
		// Append note to list.
		if(m_notes.length() != 0)
			m_notes += "\n";
		m_notes += note.replace("\n",  "").replace("\r", "");
	}

	/**
	 * Public Setter for serialisation
	 * 
	 * @param note to be added to the notes string.
	 */
	public void setNotes(String note)
	{
		addNotes(note);
	}
	

	/**
	 * 
	 */
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("ID: " + m_id + "\n");
		sb.append("Name: " + m_name + "\n");
		if(m_notes.length() > 0)
		{
			sb.append("\n");
			sb.append("Notes:");
			sb.append(m_notes);
			sb.append("\n");
		}
		return sb.toString();
	}

}
