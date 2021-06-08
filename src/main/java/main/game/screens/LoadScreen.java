package main.game.screens;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

import helix.gfx.Screen;
import main.GameData;
import main.constants.SerializationConstants;
import main.game.RpgGame;
import main.game.item.ItemInfo;

public class LoadScreen extends Screen {
	
	public LoadScreen(RpgGame game) {
		super(game);
	}

	@Override
	public void queueAssets(AssetManager manager) {
		manager.load("res/sprites/doodads/shrub.png", Texture.class);
		manager.load("res/sprites/doodads/tree.png", Texture.class);
		
		manager.load("res/sprites/mob/player/down.png", Texture.class);
		manager.load("res/sprites/mob/player/right.png", Texture.class);
		manager.load("res/sprites/mob/player/up.png", Texture.class);

		manager.load("res/sprites/mob/enemy/tiny_mage_right.png", Texture.class);
		manager.load("res/sprites/mob/enemy/tiny_mage_up_right.png", Texture.class);

		manager.load("res/sprites/UI/inventory/slot.png", Texture.class);
		manager.load("res/sprites/UI/inventory/selector.png", Texture.class);
		
		manager.load("res/sprites/items.png", Texture.class);
		manager.load("res/sprites/tiles.png", Texture.class);

		manager.load("res/sprites/UI/bar/bar.png", Texture.class);
		manager.load("res/sprites/UI/bar/bar_display.png", Texture.class);
	}

	@Override
	public void show() {
		this.parseItems(this.getGameData());
		
		this.getRpgGame().setScreen(1);
	}

	@Override
	protected void step(float delta) {
		
	}

	@Override
	protected void draw(float delta) {
		
	}

	private void parseItems(GameData gameData) {
		gameData.beginReading("/data/item");
		
		int itemsToParse = gameData.getReader().getBytes().size() / SerializationConstants.ITEM_SIZE;
		for(int i = 0; i < itemsToParse; i++) {
			ItemInfo item = new ItemInfo();
			item.parse(gameData.getReader(), i);
			System.out.println(item.toString());
			GameData.ITEM_TYPES.add(item);
		}
		
		gameData.stopReading();
		System.out.println("Loaded: " + GameData.ITEM_TYPES.size() + " items");
	}
	
	// Getters and Setters
	
	private RpgGame getRpgGame() {
		return (RpgGame)this.getGame();
	}
	
	private GameData getGameData() {
		return this.getRpgGame().getGameData();
	}

}
