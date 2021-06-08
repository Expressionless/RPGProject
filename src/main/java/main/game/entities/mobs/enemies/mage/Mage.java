package main.game.entities.mobs.enemies.mage;

import com.badlogic.gdx.graphics.Texture;

import helix.utils.math.Angle;
import helix.utils.math.Point;
import main.game.RpgGame;
import main.game.annotations.QueueAsset;
import main.game.entities.mobs.neutral.Player;
import main.game.entities.mobs.state.MobState;
import main.game.entities.mobs.template.BasicEnemy;

public class Mage extends BasicEnemy {
	@QueueAsset(ref = "res/sprites/mob/enemy/mage/tiny_mage_up_right.png", type = Texture.class)
	public static String MAGE_UP;
	@QueueAsset(ref = "res/sprites/mob/enemy/mage/tiny_mage_right.png", type = Texture.class)
	public static String MAGE_DOWN;

	private boolean searching = false;

	private final int SEARCH_TIME = 5;

	public Mage(RpgGame game, Point pos) {
		super(game, pos);
		this.addSprite(MAGE_UP, 4, 750);
		this.addSprite(MAGE_DOWN, 4, 750);
		this.setStat("speed", 0.25f);
		this.setStat("sight", 96);

		this.setStat("attack", 20);
		this.setStat("attack_range", 48);
		this.setStat("attack_speed", 0.2f);

		this.addStates();
	}

	@Override
	public void step(float delta) {
		super.step(delta);
		/*
		 * System.out.println("Chase Timer 0: " + this.getAlarm(0).getTimer() + " " +
		 * this.getAlarm(0).isActive() + " s:" + searching);
		 * System.out.println("Attack Timer 1: " + this.getAlarm(1).getTimer() + " " +
		 * this.getAlarm(1).isActive());
		 */
	}

	private void addStates() {
		Player player = this.getGameData().getPlayer();

		// Idle state
		this.addState(MobState.IDLE, () -> {
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
					this.getAlarm(0).cancel();
					return MobState.CHASE;
				}
				return MobState.SEARCHING;
			} else {
				this.setTarget(null);
				return MobState.IDLE;
			}
		});

		this.addState(MobState.ATTACK, () -> {
			System.out.println(this.getAlarm(1).isActive() + " " + this.getLastState());
			if (this.getAlarm(1).isActive())
				return MobState.ATTACK;
			else
				return this.getLastState();
		});

		this.addState(MobState.CHASE, () -> {
			float distToPlayer = getPos().getDistTo(player.getPos());
			if (distToPlayer > this.getStat("sight")) {
				// Return to IDLE if can't find the player
				if (this.getTarget() == null)
					return MobState.IDLE;
				// Search for the player otherwise
				if (!this.getAlarm(0).isActive() && !searching) {
					if (!searching) {
						this.setAlarm(0, SEARCH_TIME, () -> {
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
				if (this.getPos().getDistTo(this.getTarget().getPos()) > this.getStat("attack_range"))
					this.moveTo(this.getTarget().getPos(), this.getStat("speed"));

				else if (!this.getAlarm(1).isActive()) {
					System.out.println("setting alarm");
					this.setAlarm(1, 3, () -> {
						player.subStat("health", this.getStat("attack"));
					});
					return MobState.ATTACK;
				}
			} /*
				 * else { if (this.getPos().getDistTo(this.getTarget().getPos()) <=
				 * this.getStat("attack_range")) { this.setDirection(new Vector2(0, 0));
				 * 
				 * if (!this.getAlarm(1).isActive()) { this.setAlarm(1, () -> {
				 * player.subStat("health", this.getStat("attack")); searching = false; }, 15);
				 * System.out.println("Setting alarm: " + this.getAlarm(1));
				 * 
				 * searching = true; } else if(searching)
				 * System.out.println(this.getAlarm(1).toString()); } else {
				 * this.moveTo(this.getTarget().getPos(), this.getStat("speed")); } }
				 */
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

		if (this.getCurrentState() != MobState.CHASE)
			this.getSprite().reset();
	}
}
