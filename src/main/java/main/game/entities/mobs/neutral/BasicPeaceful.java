package main.game.entities.mobs.neutral;

import helix.utils.io.DataReader;
import helix.utils.io.DataWriter;
import helix.utils.math.Point;
import main.game.RpgGame;
import main.game.entities.Mob;

public class BasicPeaceful extends Mob {

	public BasicPeaceful(RpgGame game, Point pos) {
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

	@Override
	protected boolean handleState(float delta) {switch(this.getState()) {
		case IDLE:
			//this.getAlarm(0).setAlarm(() -> {}, 0);
			// Mill about
			if(this.getDest() == null) {
				if(!this.getAlarm(0).isActive()) {
					this.setAlarm(0, () -> {
						float x = getPos().getX() + random.nextFloat() * moveRadius;
						float y = getPos().getY() + random.nextFloat() * moveRadius;
						
						setDest(new Point(x, y));
						
					}, TIMER_OFFSET + random.nextInt(TIMER_VARIATION));
				}
				
			} else {
				this.moveTo(this.getDest());
			}
			return true;
		case ATTACK:
			return true;
		case BLOCK:
			return true;
		case MOVE:
			return true;
		}
	return false;
	}

	@Override
	public void step(float delta) {
		
	}

}
