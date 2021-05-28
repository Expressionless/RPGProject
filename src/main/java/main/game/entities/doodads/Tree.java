package main.game.entities.doodads;

import com.badlogic.gdx.assets.AssetManager;

import helix.GameData;
import helix.game.objects.Entity;
import helix.utils.math.Point;

public class Tree extends Entity {
	public static final String TREE_REF = "res/sprites/doodads/tree.png";

	public Tree(GameData gameData, Point pos) {
		super(gameData, pos);
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
