package main;

import java.util.ArrayList;

import helix.game.Data;
import helix.gfx.SpriteSheet;
import main.game.RpgGame;
import main.game.entities.Mob;
import main.game.entities.mobs.Player;
import main.game.inventory.Slot;
import main.game.item.Item;
import main.game.item.ItemType;

public class GameData extends Data {
	
	public static final ArrayList<ItemType> ITEM_TYPES = new ArrayList<>();
	public final ArrayList<Mob> mobs;
	public final ArrayList<Item> items;
	
	// "Unique" Entities
	private Player player;

	@Override
	public void init() {
		// Init inventory data
		Slot.SPRITE = this.createSprite("res/sprites/UI/inventory/slot.png");
		Slot.inventoryFont.getData().setScale(0.25f);
		
		// Init Item Data
		Item.ITEM_SHEET = new SpriteSheet(this, main.Constants.ITEMS_DIRECTORY, 8, 8);
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
	
	// Getters and Setters

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
	
	public RpgGame getGame() {
		return (RpgGame)this.getBaseGame();
	}

}
