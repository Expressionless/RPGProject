package helix.game;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.logging.Logger;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.Viewport;

import helix.game.objects.Entity;
import helix.gfx.Animation;
import helix.gfx.Screen;
import helix.gfx.Sprite;
import helix.utils.io.DataReader;
import helix.utils.io.DataWriter;
import helix.utils.math.Point;

public abstract class Data {
	protected static Logger log = Logger.getLogger(Data.class.getCanonicalName());

	public static int TICKS = 0;

	public final ArrayList<GameObject> objects;
	public final ArrayList<Entity> entities;
	public final ArrayList<Screen> screens;

	protected DataReader reader;
	protected DataWriter writer;

	private AssetManager manager;
	private Viewport viewport;
	private Camera camera;
	private BaseGame game;

	public Data(BaseGame game) {
		manager = new AssetManager();
		entities = new ArrayList<>();
		objects = new ArrayList<>();
		screens = new ArrayList<>();

		this.game = game;
	}

	/**
	 * To be called in {@link helix.game.BaseGame#create()}
	 */
	public abstract void init();

	public Sprite createSprite(String spriteName, int frameCount, float animTime) {
		Texture texture = manager.get(spriteName);
		TextureRegion region = new TextureRegion(texture);
		Animation anim = new Animation(region, spriteName, frameCount, animTime);

		return new Sprite(anim);

	}

	public Sprite createSprite(String spriteName, int frameCount) {
		return this.createSprite(spriteName, frameCount, helix.Constants.NO_ANIM);
	}

	public Sprite createSprite(String spriteName) {
		return this.createSprite(spriteName, helix.Constants.DEF_FRAMES);
	}

	protected abstract void dispose();

	private void disposeCore() {
		entities.removeIf((Entity entity) -> {
			return entity.willDispose();
		});

		objects.removeIf((GameObject obj) -> {
			return obj.willDispose();
		});

		this.dispose();
	}

	public void update(float delta) {
		TICKS++;

		this.disposeCore();

		entities.sort(new Comparator<Entity>() {

			@Override
			public int compare(Entity e1, Entity e2) {
				return (int) Math.signum(e2.getDepth() - e1.getDepth());
			}

		});

		for (GameObject object : objects) {
			object.updateAlarms();
			object.update(delta);
		}
	}

	public void render(SpriteBatch batch) {
		for (Entity entity : entities) {
			entity.render(batch);
		}
	}

	public void beginReading(String path) {
		if (writer != null)
			return;
		if (reader != null)
			return;

		reader = new DataReader(path);
	}

	public void stopReading() {
		if (reader == null)
			return;
		reader = null;
	}

	public void beginWriting(String path) {
		if (writer != null)
			return;
		if (reader != null)
			return;

		writer = new DataWriter(path);
	}

	public void stopWriting() {
		if (writer == null)
			return;

		writer.close();
		writer = null;
	}

	public <T extends GameObject> T createObject(Class<T> objectClass, Object... args) {
		Constructor<T> constructor;
		try {
			constructor = objectClass.getDeclaredConstructor(new Class[] { Data.class, Point.class });
		} catch (NoSuchMethodException | SecurityException e1) {
			e1.printStackTrace();
			return null;
		}

		try {
			return constructor.newInstance(this, (Object[]) args);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
			return null;
		}
	}

	public boolean reading() {
		return (reader != null);
	}

	public boolean writing() {
		return (writer != null);
	}

	public AssetManager getManager() {
		return manager;
	}

	public Viewport getViewport() {
		return viewport;
	}

	public void setViewport(Viewport viewport) {
		this.viewport = viewport;
	}

	public Camera getCamera() {
		return camera;
	}

	public void setCamera(Camera camera) {
		this.camera = camera;
	}

	public BaseGame getBaseGame() {
		return (BaseGame) game;
	}
}
