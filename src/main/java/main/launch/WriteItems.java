package main.launch;

import helix.utils.io.DataReader;
import helix.utils.io.DataWriter;
import main.Constants;
import main.game.item.ItemType;

public class WriteItems {

	private static boolean[] STACKABLE = {true, false, false, false};
	private static boolean[] NONSTACKABLE = {false, false, false, false};
	
	public static void main(String[] args) {
		DataWriter writer = new DataWriter("/data/item");
		new ItemType(0, "grass", Constants.MAX_STACK, STACKABLE).write(writer);
		new ItemType(1, "wood", Constants.MAX_STACK, STACKABLE).write(writer);
		new ItemType(2, "sword", 1, NONSTACKABLE).write(writer);
		new ItemType(3, "bow", 1, NONSTACKABLE).write(writer);
		
		DataReader reader = new DataReader("/data/item");
		ItemType item = new ItemType();
		item.parse(reader, 0);
		System.out.println(item);
	}

}
