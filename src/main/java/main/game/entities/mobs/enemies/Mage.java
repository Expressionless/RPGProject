package main.game.entities.mobs.enemies;

import helix.utils.math.Angle;
import helix.utils.math.Point;
import helix.utils.math.Vector2;
import main.game.RpgGame;
import main.game.entities.mobs.Player;
import main.game.entities.mobs.state.MobState;
import main.game.entities.mobs.template.BasicEnemy;

public class Mage extends BasicEnemy {
	private static final String MAGE_UP = "res/sprites/mob/enemy/tiny_mage_up_right.png";
	private static final String MAGE_DOWN = "res/sprites/mob/enemy/tiny_mage_right.png";

	private boolean searching = false;
	
	private final int SEARCH_TIME = 3;
	
	public Mage(RpgGame game, Point pos) {
		super(game, pos);
		this.addSprite(MAGE_UP, 4, 750);
		this.addSprite(MAGE_DOWN, 4, 750);
		this.setStat("speed", 0.25f);
		this.setStat("sight", 128);
		this.setStat("attack_range", 32);
		
		this.addStates();
	}

	private void addStates() {
		Player player = this.getGameData().getPlayer();
		float distToPlayer = getPos().getDistTo(player.getPos());
		
		this.getStateMachine().addState(MobState.IDLE, () -> {
			// Check if player is nearby
			if(distToPlayer > this.getStat("sight"))
				return MobState.IDLE;
			
			this.setTarget(player);
			return MobState.CHASE;
		});

		this.getStateMachine().addState(MobState.CHASE, () -> {
			if (distToPlayer > this.getStat("sight")) {
				if(this.getTarget() == null)
					return MobState.IDLE;
				if(!this.getAlarm(0).isActive() && !searching) {
					if(!searching) {
						this.setAlarm(0, () -> {
							this.setTarget(null);
						}, SEARCH_TIME);
						searching = true;
					} else {
						return MobState.IDLE;
					}
				}
				
				return MobState.CHASE;
			} else {
				if (this.getPos().getDistTo(this.getTarget().getPos()) <= this.getStat("attack_range")) {
					this.setDirection(new Vector2(0, 0));
				} else {
					this.moveTo(this.getTarget().getPos(), this.getStat("speed"));
				}
				return MobState.CHASE;
			}
		});
	}

	protected void updateSprite() {
		double angle = this.getDirection().getAngle();
		boolean left = (angle >= Angle.TOP.angle && angle < Angle.BOTTOM.angle);
		boolean up = (angle < Angle.LEFT.angle && angle >= Angle.RIGHT.angle);
		
		if(up)
			this.setSprite(MAGE_UP);
		else
			this.setSprite(MAGE_DOWN);
		this.getSprite().flip(left);
		
		this.getSprite().start();
	}
}
