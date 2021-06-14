package main.game;

import helix.utils.math.Point;
import main.GameData;
import main.game.entities.mobs.neutral.Player;
import main.game.world.Chunk;
import main.game.world.World;

public abstract class Entity extends helix.game.objects.Entity {

	private final RpgGame game;
	private World world;
	private Chunk chunk;
	
	public Chunk getChunk() {
		return chunk;
	}

	public void setChunk(Chunk chunk) {
		this.chunk = chunk;
	}

	public Entity(RpgGame game, Point pos) {
		super(game.getData(), pos);
		this.game = game;
	}
	
	public RpgGame getGame() {
		return game;
	}
	
	public GameData getGameData() {
		return this.game.getGameData();
	}
	
	public Player getPlayer() {
		return this.getGameData().getPlayer();
	}
	
	public void setWorld(World world) {
		this.world = world;
	}
	
	public World getWorld() {
		return this.world;
	}
}
