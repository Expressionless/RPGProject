package main.game.inventory;

import java.util.logging.Logger;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

import helix.gfx.Sprite;
import helix.utils.math.Point;
import helix.utils.math.Rectangle;
import main.Constants;
import main.game.item.ItemInfo;

public class Slot {
	// Slot sprite
	public static Sprite SPRITE;
	
	public static final BitmapFont inventoryFont = new BitmapFont();
	// Logger
	private static final Logger log = Logger.getLogger(Inventory.class.getName());
	
	public final int ID;
	
	private Inventory inventory;
	
	private Sprite itemSprite;
	private ItemInfo item;
	private int amount;
	
	private Rectangle bounds;
	
	private final Point screenPos;
	
	public Slot(Inventory inventory, Point pos, int id, ItemInfo item, int amount) {
		this.inventory = inventory;
		this.ID = id;
		this.item = item;
		this.amount = amount;
		this.screenPos = pos;
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
		
		// Track the screen
		Vector3 camera = inventory.getGameData().getCamera().position;
		this.getBounds().setX(screenPos.getX() + camera.x);
		this.getBounds().setY(screenPos.getY() + camera.y);
	}
	
	public void render(SpriteBatch b, Color col) {
		float x = bounds.getX();
		float y = bounds.getY();
		
		SPRITE.draw(b, x, y, col);

		if(!isEmpty() && itemSprite != null) {
			itemSprite.draw(b, x + Constants.INV_ITEM_OFFSET_X, y + Constants.INV_ITEM_OFFSET_Y, col);
			
			if(this.getItem().getFlag("stackable")) {
				String string = Integer.toString(this.getAmount());
				inventoryFont.draw(b, string, x + SPRITE.getWidth() - 3, y + 3);
			}
			
			InventoryCursor inv = inventory.getGameData().getCursor();
			if(inv != null && this.isCursorOver()) {
				inventoryFont.draw(b, item.name + "\n" + this.getItem().type, inv.getPos().getX(), inv.getPos().getY());
			}
		}
	}
	
	public boolean remove() {
		return this.remove(this.getAmount());
	}
	
	public boolean remove(int amount) {
		if(this.isEmpty())
			return false;
		if(this.getAmount() < amount)
			return false;
		
		if(this.getAmount() == amount)
			this.item = null;
		this.amount -= amount;
		
		return true;
			
	}
	
	public boolean add(ItemInfo item, int amount) {
		if(item == null)
			return false;
		if(amount == 0)
			return false;
		if(!this.getInventory().verifyType(item.getType()))
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
	
	public boolean add(ItemInfo item) {
		return this.add(item, 1);
	}

	// Getters and Setters
	public boolean isCursorOver() {
		return (this.getBounds().contains(inventory.getGameData().getCursor().getPos()));
	}
	
	public boolean isEmpty() {
		return (this.amount == 0 && this.item == null);
	}
	
	public boolean contains(ItemInfo item) {
		return (this.isEmpty()) ? false :(this.item.ID == item.ID);
	}
	
	public ItemInfo getItem() {
		return item;
	}

	public void setItem(ItemInfo item) {
		this.setItem(item, 1);
	}
	
	public void setItem(ItemInfo item, int amount) {
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
	
	public String toString() {
		return "Slot [" + 
				"id=" + ID + "," +
				"itemSprite=" + ((itemSprite != null) ? itemSprite.getName() : "null") + "," +
				"amount=" + this.amount + "," + 
				"bounds=[x="+this.getBounds().getX() + ",y="+this.getBounds().getY() + ",w=" + this.getBounds().getWidth() +",h=" + this.getBounds().getHeight() +"]"
				+ "]";
		
	}
}