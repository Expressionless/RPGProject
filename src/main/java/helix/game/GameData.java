package helix.game;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.logging.Logger;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.Viewport;

import helix.game.objects.Entity;
import helix.gfx.Animation;
import helix.gfx.Sprite;
import helix.gfx.SpriteSheet;
import helix.utils.io.DataReader;
import helix.utils.io.DataWriter;
import helix.utils.math.Point;
import main.Constants;
import main.game.entities.Mob;
import main.game.entities.mobs.Player;
import main.game.item.Item;
import main.game.item.ItemInfo;
import main.game.item.ItemType;

public class GameData {
	private static Logger log = Logger.getLogger(GameData.class.getCanonicalName());

	public static int TICKS = 0;
	public static final ArrayList<ItemType> ITEM_TYPES = new ArrayList<>();
	
	public final ArrayList<GameObject> objects;
	public final ArrayList<Entity> entities;
	public final ArrayList<Mob> mobs;
	public final ArrayList<Item> items;
		
	private DataReader reader;
	private static DataWriter writer;

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
		parseItems();
	}

	private void createItemsSheet() {
		Item.ITEM_SHEET = new SpriteSheet(this, main.Constants.ITEMS_DIRECTORY, 8, 8);
	}
	
	private void parseItems() {
		this.beginReading();
		
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

	public Sprite createSprite(String spriteName, int frameCount, float animTime) {
		Texture texture = manager.get(spriteName);
		TextureRegion region = new TextureRegion(texture);
		Animation anim = new Animation(region, spriteName, frameCount, animTime);

		return new Sprite(anim);

	}

	public Sprite createSprite(String spriteName, int frameCount) {
		return this.createSprite(spriteName, frameCount, helix.Constants.NO_ANIM);
	}

	public Sprite createSprite(String spriteName) {
		return this.createSprite(spriteName, helix.Constants.DEF_FRAMES);
	}

	private void disposeObjects() {
		items.removeIf((Item item) -> {
			return item.willDispose();
		});
		
		entities.removeIf((Entity entity) -> {
			return entity.willDispose();
		});

		mobs.removeIf((Mob mob) -> {
			return mob.willDispose();
		});
		
		objects.removeIf((GameObject obj) -> {
			return obj.willDispose();
		});
	}
	
	public void update() {
		TICKS++;
		
		this.disposeObjects();
		
		entities.sort(new Comparator<Entity>() {

			@Override
			public int compare(Entity e1, Entity e2) {
				return (int)Math.signum(e2.getDepth() - e1.getDepth());
			}
			
		});
		
		for (GameObject object : objects) {
			object.updateAlarms();
			object.update();
		}
	}

	public void render(SpriteBatch batch) {
		for (Entity entity : entities) {
			entity.render(batch);
		}
	}
	


	public void beginReading() {
		if(writer != null)
			return;
		if(reader != null)
			return;
		
		reader = new DataReader("/data/item");
	}
	
	public void stopReading() {
		if(reader == null)
			return;
		reader = null;
	}
	
	
	public void beginWriting() {
		if(writer != null)
			return;
		if(reader != null)
			return;
		
		writer = new DataWriter("/data/item");
	}
	
	public void stopWriting() {
		if(writer == null)
			return;
		
		writer.close();
		writer = null;
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

	public void spawnItem(Point pos, int id, int amount) {
		new Item(this, pos, id, amount);
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

	public boolean reading() {
		return (reader != null);
	}
	
	public boolean writing() {
		return (writer != null);
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
