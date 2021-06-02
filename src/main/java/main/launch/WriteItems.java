package main.launch;

import helix.utils.io.DataReader;
import helix.utils.io.DataWriter;
import main.Constants;
import main.game.item.ItemInfo;

public class WriteItems {

	private static boolean[] STACKABLE = {true, false, false, false};
	private static boolean[] NONSTACKABLE = {false, false, false, false};
	
	public static void main(String[] args) {
		DataWriter writer = new DataWriter("/data/item");
		new ItemInfo(0,"armour", "grass", Constants.MAX_STACK, STACKABLE).write(writer);
		new ItemInfo(1, "material", "wood", Constants.MAX_STACK, STACKABLE).write(writer);
		new ItemInfo(2, "weapon"  , "sword", 1, NONSTACKABLE).write(writer);
		new ItemInfo(3, "weapon"  , "bow", 1, NONSTACKABLE).write(writer);
		new ItemInfo(4, "tool"     , "boomerang", 1, NONSTACKABLE).write(writer);
		new ItemInfo(5, "tool"     , "basic", 1, NONSTACKABLE).write(writer);
		new ItemInfo(6, "tool"     , "axe", 1, NONSTACKABLE).write(writer);
		new ItemInfo(7, "crafting", "shaft", 1, STACKABLE).write(writer);
		new ItemInfo(8, "tool", "pickaxe", 1, NONSTACKABLE).write(writer);
		
		DataReader reader = new DataReader("/data/item");
		ItemInfo item = new ItemInfo();
		item.parse(reader, 0);
		System.out.println(item);
	}

}
