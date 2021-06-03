package main.game.entities.mobs;

import helix.utils.math.Point;
import main.game.RpgGame;
import main.game.entities.Mob;

public abstract class Enemy extends Mob {

	private String type;
	
	private Mob target;
	private Point destination;
	
	public Enemy(RpgGame game, Point pos) {
		super(game, pos);
		destination = pos.copy();
	}
	
	// Getters and Setters
	
	public Mob getTarget() {
		return target;
	}
	
	public void setTarget(Mob mob) {
		this.target = mob;
	}
	
	public Point getDest() {
		return destination;
	}
	
	public void setDest(Point point) {
		this.destination = point;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
}
