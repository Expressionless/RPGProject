package helix.gfx;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;

import helix.game.BaseGame;
import helix.game.Data;

public abstract class Screen extends ScreenAdapter {

	protected abstract void loadResources(AssetManager manager);
	protected abstract void create();
	protected abstract void step();
	protected abstract void draw(float delta);
	
	protected Data data;
	protected BaseGame game;
	
	public Screen(BaseGame game) {
		this.game = game;
		this.data = game.getData();
	}
	
	@Override
	public void show() {
		this.create();
	}
	
	@Override
	public void render(float delta) {
		this.step();
		this.draw(delta);
	}
	
	
}
