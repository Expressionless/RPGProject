package main.game.entities.mobs.enemies;

import helix.utils.io.DataReader;
import helix.utils.io.DataWriter;
import helix.utils.math.Point;
import main.game.RpgGame;
import main.game.entities.mobs.Enemy;

public class RangedEnemy extends Enemy {

	public RangedEnemy(RpgGame game, Point pos) {
		super(game, pos);
	}

	@Override
	protected boolean handleState(float delta) {
		return false;
	}

	@Override
	public void step(float delta) {
		
	}

	@Override
	public boolean write(DataWriter writer, int pos) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean parse(DataReader reader, int pos) {
		// TODO Auto-generated method stub
		return false;
	}

}
