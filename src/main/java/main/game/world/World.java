package main.game.world;

public class World {

	private Chunk[][] chunks;
	
	public World(int width, int height) {
		chunks = new Chunk[height][width];
	}
	
	// Getters and Setters
	public Chunk getChunk(int x, int y) {
		return chunks[y][x];
	}
}
