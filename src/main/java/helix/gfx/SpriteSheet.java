package helix.gfx;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import helix.game.GameData;

public class SpriteSheet {

	private TextureRegion[][] spriteTextures;
	private Sprite[][] sprites;
	private String ref;
	
	private int width, height;
	
	/**
	 * Create a SpriteSheet composed of tiles of Sprites
	 * @param data - {@link helix.game.GameData} object
	 * @param textureRef - String reference to the texture (must be loaded in AssetManager}
	 * @param tiles_x - width of each sprite in pixels
	 * @param tiles_y - height of each sprite in pixels
	 */
	public SpriteSheet(GameData data, String textureRef, int tiles_x, int tiles_y) {
		this.ref = textureRef;
		
		Texture texture = data.getManager().get(textureRef, Texture.class);
		this.spriteTextures = TextureRegion.split(texture, tiles_x, tiles_y);

		
		this.sprites = new Sprite[spriteTextures.length][spriteTextures[0].length];
		this.width = sprites.length;
		this.height = sprites[0].length;
		
		for(int i = 0; i < this.spriteTextures.length; i++) {
			TextureRegion[] row = this.spriteTextures[i];
			for(int j = 0; j < row.length; j++) {
				TextureRegion spriteRegion = this.spriteTextures[i][j];
				this.sprites[i][j] = new Sprite(spriteRegion);
			}
		}
	}
	/*
	 * Different implementation for Sprite Sheets
	 * @param x - x location of the subimage
	 * @param y - y location of the subimage
	 * @return
	 */
	public Sprite getSubSprite(int x, int y) {
		return sprites[y][x];
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public String toString() {
		return "SpriteSheet [ref=" + this.ref + ","
				+ "tiles=[w=" + this.getWidth() + ",h=" + this.getHeight() + "]"
				+ ",raw=" + (spriteTextures != null ? "[w=" + spriteTextures[0][0].getRegionWidth() +",h=" + spriteTextures[0][0].getRegionHeight() : "")
				+ "]";
	}

}
