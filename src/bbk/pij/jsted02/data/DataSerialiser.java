/**
 * DataSerialiser - Handler for serialisation and loading of data from txt 
 * 		file. Format of data will be in JSON 
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
	final private String m_folderName = "data";

	/**
	 * Filename to store and load the data from
	 */
	private String m_fileName;

	/**
	 * Boolean used to indicate whether the data has been loaded or not.
	 */
	private boolean m_loaded = false;

	/**
	 * Data that is used within the system, loaded from disk.
	 */
	HashMap<DataType, ArrayList<Object>> m_data = new HashMap<DataType, ArrayList<Object>>();

	/**
	 * File object, instance variable to stop redundant repetition of code.
	 */

	File m_file;
	/**
	 * DataSerialiser construct, constructs the arc	hive object for loading and
	 *  saving the data to/from disk.
	 *  
	 * @param Name of file that will store the data.
	 * 
	 */
	public DataSerialiser(String fileName, boolean test)
	{
		// Set the filename and infer the file object path from the provided
		//  filename.
		this.m_fileName = fileName;
		if(test)
		{
			m_fileName = "test_" + m_fileName;
		}
		
		this.m_file = new File(Paths.get(".", this.m_folderName, this.m_fileName).toString());

		// Initialise the data object (this will be written to file the first 
		//  time the process is ran).
		for(DataType dt : DataType.values())
		{
			
			m_data.put(dt, new ArrayList<Object>());
		}

		// Get an output file object.
		try
		{
			//Pass the file to the getFile method and load the data
			verifyFile();
			this.m_loaded = this.loadData();
		}
		catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * verifyFile method, used to check the required file for the serialiser, if
	 *  the file does not exist then it will create the file.
	 *  
	 * @throws IOException, FileNotFoundException
	 */
	private void verifyFile() throws IOException, FileNotFoundException
	{
		// If the file does not exist we need to create them.
		if(!this.m_file.exists())
		{
			// Check if the files parent directory exists, if it doesn't then
			//  create the folder path.
			if(!this.m_file.getParentFile().exists())
				this.m_file.getParentFile().mkdirs();

			// Creates file
			this.m_file.createNewFile();
			this.flush(this.m_data);
		}
	}

	/**
	 * getInputStream function, used to get BufferedInputStream handle on the 
	 *  file. It creates a tmp version of file before opening it, this tmp file 
	 *  is used as a back-up in case there is a corruption of the file during 
	 *  reading or writing.
	 *  
	 * @return BufferedInputStream handle to file.
	 * @throws FileNotFoundException 
	 */
	private BufferedInputStream getInputStream() throws FileNotFoundException
	{
		this.createTmpfile();
		return new BufferedInputStream(new FileInputStream(this.m_file));
	}

	private BufferedOutputStream getoutputStream() throws FileNotFoundException
	{
		this.createTmpfile();
		return new BufferedOutputStream(new FileOutputStream(this.m_file));
	}

	private void createTmpfile()
	{
		

		// Create tmp file path
		File tmpFile = new File(this.m_file.getAbsoluteFile() + ".tmp");

		// Copy the existing file to the new file
		try
		{
			Files.copy(Paths.get(this.m_file.toURI()), Paths.get(tmpFile.toURI()), java.nio.file.StandardCopyOption.REPLACE_EXISTING );
		}
		catch (IOException e)
		{
			//TODO - handle exception, what is the most graceful way?
		}
	}

	/**
	 * loadData function, used to load the data from the FileStream and into
	 *  memory for use by the system.
	 *  
	 * @return true if loaded, false if unable to
	 * 
	 * @param null
	 */
	@SuppressWarnings("unchecked")
	private boolean loadData()
	{
		// Create xmlDecoder object and try to load data from the file 
		XMLDecoder xmlDecoder = null;
		try
		{
			xmlDecoder = new XMLDecoder(this.getInputStream());
			this.m_data = (HashMap<DataType, ArrayList<Object>>)xmlDecoder.readObject();

			if(!this.m_data.isEmpty())
			{
				this.m_loaded = true;
			}
		}
		catch (FileNotFoundException e)
		{
			//TODO - handle exception
		}
		finally
		{
			xmlDecoder.close();
		}
		return this.m_loaded;
	}

	/**
	 * getData function, returns data structure for use in the system.
	 * 
	 * @return Object is returned as final format not decided yet.
	 * 
	 * @param null
	 */
	public HashMap<DataType, ArrayList<Object>> getData()
	{
		return this.m_data;
	}

	/**
	 * flush function, saves data to disk for reading in at a later date.
	 * 
	 * @return null
	 *
	 * @param null
	 */
	public void flush(HashMap<DataType, ArrayList<Object>> data)
	{
		// Create xmlEncoder object and save data to disk. 
		XMLEncoder xmlEncoder= null;
		try
		{
			xmlEncoder = new XMLEncoder(this.getoutputStream());
			xmlEncoder.writeObject(data);
		}
		catch (FileNotFoundException e)
		{
			//TODO - handle exception
		}
		finally
		{
			xmlEncoder.close();
		}
	}

}
