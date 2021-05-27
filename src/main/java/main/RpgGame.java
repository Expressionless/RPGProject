package main;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import helix.game.BaseGame;
import helix.utils.math.Point;
import main.game.entities.ItemType;
import main.game.entities.doodads.Tree;
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
	public void queueLoadAssets() {
		// Queue sprites to load
		this.getGameData().getManager().load(Constants.ITEMS_DIRECTORY, Texture.class);
		this.getGameData().getManager().load("res/sprites/player/right.png", Texture.class);
		this.getGameData().getManager().load("res/sprites/player/down.png", Texture.class);
		this.getGameData().getManager().load("res/sprites/player/up.png", Texture.class);

		this.getGameData().getManager().load("res/sprites/doodads/tree.png", Texture.class);
		this.getGameData().getManager().load("res/sprites/doodads/shrub.png", Texture.class);
	}

	@Override
	public void start() {

		this.getCamera().setToOrtho(false, frameWidth, frameHeight);
		this.batch = new SpriteBatch();
		viewport = new ExtendViewport(Constants.CAMERA_WIDTH, Constants.CAMERA_HEIGHT, this.getCamera());
		viewport.apply();
		
		this.getGameData().setViewport(viewport);

		new Player(this.getGameData(), new Point(30, 30));
		this.getGameData().spawnItem(new Point(30, 30), ItemType.WOOD.ID);
		this.getGameData().spawnItem(new Point(60, 90), "grASS");
		this.getGameData().spawnItem(new Point(140, 90), "Bow");
		this.getGameData().spawnItem(new Point(0, 20), "SWORD");
		new Tree(this.getGameData(), new Point(50, 50));
		// this.getGameData().createObject(Player.class, new Point(30, 30));

	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
	}

	@Override
	public void render() {
		ScreenUtils.clear(Constants.CLEAR_COLOR);

		// Follow the player
		if (this.getGameData() != null) {
			Player player = this.getGameData().getPlayer();
			Point playerPos = player.getPos();
			this.getCamera().position.set(playerPos.getX() + player.getWidth() / 2,
					playerPos.getY() + player.getHeight() / 2, 0);
		}
		this.getGameData().update();

		this.getCamera().update();

		this.batch.begin();
		this.batch.setProjectionMatrix(this.getCamera().combined);
		this.getGameData().render(batch);
		this.batch.end();
	}

}
