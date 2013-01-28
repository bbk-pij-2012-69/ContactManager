/**
 * DataSerialiser - Handler for serialisation and loading of data from txt 
 * 		file. Format of data will be in JSON 
 */
package bbk.pij.jsted02.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;

/**
 * @author Luke Stedman (jsted02), MSc CS Yr1 2012/13
 */
public class DataSerialiser {

	/**
	 * Folder constant within which to write the file.
	 */
	final private String folderName = "data";
	
	/**
	 * FileStream used to read and write the data.
	 */
	private FileOutputStream oFile;

	/**
	 * DataSerialiser construct, constructs the arc	hive object for loading and
	 *  saving the data to/from disk.
	 *  
	 * @param Name of file that will store the data.
	 * 
	 */
	public DataSerialiser(String fileName)
	{
		
		// Create file based on the current path, the data foldername and 
		//  the supplied filename.
		File file = new File(Paths.get(".", folderName, fileName).toString());
		try {
			//Pass the file to the getFile method and load the data
			this.oFile = getFile(file);
			this.loadData();
		}
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			try {
				this.oFile.close();
			} catch (IOException e) {
				System.err.println("ERROR: Unable to close file handle!");
			}
		}
		
	}

	/**
	 * getFile method, used to get the required file for the serialiser, if
	 *  the file does not exist then it will create the file and return the
	 *  handle to the empty file.
	 *  
	 * @param File object from which to open or create the file.
	 * @return FileOutputStream to file input.
	 * @throws IOException, FileNotFoundException
	 */
	private FileOutputStream getFile(File file) throws IOException, FileNotFoundException
	{
		// If the file does not exist we need to create them.
		if(!file.exists())
		{
			// Check if the files parent directory exists, if it doesn't then
			//  create the folder path.
			if(!file.getParentFile().exists())
			{
				file.getParentFile().mkdirs();
			}
			// Creates file
			file.createNewFile();
		}

		// Return FileOutputStream to file.
		return new FileOutputStream(file, false);
	}
	
	/**
	 * loadData function, used to load the data from the FileStream and into
	 *  memory for use by the system.
	 *  
	 *  
	 * @return true if loaded, false if unable to
	 * 
	 * @param null
	 */
	private boolean loadData()
	{
		//TODO Load the data.
		return false;
	}
	
	/**
	 * getData function, returns data structure for use in the system.
	 * 
	 * @return Object is returned as final format not decided yet.
	 * 
	 * @param null
	 */
	public Object getData()
	{
		return new Object();
	}
	
}
