package main.game.item;

import java.util.logging.Logger;

import helix.game.Serializable;
import helix.gfx.Sprite;
import helix.utils.io.DataReader;
import helix.utils.io.DataWriter;
import main.Constants;
import main.GameData;

public final class ItemType implements Serializable {
	private static final Logger log = Logger.getLogger(ItemType.class.getCanonicalName());
	
	public String name;
	public final Flags itemFlags;

	public int maxStack;
	public int ID;

	public Sprite sprite;

	public ItemType(int id, String name, int maxStack, int flags) {
		this.name = name;
		this.ID = id;
		this.maxStack = maxStack;
		this.itemFlags = new Flags(flags);
		
		attachSprite();
	}

	@SuppressWarnings("unused")
	private ItemType(int id, String name, int flags) {
		this(id, name, Constants.MAX_STACK, flags);
	}

	@SuppressWarnings("unused")
	private ItemType(int id, String name) {
		this(id, name, 0);
	}
	
	@SuppressWarnings("unused")
	public ItemType() {
		itemFlags = new Flags();
	}
	
	private void attachSprite() {
		int index[] = Item.IDtoImageIndex(ID);
		this.sprite = Item.ITEM_SHEET.getSubSprite(index[0], index[1]);
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
		System.err.println("Could not get ID: " + name);
		return -1;
	}

	public static String nameOf(final int id) {
		try {
			return ItemType.get(id).name;
		} catch (NullPointerException e) {
			return "UNDEFINED";
		}
	}

	public static ItemType get(final int ID) {
		return GameData.ITEM_TYPES.get(ID);
	}

	public Sprite getSprite() {
		return this.sprite;
	}

	public boolean getFlag(String flag) {
		return this.itemFlags.getFlag(flag);
	}

	public void setFlag(String flag, boolean val) {
		this.itemFlags.setFlag(flag, val);
	}

	@Override
	public String toString() {
		return "ItemType [ID=" + ID + ", name=" + name + ", flags=[" + itemFlags.toString() + "]]";
	}

	// Helper class
	@SuppressWarnings("unused")
	private class Flags {

		private boolean stackable = true;
		private int maxStack = 50;

		public Flags() {

		}

		public Flags(int... flags) {
			stackable = (flags[0] & 0x01) != 0;
		}

		public void setFlag(String flag, boolean val) {
			try {
				Flags.class.getDeclaredField(flag).set(this, val);
			} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
				e.printStackTrace();
			}
		}

		public boolean getFlag(String flag) {
			try {
				return Flags.class.getDeclaredField(flag).getBoolean(this);
			} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
				e.printStackTrace();
			}

			return false;
		}
		
		public void setFlags(int... flags) {
			stackable = (flags[0] & 0x01) != 0;
		}

		@Override
		public String toString() {
			return "Flags [stackable=" + stackable + "]";
		}
		
		public boolean[] getFlags() {
			return new boolean[] {stackable, false, false, false};
		}
	}

	@Override
	public boolean write(DataWriter writer, int position) {
		if(writer == null)
			return false;
		writer.write(ID);
		writer.write(name, Constants.MAX_ITEM_NAME_LEN);
		writer.write(maxStack);
		writer.writeBools(itemFlags.getFlags());
		return true;
	}

	@Override
	public boolean parse(DataReader reader, int position) {
		if(reader == null)
			return false;
		position *= Constants.ITEM_SIZE;

		log.fine("Parsing new item at: " + position);
		// Read in the ID
		this.ID = reader.getInt(position + Constants.ID_POS);
		log.fine("ID: "  + this.ID);
		// Read in the name
		this.name = reader.getString(position + Constants.NAME_POS, Constants.MAX_ITEM_NAME_LEN);
		log.fine("Name: " + this.name);
		// Read in maxStack
		this.maxStack = reader.getInt(position + Constants.STACK_POS);
		log.fine("Stack: " + this.maxStack);
		// Read in the flags
		int flags = reader.getInt(position + Constants.FLAG_POS);
		itemFlags.setFlags(flags);
		log.fine("Flags: " + flags);

		this.attachSprite();
		return true;
	}

}
