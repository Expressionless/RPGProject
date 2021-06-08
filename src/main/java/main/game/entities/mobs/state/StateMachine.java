package main.game.entities.mobs.state;

import java.util.HashMap;

public class StateMachine {
	private MobState currentState, defaultState, lastState;
	
	public final HashMap<MobState, StateEvent> events;
	
	public StateMachine() {
		this.events = new HashMap<>();
	}
	
	public StateMachine(MobState defaultState, StateEvent defaultEvent) {
		this.events = new HashMap<>();
		
		this.defaultState = defaultState;
		this.addState(defaultState, defaultEvent);
	}
	
	public void next() {
		if(this.lastState != null && this.currentState != this.lastState) System.out.println(this.toString());
		this.setState(this.events.get(currentState).call());
	}
	
	private boolean setState(MobState state) {
		if(state == null || this.currentState == state)
			return false;
		
		if(this.currentState != null) {
			if(this.lastState != null && this.lastState != this.currentState)
				this.lastState = this.currentState;
			else if(this.lastState == null)
				this.lastState = this.currentState;
		}
		this.currentState = state;
		
		//System.out.println("Set state to: " + this.currentState);
		return true;
	}
	
	private boolean setToLast() {
		return this.setState(this.lastState);
	}
	
	public boolean addState(MobState state, StateEvent event) {
		if(event == null)
			return false;
		if(state == null)
			return false;
		if(this.currentState == null)
			this.setState(state);
		
		this.events.put(state, event);
		
		return true;
	}
	
	@Override
	public String toString() {
		return "StateMachine [current=" + this.currentState.name() + 
				",last=" + this.lastState.name() + 
				((this.defaultState != null) ? ",default=" + this.defaultState.name() : "")
				+ "]";
	}
	
	public MobState getCurrentState() {
		return this.currentState;
	}
	
	public MobState getLastState() {
		return this.lastState;
	}
}