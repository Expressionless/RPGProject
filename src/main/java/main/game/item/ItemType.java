package main.game.item;

import helix.gfx.Sprite;
import main.Constants;
import main.GameData;

public final class ItemType {
	public final String name;
	public final Flags itemFlags;
	
	public final int maxStack;
	public final int ID;
	
	public final Sprite sprite;	

	public ItemType(int id, String name, int maxStack, int flags) {
		this.name = name;
		this.ID = id;
		this.maxStack = maxStack;
		this.itemFlags = new Flags(flags);
		
		int index[] = Item.IDtoImageIndex(id);
		this.sprite = Item.ITEM_SHEET.getSubSprite(index[0], index[1]);
	}

	@SuppressWarnings("unused")
	private ItemType(int id, String name, int flags) {
		this(id, name, Constants.MAX_STACK, flags);
	}
	
	@SuppressWarnings("unused")
	private ItemType(int id, String name) {
		this(id, name, 0);
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
		
		@Override
		public String toString() {
			return "Flags [stackable=" + stackable + "]";
		}
	}
	
}
