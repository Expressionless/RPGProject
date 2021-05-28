package helix;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Comparator;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.Viewport;

import helix.game.Entity;
import helix.game.GameObject;
import helix.game.Item;
import helix.gfx.Animation;
import helix.gfx.Sprite;
import helix.gfx.SpriteSheet;
import helix.utils.math.Point;
import main.Constants;
import main.game.entities.ItemType;
import main.game.entities.Mob;
import main.game.entities.mobs.Player;

public class GameData {

	public static int TICKS = 0;
	public final ArrayList<GameObject> objects;
	public final ArrayList<Entity> entities;
	public final ArrayList<Mob> mobs;
	public final ArrayList<Item> items;

	private AssetManager manager;
	private Viewport viewport;
	private Camera camera;

	// "Unique" Entities
	private Player player;

	public GameData() {
		manager = new AssetManager();
		entities = new ArrayList<>();
		objects = new ArrayList<>();
		mobs = new ArrayList<>();
		items = new ArrayList<>();
		
	}

	/**
	 * To be called in {@link helix.game.BaseGame#create()}
	 */
	public void init() {
		createItemsSheet();
	}

	private void createItemsSheet() {
		Item.ITEM_SHEET = new SpriteSheet(this, Constants.ITEMS_DIRECTORY, 8, 8);
	}

	public Sprite createSprite(String spriteName, int frameCount, float animTime) {
		Texture texture = manager.get(spriteName);
		TextureRegion region = new TextureRegion(texture);
		Animation anim = new Animation(region, spriteName, frameCount, animTime);

		return new Sprite(anim);

	}

	public Sprite createSprite(String spriteName, int frameCount) {
		return this.createSprite(spriteName, frameCount, Constants.NO_ANIM);
	}

	public Sprite createSprite(String spriteName) {
		return this.createSprite(spriteName, Constants.DEF_FRAMES);
	}

	public void update() {
		TICKS++;
		entities.sort(new Comparator<Entity>() {

			@Override
			public int compare(Entity e1, Entity e2) {
				return (int)Math.signum(e2.getDepth() - e1.getDepth());
			}
			
		});
		
		for (GameObject object : objects) {
			object.update();
		}
	}

	public void render(SpriteBatch batch) {
		for (Entity entity : entities) {
			entity.render(batch);
		}
	}

	public void spawnItem(Point pos, int itemID) {
		new Item(this, pos, itemID);
	}

	public void spawnItem(double x, double y, int itemID) {
		this.spawnItem(new Point(x, y), itemID);
	}

	public void spawnItem(Point pos, String name) {
		this.spawnItem(pos, ItemType.idOf(name));
	}

	public void spawnItem(double x, double y, String name) {
		this.spawnItem(new Point(x, y), name);
	}

	public <T extends GameObject> T createObject(Class<T> objectClass, Object... args) {
		Constructor<T> constructor;
		try {
			constructor = objectClass.getDeclaredConstructor(new Class[] { GameData.class, Point.class });
		} catch (NoSuchMethodException | SecurityException e1) {
			e1.printStackTrace();
			return null;
		}

		try {
			return constructor.newInstance(this, (Object[]) args);	
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public AssetManager getManager() {
		return manager;
	}

	public Viewport getViewport() {
		return viewport;
	}

	public void setViewport(Viewport viewport) {
		this.viewport = viewport;
	}

	public Camera getCamera() {
		return camera;
	}

	public void setCamera(Camera camera) {
		this.camera = camera;
	}
	
	
}
