package helix.game;

import helix.GameData;
import helix.utils.math.Point;
import helix.utils.math.Vector2;

public abstract class GameObject {

	private Point pos;
	
	private Vector2 direction;
	public final GameData gameData;
	
	public abstract void step();
	
	public GameObject(GameData gameData, Point pos) {
		this.pos = pos;
		this.gameData = gameData;
		this.direction = new Vector2(0, 0);
		gameData.objects.add(this);
	}
	
	public void update() {
		step();
	}
	
	// Getters and Setters
	public Point getPos() {
		return pos;
	}

	public void setPos(float x, float y) {
		this.pos.setX(x);
		this.pos.setY(y);
	}
	
	public void addPos(float x, float y) {
		this.setPos(this.getPos().getX() + x, this.getPos().getY() + y);
	}

	public Vector2 getDirection() {
		return direction;
	}
	
	public String toString() {
		return ("GameObject [pos=" + pos.toString() + ", "
				+ "direction=" + direction.toString() + ", "
				+ "]");
	}
}
