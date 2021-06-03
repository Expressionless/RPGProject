package main.game.entities.mobs.enemies;

import java.util.Random;

import helix.utils.math.Point;
import main.game.RpgGame;
import main.game.entities.mobs.Enemy;

public class BasicEnemy extends Enemy {

	private float moveRadius = 32;
	private final int TIMER_VARIATION = 4, TIMER_OFFSET = 2;
	
	private Random random;
	
	public BasicEnemy(RpgGame game, Point pos) {
		super(game, pos);
		
		random = new Random();
	}

	@Override
	protected boolean handleState(float delta) {
		switch(this.getState()) {
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
