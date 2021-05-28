package main.game.item;

import helix.game.GameData;
import helix.utils.io.DataReader;
import helix.utils.io.DataWriter;

public class ItemType {

	public final String name;
	public final int ID;
	public final Flags itemFlags;
	
	private static DataReader reader;
	private static DataWriter writer;

	private ItemType(int id, String name, int... flags) {
		this.name = name;
		this.ID = id;
		this.itemFlags = new Flags(flags);
	}

	private ItemType(int id, String name) {
		this(id, name, new int[] {});
	}

	public static void beginReading() {
		if(writer != null)
			return;
		if(reader != null)
			return;
		
		reader = new DataReader("/data/item");
	}
	
	public static void stopReading() {
		if(reader == null)
			return;
		reader = null;
	}
	
	
	public static void beginWriting() {
		if(writer != null)
			return;
		if(reader != null)
			return;
		
		writer = new DataWriter("/data/item");
	}
	
	public static void stopWriting() {
		if(writer == null)
			return;
		
		writer.close();
		writer = null;
	}
	
	public static void addItem(int id, String name, boolean[] flags) {
		if(writer == null)
			return;
		writer.write(id);
		writer.write(name, helix.Constants.MAX_ITEM_NAME_LEN);
		writer.writeBools(flags);
	}

	public static ItemType parseItemType(int position) {
		if(reader == null)
			return null;
		position *= helix.Constants.ITEM_SIZE;

		// Read in the ID
		int id = reader.getInt(position);
		String name = reader.getString(position + 1, helix.Constants.MAX_ITEM_NAME_LEN);
		int flags = reader.getInt(position + helix.Constants.MAX_ITEM_NAME_LEN + 1);

		return new ItemType(id, name, flags);
	}

	/**
	 * Get the ID of an Item based on it's name (not case sensitive)
	 * 
	 * @param name - name of the item to find
	 * @return id of the item or -1 if no item has the specified name
	 */
	public static int idOf(final String name) {
		for (int i = 0; i < GameData.ITEM_TYPES.size(); i++) {
			if (GameData.ITEM_TYPES.get(i).name.toLowerCase().equals(name.toLowerCase()))
				return GameData.ITEM_TYPES.get(i).ID;
		}
		return -1;
	}

	public static String nameOf(final int id) {
		try {
			return ItemType.get(id).toString();
		} catch (NullPointerException e) {
			return "UNDEFINED";
		}
	}

	public static ItemType get(final int ID) {
		return GameData.ITEM_TYPES.get(ID);
	}

	public static boolean reading() {
		return (reader != null);
	}
	
	public static boolean writing() {
		return (writer != null);
	}
	
	@Override
	public String toString() {
		return "ItemType [ID=" + ID + ", name=" + name + ", flags=[" + itemFlags.toString() + "]]";
	}

	// Helper class
	@SuppressWarnings("unused")
	protected class Flags {

		private boolean stackable = true;
		private int maxStack = 50;

		public Flags() {

		}

		public Flags(int... flags) {
			stackable = (flags[0] & 0x01) != 0;
		}

		public void setStat(String stat, float val) {
			try {
				Flags.class.getDeclaredField(stat).set(this, val);
			} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
				e.printStackTrace();
			}
		}

		public float getStat(String stat) {
			try {
				return Flags.class.getDeclaredField(stat).getFloat(this);
			} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
				e.printStackTrace();
			}

			return 0;
		}
		
		@Override
		public String toString() {
			return "Flags [stackable=" + stackable + "]";
		}
	}
	
}
