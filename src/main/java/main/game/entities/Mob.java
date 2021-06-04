package main.game.entities;

import helix.game.GameObject;
import helix.game.Serializable;
import helix.utils.math.Point;
import main.game.Entity;
import main.game.RpgGame;
import main.game.entities.mobs.MobState;
import main.game.inventory.Inventory;
import main.game.item.Item;
import main.game.item.ItemInfo;

public abstract class Mob extends Entity implements Serializable {
	private Inventory inventory;
	
	private final MobStats stats;
	private MobState currentState, lastState;
	
	protected abstract boolean handleState(float delta);
	
	public Mob(RpgGame game, Point pos) {
		super(game, pos);
		
		this.stats = new MobStats();
		//this.inventory = new GenericInventory(game, pos, 8, 6);

		game.getGameData().mobs.add(this);
	}
	
	@Override
	protected void preStep(float delta) {
		super.preStep(delta);
		this.inventory.update(delta);
		this.handleState(delta);
	}

	@SuppressWarnings("unchecked")
	public <T extends Mob> T findMob(Class<T> searchClass) {
		for(GameObject object : this.getGame().getGameData().mobs) {
			if(searchClass.isInstance(object))
				return (T)object;
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public <T extends Mob> T findNearestMob(Class<T> searchClass) {
		GameObject current = null;
		for(GameObject object : this.getGame().getGameData().mobs) {
			if(!searchClass.isInstance(object))
				continue;
			if(current == null) {
				current = object;
				continue;
			}
			
			float dis1 = this.getPos().getDistTo(object.getPos());
			float dis2 = this.getPos().getDistTo(current.getPos());
			if(dis1 < dis2)
				current = object;
		}
		if(current != null)
			return (T)current;
		else return null;
	}

	public Item findItem(String name) {
		return this.findItem(ItemInfo.idOf(name));
	}
	
	public Item findItem(int ID) {
		for(Item object : this.getGame().getGameData().items) {
			if(Item.class.isInstance(object) && object.getID() == ID)
				return object;
		}
		return null;
	}
	
	public Item findNearestItem() {
		Item current = null;
		
		for(Item object : this.getGame().getGameData().items) {
			if(current == null) {
				current = object;
				continue;
			}
			
			float dis1 = this.getPos().getDistTo(object.getPos());
			float dis2 = this.getPos().getDistTo(current.getPos());
			if(dis1 < dis2)
				current = object;
		}
		
		if(current != null)
			return current;
		else return null;
	}
	
	public Item findNearestItem(String name) {
		return this.findNearestItem(ItemInfo.idOf(name));
	}
	
	public Item findNearestItem(int ID) {
		Item current = null;
		
		for(Item object : this.getGame().getGameData().items) {
			if(object.getID() != ID)
				continue;
				
			if(current == null) {
				current = object;
				continue;
			}
			
			float dis1 = this.getPos().getDistTo(object.getPos());
			float dis2 = this.getPos().getDistTo(current.getPos());
			if(dis1 < dis2)
				current = object;
		}
		
		if(current != null)
			return current;
		else return null;
	}
	
	// Getters and Setters
	public float getStat(String stat) {
		return this.stats.getStat(stat);
	}
	
	public void setStat(String stat, float val) {
		this.stats.setStat(stat, val);
	}

	public MobStats getMobStats() {
		return stats;
	}
	
	public Inventory getInventory() {
		return this.inventory;
	}
	
	public void setInventory(Inventory inv) {
		this.inventory = inv;
	}
	
	// Helper class
	@SuppressWarnings("unused")
	protected class MobStats {

		private float speed = 0, maxSpeed = 1.5f;
		private float acc = 0;
		private float defence;
		private float attack;

		public void setStat(String stat, float val) {
			try {
				MobStats.class.getDeclaredField(stat).set(this, val);
			} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
				e.printStackTrace();
			}
		}

		public float getStat(String stat) {
			try {
				return MobStats.class.getDeclaredField(stat).getFloat(this);
			} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
				e.printStackTrace();
			}

			return 0;
		}
	}
	
	public MobState getState() {
		return currentState;
	}
	
	public MobState getLastState() {
		return lastState;
	}
	
	public void setCurrentState(MobState state) {
		if(this.currentState != null)
			this.lastState = this.currentState;
		this.currentState = state;
	}
	
	public void setCurrentState(String state) {
		this.setCurrentState(MobState.valueOf(state));
	}
	
}
