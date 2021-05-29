package main.game.inventory;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import helix.gfx.Sprite;
import main.Constants;
import main.game.item.ItemType;

/**
 * Basic Stackable Inventory
 * @author bmeachem
 *
 */
public class Inventory {
	public static final BitmapFont inventoryFont = new BitmapFont();
	
	// Slot sprite
	public static Sprite slotSprite;
	
	// Max Stack
	public final int MAX_STACK = 50;
	
	private final Slot[][] slots;
	private final int width, height;
	
	private boolean visible;
	
	public Inventory(int w, int h) {
		this.width = w;
		this.height = h;
		this.visible = false;
		this.slots = new Slot[w][h];
		
		int row, column;
		for(row = 0; row < w; row++) {
			for(column = 0; column < h; column++) {
				slots[row][column] = new Slot(row * h + column);
			}
		}
		
	}
	
	public Inventory() {
		this(Constants.DEF_INV_WIDTH, Constants.DEF_INV_HEIGHT);
	}
	
	public void update() {
		for(Slot[] row : slots) {
			for(Slot slot: row) {
				slot.update();
			}
		}
	}
	
	public void render(SpriteBatch b, float screenPosX, float screenPosY) {
		float x = screenPosX;
		float y = screenPosY;
		
		if(this.isVisible()) {
			Color col = Color.WHITE.cpy();
			col.a = 0.6f;
			for(int i = 0; i < this.slots.length; i++) {
				for(int j = 0; j < this.slots[i].length; j++) {
					Inventory.slotSprite.draw(b, x, y, col);
					if(!this.slots[i][j].isEmpty()) {
						this.slots[i][j].getItem().getSprite().copy().draw(b, x + Constants.INV_ITEM_OFFSET_X, y + Constants.INV_ITEM_OFFSET_Y, col);
						if(this.slots[i][j].getItem().getFlag("stackable")) {
							String string = Integer.toString(this.slots[i][j].getAmount());
							inventoryFont.draw(b, string, x + slotSprite.getWidth() - 3, y + 3);
						}
					}
					x += Inventory.slotSprite.getWidth() + Constants.INVENTORY_MARGIN;
				}
				x = screenPosX;
				y -= Inventory.slotSprite.getHeight() + Constants.INVENTORY_MARGIN;
			}
		}
	}

	public boolean add(ItemType item, int amount) {
		return this.add(item, amount, false);
	}
	
	public boolean add(ItemType item, int amount, boolean dryRun) {
		// Null checks
		if(width == 0)
			return false;
		if(height == 0)
			return false;
		
		int row, column;
		Slot firstFree = null;
		
		for(row = 0; row < slots.length; row++) {
			for(column = 0; column < slots[0].length; column++) {
				Slot currentSlot = slots[row][column];
				
				// If found the first free slot, make note of it and move on
				if(firstFree == null && currentSlot.isEmpty()) {
					if(!item.getFlag("stackable")) {
						if(!dryRun)
							currentSlot.addItem(item, amount);
						return true;
					}
					firstFree = currentSlot;
					continue;
				}
				// Skip the slot if it contains an item that is not the same as what we're adding
				// or if the item we're adding is not stackable
				if(!currentSlot.isEmpty())
					if(!currentSlot.contains(item) || !item.getFlag("stackable"))
						continue;
				
				// Add the item to the inv
				if(!dryRun)
					currentSlot.addItem(item, amount);
				
				return true;				
			}
		}
		
		if(firstFree != null) {
			if(!dryRun)
				firstFree.addItem(item, amount);
			return true;
		}
		
		return false;
	}

	/*
	 * public boolean remove(int itemID, int amount) { if(this.items[itemID] <
	 * amount) return false;
	 * 
	 * this.items[itemID] -= amount; return true; }
	 * 
	 * public boolean remove(String name, int amount) { return
	 * this.remove(ItemType.idOf(name), amount); }
	 */
	
	public Slot getSlot(int x, int y) {
		return slots[x][y];
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	private String slotToString(Slot s) {
		return "|" + (!s.isEmpty() ? s.getItem().ID : Constants.NO_ITEM) + "." + s.getAmount() + "|";
	}
	
	public void print() {
		for(int x = 0; x < this.width; x++) {
			String row = "";
			for(int y = 0; y < this.height; y++) {
				row += slotToString(slots[x][y]);
			}
			
			System.out.println(row);
		}
		
	}
	
	
}
