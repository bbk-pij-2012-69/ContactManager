/**
 * Class used to test the data interface and serialisation used within the
 * contact manager
 */
package bbk.pij.jsted02.test;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import bbk.pij.jsted02.ContactManagerImpl;
import bbk.pij.jsted02.interfaces.Contact;
import bbk.pij.jsted02.interfaces.ContactManager;
import bbk.pij.jsted02.interfaces.Meeting;
import bbk.pij.jsted02.interfaces.PastMeeting;

/**
 * @author Luke Stedman (jsted02), MSc CS Yr1 2012/13
 */
public class ContactManagerDataTest {
    
    /**
     * Test that verifies that the test flag is passed through the contact
     * manager and to the data interface, ensuring that the test data is wiped
     * before use - required for testing of data without screwing with the live
     * data.
     */
    @Test
    public void checkTestData() {
        
        // Create CM App with wiped testing Data
        ContactManager cmApp = new ContactManagerImpl(true, true);
        cmApp.addNewContact("TestContact", "A test contact...");
        
        Set<Contact> contacts = cmApp.getContacts("TestContact");
        Calendar date = Calendar.getInstance();
        date.add(Calendar.YEAR, 1);
        
        cmApp.addFutureMeeting(contacts, date);
        
        List<Meeting> meetings = cmApp.getFutureMeetingList(contacts.iterator()
                .next());
        
        assertTrue("Incorrect number of contacts returned " + contacts.size()
                + " v. 1", contacts.size() == 1);
        assertTrue("Incorrect number of meetings returned " + meetings.size()
                + " v. 1", meetings.size() == 1);
        cmApp.flush();
        
        cmApp = null;
        // Create CM App with testing Data
        cmApp = new ContactManagerImpl(true);
        
        Set<Contact> contacts1 = cmApp.getContacts("TestContact");
        List<Meeting> meetings1 = cmApp.getFutureMeetingList(contacts1
                .iterator().next());
        
        assertTrue("Incorrect number of contacts returned " + contacts1.size()
                + " v. 1", contacts1.size() == 1);
        assertTrue("Incorrect number of meetings returned " + meetings1.size()
                + " v. 1", meetings1.size() == 1);
        
        cmApp = null;
        // Create CM App with wiped testing Data
        
        cmApp = new ContactManagerImpl(true, true);
        
        Set<Contact> contacts2 = cmApp.getContacts("TestContact");
        assertTrue("Incorrect number of contacts returned " + contacts2.size()
                + " v. 0", contacts2.size() == 0);
    }
    
    @Test
    public void checkMeetingsData() {
        // Create CM App with wiped testing Data
        ContactManager cmApp = new ContactManagerImpl(true, true);
        cmApp.addNewContact("TestContact", "A test contact...");
        Set<Contact> contacts = cmApp.getContacts("TestContact");
        Calendar date = Calendar.getInstance();
        for (int i = 1; i < 11; ++i) {
            date.add(Calendar.MONTH, i);
            cmApp.addFutureMeeting(contacts, date);
        }
        
        List<Meeting> meetings = cmApp.getFutureMeetingList(contacts.iterator()
                .next());
        cmApp.flush();

        cmApp = null;
        cmApp = new ContactManagerImpl(true);
        
        Set<Contact> contacts1 = cmApp.getContacts("TestContact");
        List<Meeting> meetings1 = cmApp.getFutureMeetingList(contacts1.iterator()
                .next());
        assertTrue(meetings.size() == meetings1.size());
        
    }
    
    @Test
    public void checkContactsData() {
        // Create CM App with wiped testing Data
        ContactManager cmApp = new ContactManagerImpl(true, true);
        for (int i = 1; i < 11; ++i) {
            cmApp.addNewContact("TestContact" + i, "A test contact..." + i);
        }
        Set<Contact> contacts = cmApp.getContacts("TestContact");
        
        cmApp.flush();

        cmApp = null;
        cmApp = new ContactManagerImpl(true);
        
        Set<Contact> contacts1 = cmApp.getContacts("TestContact");
        assertTrue(contacts.size() == contacts1.size());
        
    }

    @Test
    public void checkFutureMeetings() {
        // Create CM App with wiped testing Data
        ContactManager cmApp = new ContactManagerImpl(true, true);
        cmApp.addNewContact("TestContact", "A test contact...");
        Set<Contact> contacts = cmApp.getContacts("TestContact");
        Calendar date = Calendar.getInstance();
    
        for (int i = 1; i < 10; ++i) {
            date.add(Calendar.MONTH, i);
            cmApp.addFutureMeeting(contacts, date);
        }
        
        List<Meeting> meetings = cmApp.getFutureMeetingList(contacts.iterator()
                .next());
        cmApp.flush();

        cmApp = null;
        cmApp = new ContactManagerImpl(true);

        for(Meeting meeting: meetings)
        {
            assertFalse("Meeting id ("+meeting.getId()+") is null", cmApp.getFutureMeeting(meeting.getId()) == null);
            assertTrue(cmApp.getFutureMeeting(meeting.getId()).getDate().compareTo(meeting.getDate()) == 0);
        }
        
    }

    @Test
    public void checkPastMeeting() {
        // Create CM App with wiped testing Data
        ContactManager cmApp = new ContactManagerImpl(true, true);
        cmApp.addNewContact("TestContact", "A test contact...");
        Set<Contact> contacts = cmApp.getContacts("TestContact");
        Calendar date = Calendar.getInstance();
    
        for (int i = 1; i < 10; ++i) {
            date.add(Calendar.MONTH, i * -1);
            cmApp.addNewPastMeeting(contacts, date, "Past Meeting..." + i);
        }
        
        List<PastMeeting> meetings = cmApp.getPastMeetingList(contacts.iterator()
                .next());
        cmApp.flush();
        cmApp = null;
        cmApp = new ContactManagerImpl(true);

        for(PastMeeting meeting: meetings)
        {
            assertFalse("Meeting id ("+meeting.getId()+") is null", cmApp.getPastMeeting(meeting.getId()) == null);
            assertTrue(cmApp.getPastMeeting(meeting.getId()).getDate().compareTo(meeting.getDate()) == 0);
            assertTrue(cmApp.getPastMeeting(meeting.getId()).getNotes().equals(meeting.getNotes()));
        }
        
    }

    @Test
    public void checkContacts(){
        ContactManager cmApp = new ContactManagerImpl(true, true);
        for (int i = 1; i < 10; ++i) {
            cmApp.addNewContact("TestContact" + i, "A test contact..." + i);
        }
        Set<Contact> contacts = cmApp.getContacts("TestContact");
        cmApp.flush();
        cmApp = null;
        cmApp = new ContactManagerImpl(true);
    
        for(Contact contact: contacts)
        {
            assertFalse("Contact id ("+contact.getId()+") is null", cmApp.getContacts(contact.getId()) == null);
            assertTrue(cmApp.getContacts(contact.getId()).iterator().next().getName().equals(contact.getName()));
            assertTrue(cmApp.getContacts(contact.getId()).iterator().next().getNotes().equals(contact.getNotes()));
        }
    }
}
