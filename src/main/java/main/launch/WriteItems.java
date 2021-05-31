package main.launch;

import helix.utils.io.DataWriter;
import main.Constants;
import main.GameData;

public class WriteItems {

	private static boolean[] STACKABLE = {true, false, false, false};
	private static boolean[] NONSTACKABLE = {false, false, false, false};
	
	public static void main(String[] args) {
		DataWriter writer = new DataWriter("/data/item");
		GameData.addItem(writer, 0, "grass", Constants.MAX_STACK, STACKABLE);
		GameData.addItem(writer, 1, "wood", Constants.MAX_STACK, STACKABLE);
		GameData.addItem(writer, 2, "sword", Constants.MAX_STACK, NONSTACKABLE);
		GameData.addItem(writer, 3, "bow", Constants.MAX_STACK, NONSTACKABLE);
	}

}
