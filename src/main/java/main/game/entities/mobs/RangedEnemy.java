package main.game.entities.mobs;

import java.lang.reflect.InvocationTargetException;

import helix.utils.math.Point;
import main.game.RpgGame;
import main.game.annotations.Damage;
import main.game.entities.Projectile;
import main.game.entities.utils.AttackInfo;
import main.game.enums.DamageType;

public abstract class RangedEnemy<T extends Projectile> extends Enemy {
	private static final int SHOOT_ALARM = 1;

	private final Class<T> projectileType;

	private boolean can_shoot = true;
	
	public RangedEnemy(RpgGame game, Class<T> projectileClass, Point pos) {
		super(game, pos);
		
		this.projectileType = projectileClass;
		
		System.out.println("Damage Type: " + projectileType.getAnnotation(Damage.class).value());
	}
	
	public boolean fireProjectile(float damage, Point destination) {
		if(!can_shoot) {
			this.setAlarm(SHOOT_ALARM, (int)(1f / this.getStat("attack_speed")), () -> {
				can_shoot = true;
			});
			return false;
		} else {
			Projectile proj = createProjectile(damage, destination);
			can_shoot = false;
			return true;
		}
	}
	
	private Projectile createProjectile(float damage, Point destination) {
		
		DamageType projectileDamageType = projectileType.getAnnotation(Damage.class).value();
		System.out.println(projectileDamageType);
		
		AttackInfo attack = new AttackInfo(projectileDamageType, damage);
		try {
			return projectileType.getDeclaredConstructor(RpgGame.class, AttackInfo.class, Point.class, Point.class).newInstance(this.getGame(), attack, this.getPos(), destination);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			System.err.println("Error creating projectile from: " + projectileType.getCanonicalName());
			e.printStackTrace();
			return null;
		}
	}
}
