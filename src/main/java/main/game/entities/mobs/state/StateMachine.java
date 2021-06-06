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
		this.setState(this.events.get(currentState).call());
	}
	
	private boolean setState(MobState state) {
		if(state == null)
			return false;
		
		if(this.currentState != null)
			this.lastState = this.currentState;
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
}