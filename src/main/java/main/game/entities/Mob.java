package main.game.entities;

import helix.game.GameData;
import helix.game.GameObject;
import helix.game.objects.Entity;
import helix.utils.math.Point;
import main.game.inventory.Inventory;

public abstract class Mob extends Entity {
	private Inventory inventory;
	
	private final MobStats stats;
	
	public Mob(GameData gameData, Point pos) {
		super(gameData, pos);
		this.stats = new MobStats();
		this.gameData.mobs.add(this);
		this.inventory = new Inventory();
	}
	
	@Override
	protected void preStep() {
		this.inventory.update();
	}

	@SuppressWarnings("unchecked")
	public <T extends Mob> T findMob(Class<T> searchClass) {
		for(GameObject object : gameData.mobs) {
			if(searchClass.isInstance(object))
				return (T)object;
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public <T extends Mob> T findNearestMob(Class<T> searchClass) {
		GameObject current = null;
		for(GameObject object : gameData.mobs) {
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
	
}
