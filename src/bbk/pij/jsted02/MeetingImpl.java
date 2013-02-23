/**
 * MeetingImpl class - concrete class for Meeting object, contains contacts at
 * meeting, date of meeting, id of meeting and notes of meeting. 
 */
package bbk.pij.jsted02;

import java.util.Calendar;
import java.util.Set;

import bbk.pij.jsted02.interfaces.Contact;
import bbk.pij.jsted02.interfaces.Meeting;

/**
 * @author Luke Stedman (jsted02), MSc CS Yr1 2012/13
 */
public class MeetingImpl implements Meeting {

	/**
	 * Static ID increments when each Contact is created.
	 */
	private static int meeting_count = 0;
	
	/**
	 * List of contacts at the meeting.
	 */
	private Set<Contact> m_contacts;

	/**
	 * Date of the meeting
	 */
	private Calendar m_date;
	
	/**
	 * Set the instance id to the value of the incremental count
	 */
	private int m_id = MeetingImpl.meeting_count++;
	
	/**
	 * Notes made about the meeting.
	 */
	protected String m_notes;

	/**
	 * Empty constructor for serialisation
	 */
	public MeetingImpl()
	{
		
	}
	
	/* (non-Javadoc)
	 * @see bbk.pij.jsted02.interfaces.Meeting#getId()
	 */
	@Override
	public int getId()
	{
		return this.m_id;
	}

	/* (non-Javadoc)
	 * @see bbk.pij.jsted02.interfaces.Meeting#getDate()
	 */
	@Override
	public Calendar getDate()
	{
		return this.m_date;
	}

	/* (non-Javadoc)
	 * @see bbk.pij.jsted02.interfaces.Meeting#getContacts()
	 */
	@Override
	public Set<Contact> getContacts()
	{
		return this.m_contacts;
	}

	/**
	 * Sets ID of the meeting
	 * 
	 * @param ID of meeting to assign
	 */
	public void setId(int id)
	{
		this.m_id = id;
	}
	
	/**
	 * Sets contacts associated with the meeting
	 * 
	 * @param Set of contacts to be associated with the meeting.
	 */
	public void setContacts(Set<Contact> contacts)
	{
		this.m_contacts = contacts;
	}
	
	/**
	 * Appends another contact to the meeting
	 * 
	 * @param Contact to append to the meeting
	 */
	public void addContact(Contact contact)
	{
		this.m_contacts.add(contact);
	}
	
	/**
	 * Sets date of the meeting
	 * 
	 * @param Date of meeting to set.
	 */
	public void setDate(Calendar date)
	{
		this.m_date = date;
	}

	/**
	 * Sets notes associated with the meeting
	 * 
	 * @param Notes to associate with the meeting
	 */
	public void setNotes(String notes)
	{
		this.addNotes(notes);
	}

	/**
	 * Adds addition notes to the meeting
	 * 
	 * @param Notes to add to the existing meeting notes
	 */
	public void addNotes(String notes)
	{
		this.m_notes += "\n" + notes;
	}
}
