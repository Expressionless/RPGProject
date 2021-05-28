package helix.game;

import helix.GameData;
import helix.game.objects.alarm.Alarm;
import helix.utils.math.Point;
import helix.utils.math.Vector2;

public abstract class GameObject {

	private Point pos;
	
	private Vector2 direction;
	public final GameData gameData;
	
	private Alarm[] alarm;

	private boolean shouldDispose;
	
	public abstract void step();
	
	public GameObject(GameData gameData, Point pos) {
		this.pos = pos;
		this.gameData = gameData;
		this.direction = new Vector2(0, 0);
		this.initAlarms();
		
		gameData.objects.add(this);
	}
	
	private void initAlarms() {
		alarm = new Alarm[Alarm.ALARM_COUNT];
		for(int i = 0; i < Alarm.ALARM_COUNT; i++) {
			alarm[i] = new Alarm();
		}
	}
	
	public void update() {
		step();
	}
	
	public void updateAlarms() {
		for(Alarm alarm : alarm) {
			alarm.update();
		}
	}
	
	@SuppressWarnings("unchecked")
	public <T extends GameObject> T find(Class<T> searchClass) {
		for(GameObject object : gameData.objects) {
			if(searchClass.isInstance(object))
				return (T)object;
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public <T extends GameObject> T findNearest(Class<T> searchClass) {
		GameObject current = null;
		for(GameObject object : gameData.objects) {
			if(!searchClass.isInstance(object))
				continue;
			if(current == null) {
				current = object;
				continue;
			}
			
			float dis1 = this.getPos().getDistTo(object.getPos());
			float dis2 = this.getPos().getDistTo(current.getPos());
			if(dis1 < dis2)
				current = object;
		}
		if(current != null)
			return (T)current;
		else return null;
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
	
	public boolean willDispose() {
		return shouldDispose;
	}
	
	public void dispose() {
		shouldDispose = true;
	}
}
