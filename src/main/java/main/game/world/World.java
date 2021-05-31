package main.game.world;

import main.game.RpgGame;

public class World {

	private Chunk[][] chunks;
	
	private RpgGame game;
	
	public World(int width, int height) {
		chunks = new Chunk[height][width];
	}
	
	private void initChunks() {
		int x, y;
		int xPos, yPos;
		
		for(y = 0; y < chunks.length; y++) {
			for(x = 0; x < chunks[y].length; x++) {
				
				chunks[y][x] = new Chunk(game, x, y);
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
}
