package main.game.inventory;

import helix.utils.math.Point;
import main.game.RpgGame;
import main.game.item.ItemType;

public class ArmourInventory extends Inventory {

	public ArmourInventory(RpgGame game, Point screenPos) {
		super(game, screenPos, 1, 4);
		this.clearAllowedTypes();
		this.addAllowedTypes("ARMOUR");
	}

	@Override
	public Inventory copy() {
		// TODO Auto-generated method stub
		return null;
	}

}
