package helix.game.inventory;

import main.game.entities.ItemType;

public class Inventory {
	
	/**
	 * Array of all the items. Each index corresponds to an item's ID e.g items[0]
	 * would be the amount of Grass in the inventory
	 */
	private int[] items;
	
	// Max Stack
	public final int MAX_STACK = 50;

	public Inventory() {
		items = new int[ItemType.values().length];
	}

	public boolean set(int itemID, int amount) {
		this.items[itemID] = amount;
		return true;
	}

	public boolean set(String name, int amount) {
		return this.set(ItemType.idOf(name), amount);
	}

	public boolean add(int itemID, int amount) {
		this.items[itemID] += amount;
		return true;
	}

	public boolean add(String name, int amount) {
		return this.add(ItemType.idOf(name), amount);
	}

	public boolean remove(int itemID, int amount) {
		if(this.items[itemID] < amount)
			return false;
		
		this.items[itemID] -= amount;
		return true;
	}

	public boolean remove(String name, int amount) {
		return this.remove(ItemType.idOf(name), amount);
	}

	public int get(int itemID) {
		return items[itemID];
	}

	public int get(String name) {
		return this.get(ItemType.idOf(name));
	}
}
