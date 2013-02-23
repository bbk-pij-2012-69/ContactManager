/**
 * 
 */
package bbk.pij.jsted02;

import bbk.pij.jsted02.interfaces.PastMeeting;

/**
 * @author jsted02
 *
 */
public class PastMeetingImpl extends MeetingImpl implements PastMeeting {

	/* (non-Javadoc)
	 * @see bbk.pij.jsted02.interfaces.PastMeeting#getNotes()
	 */
	@Override
	public String getNotes() {
		// TODO Auto-generated method stub
		return this.m_notes;
	}

}
