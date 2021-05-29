package main.game.item;

import java.util.Random;

import com.badlogic.gdx.assets.AssetManager;

import helix.game.Data;
import helix.gfx.Sprite;
import helix.gfx.SpriteSheet;
import helix.utils.math.Point;
import main.Constants;
import main.game.RpgGame;
import main.game.entities.Doodad;
import main.game.inventory.Inventory;

public class Item extends Doodad {
	public static SpriteSheet ITEM_SHEET;

	private float animOffset;
	private int amount;

	@Override
	public void loadSprites(AssetManager manager) {
		// manager.load(Constants.ITEMS_DIRECTORY, Texture.class);
	}

	public final ItemType item;

	public Item(RpgGame game, Point pos, int itemID, int amount) {
		super(game, pos);
		this.item = ItemType.get(itemID);
		this.attachItemSprite();

		game.getGameData().items.add(this);

		this.animOffset = (float) (new Random().nextFloat() * (Constants.ITEM_BREATHE_LENGTH / 2 * Math.PI));
		this.amount = amount;
	}

	public Item(RpgGame game, Point pos, String itemName) {
		this(game, pos, ItemType.idOf(itemName), 1);
	}

	public static int[] IDtoImageIndex(int ID) {
		int items_width = ITEM_SHEET.getWidth(); // 16
		int items_height = ITEM_SHEET.getHeight(); // 16

		int x = ID % items_height; // x = 2
		int y = (int) Math.floor(ID / items_width); // y = 0

		return new int[] { x, y };
	}

	private void attachItemSprite() {
		if (this.getSprite() == null) {
			int[] index = IDtoImageIndex(this.getID());
			Sprite s = ITEM_SHEET.getSubSprite(index[0], index[1]).copy();
			s.setName(ItemType.nameOf(this.item.ID));
			this.setSprite(ITEM_SHEET.getSubSprite(index[0], index[1]));
		}

	}

	@Override
	public void step() {
		float XScale = (float) (Math
				.sin((double) (Data.TICKS + animOffset) / (double) (Constants.ITEM_BREATHE_LENGTH)) * 0.2 + 1);
		float YScale = XScale;
		// OSCILLATE BETWEEN 0.8 - 1.2
		if (this.getSprite() != null) {
			this.getSprite().setScale(XScale, YScale);
		}
		Inventory pInv = this.getGame().getGameData().getPlayer().getInventory();

		if (this.distTo(this.getGame().getGameData().getPlayer()) < Constants.ITEM_SUCK_DISTANCE) {
			if (pInv.add(item, amount, true)) {
				if (this.distTo(this.getGame().getGameData().getPlayer()) <= Constants.PICKUP_DISTANCE) {
					if (this.getGame().getGameData().getPlayer().getInventory().add(this.item, this.amount))
					this.dispose();
				}
				this.moveTo(this.getGame().getGameData().getPlayer(), Constants.ITEM_SPEED);
			}
		}
	}

	// Getters and Setters

	public int getID() {
		return this.item.ID;
	}

	public String getName() {
		return this.item.toString();
	}

	public String toString() {
		return "Item [name=" + item.toString() + ",ID=" + item.ID + ",pos=" + this.getPos().toString() + ",tex="
				+ ((this.getSprite() != null) ? this.getSprite().toString() : "null") + "]";
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}
}
