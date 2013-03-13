/**
 * 
 */
package bbk.pij.jsted02.meetings;

import java.util.Calendar;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import bbk.pij.jsted02.interfaces.Contact;
import bbk.pij.jsted02.interfaces.Meeting;

/**
 * @author Luke Stedman (jsted02), MSc CS Yr1 2012/13
 */
public class MeetingImpl implements Meeting {

	public static Comparator<Meeting> COMPARATOR_DATE_ASC = new Comparator<Meeting>() {
		@Override
		public int compare(Meeting m1, Meeting m2) {
			return m1.getDate().compareTo(m2.getDate());
		}
	};

	public static Comparator<Meeting> COMPARATOR_DATE_DSC = new Comparator<Meeting>() {
		@Override
		public int compare(Meeting m1, Meeting m2) {
			return m2.getDate().compareTo(m1.getDate());
		}
	};

	/**
	 * Static count to increment with each created Meeting.
	 */
	private static int m_meeting_count = 0;

	/**
	 * Member id variable
	 */
	int m_id;

	/**
	 * Member date variable
	 */
	Calendar m_date;

	/**
	 * Member contacts variable
	 */
	Set<Contact> m_contacts;

	/**
	 * Private constructor - ensures that Meeting cannot be create directly.
	 * Only Past/Future meetings should be created.
	 */
	protected MeetingImpl() {
		setId(MeetingImpl.m_meeting_count++);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see bbk.pij.jsted02.interfaces.Meeting#getId()
	 */
	@Override
	public int getId() {
		return m_id;
	}

	/**
	 * Sets ID of meeting, private as should only be set by instance.
	 * 
	 * @param integer
	 *            id to set.
	 */
	private void setId(int id) {
		m_id = id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see bbk.pij.jsted02.interfaces.Meeting#getDate()
	 */
	@Override
	public Calendar getDate() {
		// System.out.println("Getting calendar for " + m_id + ": " +
		// m_date.get(Calendar.YEAR));
		return m_date;
	}

	/**
	 * Sets date of meeting
	 * 
	 * @param Calendar
	 *            date of meeting.
	 */
	public void setDate(Calendar date) {
		// System.out.println("Setting calendar for " + m_id + ": " +
		// date.get(Calendar.YEAR));
		m_date = date;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see bbk.pij.jsted02.interfaces.Meeting#getContacts()
	 */
	@Override
	public Set<Contact> getContacts() {
		return m_contacts;
	}

	/**
	 * Sets contacts of Meeting
	 * 
	 * @param Set
	 *            of contacts attending the meeting
	 */
	public void setContacts(Set<Contact> contacts) {
		m_contacts = contacts;
	}

	/**
	 * Sets contacts of Meeting
	 * 
	 * @param List
	 *            of contacts attending the meeting
	 */
	public void setContacts(List<Contact> contacts) {
		setContacts(new HashSet<Contact>(contacts));
	}
}
