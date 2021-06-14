package main.game.world;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import helix.game.Serializable;
import helix.utils.io.BinaryReader;
import helix.utils.io.BinaryWriter;
import helix.utils.math.Point;
import helix.utils.math.Rectangle;
import main.constants.WorldConstants;
import main.game.Entity;
import main.game.RpgGame;

public final class Chunk implements Serializable {

	private World world;
	
	private Rectangle bounds;
	
	private Tile[][] tiles;
	private ArrayList<Entity> entities;
	
	private final RpgGame game;
	
	public Chunk(World world, Point pos) {
		this(world, pos.getX(), pos.getY());
	}

	public Chunk(World world, float x, float y) {
		tiles = new Tile[WorldConstants.CHUNK_HEIGHT][WorldConstants.CHUNK_WIDTH];
		
		this.bounds = new Rectangle(x, y, tiles[0].length * WorldConstants.TILE_WIDTH,
				tiles.length * WorldConstants.TILE_HEIGHT);
		this.entities = new ArrayList<>();
		
		this.game = world.getGame();
		this.world = world;
	}

	public void step(float delta) {
		while(entities.size() > WorldConstants.MAX_ENTITIES_PER_CHUNK)
			entities.remove(entities.get(entities.size() - 1));
	}

	public void draw(SpriteBatch batch) {

	}
	
	public Point getGameCoordinates(Point pos) {
		
	}
	
	// Getters and Setters
	
	public Rectangle getBounds() {
		return bounds;
	}
	
	public void addEntity(Entity entity) {
		if(entities.size() >= WorldConstants.MAX_ENTITIES_PER_CHUNK)
			return;
		entities.add(entity);
	}
	
	public RpgGame getGame() {
		return game;
	}
	
	// Serialization
	@Override
	public boolean write(BinaryWriter writer, int pos) {
		return true;
	}

	@Override
	public boolean parse(BinaryReader reader, int pos) {
		return true;
	}

}
