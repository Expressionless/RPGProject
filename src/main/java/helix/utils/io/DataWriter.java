package helix.utils.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Logger;

import helix.Constants;

public class DataWriter {
	private static final Logger log = Logger.getLogger(DataReader.class.getName());

	private OutputStream out;
	private String outputPath;

	public DataWriter(String filePath) {
		this(filePath, true);
	}

	public DataWriter(String outputDir, boolean relative) {
		if (relative)
			outputDir = Constants.ABS_PATH + outputDir;

		this.outputPath = outputDir;

		File outputFile = new File(outputDir);
		try {
			outputFile.createNewFile();
		} catch (IOException e1) {
			log.severe("Failed to create new file: " + outputDir);
			e1.printStackTrace();
		}

		try {
			out = new FileOutputStream(outputFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void write(byte... bytes) {
		try {
			System.out.println("wrote: " + bytes.length + " bytes");
			out.write(bytes);
		} catch (IOException e) {
			log.severe("Failed to write data to file: " + outputPath);
			e.printStackTrace();
		}
	}

	public void write(int... intBytes) {
		byte[] bytes = new byte[intBytes.length];
		for (int i = 0; i < intBytes.length; i++) {
			bytes[i] = (byte) intBytes[i];
		}
		this.write(bytes);
	}

	/**
	 * Write a string to data. Will convert to binary first
	 * 
	 * @param string - String to write
	 */
	public void write(String string, int allocation) {
		while (string.length() < allocation) {
			string += " ";
		}
		this.write(string.getBytes());
	}

	public void writeBools(boolean[] flags, int numBlocks) {
		int[] blocks = new int[numBlocks];
		for(int i = 0; i < blocks.length; i++) {
			for(int j = 0; j < flags.length; j++) {
				if(flags[j]) {
					blocks[i] = (blocks[i] ^ (int)Math.pow(2, j));
				}
			}
			
			this.write(blocks);
		}
	}

	public void writeBools(boolean[] flags) {
		if(flags.length != 4)
			return;
		this.writeBools(flags, 1);
	}

	public void close() {
		try {
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
