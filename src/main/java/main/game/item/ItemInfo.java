package main.game.item;

/**
 * Small helper class to assist with updating item data
 * @author bmeachem
 *
 */
public class ItemInfo {
	public String name;
	
	public int id;
	public int maxStack;
	
	public boolean[] flags;
	
	
	public ItemInfo(int id, String name, int maxStack, boolean[] flags) {
		if(flags.length != 4)
			System.err.println("Illegal number of flags for ItemInfo: " + name + ", flags: " + flags.length);
		this.id = id;
		this.name = name;
		this.maxStack = maxStack;
		this.flags = flags;
	}
}