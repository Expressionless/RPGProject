package helix.game.objects;

import java.util.HashMap;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import helix.game.Data;
import helix.game.GameObject;
import helix.game.objects.entity.Collider;
import helix.gfx.Sprite;
import helix.utils.math.Point;

public abstract class Entity extends GameObject {
	public static final BitmapFont font = new BitmapFont();
	public static final ShapeRenderer renderer = new ShapeRenderer();

	private float depth;
	
	private HashMap<String, Sprite> sprites;
	private Sprite currentSprite;
	
	private Collider collider;


	public Entity(Data data, Point pos) {
		super(data, pos);

		this.sprites = new HashMap<String, Sprite>();
		this.getData().entities.add(this);
		this.currentSprite = null;
		this.collider = new Collider(this);
	}
	
	public void loadSprites(AssetManager manager) {};

	@Override
	protected void preStep(float delta) {
		this.depth = this.getPos().getY();
	}
	
	public void render(SpriteBatch batch) {
		if (currentSprite != null) {
			currentSprite.draw(batch, this.getPos().getX(), this.getPos().getY());
		}
		
		draw(batch);
	}

	protected void draw(SpriteBatch batch) {
	}

	public final boolean addSprite(String spriteName, int numFrames) {
		return this.addSprite(spriteName, numFrames, -1);
	}

	public final boolean addSprite(String spriteName) {
		return this.addSprite(spriteName, 1);
	}

	public final boolean addSprite(String spriteName, int numFrames, int duration) {
		if (sprites.containsKey(spriteName))
			return false;

		sprites.put(spriteName, this.getData().createSprite(spriteName, numFrames, duration));
		if (this.currentSprite == null)
			this.setSprite(spriteName);
		return true;
	}
	
	@SuppressWarnings("unchecked")
	public final <T extends Entity> T findEntity(Class<T> searchClass) {
		for(GameObject object : this.getData().entities) {
			if(searchClass.isInstance(object))
				return (T)object;
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public final <T extends Entity> T findNearestEntity(Class<T> searchClass) {
		GameObject current = null;
		for(GameObject object : this.getData().entities) {
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

	// Getters and Setters
	
	/**
	 * Set time in milliseconds
	 * 
	 * @param time (time in millis)
	 */
	public final void setCurrentAnimSpeed(float time) {
		this.currentSprite.getAnimation().setAnimTime(time);
	}

	/**
	 * Set the current Sprite
	 * 
	 * @param spriteName
	 */
	public final void setSprite(String spriteName) {
		if (this.currentSprite == null || !this.currentSprite.equals(sprites.get(spriteName)))
			this.currentSprite = sprites.get(spriteName);
	}

	public final void setSprite(Sprite s) {
		sprites.put(s.getName(), s);
		this.currentSprite = s;
	}

	/**
	 * Return the current Sprite
	 * 
	 * @return currentSprite
	 */
	public final Sprite getSprite() {
		return currentSprite;
	}

	public final float getWidth() {
		if(this.currentSprite != null)
			return currentSprite.getWidth();
		else return 0;
	}

	public final float getHeight() {
		if(this.currentSprite != null)
			return currentSprite.getHeight();
		else return 0;
	}
	
	public final float getDepth() {
		return depth;
	}
	
	public final Collider getCollider() {
		return collider;
	}
}
