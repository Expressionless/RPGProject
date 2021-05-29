package helix.game;

import helix.game.objects.alarm.Alarm;
import helix.utils.math.Point;
import helix.utils.math.Vector2;
import main.Constants;

public abstract class GameObject {
	private final Data data;
	
	private Alarm[] alarm;
	private boolean shouldDispose;

	private Point pos;
	private Vector2 direction;
	
	protected void preStep() {}
	protected abstract void step();
	protected void postStep() {}
	
	public GameObject(Data data, Point pos) {
		this.pos = pos;
		this.data = data;
		this.direction = new Vector2(0, 0);
		this.initAlarms();
		
		data.objects.add(this);
	}
	
	private void initAlarms() {
		alarm = new Alarm[Alarm.ALARM_COUNT];
		for(int i = 0; i < Alarm.ALARM_COUNT; i++) {
			alarm[i] = new Alarm();
		}
	}

	public void update() {
		
		preStep();
		step();
		postStep();
	}
	
	public void updateAlarms() {
		for(Alarm alarm : alarm) {
			alarm.update();
		}
	}
	
	@SuppressWarnings("unchecked")
	public <T extends GameObject> T find(Class<T> searchClass) {
		for(GameObject object : data.objects) {
			if(searchClass.isInstance(object))
				return (T)object;
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public <T extends GameObject> T findNearest(Class<T> searchClass) {
		GameObject current = null;
		for(GameObject object : data.objects) {
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
	
	public float distTo(GameObject other) {
		return this.getPos().sub(other.getPos()).length();
	}
	
	public void move() {
		this.move(this.getDirection(), 1);
	}
	
	public void move(float speed) {
		this.move(this.getDirection(), speed);
	}
	
	public void move(Vector2 direction) {
		this.move(direction, 1);
	}
	
	public void move(Vector2 direction, float speed) {
		direction = direction.getUnitVector();
		this.setPos(getPos().add(direction.multiply(speed)));
	}
	
	public void moveTo(GameObject target) {
		this.moveTo(target, Constants.DEFAULT_SPEED);
	}
	
	public void moveTo(GameObject target, float speed) {
		Vector2 dir = this.getPos().sub(target.getPos()).toVector2();
		this.move(dir, -speed);
	}
	
	// Getters and Setters
	public Point getPos() {
		return pos;
	}
	
	public void setPos(Point other) {
		this.pos = other.copy();
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
	
	public Data getData() {
		return data;
	}
}
