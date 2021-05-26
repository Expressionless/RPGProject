package main.game;

import java.util.HashMap;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import helix.GameData;
import helix.game.GameObject;
import helix.gfx.Sprite;
import helix.utils.Point;

public abstract class Entity extends GameObject {
	
	private HashMap<String, Sprite> sprites;
	private Sprite currentSprite;
	
	public abstract void draw();
	
	public Entity(GameData gameData, Point pos) {
		super(gameData, pos);
		
		this.sprites = new HashMap<String, Sprite>();
		this.gameData.entities.add(this);
		this.currentSprite = null;
	}
	
	public void render(SpriteBatch batch) {
		if(currentSprite != null) {
			currentSprite.draw(batch, this.getPos().getX(), this.getPos().getY());
		}
		draw();
	}
	
	public boolean addSprite(String spriteName, int numFrames) {
		return this.addSprite(spriteName, numFrames, -1);
	}
	
	public boolean addSprite(String spriteName, int numFrames, int duration) {
		if(sprites.containsKey(spriteName))
			return false;
		
		sprites.put(spriteName, gameData.createSprite(spriteName, numFrames, duration));
		if(this.currentSprite == null)
			this.setSprite(spriteName);
		return true;
	}
	/**
	 * Set time in milliseconds
	 * @param time (time in millis)
	 */
	public void setCurrentAnimSpeed(float time) {
		this.currentSprite.getAnimation().setAnimTime(time);
	}

	/**
	 * Set the current Sprite
	 * @param spriteName
	 */
	public void setSprite(String spriteName) {
		if(this.currentSprite == null || !this.currentSprite.equals(sprites.get(spriteName)))
			this.currentSprite = sprites.get(spriteName);
	}
	
	/**
	 * Return the current Sprite
	 * @return currentSprite
	 */
	public Sprite getSprite() {
		return currentSprite;
	}
}
