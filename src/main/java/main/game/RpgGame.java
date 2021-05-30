package main.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import helix.game.BaseGame;
import main.Constants;
import main.GameData;
import main.game.screens.GameScreen;

public class RpgGame extends BaseGame {

	private SpriteBatch batch;
	private Viewport viewport;

	private FPSLogger fps;

	public RpgGame() {
		super(Constants.TITLE, Constants.FRAME_WIDTH, Constants.FRAME_HEIGHT);

		config.setIdleFPS(Constants.IDLE_FPS);
		config.setForegroundFPS(Constants.TARGET_FPS);
		config.useVsync(true);
		config.setResizable(false);
		fps = new FPSLogger();
		this.setData(new GameData(this));
	}

	@Override
	protected void initScreens() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void queueLoadAssets() {
		// Queue sprites to load
		this.getGameData().getManager().load(Constants.ITEMS_DIRECTORY, Texture.class);
		this.getGameData().getManager().load("res/sprites/player/right.png", Texture.class);
		this.getGameData().getManager().load("res/sprites/player/down.png", Texture.class);
		this.getGameData().getManager().load("res/sprites/player/up.png", Texture.class);

		this.getGameData().getManager().load("res/sprites/doodads/tree.png", Texture.class);
		this.getGameData().getManager().load("res/sprites/doodads/shrub.png", Texture.class);

		this.getGameData().getManager().load("res/sprites/UI/inventory/slot.png", Texture.class);
	}

	@Override
	protected void start() {

		this.getCamera().setToOrtho(false, frameWidth, frameHeight);
		viewport = new ExtendViewport(Constants.CAMERA_WIDTH, Constants.CAMERA_HEIGHT, this.getCamera());
		viewport.apply();

		this.batch = new SpriteBatch();
		this.getGameData().setViewport(viewport);
		
		this.setScreen(new GameScreen(this));
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
	}

	@Override
	public void render() {

		ScreenUtils.clear(new Color(0, 0.25f, 0, 1));
		// Load anything there is to load
		// if nothing to load -> update the screen
		if(this.getGameData().getManager().update()) {	
			this.getScreen().render(Gdx.graphics.getDeltaTime());
		}
		
		fps.log();
		ScreenUtils.clear(Constants.CLEAR_COLOR);

		this.getGameData().update(Gdx.graphics.getDeltaTime());
		this.getCamera().update();

		this.batch.begin();
		this.batch.setProjectionMatrix(this.getCamera().combined);
		this.getGameData().render(batch);
		this.batch.end();
	}

	// Getters and Setters
	
	public GameData getGameData() {
		return (GameData) this.getData();
	}
	
	public RpgGame getGame() {
		return this;
	}

}
