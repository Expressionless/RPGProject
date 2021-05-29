package main.game.inventory;

import java.util.logging.Logger;

import main.game.item.ItemType;

public class Slot {
	// Logger
	private static final Logger log = Logger.getLogger(Inventory.class.getName());
	
	public final int ID;
	
	private ItemType item;
	private int amount;
	
	public Slot(int id, ItemType item, int amount) {
		this.ID = id;
		this.item = item;
		this.amount = amount;
	}
	
	public Slot(int id) {
		this(id, null, 0);
	}
	
	public void update() {
		if(this.amount == 0)
			this.item = null;
		if(this.item == null)
			this.amount = 0;
	}
	
	public boolean isEmpty() {
		return (this.amount == 0 && this.item == null);
	}
	
	public boolean addItem(ItemType item) {
		return this.addItem(item, 1);
	}
	
	public boolean addItem(ItemType item, int amount) {
		if(item == null)
			return false;
		if(amount == 0)
			return false;
		
		if(this.item != null) {
			if(this.item.ID == item.ID) {
				log.fine("Found preexisting matching item: " + item.name);
				this.amount += amount;
				return true;				
			} else {
				log.fine("Can't add item to slot " + this.ID + ": " + item.name 
						+ "\nCurrently contains: " + this.getItem().name);
				return false;
			}
		} else {
			this.setItem(item, amount);
			return true;
		}
	}

	// Getters and Setters
	
	public boolean contains(ItemType item) {
		if(this.isEmpty())
			return false;
		return (this.item.ID == item.ID);
	}
	
	public ItemType getItem() {
		return item;
	}

	public void setItem(ItemType item) {
		this.setItem(item, 1);
	}
	
	public void setItem(ItemType item, int amount) {
		this.item = item;
		this.amount = amount;
	}

	public int getAmount() {
		return amount;
	}
}