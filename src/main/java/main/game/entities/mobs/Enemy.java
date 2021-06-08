package main.game.entities.mobs;

import helix.utils.math.Point;
import main.game.RpgGame;

public abstract class Enemy extends StateBasedMob {

	private String type;
	
	public Enemy(RpgGame game, Point pos) {
		super(game, pos); 
	}
	
	// Getters and Setters
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
}
