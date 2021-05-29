package main.game.entities.doodads;

import com.badlogic.gdx.assets.AssetManager;

import helix.utils.math.Point;
import main.game.RpgGame;
import main.game.entities.Doodad;

public class Tree extends Doodad {
	public static final String TREE_REF = "res/sprites/doodads/tree.png";

	public Tree(RpgGame game, Point pos) {
		super(game, pos);
		this.addSprite(TREE_REF);
		this.setSprite(TREE_REF);
	}

	@Override
	public void loadSprites(AssetManager manager) {

	}

	@Override
	public void step() {

	}

}
