package main.game.entities.mobs.template;

import helix.utils.io.DataReader;
import helix.utils.io.DataWriter;
import helix.utils.math.Point;
import main.game.RpgGame;
import main.game.entities.mobs.Enemy;

public abstract class RangedEnemy extends Enemy {

	public RangedEnemy(RpgGame game, Point pos) {
		super(game, pos);
	}

	@Override
	public boolean write(DataWriter writer, int pos) {
		return false;
	}

	@Override
	public boolean parse(DataReader reader, int pos) {
		return false;
	}

}
