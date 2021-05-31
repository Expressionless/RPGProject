package helix.game.objects.alarm;

public final class Alarm {
	public static final int ALARM_COUNT = 10;
	public static final int NO_ALARM = -1;

	private Event action;
	private int timer = NO_ALARM, startTime;
	
	private float second = 0;
	
	public Alarm() {
		this.action = null;
	}
	
	public Alarm(Event action, int timer) {
		this.action = action;
		this.timer = timer;
	}
	
	public void update(float delta) {
		if(second >= 1f) {
			if(timer > 0) {
				timer--;
			} else if (timer == 0) {
				action.event();
				timer = NO_ALARM;
			}
			second = 0;
		} else second += delta;
	}
	
	public float percent() {
		if(isActive() && startTime != 0)
			return (1f - ((float)timer / (float)startTime));
		else return 0f;
	}
	
	public void setAlarm(Event action, int timer) {
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
		return (timer != NO_ALARM);
	}
	
}
