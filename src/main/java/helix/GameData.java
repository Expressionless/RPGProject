package helix;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import helix.game.GameObject;
import helix.gfx.Animation;
import helix.gfx.Sprite;
import helix.utils.Point;
import main.game.Entity;
import main.game.entities.Mob;

public class GameData {

	private AssetManager manager;
	
	public final ArrayList<GameObject> objects;
	public final ArrayList<Entity> entities;
	public final ArrayList<Mob> mobs;
	
	public GameData() {
		manager = new AssetManager();
		entities = new ArrayList<>();
		objects = new ArrayList<>();
		mobs = new ArrayList<>();
	}
	
	public Sprite createSprite(String spriteName, int frameCount, float animTime) {
		Texture texture = manager.get(spriteName);
		TextureRegion region = new TextureRegion(texture);
		Animation anim = new Animation(region, spriteName, frameCount, animTime);

		return new Sprite(anim);
		
	}
	
	public void update() {
		for(GameObject object : objects) {
			object.update();
		}
	}
	
	public void render(SpriteBatch batch) {
		for(Entity entity : entities) {
			entity.render(batch);
		}
	}
	
	public <T extends GameObject> T createObject(Class<T> objectClass, Object...args) {
		Constructor<T> constructor;
		try {
			constructor = objectClass.getDeclaredConstructor(new Class[] {Point.class});
		} catch (NoSuchMethodException | SecurityException e1) {
			e1.printStackTrace();
			return null;
		}

		try {
			return constructor.newInstance((Object[]) args);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
			return null;
		}
	}

	public AssetManager getManager() {
		return manager;
	}
	
}
