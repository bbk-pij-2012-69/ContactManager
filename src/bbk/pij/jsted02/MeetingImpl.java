/**
 * 
 */
package bbk.pij.jsted02;

import java.util.Calendar;
import java.util.Set;

import bbk.pij.jsted02.interfaces.Contact;
import bbk.pij.jsted02.interfaces.Meeting;

/**
 * @author jsted02
 *
 */
public class MeetingImpl implements Meeting {

	private Set<Contact> m_contacts;
	private Calendar m_date;
	private int m_id;
	protected String m_notes;

	/* (non-Javadoc)
	 * @see bbk.pij.jsted02.interfaces.Meeting#getId()
	 */
	@Override
	public int getId() {
		return this.m_id;
	}

	/* (non-Javadoc)
	 * @see bbk.pij.jsted02.interfaces.Meeting#getDate()
	 */
	@Override
	public Calendar getDate() {
		return this.m_date;
	}

	/* (non-Javadoc)
	 * @see bbk.pij.jsted02.interfaces.Meeting#getContacts()
	 */
	@Override
	public Set<Contact> getContacts() {
		return this.m_contacts;
	}

	public void setContacts(Set<Contact> contacts)
	{
		this.m_contacts = contacts;
	}
	
	public void setDate(Calendar date) {
		this.m_date = date;
	}

	public void setNotes(String notes) {
		this.m_notes = notes;
	}

	public void appendNote(String notes) {
		this.m_notes += "\n" + notes;
	}
	
}
