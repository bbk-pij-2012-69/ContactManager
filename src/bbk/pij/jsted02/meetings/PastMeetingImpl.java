/**
 * PastMeetingImpl - Implementation of the PastMeeting interface
 */
package bbk.pij.jsted02.meetings;

import bbk.pij.jsted02.interfaces.PastMeeting;

/**
 * @author Luke Stedman (jsted02), MSc CS Yr1 2012/13
 */
public class PastMeetingImpl extends MeetingImpl implements PastMeeting {

	// Members
	/**
	 * String member to store notes about the meeting
	 */
	private String m_notes;

	// Setters
	/**
	 * Method to set notes for past meeting
	 * 
	 * @param text
	 *            string of notes to set against the meeting
	 */
	public void setNotes(String text) {
		m_notes = text;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see bbk.pij.jsted02.interfaces.PastMeeting#getNotes()
	 */
	@Override
	public String getNotes() {
		return m_notes;
	}

}
