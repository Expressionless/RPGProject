package main.game.world;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import helix.game.Serializable;
import helix.utils.io.DataReader;
import helix.utils.io.DataWriter;
import helix.utils.math.Point;
import helix.utils.math.Rectangle;
import main.Constants;
import main.game.Entity;
import main.game.RpgGame;

public class Chunk implements Serializable {

	private Rectangle bounds;
	private ArrayList<Entity> entities;

	public Chunk(RpgGame game, Point pos) {
		this(game, pos.getX(), pos.getY());
	}

	public Chunk(RpgGame game, float x, float y) {
		this.bounds = new Rectangle(x, y, Constants.CHUNK_WIDTH * Constants.TILE_WIDTH,
				Constants.CHUNK_HEIGHT * Constants.TILE_HEIGHT);
		this.entities = new ArrayList<>();
	}

	public void step(float delta) {

	}

	public void draw(SpriteBatch batch) {

	}
	// Getters and Setters

	// Serialization
	@Override
	public boolean write(DataWriter writer, int pos) {
		return true;
	}

	@Override
	public boolean parse(DataReader reader, int pos) {
		return true;
	}

}
