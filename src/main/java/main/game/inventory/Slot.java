package main.game.inventory;

import java.util.logging.Logger;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import helix.gfx.Sprite;
import helix.utils.math.Point;
import helix.utils.math.Rectangle;
import main.Constants;
import main.game.item.ItemType;

public class Slot {
	// Slot sprite
	public static Sprite SPRITE;
	
	public static final BitmapFont inventoryFont = new BitmapFont();
	// Logger
	private static final Logger log = Logger.getLogger(Inventory.class.getName());
	
	public final int ID;
	
	private Inventory inventory;
	
	private Sprite itemSprite;
	private ItemType item;
	private int amount;
	
	private Rectangle bounds;
	
	public Slot(Inventory inventory, Point pos, int id, ItemType item, int amount) {
		this.inventory = inventory;
		this.ID = id;
		this.item = item;
		this.amount = amount;
		
		this.itemSprite = (item != null) ? item.getSprite() : null;
		
		this.bounds = new Rectangle(pos.getX(), pos.getY(), SPRITE.getWidth(), SPRITE.getHeight());
	}
	
	public Slot(Inventory inventory, Point pos, int id) {
		this(inventory, pos, id, null, 0);
	}
	
	public void update() {
		if(this.amount == 0)
			this.item = null;
		if(this.item == null)
			this.amount = 0;
	}
	
	public void render(SpriteBatch b, Color col) {
		float x = inventory.getGameData().getCamera().position.x + bounds.getX();
		float y = inventory.getGameData().getCamera().position.y + bounds.getY() + Constants.INV_ITEM_OFFSET_Y;
		
		SPRITE.draw(b, x, y, col);

		if(!isEmpty() && itemSprite != null) {
			
			itemSprite.draw(b, x + Constants.INV_ITEM_OFFSET_X, y + Constants.INV_ITEM_OFFSET_Y, col);
			
			if(this.getItem().getFlag("stackable")) {
				String string = Integer.toString(this.getAmount());
				inventoryFont.draw(b, string, x + SPRITE.getWidth() - 3, y + 3);
			}
		}
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
		return (this.isEmpty()) ? false :(this.item.ID == item.ID);
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
		this.itemSprite = (item != null) ? item.getSprite() : null;
	}

	public int getAmount() {
		return amount;
	}
	
	public Rectangle getBounds() {
		return bounds;
	}
	
	public Inventory getInventory() {
		return inventory;
	}
}