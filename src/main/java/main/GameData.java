package main;

import java.util.ArrayList;

import helix.game.Data;
import helix.gfx.SpriteSheet;
import helix.utils.math.Point;
import main.game.RpgGame;
import main.game.entities.Mob;
import main.game.entities.mobs.Player;
import main.game.item.Item;
import main.game.item.ItemInfo;
import main.game.item.ItemType;

public class GameData extends Data {
	
	public static final ArrayList<ItemType> ITEM_TYPES = new ArrayList<>();
	public final ArrayList<Mob> mobs;
	public final ArrayList<Item> items;
	
	// "Unique" Entities
	private Player player;
	private RpgGame game;

	@Override
	public void init() {
		Item.ITEM_SHEET = new SpriteSheet(this, main.Constants.ITEMS_DIRECTORY, 8, 8);
		parseItems();
	}
	
	public GameData(RpgGame game) {
		super();
		mobs = new ArrayList<>();
		items = new ArrayList<>();
		
		this.game = game;
	}
	
	public ItemType parseItemType(int position) {
		if(reader == null)
			return null;
		position *= Constants.ITEM_SIZE;

		log.fine("Parsing new item at: " + position);
		// Read in the ID
		int id = reader.getInt(position + Constants.ID_POS);
		log.fine("ID: "  + id);
		// Read in the name
		String name = reader.getString(position + Constants.NAME_POS, Constants.MAX_ITEM_NAME_LEN);
		log.fine("Name: " + name);
		// Read in maxStack
		int maxStack = reader.getInt(position + Constants.STACK_POS);
		log.fine("Stack: " + maxStack);
		// Read in the flags
		int flags = reader.getInt(position + Constants.FLAG_POS);
		log.fine("Flags: " + flags);
		return new ItemType(id, name, maxStack, flags);
	}
	
	public void addItem(ItemInfo info) {
		addItem(info.id, info.name, info.maxStack, info.flags);
	}
	
	public void addItem(int id, String name, int maxStack, boolean[] flags) {
		if(writer == null)
			return;
		writer.write(id);
		writer.write(name, Constants.MAX_ITEM_NAME_LEN);
		writer.write(maxStack);
		writer.writeBools(flags);
	}

	
	public void spawnItem(Point pos, int id, int amount) {
		new Item(game, pos, id, amount);
	}
	/**
	 * Spawn a single item of itemID: itemID with position: pos
	 * @param pos - {@link helix.utils.math.Point}
	 * @param itemID
	 */
	public void spawnItem(Point pos, int itemID) {
		this.spawnItem(pos, itemID, 1);
	}

	public void spawnItem(double x, double y, int itemID) {
		this.spawnItem(new Point(x, y), itemID);
	}
	
	public void spawnItem(double x, double y, int itemID, int amount) {
		this.spawnItem(new Point(x, y), itemID, amount);
	}
	
	public void spawnItem(Point pos, String name, int amount) {
		this.spawnItem(pos, ItemType.idOf(name), amount);
	}

	public void spawnItem(Point pos, String name) {
		this.spawnItem(pos, name, 1);
	}

	public void spawnItem(double x, double y, String name) {
		this.spawnItem(new Point(x, y), ItemType.idOf(name), 1);
	}
	
	public void spawnItem(double x, double y, String name, int amount) {
		this.spawnItem(new Point(x, y), ItemType.idOf(name), amount);
	}

	private void parseItems() {
		this.beginReading("/data/item");
		
		int numsToParse = 4;
		for(int i = 0; i < numsToParse; i++) {
			ItemType item = this.parseItemType(i);
			if(item == null)
				continue;
			ITEM_TYPES.add(item);
		}
		
		this.stopReading();
		
		System.out.println("Loaded: " + ITEM_TYPES.size() + " items");
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

}
