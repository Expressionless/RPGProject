package helix.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.OrthographicCamera;


public abstract class BaseGame extends Game {

	public final Lwjgl3ApplicationConfiguration config;
	public final int frameWidth, frameHeight;
	public final String title;
	
	private Data data;
	
	private OrthographicCamera camera;
	private float progress, lastProgress;
	
	protected abstract void start();
	protected abstract void addScreens();
	
	public BaseGame(String title, int frameWidth, int frameHeight) {
		config = new Lwjgl3ApplicationConfiguration();
		config.setTitle(title);
		config.setWindowedMode(frameWidth, frameHeight);

		config.setIdleFPS(0);
		config.setForegroundFPS(0);
		config.useVsync(true);
		config.setResizable(false);
		
		this.title = title;
		this.frameWidth = frameWidth;
		this.frameHeight = frameHeight;
		
		camera = new OrthographicCamera();
	}
	
	@Override
	public final void create() {
		this.addScreens();

		// Load loading screen stuff
		this.getData().getManager().finishLoading();
		this.getData().setCamera(camera);

		this.queueAssets();
		this.loadTextures();
		this.data.init();
		
		try {
			this.setScreen(this.getData().screens.get(0));
		} catch(NullPointerException e) {
			
		}
		
		this.start();
	}
	
	private void loadTextures() {
		// Load Resources
		while (!this.getData().getManager().update()) {
			// Check if progress got updated
			progress = this.getData().getManager().getProgress();
			if(progress != lastProgress) {
				System.out.println("loading: " + this.getData().getManager().getProgress());
				this.lastProgress = progress;
			}
		}
		
	}
	
	private void queueAssets() {
		for(int i = 0; i < this.getData().screens.size(); i++) {
			this.getData().screens.get(i).queueAssets(this.getData().getManager());
		}
		
		
	}
	
	// Getters and Setters
	
	public final OrthographicCamera getCamera() {
		return camera;
	}
	
	public final Data getData() {
		return data;
	}
	
	public final void setData(Data data) {
		this.data = data;
	}
	
	@Override
	public void setScreen(Screen s) {
		super.setScreen(s);
	}
}
