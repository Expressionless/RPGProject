package main.game.entities.mobs.enemies;

import helix.utils.math.Angle;
import helix.utils.math.Point;
import main.game.RpgGame;
import main.game.entities.mobs.template.BasicEnemy;

public class Mage extends BasicEnemy {
	private static final String MAGE_UP = "res/sprites/mob/enemy/tiny_mage_up_right.png";
	private static final String MAGE_DOWN = "res/sprites/mob/enemy/tiny_mage_right.png";

	public Mage(RpgGame game, Point pos) {
		super(game, pos);
		this.addSprite(MAGE_UP, 4, 750);
		this.addSprite(MAGE_DOWN, 4, 750);
		this.setStat("speed", 0.25f);
	}
	
	@Override
	public void step(float delta) {
		this.getStateMachine().next();

		if(this.getDirection().length() == 0) {
			this.getSprite().restart();
			this.getSprite().stop();
		} else {
			this.updateSprite();
		}
	}
	
	private void updateSprite() {
		double angle = this.getDirection().getAngle();

		boolean up = angle < Angle.TOP_LEFT.angle && angle > Angle.TOP_RIGHT.angle;
		boolean left = angle <= Angle.BOTTOM_LEFT.angle && angle >= Angle.TOP_LEFT.angle;
		boolean right = (angle >= 0 && angle <= Angle.TOP_RIGHT.angle)
				|| (angle < 0 && angle >= Angle.BOTTOM_RIGHT.angle);
		boolean down = angle < Angle.BOTTOM_RIGHT.angle && angle > Angle.BOTTOM_LEFT.angle;

		if (up) {
			setSprite(MAGE_UP);
			if(right)
				getSprite().flip(false);
			else if(left)
				getSprite().flip(true);
		} else if (right) {
			setSprite(MAGE_DOWN);
			getSprite().flip(false);
		} else if (down) {
			setSprite(MAGE_DOWN);
			if(right)
				getSprite().flip(false);
			else if(left)
				getSprite().flip(true);
		} else if (left) {
			setSprite(MAGE_DOWN);
			getSprite().flip(true);
		}
		
		this.getSprite().start();
	}
}
