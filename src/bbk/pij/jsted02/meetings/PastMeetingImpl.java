/**
 * 
 */
package bbk.pij.jsted02.meetings;

import bbk.pij.jsted02.interfaces.PastMeeting;

/**
 * @author jsted02
 *
 */
public class PastMeetingImpl extends MeetingImpl implements PastMeeting {

	private String m_notes;

	/* (non-Javadoc)
	 * @see bbk.pij.jsted02.interfaces.PastMeeting#getNotes()
	 */
	@Override
	public String getNotes() {
		// TODO Auto-generated method stub
		return this.m_notes;
	}

	public void setNotes(String text) {
		// TODO Auto-generated method stub
		
	}

}
