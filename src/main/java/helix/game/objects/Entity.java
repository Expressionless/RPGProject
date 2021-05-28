package helix.game.objects;

import java.util.HashMap;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import helix.game.GameData;
import helix.game.GameObject;
import helix.game.objects.entity.Collider;
import helix.gfx.Sprite;
import helix.utils.math.Point;
import main.game.entities.Item;
import main.game.item.ItemType;

public abstract class Entity extends GameObject {
	public static final BitmapFont font = new BitmapFont();
	public static final ShapeRenderer renderer = new ShapeRenderer();

	private float depth;
	
	private HashMap<String, Sprite> sprites;
	private Sprite currentSprite;
	
	private Collider collider;

	public abstract void loadSprites(AssetManager manager);

	public Entity(GameData gameData, Point pos) {
		super(gameData, pos);

		this.sprites = new HashMap<String, Sprite>();
		this.gameData.entities.add(this);
		this.currentSprite = null;
	}

	@Override
	public void update() {
		this.depth = this.getPos().getY();
		this.collider = new Collider(this);
		this.step();
	}
	
	public void render(SpriteBatch batch) {
		if (currentSprite != null) {
			currentSprite.draw(batch, this.getPos().getX(), this.getPos().getY());
		}
		
		draw(batch);
	}

	public void draw(SpriteBatch batch) {
	}

	public boolean addSprite(String spriteName, int numFrames) {
		return this.addSprite(spriteName, numFrames, -1);
	}

	public boolean addSprite(String spriteName) {
		return this.addSprite(spriteName, 1);
	}

	public boolean addSprite(String spriteName, int numFrames, int duration) {
		if (sprites.containsKey(spriteName))
			return false;

		sprites.put(spriteName, gameData.createSprite(spriteName, numFrames, duration));
		if (this.currentSprite == null)
			this.setSprite(spriteName);
		return true;
	}
	
	@SuppressWarnings("unchecked")
	public <T extends Entity> T findEntity(Class<T> searchClass) {
		for(GameObject object : gameData.entities) {
			if(searchClass.isInstance(object))
				return (T)object;
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public <T extends Entity> T findNearestEntity(Class<T> searchClass) {
		GameObject current = null;
		for(GameObject object : gameData.entities) {
			if(!searchClass.isInstance(object))
				continue;
			if(current == null) {
				current = object;
				continue;
			}
			
			float dis1 = this.getPos().getDistTo(object.getPos());
			float dis2 = this.getPos().getDistTo(current.getPos());
			if(dis1 < dis2)
				current = object;
		}
		if(current != null)
			return (T)current;
		else return null;
	}
	
	public Item findItem(String name) {
		return this.findItem(ItemType.idOf(name));
	}
	
	public Item findItem(int ID) {
		for(Item object : gameData.items) {
			if(Item.class.isInstance(object) && object.getID() == ID)
				return object;
		}
		return null;
	}
	
	public Item findNearestItem() {
		Item current = null;
		
		for(Item object : gameData.items) {
			if(current == null) {
				current = object;
				continue;
			}
			
			float dis1 = this.getPos().getDistTo(object.getPos());
			float dis2 = this.getPos().getDistTo(current.getPos());
			if(dis1 < dis2)
				current = object;
		}
		
		if(current != null)
			return current;
		else return null;
	}
	
	public Item findNearestItem(String name) {
		return this.findNearestItem(ItemType.idOf(name));
	}
	
	public Item findNearestItem(int ID) {
		Item current = null;
		
		for(Item object : gameData.items) {
			if(object.getID() != ID)
				continue;
				
			if(current == null) {
				current = object;
				continue;
			}
			
			float dis1 = this.getPos().getDistTo(object.getPos());
			float dis2 = this.getPos().getDistTo(current.getPos());
			if(dis1 < dis2)
				current = object;
		}
		
		if(current != null)
			return current;
		else return null;
	}
	

	// Getters and Setters
	
	/**
	 * Set time in milliseconds
	 * 
	 * @param time (time in millis)
	 */
	public void setCurrentAnimSpeed(float time) {
		this.currentSprite.getAnimation().setAnimTime(time);
	}

	/**
	 * Set the current Sprite
	 * 
	 * @param spriteName
	 */
	public void setSprite(String spriteName) {
		if (this.currentSprite == null || !this.currentSprite.equals(sprites.get(spriteName)))
			this.currentSprite = sprites.get(spriteName);
	}

	public void setSprite(Sprite s) {
		sprites.put(s.getName(), s);
		this.currentSprite = s;
	}

	/**
	 * Return the current Sprite
	 * 
	 * @return currentSprite
	 */
	public Sprite getSprite() {
		return currentSprite;
	}

	public float getWidth() {
		return currentSprite.getWidth();
	}

	public float getHeight() {
		return currentSprite.getHeight();
	}
	
	public float getDepth() {
		return depth;
	}
	
	public Collider getCollider() {
		return collider;
	}
}
