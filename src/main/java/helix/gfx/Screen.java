package helix.gfx;

import com.badlogic.gdx.ScreenAdapter;

import helix.game.BaseGame;
import helix.game.Data;

/**
 * Basic implementation of {@link ScreenAdapater}
 * Contains references to {@link Data} and {@link BaseGame}
 * @author bmeachem
 *
 */
public abstract class Screen extends ScreenAdapter {
	
	/**
	 * Basic Step event of the screen. Called before {@link Screen#draw}
	 * @param delta - Time since last update (seconds)
	 */
	protected abstract void step(float delta);
	
	/**
	 * Basic Draw event of the screen. Called after {@link Screen#step}
	 * @param delta - Time since last update (seconds)
	 */
	protected abstract void draw(float delta);
	
	/**
	 * reference to main {@link Data}
	 */
	private Data data;
	
	/**
	 * reference to main {@link BaseGame}
	 */
	private BaseGame game;
	
	/**
	 * Create a new Screen and link it to a {@link BaseGame}
	 * @param game - Game to link to 
	 */
	public Screen(BaseGame game) {
		this.game = game;
		this.data = game.getData();
	}
	
	@Override
	public void render(float delta) {
		this.step(delta);
		this.draw(delta);
	}
	
	// Getters and Setters
	public BaseGame getGame()  {
		return game;
	}
	
	public Data getData() {
		return data;
	}
	
}
