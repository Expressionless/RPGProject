package helix.game.objects.entity.mob;

import helix.GameData;
import helix.game.inventory.Inventory;
import helix.game.objects.Entity;
import helix.utils.math.Point;

public abstract class Mob extends Entity {
	private final Inventory inventory;
	
	private final MobStats stats;
	
	public Mob(GameData gameData, Point pos) {
		super(gameData, pos);
		this.stats = new MobStats();
		this.gameData.mobs.add(this);
		this.inventory = new Inventory();
	}

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
}
