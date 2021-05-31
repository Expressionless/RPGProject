package main.game.entities.doodads;


import com.badlogic.gdx.assets.AssetManager;

import helix.utils.math.Point;
import main.Constants;
import main.game.RpgGame;
import main.game.entities.Doodad;

public class Tree extends Doodad {
	public static final String TREE_REF = "res/sprites/doodads/tree.png";
	
	private float growtimer;
	
	public Tree(RpgGame game, Point pos) {
		super(game, pos);
		this.addSprite(TREE_REF);
		this.setSprite(TREE_REF);
		this.growtimer = Constants.TREE_GROWTH_CYCLE;
		this.getSprite().setScale(1, 0.5f);
		this.getAlarm(0).setAlarm(() -> {
			
		}, (int)growtimer);
	}

	@Override
	public void loadSprites(AssetManager manager) {
		
	}

	@Override
	public void step(float delta) {
		this.getSprite().setScale(1, 1.0f - (float)(this.getAlarm(0).getTimer()) / growtimer * 0.5f);
	}

}
