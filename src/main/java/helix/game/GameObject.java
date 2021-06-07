package helix.game;

import helix.Constants;
import helix.game.objects.alarm.Alarm;
import helix.game.objects.alarm.Event;
import helix.utils.math.Point;
import helix.utils.math.Vector2;

public abstract class GameObject {
	public static int ID_NEXT = 0;

	public final int id;
	
	private Alarm[] alarm;
	private boolean shouldDispose;

	private Point pos;
	private Vector2 direction;

	private final Data data;
	
	protected void preStep(float delta) {}
	public abstract void step(float delta);
	protected void postStep(float delta) {}
	
	public GameObject(Data data, Point pos) {
		this.pos = pos;
		this.data = data;
		this.direction = new Vector2(0, 0);
		this.initAlarms();
		
		this.id = ID_NEXT++;
		data.objects.add(this);
	}
	
	private void initAlarms() {
		alarm = new Alarm[Alarm.ALARM_COUNT];
		for(int i = 0; i < Alarm.ALARM_COUNT; i++) {
			alarm[i] = new Alarm();
		}
	}

	public final void update(float delta) {
		preStep(delta);
		step(delta);
		postStep(delta);
	}
	
	public final void updateAlarms(float delta) {
		for(Alarm alarm : alarm) {
			alarm.update(delta);
		}
	}
	
	@SuppressWarnings("unchecked")
	public final <T extends GameObject> T find(Class<T> searchClass) {
		for(GameObject object : data.objects) {
			if(searchClass.isInstance(object))
				return (T)object;
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public final <T extends GameObject> T findNearest(Class<T> searchClass) {
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
	
	public final float distTo(GameObject other) {
		return this.getPos().sub(other.getPos()).length();
	}

	public final void move(float speed) {
		this.setPos(getPos().add(direction.multiply(speed)));
	}
	
	public final void moveTo(Point point, float speed) {
		this.direction = point.sub(this.getPos()).toVector2().getUnitVector();
		this.move(speed);
	}
	
	public final void moveTo(GameObject target, float speed) {
		this.moveTo(target.getPos(), speed);
	}
	
	public final void moveTo(Point point) {
		this.moveTo(point, Constants.DEFAULT_SPEED);
	}
	
	public final void moveTo(GameObject target) {
		this.moveTo(target, Constants.DEFAULT_SPEED);
	}
	
	
	// Getters and Setters
	public Point getPos() {
		return pos;
	}
	
	public final void setPos(Point other) {
		this.pos = other.copy();
	}

	public final void setPos(float x, float y) {
		this.pos.setX(x);
		this.pos.setY(y);
	}
	
	public final void addPos(float x, float y) {
		this.setPos(this.getPos().getX() + x, this.getPos().getY() + y);
	}

	public final Vector2 getDirection() {
		return direction;
	}
	
	public final void setDirection(Vector2 dir) {
		this.direction = dir;
	}
	
	public String toString() {
		return ("GameObject [pos=" + pos.toString() + ", "
				+ "direction=" + direction.toString() + ", "
				+ "]");
	}
	
	public final boolean willDispose() {
		return shouldDispose;
	}
	
	public final void dispose() {
		shouldDispose = true;
	}
	
	public final Data getData() {
		return data;
	}
	
	public final Alarm getAlarm(int index) {
		return alarm[index];
	}
	
	public final void setAlarm(int index, Event action, int timer) {
		this.getAlarm(0).setAlarm(action, timer);
	}
}
