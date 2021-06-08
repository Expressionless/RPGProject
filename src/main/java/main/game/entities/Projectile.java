package main.game.entities;

import helix.utils.math.Point;
import main.game.Entity;
import main.game.RpgGame;
import main.game.entities.utils.AttackInfo;
import main.game.enums.DamageType;

public abstract class Projectile extends Entity {

	public final AttackInfo attack;
	public final Point destination;
	
	public abstract DamageType getDamageType();
	
	public Projectile(RpgGame game, AttackInfo attack, Point pos, Point destination) {
		super(game, pos);
		
		this.destination = destination;
		this.attack = attack;
	}

	@Override
	public void step(float delta) {
		float distRemaining = this.getPos().getDistTo(destination);
		if(distRemaining < this.getWidth() / 2)
			this.dispose();
		else
			this.moveTo(destination);
	}
	
	@SuppressWarnings("unused")
	private class ProjectileFlags extends AttribHandler<Object> {
		private DamageType damage_type;
		
		
		public void setDamageType(DamageType type) {
			this.damage_type = type;
		}
	}
}
