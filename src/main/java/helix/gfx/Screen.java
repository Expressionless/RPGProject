package helix.gfx;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;

public abstract class Screen extends ScreenAdapter {

	public abstract void loadResources(AssetManager manager);
	public abstract void create();
	
	@Override
	public void show() {
		this.create();
	}
}
