package main.game.inventory;

import helix.utils.math.Point;
import main.game.RpgGame;

public class GenericInventory extends Inventory {

	public GenericInventory(RpgGame game, Point screenPos, int w, int h) {
		super(game, screenPos, w, h);
		this.resetAllowedTypes();
	}

	@Override
	public GenericInventory copy() {
		return new GenericInventory(this.getGame(), this.getPos(), this.getWidth(), this.getHeight());
	}

}
