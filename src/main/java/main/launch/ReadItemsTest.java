package main.launch;

import helix.utils.io.BinaryReader;
import main.Constants;

public class ReadItemsTest {

	public static void main(String[] args) {
		BinaryReader reader = new BinaryReader("/data/item");
		
		if(reader.getBytes().size() == 0) {
			System.exit(-1);
		}

		byte[] bytes = reader.getBytes(0,  Constants.ITEM_SIZE);
		bytes = reader.getBytes(Constants.ITEM_SIZE,  Constants.ITEM_SIZE);
		//System.out.println(new String(bytes));
		//bytes = reader.getBytes(Constants.ITEM_SIZE + 1,  Constants.ITEM_SIZE);
		//System.out.println(new String(bytes));
		
		for(int i = 0; i < bytes.length; i++) {
			if(i == Constants.ID_POS)
				System.out.println("ID");
			else if(i == Constants.NAME_POS)
				System.out.println("NAME");
			else if(i == Constants.STACK_POS)
				System.out.println("STACK");
			else if(i == Constants.FLAG_POS)
				System.out.println("FLAGS");
			System.out.println(bytes[i]);
		}
	}
	
}
