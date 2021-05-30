package helix.game.objects.alarm;

public final class Alarm {
	public static final int ALARM_COUNT = 10;
	public static final int ALARM_INACTIVE = -1;

	private Event action;
	private int timer = ALARM_INACTIVE, startTime;
	
	public Alarm() {
		this.action = null;
	}
	
	public Alarm(Event action, int timer) {
		this.action = action;
		this.timer = timer;
	}
	
	public void update() {
		if(timer > 0) {
			timer--;
		} else if (timer == 0) {
			action.event();
			timer = ALARM_INACTIVE;
		}
	}
	
	public float percent() {
		if(isActive() && startTime != 0)
			return (1f - ((float)timer / (float)startTime));
		else return 0f;
	}
	
	public void setAction(Event action, int timer) {
		this.action = action;
		this.setTimer(timer);
	}
	
	public void setTimer(int timer) {
		this.timer = timer;
		this.startTime = timer;
	}
	
	public int getTimer() {
		return timer;
	}
	
	public boolean isActive() {
		return (timer != ALARM_INACTIVE);
	}
	
}
