package helix.game;

import com.badlogic.gdx.assets.AssetManager;

import helix.GameData;
import helix.gfx.Sprite;
import helix.gfx.SpriteSheet;
import helix.utils.math.Point;
import main.game.entities.ItemType;

public class Item extends Entity {
	public static SpriteSheet ITEM_SHEET;

	@Override
	public void loadSprites(AssetManager manager) {
		// manager.load(Constants.ITEMS_DIRECTORY, Texture.class);
	}

	public final ItemType item;

	public Item(GameData gameData, Point pos, int itemID) {
		super(gameData, pos);
		this.item = ItemType.get(itemID);
		this.attachItemSprite();

		this.gameData.items.add(this);
	}

	public Item(GameData gameData, Point pos, String itemName) {
		this(gameData, pos, ItemType.idOf(itemName));
	}

	private int[] IDtoImageIndex() {
		int items_width = ITEM_SHEET.getWidth(); // 16
		int items_height = ITEM_SHEET.getHeight(); // 16
		// this.itemID = 2
		int y = (int) Math.floor(this.item.ID / items_width); // y = 0
		int x = this.item.ID % items_height; // x = 2

		return new int[] { x, y};
	}

	private void attachItemSprite() {
		if (this.getSprite() == null) {
			int[] index = this.IDtoImageIndex();
			Sprite s = ITEM_SHEET.getSubSprite(index[0], index[1]);
			s.setName(ItemType.nameOf(this.item.ID));
			this.setSprite(ITEM_SHEET.getSubSprite(index[0], index[1]));
		}

	}

	@Override
	public void step() {
		float XScale = (float) (Math.sin((double) GameData.TICKS / (double) (1000)) * 0.4 + 1);
		float YScale = XScale;
		// OSCILLATE BETWEEN 0.8 - 1.2
		if (this.getSprite() != null) {
			this.getSprite().setScale(XScale, YScale);
		}
	}

	public String toString() {
		return "Item [name=" + item.toString() + ",ID=" + item.ID + ",pos=" + this.getPos().toString() + ",tex="
				+ ((this.getSprite() != null) ? this.getSprite().toString() : "null") + "]";
	}
}
