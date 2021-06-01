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

	private Player player;
	private SpriteBatch batch;

	public GameScreen(RpgGame game) {
		super(game);
	}

	@Override
	public void create() {
		this.world = new World(32, 32);
		world.setGame(this.getRpgGame());

		player = new Player(getRpgGame(), new Point(30, 30));
		this.batch = new SpriteBatch();
		ItemSpawner is = new ItemSpawner(this.getRpgGame());
		is.spawnItem(50, 20, "grass", 5);
		is.spawnItem(50, 110, "wood", 5);
		is.spawnItem(50, 150, "wood", 5);
		is.spawnItem(50, 110, "wood", 5);
		is.spawnItem(50, 110, "wood", 5);
		is.spawnItem(50, 130, "bow");
		is.spawnItem(50, 160, "bow");
		is.spawnItem(50, 190, "bow");
		is.spawnItem(50, 220, "shaft");
		is.spawnItem(50, 250, "boOmerang");
		is.spawnItem(50, 250, "axe");
		is.spawnItem(50, 300, "pickaxe");
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
				Point p = getGameData().toGameCoords(screenX, screenY);
				getGameData().getCursor().getPos().setX(p.getX());// + screenX / Constants.RATIO_X);
				getGameData().getCursor().getPos().setY(p.getY());// - screenY / Constants.RATIO_Y);
				return true;
			}

			@Override
			public boolean touchDown(int screenX, int screenY, int pointer, int button) {
				InventoryCursor cursor = getGameData().getCursor();
				// TODO: Set inventory bounds and do a bounds check on that
				for (Slot[] slots : player.getInventory().getSlots()) {
					for (Slot slot : slots) {
						if (slot.isCursorOver()) {
							if(cursor.hasNothing() && !slot.isEmpty()) {
								cursor.take(slot);
							} else if(!cursor.hasNothing()) {
								if(slot.isEmpty())
									cursor.place(slot);
								else
									cursor.swap(slot);
							}
							
							// Interaction is done
							return true;
						}
					}
				}
				// if not over any slots place item on ground
				if(cursor.getItem() != null) {
					ItemSpawner is = new ItemSpawner(getRpgGame());
					is.spawnItem(cursor.getPos().copy(), cursor.getItem().ID, cursor.getAmount());
					
					cursor.setItem(null);
					return true;
				}
				
				// Interaction is done
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
