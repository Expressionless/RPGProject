package main.game.screens;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

import helix.gfx.Screen;
import main.Constants;
import main.GameData;
import main.game.RpgGame;
import main.game.item.ItemType;

public class LoadScreen extends Screen {
	
	public LoadScreen(RpgGame game) {
		super(game);
	}

	@Override
	public void queueAssets(AssetManager manager) {
		manager.load("res/sprites/doodads/shrub.png", Texture.class);
		manager.load("res/sprites/doodads/tree.png", Texture.class);
		
		manager.load("res/sprites/player/down.png", Texture.class);
		manager.load("res/sprites/player/right.png", Texture.class);
		manager.load("res/sprites/player/up.png", Texture.class);
		
		manager.load("res/sprites/UI/inventory/slot.png", Texture.class);
		
		manager.load("res/sprites/items.png", Texture.class);
		manager.load("res/sprites/tiles.png", Texture.class);
	}

	@Override
	protected void create() {
		this.parseItems(this.getGameData());
		
		this.getRpgGame().setScreen(1);
	}

	@Override
	protected void step() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void draw(float delta) {
		// TODO Auto-generated method stub
		
	}

	private void parseItems(GameData gameData) {
		gameData.beginReading("/data/item");
		
		int itemsToParse = gameData.getReader().getBytes().size() / Constants.ITEM_SIZE;
		for(int i = 0; i < itemsToParse; i++) {
			ItemType item = new ItemType();
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
