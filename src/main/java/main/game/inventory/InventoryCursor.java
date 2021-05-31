package main.game.inventory;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import helix.utils.math.Point;
import main.game.Entity;
import main.game.RpgGame;
import main.game.item.ItemType;

public final class InventoryCursor extends Entity {

	private ItemType item;
	private int amount;
	
	public InventoryCursor(RpgGame game) {
		super(game, new Point(0, 0));
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
	
	public void swap(Slot s) {
		ItemType item = s.getItem();
		int amount = s.getAmount();
		if(item.ID == this.item.ID) {
			s.setItem(this.item, this.amount + amount);
			this.setItem(null);
		} else {
			s.setItem(this.item, this.amount);
			this.setItem(item, amount);
		}
	}
	
	public void place(Slot s) {
		s.setItem(this.item, this.amount);
		this.setItem(null);
	}
	
	// Getters and Setters
	public boolean hasNothing() {
		return (this.item == null && this.amount == 0);
	}
	
	public void setItem(ItemType item, int amount) {
		this.item = item;
		this.amount = amount;
	}
	
	public void setItem(int id, int amount) {
		this.setItem(ItemType.get(id));
	}
	
	public void setItem(int id) {
		this.setItem(id, 1);
	}
	
	public void setItem(String name, int amount) {
		this.setItem(ItemType.idOf(name), amount);
	}
	
	public void setItem(ItemType item) {
		this.setItem(item, 1);
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
