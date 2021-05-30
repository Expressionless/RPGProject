package main.game.screens;

import com.badlogic.gdx.assets.AssetManager;

import helix.gfx.Screen;
import helix.utils.math.Point;
import main.game.RpgGame;
import main.game.entities.mobs.Player;

public class GameScreen extends Screen {
	
	public GameScreen(RpgGame game) {
		super(game);
	}

	@Override
	public void loadResources(AssetManager manager) {

	}

	@Override
	public void create() {
		new Player((RpgGame)game, new Point(30, 30));
	}

	@Override
	protected void step() {
		data.getCamera().position.x = ((RpgGame)game).getGameData().getPlayer().getPos().getX();
		data.getCamera().position.y = ((RpgGame)game).getGameData().getPlayer().getPos().getY();
	}

	@Override
	protected void draw(float delta) {
		
	}

	
	
}
