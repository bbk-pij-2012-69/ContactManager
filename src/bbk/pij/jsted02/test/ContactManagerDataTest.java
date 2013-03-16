/**
 * Class used to test the data interface and serialisation used within the
 * contact manager
 */
package bbk.pij.jsted02.test;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Set;

import org.junit.Test;

import bbk.pij.jsted02.ContactManagerImpl;
import bbk.pij.jsted02.interfaces.Contact;
import bbk.pij.jsted02.interfaces.ContactManager;
import bbk.pij.jsted02.meetings.FutureMeetingImpl;

/**
 * @author Luke Stedman (jsted02), MSc CS Yr1 2012/13
 */
public class ContactManagerDataTest {
    
    /**
     * Test that verifies that the test flag is passed through the contact
     * manager and to the data interface, ensuring that the test data is wiped
     * and cleaned up after use - required for testing of data without screwing
     * with the live data.
     */
    @Test
    public void checkTestData() {
        ContactManager cmApp = new ContactManagerImpl(true);
        cmApp.addNewContact("TestContact", "A test contact...");
        Set<Contact> contacts = cmApp.getContacts("TestContact");
        Calendar date = Calendar.getInstance();
        date.add(Calendar.YEAR, 1);
        int meetingId = cmApp.addFutureMeeting(contacts, date);
        cmApp.flush();
        
        cmApp = null;
        
        cmApp = new ContactManagerImpl(true);
        assertTrue("Incorrect number of contacts returned "
                + cmApp.getContacts("TestContact").size() + " v. 1", cmApp
                .getContacts("TestContact").size() == 1);
        assertTrue("Meeting is instance is incorrect: "
                + cmApp.getFutureMeeting(meetingId).getClass().toString(),
                cmApp.getFutureMeeting(meetingId) instanceof FutureMeetingImpl);
    }
    
    // Test that meetings are stored
    
    // Test that contacts are stored
    
    // Test that id's are consistent for meetings
    
    // Test that id's are consistent for contacts
    
}
