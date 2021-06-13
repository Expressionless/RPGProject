package main.game.entities.mobs.ai;

import main.GameData;
import main.game.entities.Mob;
import main.game.entities.mobs.ai.state.MobState;
import main.game.entities.mobs.neutral.Player;

public final class ChaseAI extends AI {

	private static final int SEARCH_ALARM = 0;
	
	private Player player;
	private float distToPlayer;
	
	private Mob target;
	
	private GameData gameData;
	
	public ChaseAI(Mob mob) {
		super(mob);
		
		this.gameData = mob.getGameData();
	}

	@Override
	protected void initStates() {
		this.addState(MobState.IDLE, () -> {
			if(target == null) {
				if(distToPlayer < mob.getStat("sight")) {
					target = player;
					return MobState.CHASE;
				} else {
					
					// Insert IDLE move code here
					
				}
			} else return MobState.CHASE;
			
			return MobState.IDLE;
		});
		
		this.addState(MobState.SEARCHING, () -> {
			// If no target, go back to being idle
			if(this.target == null)
				return MobState.IDLE;
			
			// If ran out of search time, go back to being idle
			if(!mob.getAlarm(SEARCH_ALARM).isActive()) {
				this.target = null;
				return MobState.IDLE;
			}
			
			float distToTarget = mob.getPos().getDistTo(this.target.getPos());
			
			if(distToTarget < mob.getStat("sight")) {
				mob.getAlarm(SEARCH_ALARM).cancel();
				return MobState.CHASE;
			}
			
			return MobState.SEARCHING;
		});
		
		this.addState(MobState.CHASE, () -> {
			if(target == null)
				return MobState.IDLE;
			
			float distToTarget = mob.getPos().getDistTo(target.getPos());
			
			if(distToTarget > mob.getStat("sight")) {
				this.search();
				return MobState.SEARCHING;
			} else {
				mob.moveTo(target.getPos());
				return MobState.CHASE;
			}
		});
	}
	
	private void search() {
		mob.setAlarm(2, (int)mob.getStat("search_time"), () -> {
			this.target = null;
		});
	}

	@Override
	protected void step() {
		if(player == null)
			player = this.gameData.getPlayer();
		distToPlayer = mob.getPos().getDistTo(player.getPos());
	}

}
