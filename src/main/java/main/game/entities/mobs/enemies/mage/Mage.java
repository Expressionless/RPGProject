package main.game.entities.mobs.enemies.mage;

import helix.utils.io.BinaryReader;
import helix.utils.io.BinaryWriter;
import helix.utils.math.Angle;
import helix.utils.math.Point;
import main.game.RpgGame;
import main.game.annotations.QueueAsset;
import main.game.entities.mobs.RangedEnemy;
import main.game.entities.mobs.ai.state.MobState;
import main.game.entities.mobs.neutral.Player;
import main.game.entities.projectiles.MageProjectile;

public class Mage extends RangedEnemy<MageProjectile> {
	
	private static final int SEARCH_ALARM = 0;
	private static final int ATTACK_ALARM = 1;
	
	@QueueAsset(ref = "res/sprites/mob/enemy/mage/tiny_mage_up_right.png")
	public static String MAGE_UP;
	
	@QueueAsset(ref = "res/sprites/mob/enemy/mage/tiny_mage_right.png")
	public static String MAGE_DOWN;

	private boolean searching = false;

	private final int SEARCH_TIME = 5;

	public Mage(RpgGame game, Point pos) {
		super(game, MageProjectile.class, pos);
		this.addSprite(MAGE_UP, 4, 750);
		this.addSprite(MAGE_DOWN, 4, 750);
		this.setStat("speed", 0.25f);
		this.setStat("sight", 96);

		this.setStat("attack", 20);
		this.setStat("attack_range", 48);
		this.setStat("attack_speed", 0.5f);

		this.setStat("proj_speed", 0.5f);
		this.setStat("proj_range", 512);
		
		this.setStat("cast_time", 0.1f);
		
		this.addStates();
	}

	@Override
	public void step(float delta) {
		super.step(delta);
	}

	private void addStates() {
		Player player = this.getGameData().getPlayer();

		this.addState(MobState.ATTACK, () -> {
			if(this.getAlarm(2).isActive())
				return MobState.ATTACK;
			else {
				if(this.getLastState() != null)
					return this.getLastState();
				else return MobState.IDLE;
			}
		});
		
		// Idle state
		this.addState(MobState.IDLE, () -> {
			if(this.getTarget() != null)
				this.setTarget(null);
			float distToPlayer = getPos().getDistTo(player.getPos());
			// Check if player is not nearby
			// If so exit with IDLE
			if (distToPlayer > this.getStat("sight"))
				return MobState.IDLE;
			else {
				this.setTarget(player);
				return MobState.CHASE;
			}
		});

		this.addState(MobState.SEARCHING, () -> {
			float distToPlayer = getPos().getDistTo(player.getPos());
			if (searching) {
				if (distToPlayer < this.getStat("sight")) {
					this.setTarget(player);
					this.getAlarm(SEARCH_ALARM).cancel();
					return MobState.CHASE;
				}
				return MobState.SEARCHING;
			} else {
				this.setTarget(null);
				return MobState.IDLE;
			}
		});

		this.addState(MobState.CHASE, () -> {
			float distToPlayer = getPos().getDistTo(player.getPos());
			if (distToPlayer > this.getStat("sight")) {
				// Return to IDLE if can't find the player
				if (this.getTarget() == null)
					return MobState.IDLE;
				// Search for the player otherwise
				if (!this.getAlarm(SEARCH_ALARM).isActive() && !searching) {
					if (!searching) {
						this.setAlarm(SEARCH_ALARM, SEARCH_TIME, () -> {
							this.setTarget(null);
						});
						searching = true;
					} else {
						return MobState.IDLE;
					}
				}

			} else {
				if (this.getTarget() == null)
					return MobState.IDLE;
				
				if (this.getPos().getDistTo(this.getTarget().getPos()) > this.getStat("attack_range") && !this.getAlarm(2).isActive())
					this.moveTo(this.getTarget().getPos(), this.getStat("speed"));

				else if (this.canShoot()) {
					this.setAlarm(2, (int)this.getStat("cast_time"), () -> {
						this.fireProjectile(this.getStat("attack"), this.getStat("proj_speed"), player.getPos());
					});
					return MobState.ATTACK;
				}
			}
			return MobState.CHASE;
		});
	}

	protected void updateSprite() {
		double angle = this.getDirection().getAngle();
		boolean left = (angle >= Angle.TOP.angle && angle < Angle.BOTTOM.angle);
		boolean up = (angle < Angle.LEFT.angle && angle >= Angle.RIGHT.angle);

		if (up)
			this.setSprite(MAGE_UP);
		else
			this.setSprite(MAGE_DOWN);
		this.getSprite().flip(left);

		this.getSprite().start();

		if (this.getCurrentState() != MobState.CHASE || this.getAlarm(2).isActive())
			this.getSprite().reset();
	}

	@Override
	public boolean write(BinaryWriter writer, int pos) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean parse(BinaryReader reader, int pos) {
		// TODO Auto-generated method stub
		return false;
	}
}
