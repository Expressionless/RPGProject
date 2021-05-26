package helix.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.OrthographicCamera;

import helix.GameData;

public abstract class BaseGame extends Game {

	public final int frameWidth, frameHeight;
	public final String title;
	
	private GameData gameData;
	
	public Lwjgl3ApplicationConfiguration config;
	
	private OrthographicCamera camera;
	
	public BaseGame(String title, int frameWidth, int frameHeight) {
		config = new Lwjgl3ApplicationConfiguration();
		config.setTitle(title);
		config.setWindowedMode(frameWidth, frameHeight);
		
		this.title = title;
		this.frameWidth = frameWidth;
		this.frameHeight = frameHeight;
		
		camera = new OrthographicCamera();
		this.gameData = new GameData();
	}
	
	public OrthographicCamera getCamera() {
		return camera;
	}
	
	public GameData getGameData() {
		return gameData;
	}
}
