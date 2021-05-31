package main;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector3;

import helix.game.Data;
import helix.gfx.SpriteSheet;
import helix.utils.math.Point;
import main.game.RpgGame;
import main.game.entities.Mob;
import main.game.entities.mobs.Player;
import main.game.inventory.InventoryCursor;
import main.game.inventory.Slot;
import main.game.item.Item;
import main.game.item.ItemType;

public final class GameData extends Data {
	
	public static final ArrayList<ItemType> ITEM_TYPES = new ArrayList<>();
	public final ArrayList<Mob> mobs;
	public final ArrayList<Item> items;
	
	// "Unique" Entities
	private Player player;
	private InventoryCursor cursor;

	@Override
	public void init() {
		// Init inventory data
		Slot.SPRITE = this.createSprite("res/sprites/UI/inventory/slot.png");
		Slot.inventoryFont.getData().setScale(0.25f);
		
		// Init Item Data
		Item.ITEM_SHEET = new SpriteSheet(this, main.Constants.ITEMS_DIRECTORY, 8, 8);
		
		cursor = new InventoryCursor(this.getGame());
	}
	
	public GameData(RpgGame game) {
		super(game);
		mobs = new ArrayList<>();
		items = new ArrayList<>();
	}

	@Override
	protected void dispose() {
		items.removeIf((Item item) -> {
			return item.willDispose();
		});
		mobs.removeIf((Mob mob) -> {
			return mob.willDispose();
		});
	}
	
	/**
	 * Convert UI Coordinates to a position in the game
	 * @param x - X coord
	 * @param y - Y Coord
	 * @return
	 */
	public Point toScreenCoords(float x, float y) {
		Vector3 camPos = this.getCamera().position;
		x = (x - camPos.x + Constants.CAMERA_WIDTH / 2) * Constants.RATIO_X;
		y = (y - camPos.y - Constants.CAMERA_HEIGHT / 2) * (-Constants.RATIO_Y);
		return new Point(x, y);
	}

	/**
	 * Convert Game Coordinates to UI Coordinates
	 * @param x
	 * @param y
	 * @return
	 */
	public Point toGameCoords(float x, float y) {
		Vector3 camPos = this.getCamera().position;
		x = camPos.x - Constants.CAMERA_WIDTH / 2 + (x / Constants.RATIO_X);
		y = camPos.y + Constants.CAMERA_HEIGHT / 2 + (-y / Constants.RATIO_Y);
		return new Point(x, y);
	}
	
	// Getters and Setters

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
	
	public InventoryCursor getCursor() {
		return cursor;
	}

	public void setCursor(InventoryCursor cursor) {
		this.cursor = cursor;
	}

	public RpgGame getGame() {
		return (RpgGame)this.getBaseGame();
	}

}
