package main.game.world;

import com.badlogic.gdx.graphics.Texture;

import helix.game.Serializable;
import helix.gfx.Sprite;
import helix.gfx.SpriteSheet;
import helix.utils.io.BinaryReader;
import helix.utils.io.BinaryWriter;
import helix.utils.math.Point;
import main.game.Entity;
import main.game.annotations.QueueAsset;

public class Tile extends Entity implements Serializable {
	
	@QueueAsset(ref="res/sprites/tiles.png", type=Texture.class)
	public static String TILE_SHEET_REF;
	public static SpriteSheet TILE_SHEET;
	
	private Chunk chunk;
	private Sprite tileSprite;
	
	Tile(Chunk chunk, int tileX, int tileY, int tileId) {
		super(chunk.getGame(), chunk.getGameCoordinates(new Point(tileX, tileY)));
		
		int[] subcoords = TILE_SHEET.getImageCoords(tileId);
		
		this.chunk = chunk;
		this.tileSprite = TILE_SHEET.getSubSprite(subcoords[0], subcoords[1]);
	}

	@Override
	public void step(float delta) {
		// TODO Auto-generated method stub
		
	}
	
	// Serialization
	
	@Override
	public boolean write(BinaryWriter writer, int pos) {
		return false;
	}

	@Override
	public boolean parse(BinaryReader reader, int pos) {
		return false;
	}
}
