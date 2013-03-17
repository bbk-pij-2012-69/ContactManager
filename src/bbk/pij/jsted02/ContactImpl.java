/**
 * ContactImpl class - concrete class for Contact object, contains name and
 * notes for contacts in the contact management system.
 */
package bbk.pij.jsted02;

import bbk.pij.jsted02.interfaces.Contact;

/**
 * @author Luke Stedman (jsted02), MSc CS Yr1 2012/13
 */
public class ContactImpl implements Contact {
    
    // Members
    /**
     * Notes associate with the Contact.
     */
    private String m_notes = "";
    /**
     * Name of contact.
     */
    private String m_name;
    
    /**
     * ID of the contact
     */
    private int    m_id    = -1;
    
    // Constructor
    /**
     * Empty constructor for serialisation
     */
    public ContactImpl() {
    }
    
    // Setters and Getters
    /**
     * Sets the name of the contact
     * 
     * @param name
     *            Name of Contact
     */
    public void setName(String name) {
        m_name = name;
    }
    
    /**
     * Sets the id of the contact, private to prevent external classes
     * overriding once set
     * 
     * @param id
     *            Integer id of Contact
     */
    public void setId(int id) {
        m_id = id;
    }
    
    /**
     * Sets the notes against the contact
     * 
     * @param note
     *            Note to set against the contact.
     */
    public void setNotes(String note) {
        addNotes(note);
    }
    
    /*
     * (non-Javadoc)
     * @see bbk.pij.jsted02.interfaces.Contact#getId()
     */
    @Override
    public int getId() {
        return m_id;
    }
    
    /*
     * (non-Javadoc)
     * @see bbk.pij.jsted02.interfaces.Contact#getName()
     */
    @Override
    public String getName() {
        return m_name;
    }
    
    /*
     * (non-Javadoc)
     * @see bbk.pij.jsted02.interfaces.Contact#getNotes()
     */
    @Override
    public String getNotes() {
        return m_notes;
    }
    
    /*
     * (non-Javadoc)
     * @see bbk.pij.jsted02.interfaces.Contact#addNotes(java.lang.String)
     */
    @Override
    public void addNotes(String note) {
        // Create note to be added, if current notes is not zero append current
        // notes and insert a carriage return
        String addNote = "";
        if (m_notes.length() != 0)
            addNote += m_notes + "\n";
        
        // Append new note
        addNote += note.replace("\n", "").replace("\r", "");
        m_notes = addNote;
    }
}
