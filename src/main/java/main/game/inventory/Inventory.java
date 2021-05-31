package main.game.inventory;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import helix.utils.math.Point;
import main.Constants;
import main.GameData;
import main.game.RpgGame;
import main.game.item.ItemType;

/**
 * Basic Stackable Inventory
 * @author bmeachem
 *
 */
public class Inventory {
	public static final float INV_X = 40 - Constants.CAMERA_WIDTH / 4;
	public static final float INV_Y = 15 - Constants.CAMERA_HEIGHT / 8;
	
	public static final Color COL = new Color(1, 1, 1, 0.6f);
	
	// Max Stack
	public final int MAX_STACK = 50;
	
	private final Slot[][] slots;
	
	private boolean visible;
	private Point screenPos;
	
	private RpgGame game;
	
	public Inventory(RpgGame game, Point screenPos, int w, int h) {
		this.visible = false;
		this.slots = new Slot[h][w];
		this.screenPos = screenPos;
		this.game = game;
		
		initSlots(w, h);
	}
	
	public Inventory(RpgGame game, int w, int h) {
		this(game, new Point(INV_X, INV_Y), w, h);
	}
	
	public Inventory(RpgGame game) {
		this(game, Constants.DEF_INV_WIDTH, Constants.DEF_INV_HEIGHT);
	}
	
	private void initSlots(int w, int h) {

		int row, column;
		float x, y;
		for(column = 0; column < h; column++) {
			y = this.screenPos.getY() - column * (Slot.SPRITE.getHeight() + Constants.INVENTORY_MARGIN);
			for(row = 0; row < w; row++) {
				x = this.screenPos.getX() + row * (Slot.SPRITE.getWidth() + Constants.INVENTORY_MARGIN);
				slots[column][row] = new Slot(this, new Point(x, y), h * column + row);
			}
		}
	}
	
	public void update() {
		for(Slot[] row : slots) {
			for(Slot slot: row) {
				slot.update();
			}
		}
	}
	
	public void render(SpriteBatch b) {		
		if(this.isVisible()) {			
			for(int i = 0; i < this.slots.length; i++) {
				for(int j = 0; j < this.slots[i].length; j++) {
					this.slots[i][j].render(b, COL);
				}
			}
		}
	}
	
	private void addToSlot(Slot s, ItemType item, int amount) {
		s.addItem(item, amount);
	}

	public boolean add(ItemType item, int amount) {
		return this.add(item, amount, false);
	}
	
	public boolean add(ItemType item, int amount, boolean dryRun) {
		// Null checks
		if(slots.length == 0)
			return false;
		if(slots[0].length == 0)
			return false;
		
		int row, column;
		Slot firstFree = null;
		
		for(row = 0; row < slots.length; row++) {
			for(column = 0; column < slots[row].length; column++) {
				Slot currentSlot = slots[row][column];
				
				// If found the first free slot, make note of it and move on
				if(firstFree == null && currentSlot.isEmpty()) {
					if(!item.getFlag("stackable")) {
						if(!dryRun) {
							this.addToSlot(currentSlot, item, amount);
						}
						return true;
					}
					firstFree = currentSlot;
				}
				// Skip the slot if it contains an item that is not the same as what we're adding
				// or if the item we're adding is not stackable
				if(!currentSlot.isEmpty())
					if(!currentSlot.contains(item) || !item.getFlag("stackable"))
						continue;
				
				// Add the item to the inv
				if(!dryRun) {
					this.addToSlot(currentSlot, item, amount);
				}
				
				return true;				
			}
		}
		
		if(firstFree != null) {
			if(!dryRun) {
				firstFree.addItem(item, amount);
			}
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
	
	public Slot[][] getSlots() {
		return slots;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	public RpgGame getGame() {
		return game;
	}
	
	public GameData getGameData() {
		return game.getGameData();
	}
	
	private String slotToString(Slot s) {
		return "|" + (!s.isEmpty() ? s.getItem().ID : Constants.NO_ITEM) + "." + s.getAmount() + "|";
	}
	
	public void print() {
		for(int x = 0; x < this.slots.length; x++) {
			String row = "";
			for(int y = 0; y < this.slots[x].length; y++) {
				row += slotToString(slots[x][y]);
			}
			
			System.out.println(row);
		}
		
	}
}
