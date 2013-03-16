/**
 * DataSerialiser - Handler for serialisation and loading of data from txt file.
 */
package bbk.pij.jsted02.data;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

import bbk.pij.jsted02.data.DataInterface.DataType;

/**
 * @author Luke Stedman (jsted02), MSc CS Yr1 2012/13
 */
public class DataSerialiser {
    
    /**
     * Folder constant within which to write the file.
     */
    final private String                 m_folderName = "data";
    
    /**
     * Filename to store and load the data from
     */
    private String                       m_fileName;
    
    /**
     * Data that is used within the system, loaded from disk.
     */
    HashMap<DataType, ArrayList<Object>> m_data;
    
    /**
     * File object, the file interface with the data
     */
    File                                 m_file;
    
    /**
     * DataSerialiser construct, constructs the arc hive object for loading and
     * saving the data to/from disk.
     * 
     * @param Name
     *            of file that will store the data.
     */
    public DataSerialiser(String fileName, boolean test, boolean wipe) {
        // Initialise the data set.
        m_data = new HashMap<DataType, ArrayList<Object>>();
        
        // Set the filename and infer the file object path from the provided
        // filename.
        m_fileName = fileName;
        if (test) {
            m_fileName = "test_" + m_fileName;
        }
        m_file = new File(Paths.get(".", m_folderName, m_fileName).toString());
        
        // Initialise the data object (this will be written to file the first
        // time the process is ran).
        for (DataType dt : DataType.values()) {
            m_data.put(dt, new ArrayList<Object>());
        }
        
        // Get an output file object.
        try {
            verifyFile(wipe);
            loadData();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    /**
     * verifyFile method, used to check the required file for the serialiser, if
     * the file does not exist then it will create the file.
     * 
     * @param wipe
     *            Boolean specifying whether the data should be wipe or not
     * @throws IOException
     *             , FileNotFoundException
     */
    private void verifyFile(boolean wipe) throws IOException,
            FileNotFoundException {
        
        boolean newFile = !m_file.exists();
        // If the file does not exist we need to create them.
        if (newFile) {
            // Check if the files parent directory exists, if it doesn't then
            // create the folder path.
            if (!m_file.getParentFile().exists())
                m_file.getParentFile().mkdirs();
            
        }
        
        if (newFile || wipe) {
            // Creates file and flushes data to it.
            m_file.createNewFile();
            flush(m_data);
        }
    }
    
    /**
     * getInputStream function, used to get BufferedInputStream handle on the
     * file. It creates a tmp version of file before opening it, this tmp file
     * is used as a back-up in case there is a corruption of the file during
     * reading or writing.
     * 
     * @return BufferedInputStream handle to file.
     * @throws FileNotFoundException
     */
    private BufferedInputStream getInputStream() throws FileNotFoundException {
        createTmpfile();
        return new BufferedInputStream(new FileInputStream(m_file));
    }
    
    private BufferedOutputStream getoutputStream() throws FileNotFoundException {
        createTmpfile();
        return new BufferedOutputStream(new FileOutputStream(m_file));
    }
    
    private void createTmpfile() {
        
        // Create tmp file path
        File tmpFile = new File(m_file.getAbsoluteFile() + ".tmp");
        
        // Copy the existing file to the new file
        try {
            Files.copy(Paths.get(m_file.toURI()), Paths.get(tmpFile.toURI()),
                    java.nio.file.StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            // TODO - handle exception, what is the most graceful way?
        }
    }
    
    /**
     * loadData function, used to load the data from the FileStream and into
     * memory for use by the system.
     */
    @SuppressWarnings("unchecked")
    private void loadData() {
        // Create xmlDecoder object and try to load data from the file
        XMLDecoder xmlDecoder = null;
        try {
            xmlDecoder = new XMLDecoder(getInputStream());
            m_data = (HashMap<DataType, ArrayList<Object>>) xmlDecoder
                    .readObject();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            xmlDecoder.close();
        }
    }
    
    /**
     * getData function, returns data structure for use in the system.
     * 
     * @return A hash map representing the different types of data, each map
     *         entry contains a list of the data stored against that entry type.
     */
    public HashMap<DataType, ArrayList<Object>> getData() {
        return m_data;
    }
    
    /**
     * flush function, saves data to disk for reading in at a later date.
     * 
     * @param The
     *            hash map of data to store.
     */
    public void flush(HashMap<DataType, ArrayList<Object>> data) {
        // Create xmlEncoder object and save data to disk.
        XMLEncoder xmlEncoder = null;
        try {
            xmlEncoder = new XMLEncoder(getoutputStream());
            xmlEncoder.writeObject(data);
        } catch (FileNotFoundException e) {
            // TODO - handle exception
        } finally {
            xmlEncoder.close();
        }
    }
    
}
