package main.game.world;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import helix.game.Serializable;
import helix.utils.io.BinaryReader;
import helix.utils.io.BinaryWriter;
import helix.utils.math.Point;
import helix.utils.math.Rectangle;
import helix.utils.reflection.ClassUtils;
import main.constants.WorldConstants;
import main.game.Entity;
import main.game.RpgGame;
import main.game.entities.Mob;

public final class Chunk implements Serializable {

	private World world;

	private Rectangle bounds;

	private Tile[][] tiles;
	private ArrayList<Entity> entities;

	private final RpgGame game;

	private boolean visible;

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

	public void spawnMob(Class<Mob> clazz, Object... args) {
		Constructor<Mob> constructor = ClassUtils.getSpecificConstructor(clazz, args);
		
		try {
			Mob newMob = constructor.newInstance(args);
			System.out.println("created a new: " + newMob);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			if(e instanceof IllegalArgumentException) {
				System.err.println("Error spawning mob of type: " + clazz.getName());
			}
			e.printStackTrace();
		}
		
	}

	public void step(float delta) {
		while (entities.size() > WorldConstants.MAX_ENTITIES_PER_CHUNK)
			entities.remove(entities.get(entities.size() - 1));
	}

	public void draw(SpriteBatch batch) {

	}

	public Point getGameCoordinates(Point pos) {
		return null;
	}

	// Getters and Setters

	public void setVisible(boolean visible) {
		if (visible == this.isVisible())
			return;
		this.visible = visible;
		for (Tile[] row : tiles) {
			for (Tile tile : row)
				tile.setVisible(visible);
		}
	}

	public boolean isVisible() {
		return visible;
	}

	public Rectangle getBounds() {
		return bounds;
	}

	public void addEntity(Entity entity) {
		if (entities.size() >= WorldConstants.MAX_ENTITIES_PER_CHUNK)
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
