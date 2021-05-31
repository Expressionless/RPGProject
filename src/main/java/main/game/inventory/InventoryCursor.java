package main.game.inventory;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import helix.utils.math.Point;
import main.game.Entity;
import main.game.RpgGame;
import main.game.item.ItemType;

public class InventoryCursor extends Entity {

	private ItemType item;
	private int amount;
	
	public InventoryCursor(RpgGame game, Point pos) {
		super(game, pos);
		this.item = null;
		this.amount = 0;
	}

	@Override
	public void draw(SpriteBatch b) {
		if(this.item != null) {
			this.item.getSprite().draw(b, getPos().getX(), getPos().getY());
		}
	}

	@Override
	protected void preStep(float delta) {
		super.preStep(delta);
		// Keep the bugs away
		if(this.item == null)
			this.amount = 0;
		if(this.amount == 0)
			this.item = null;
	}
	
	@Override
	protected void step(float delta) {
		
	}
	
	public void take(Slot s) {
		if(s.getItem() != null) {
			this.setItem(s.getItem());
			this.setAmount(s.getAmount());
			
			s.setItem(null);
		}
	}
	
	// Getters and Setters
	public void setItem(int id) {
		this.item = ItemType.get(id);
	}
	
	public void setItem(String name) {
		this.setItem(ItemType.idOf(name));
	}
	
	public void setItem(ItemType item) {
		this.item = item;
	}
	
	public ItemType getItem() {
		return this.item;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}
}
