package main.game.entities.projectiles;

import helix.utils.math.Point;
import main.game.RpgGame;
import main.game.annotations.Damage;
import main.game.annotations.QueueAsset;
import main.game.entities.Projectile;
import main.game.entities.utils.AttackInfo;
import main.game.enums.DamageType;

@Damage(DamageType.BLUNT)
public class MageProjectile extends Projectile {
	@QueueAsset(ref="res/sprites/projectile/mage_ball.png")
	public static String SPRITE_REF;
	
	public MageProjectile(RpgGame game, AttackInfo attack, Point pos, Point destination) {
		super(game, attack, pos, destination);
	}

	@Override
	public DamageType getDamageType() {
		// TODO Auto-generated method stub
		return null;
	}

}
