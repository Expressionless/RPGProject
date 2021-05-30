package main.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.assets.AssetManager;

import helix.gfx.Screen;
import helix.utils.math.Point;
import main.Constants;
import main.GameData;
import main.game.RpgGame;
import main.game.entities.mobs.Player;

public final class GameScreen extends Screen {
	
	public GameScreen(RpgGame game) {
		super(game);
	}

	@Override
	public void loadResources(AssetManager manager) {

	}

	@Override
	public void create() {
		new Player(getRpgGame(), new Point(30, 30));
		getRpgGame().getGameData().spawnItem(50, 80, "dildo", 5);
		getRpgGame().getGameData().spawnItem(50, 20, "grass", 5);
		getRpgGame().getGameData().spawnItem(50, 110, "wood", 5);
		getRpgGame().getGameData().spawnItem(50, 130, "bow");
		getRpgGame().getGameData().spawnItem(50, 160, "bow");
		getRpgGame().getGameData().spawnItem(50, 190, "bow");
		getRpgGame().getGameData().spawnItem(50, 220, "bow");
	}

	@Override
	protected void step() {
		// Update Camera
		data.getCamera().position.x = getRpgGame().getGameData().getPlayer().getPos().getX();
		data.getCamera().position.y = getRpgGame().getGameData().getPlayer().getPos().getY();

		Player player = this.getRpgGame().getGameData().getPlayer();
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
				// getPos().setX(screenX);
				// getPos().setY(screenY);
				return true;
			}

			@Override
			public boolean touchDown(int screenX, int screenY, int pointer, int button) {

				System.out.println("Coords: " + screenX + " " + screenY);
				return true;
			}
		});
	}

	@Override
	protected void draw(float delta) {

	}

	// Getters and Setters
	public RpgGame getRpgGame() {
		return (RpgGame) game;
	}

	public GameData getGameData() {
		return (GameData) data;
	}
}
