package main;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import helix.game.BaseGame;
import helix.utils.Point;
import main.game.entities.mobs.Player;

public class RpgGame extends BaseGame {

	private SpriteBatch batch;
	private Viewport viewport;
	
	public RpgGame() {
		super(Constants.TITLE, Constants.FRAME_WIDTH, Constants.FRAME_HEIGHT);
		
		config.setIdleFPS(Constants.IDLE_FPS);
		config.setForegroundFPS(Constants.TARGET_FPS);
		config.useVsync(true);
		config.setResizable(false);
	}


	@Override
	public void create() {
		// Load loading screen stuff
		this.getGameData().getManager().finishLoading();

		this.getGameData().getManager().load("res/sprites/player/right.png", Texture.class);
		this.getGameData().getManager().load("res/sprites/player/down.png", Texture.class);
		this.getGameData().getManager().load("res/sprites/player/up.png", Texture.class);

		this.getCamera().setToOrtho(false, frameWidth, frameHeight);
		while (!this.getGameData().getManager().update()) {
			System.out.println("loading: " + this.getGameData().getManager().getProgress());
		}
		
		this.batch = new SpriteBatch();
		viewport = new ExtendViewport(Constants.CAMERA_WIDTH, Constants.CAMERA_HEIGHT, this.getCamera());
		viewport.apply();
		
		new Player(this.getGameData(), new Point(30, 30));
		//gameData.createObject(Player.class, new Point(30, 30));
		
		
	}
	
	@Override
	public void resize(int width, int height) {
		viewport.update(width,  height);
		this.getCamera().position.set(0, 0, 0);
	}
	
	@Override
	public void render() {
		ScreenUtils.clear(Constants.CLEAR_COLOR);
		this.getCamera().update();
		this.getGameData().update();
		
		this.batch.begin();
		this.batch.setProjectionMatrix(this.getCamera().combined);
		this.getGameData().render(batch);
		this.batch.end();
	}
	

}
