package helix.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.OrthographicCamera;

public abstract class BaseGame extends Game {

	public final Lwjgl3ApplicationConfiguration config;
	public final int frameWidth, frameHeight;
	public final String title;
	
	private final GameData gameData;
	
	private OrthographicCamera camera;
	private float progress, lastProgress;
	
	public abstract void start();
	public abstract void queueLoadAssets();
	
	public BaseGame(String title, int frameWidth, int frameHeight) {
		config = new Lwjgl3ApplicationConfiguration();
		config.setTitle(title);
		config.setWindowedMode(frameWidth, frameHeight);
		
		this.title = title;
		this.frameWidth = frameWidth;
		this.frameHeight = frameHeight;
		
		camera = new OrthographicCamera();
		this.gameData = new GameData();
		this.gameData.setCamera(this.camera);
	}
	
	@Override
	public void create() {

		// Load loading screen stuff
		this.getGameData().getManager().finishLoading();
		this.load();
		
		this.gameData.init();
		this.start();
	}
	
	private void load() {	
		this.queueLoadAssets();
		
		// Load Resources
		while (!this.getGameData().getManager().update()) {
			
			// Check if progress got updated
			progress = this.getGameData().getManager().getProgress();
			if(progress != lastProgress) {
				System.out.println("loading: " + this.getGameData().getManager().getProgress());
				this.lastProgress = progress;
			}
		}
	}
	
	public OrthographicCamera getCamera() {
		return camera;
	}
	
	public GameData getGameData() {
		return gameData;
	}
}
