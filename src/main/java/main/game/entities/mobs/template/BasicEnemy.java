package main.game.entities.mobs.template;

import java.util.Random;

import helix.utils.io.DataReader;
import helix.utils.io.DataWriter;
import helix.utils.math.Point;
import main.Constants;
import main.game.RpgGame;
import main.game.entities.mobs.Enemy;
import main.game.entities.mobs.state.MobState;

public abstract class BasicEnemy extends Enemy {

	private float moveRadius = 32;

	private Random random;

	public BasicEnemy(RpgGame game, Point pos) {
		super(game, pos);

		random = new Random();
		
		this.initStates();
	}

	private void initStates() {

		this.getStateMachine().addState(MobState.IDLE, () -> {
			if(!this.getAlarm(0).isActive()) {
				this.setAlarm(0, () -> {
					int xDir = -1 + random.nextInt(2);
					if(xDir == 0)
						xDir = 1;
					
					int yDir = -1 + random.nextInt(2);
					if(yDir == 0)
						yDir = 1;
					
					float x = getPos().getX() + random.nextFloat() * moveRadius * xDir;
					float y = getPos().getY() + random.nextFloat() * moveRadius * yDir;

					this.setDest(new Point(x, y));
				}, 3);
				return MobState.MOVE;
			} else return MobState.IDLE;
		});
		
		this.getStateMachine().addState(MobState.MOVE, () -> {
			if(this.getDest() == null)
				return MobState.IDLE;
			else {
				if(this.getPos().getDistTo(this.getDest()) <= Constants.ITEM_SUCK_DISTANCE) {
					this.setDest(null);
				} else
					this.moveTo(this.getDest(), this.getStat("speed"));
				return MobState.MOVE;
			}
		});
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
