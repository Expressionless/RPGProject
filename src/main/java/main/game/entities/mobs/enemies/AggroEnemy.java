package main.game.entities.mobs.enemies;

import helix.utils.math.Point;
import main.game.RpgGame;
import main.game.entities.mobs.Enemy;

public class AggroEnemy extends Enemy {

	public AggroEnemy(RpgGame game, Point pos) {
		super(game, pos);
	}

	@Override
	protected boolean handleState(float delta) {
		return false;
	}

	@Override
	public void step(float delta) {
		
	}

}
