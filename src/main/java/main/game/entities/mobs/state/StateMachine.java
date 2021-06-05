package main.game.entities.mobs.state;

import java.util.ArrayList;

public class StateMachine {
	public MobState currentState, lastState;
	
	public final ArrayList<StateEvent> events;
	
	public StateMachine(MobState initialState) {
		this.events = new ArrayList<>();
	}
	
	public StateEvent next() {
		return new StateEvent() {

			@Override
			public void call() {
				
			}
			
		};
	}
	
	public void addState(StateEvent event) {
		
	}
}