package main.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import helix.gfx.Screen;
import helix.utils.math.Point;
import main.Constants;
import main.GameData;
import main.game.RpgGame;
import main.game.entities.doodads.Tree;
import main.game.entities.mobs.Player;
import main.game.inventory.InventoryCursor;
import main.game.inventory.Slot;
import main.game.item.ItemSpawner;
import main.game.world.World;

public final class GameScreen extends Screen {

	private World world;

	private InventoryCursor cursor;
	private Player player;
	private SpriteBatch batch;

	public GameScreen(RpgGame game) {
		super(game);
	}

	@Override
	public void create() {
		player = new Player(getRpgGame(), new Point(30, 30));
		cursor = new InventoryCursor(this.getRpgGame(), new Point(0, 0));
		this.batch = new SpriteBatch();
		ItemSpawner is = new ItemSpawner(this.getRpgGame());
		is.spawnItem(50, 20, "grass", 5);
		is.spawnItem(50, 110, "wood", 5);
		is.spawnItem(50, 130, "bow");
		is.spawnItem(50, 160, "bow");
		is.spawnItem(50, 190, "bow");
		is.spawnItem(50, 220, "bow");
		new Tree(getRpgGame(), new Point(100, 80));
	}

	@Override
	protected void step() {
		this.handleInput();
		this.updateCamera();
	}

	@Override
	protected void draw(float delta) {
		this.getGameData().update(Gdx.graphics.getDeltaTime());
		this.getGameData().getCamera().update();

		this.batch.begin();
		this.batch.setProjectionMatrix(this.getGameData().getCamera().combined);
		this.getGameData().render(batch);
		this.batch.end();
	}

	private void updateCamera() {
		// Update Camera
		getData().getCamera().position.x = player.getPos().getX();
		getData().getCamera().position.y = player.getPos().getY();
	}

	private void handleInput() {
		Gdx.input.setInputProcessor(new InputAdapter() {

			@Override
			public boolean keyDown(int keycode) {
				switch (keycode) {
				case Constants.KEY_INV:
					if (player.getInventory().isVisible()) {
						player.getInventory().setVisible(false);
						player.getHotbar().setVisible(false);
					} else {
						player.getInventory().setVisible(true);
						player.getHotbar().setVisible(true);
					}
					break;
				case Constants.KEY_DOWN:
					player.setMovement(Constants.DOWN, true);
					break;
				case Constants.KEY_RIGHT:
					player.setMovement(Constants.RIGHT, true);
					break;
				case Constants.KEY_LEFT:
					player.setMovement(Constants.LEFT, true);
					break;
				case Constants.KEY_UP:
					player.setMovement(Constants.UP, true);
					break;
				}
				return true;
			}

			@Override
			public boolean keyUp(int keycode) {
				switch (keycode) {
				case Constants.KEY_DOWN:
					player.setMovement(Constants.DOWN, false);
					break;
				case Constants.KEY_RIGHT:
					player.setMovement(Constants.RIGHT, false);
					break;
				case Constants.KEY_LEFT:
					player.setMovement(Constants.LEFT, false);
					break;
				case Constants.KEY_UP:
					player.setMovement(Constants.UP, false);
					break;
				}
				return true;
			}

			@Override
			public boolean mouseMoved(int screenX, int screenY) {
				cursor.getPos().setX(screenX);
				cursor.getPos().setY(screenY);
				return true;
			}

			@Override
			public boolean touchDown(int screenX, int screenY, int pointer, int button) {
				for (Slot[] slots : player.getInventory().getSlots()) {
					for (Slot slot : slots) {
						if (slot.getBounds().contains(cursor.getPos())) {
							cursor.take(slot);
						}
					}
				}
				System.out.println("Coords: " + cursor.getPos().toString());
				return true;
			}
		});
	}

	// Getters and Setters
	public RpgGame getRpgGame() {
		return (RpgGame) getGame();
	}

	public GameData getGameData() {
		return (GameData) getGame().getData();
	}

	// Unimplemented Overrides
	@Override
	public void queueAssets(AssetManager manager) {

	}
}
