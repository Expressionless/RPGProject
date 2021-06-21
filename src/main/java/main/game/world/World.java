package main.game.world;

import helix.game.Serializable;
import helix.utils.io.BinaryReader;
import helix.utils.io.BinaryWriter;
import helix.utils.math.Point;
import main.constants.WorldConstants;
import main.game.RpgGame;
import main.game.entities.mobs.neutral.Player;

public final class World implements Serializable {

	private Chunk[][] chunks;
	
	private RpgGame game;
	
	private Player player;
	
	/**
	 * World origin in game coordinates
	 */
	private Point worldOrigin;
	
	public World(Point worldOrigin, int width, int height) {
		chunks = new Chunk[height][width];
		this.worldOrigin = worldOrigin;
		initChunks();
	}
	
	private void initChunks() {
		int x, y;
		int xPos = WorldConstants.CHUNK_WIDTH, yPos = WorldConstants.CHUNK_HEIGHT;
		
		for(y = 0; y < chunks.length; y++) {
			xPos += WorldConstants.CHUNK_WIDTH_PX;
			yPos += WorldConstants.CHUNK_HEIGHT_PX;
			for(x = 0; x < chunks[y].length; x++) {
				chunks[y][x] = new Chunk(this, xPos, yPos);
				
				
				if(this.game.getGameData().getCurrentWorld() != this)
					continue;
					
				chunks[y][x].setVisible(false);
			}
		}
	}
	
	// Getters and Setters
	public Chunk getChunk(int x, int y) {
		return chunks[y][x];
	}
	
	public RpgGame getGame() {
		return this.game;
	}
	
	public void setGame(RpgGame game) {
		this.game = game;
	}

	@Override
	public boolean write(BinaryWriter writer, int pos) {
		return false;
	}

	@Override
	public boolean parse(BinaryReader reader, int pos) {
		return false;
	}
}
