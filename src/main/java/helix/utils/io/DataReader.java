package helix.utils.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.logging.Logger;

import helix.Constants;

public class DataReader {
	private static final Logger log = Logger.getLogger(DataReader.class.getName());

	public final String filePath;

	private InputStream is;
	
	private final ArrayList<Byte> bytes;
	
	public DataReader(String filePath) {
		this(filePath, true);
	}
	
	public DataReader(String filePath, boolean relative) {
		this.bytes = new ArrayList<Byte>();
		
		if(relative)
			filePath = Constants.ABS_PATH + filePath;
		if(!new File(filePath).exists())
			System.err.println("File doesn't exist: " + filePath);
		
		this.filePath = filePath;
		this.readBytes();
		
		if(this.bytes.size() == 0)
			System.out.println("BAD FILE");
		System.out.println("Read in " + this.bytes.size() + " Bytes from " + this.filePath);
	}
	
	public String getString(int position, int length) {
		// start at specified position
		byte[] data = new byte[length];
		for(int i = 0; i < data.length - 1; i++) {
			data[i] = bytes.get(position + i);
		}

		return new String(data).trim();
	}
	
	/**
	 * Return a boolean value from a byte at a specified position
	 * @param bytePosition - Position of the byte in the data to look at
	 * @param bitPosition - Position of the bit in the data to look at
	 * @return
	 */
	public boolean getBool(int bytePosition, int bitPosition) {
		return this.getBoolean(bytePosition, bitPosition);
	}
	
	public boolean getBoolean(int bytePosition, int bitPosition) {
		int block = 0x00;
		
		block = bytes.get(bytePosition) | block;
		
		int convertedBitPosition = (int)Math.pow(2, bitPosition);
		return ((block & convertedBitPosition) != 0);
	}
	
	public int getInt(int position) {
		return (int)bytes.get(position);
	}
	
	private void readBytes() {
		try {
			is = new FileInputStream(filePath);
		} catch (FileNotFoundException e) {
			log.severe("FAILED TO FIND FILE: " + filePath);
			e.printStackTrace();
		}
		
		byte byteRead;
		int numBytes = 0;
		// read data
		try {
			while((byteRead = (byte)is.read()) != -1) {
				bytes.add(byteRead);
				numBytes++;
			}
		} catch (IOException e) {
			log.severe("Failed to read byte at: " + numBytes);
			e.printStackTrace();
		}

		//try {
		//  is.close();
		//} catch (IOException e) {
		//	e.printStackTrace();
		//}
	}

	public ArrayList<Byte> getBytes() {
		return bytes;
	}
	
	

}
