/**
 * 
 */
package bbk.pij.jsted02;

import java.util.ArrayList;
import bbk.pij.jsted02.interfaces.Contact;

/**
 * @author Luke Stedman (jsted02), MSc CS Yr1 2012/13
 */
public class ContactImpl implements Contact {

	private static int m_id = 0;
	private ArrayList<String> m_notes;
	private String m_name;
	
	public ContactImpl(String name, String notes)
	{
		++ContactImpl.m_id;
		this.m_name = name;
		this.m_notes = new ArrayList<String>();
		this.m_notes.add(notes);
		System.out.println(this.getId());
	}
	
	/* (non-Javadoc)
	 * @see bbk.pij.jsted02.interfaces.Contact#getId()
	 */
	@Override
	public int getId() {
		return ContactImpl.m_id;
	}

	/* (non-Javadoc)
	 * @see bbk.pij.jsted02.interfaces.Contact#getName()
	 */
	@Override
	public String getName() {
		 return this.m_name;
	}

	/* (non-Javadoc)
	 * @see bbk.pij.jsted02.interfaces.Contact#getNotes()
	 */
	@Override
	public String getNotes() {
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < this.m_notes.size();++i)
			sb.append(this.m_notes.get(i) + "\n");
		return sb.toString();
	}

	/* (non-Javadoc)
	 * @see bbk.pij.jsted02.interfaces.Contact#addNotes(java.lang.String)
	 */
	@Override
	public void addNotes(String note) {
		this.m_notes.add(note);
	}

}
