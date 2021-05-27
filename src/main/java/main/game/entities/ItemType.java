package main.game.entities;

public enum ItemType {

	GRASS(0),
	WOOD(1),
	SWORD(2),
	BOW(3);
	
	public final int ID;
	
	private ItemType(int ID) {
		this.ID = ID;
	}
	
	/**
	 * Get an Item by it's ID
	 * @param ID - ID of the item
	 * @return - item if item with specified ID exists, null otherwise
	 */
	public static ItemType get(final int ID) {
		ItemType[] items = ItemType.values();
		for(ItemType item : items) {
			if(item.ID == ID)
				return item;
		}
		
		return null;
	}

	/**
	 * Get an Item by it's name (Not case sensitive)
	 * @param name - Name of the item
	 * @return - item if item with specified name exists, null otherwise
	 */
	public static ItemType get(final String name) {
		return ItemType.valueOf(name.toUpperCase());
	}
	
	/**
	 * Get the ID of an Item based on it's name (not case sensitive)
	 * @param name - name of the item to find
	 * @return id of the item or -1 otherwise
	 */
	public static int idOf(final String name) {
		try {
		return ItemType.get(name.toUpperCase()).ID;
		} catch (NullPointerException e) {
			return -1;
		}
	}
	
	public static String nameOf(final int id) {
		try {
			return ItemType.get(id).toString();
		} catch(NullPointerException e) {
			return "UNDEFINED";
		}
	}
}
