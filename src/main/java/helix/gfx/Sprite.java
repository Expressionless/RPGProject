package helix.gfx;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import helix.Constants;
import helix.utils.math.Vector2;

public class Sprite {

	private final Animation animation;
	private String name;

	private Vector2 scale;

	private boolean flipped = false;

	// Bounds relative to top left of sprite
	private final Rectangle bounds;

	public Sprite(Animation animation) {
		this.animation = animation;
		if(this.animation.getName().isBlank())
			this.name = Constants.DEFAULT_SPRITE;
		else
			this.name = animation.getName();

		scale = new Vector2(1, 1);
		bounds = new Rectangle(0, 0, this.animation.getWidth(), this.animation.getHeight());
	}
	
	public Sprite(TextureRegion region, int frameCount, int duration) {
		this(new Animation(region, "", frameCount, duration));
	}
	
	public Sprite(TextureRegion region, int frameCount) {
		this(region, frameCount, Constants.NO_ANIM);
	}
	
	public Sprite(TextureRegion region) {
		this(region, Constants.DEF_FRAMES, Constants.NO_ANIM);
	}
	
	public void draw(SpriteBatch batch, float x, float y) {
		this.animation.update();
		this.setBounds(x, y, this.animation.getWidth(), this.animation.getHeight());
		if (flipped) {
			batch.draw(animation.getFrame(), x + animation.getWidth(), y, -animation.getWidth() * scale.getX(),
					animation.getHeight() * scale.getY());
		} else
			batch.draw(animation.getFrame(), x, y, animation.getWidth() * scale.getX(), animation.getHeight() * scale.getY());
	}

	public void restart() {
		this.animation.restart();
	}

	public void start() {
		this.animation.start();
	}

	public void stop() {
		this.animation.stop();
	}

	// Getters and Setters

	public Rectangle getBounds() {
		return bounds;
	}

	private void setBounds(float x, float y, float width, float height) {
		bounds.x = x;
		bounds.y = y;
		bounds.width = width;
		bounds.height = height;
	}
	
	public TextureRegion getSubImage(int index) {
		return this.animation.getSubImage(index);
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public Animation getAnimation() {
		return animation;
	}

	public void flip(boolean flipped) {
		this.flipped = flipped;
	}

	public boolean isFlipped() {
		return flipped;
	}

	public float getWidth() {
		return bounds.width;
	}

	public float getHeight() {
		return bounds.height;
	}

	public Vector2 getScale() {
		return scale;
	}

	public void setScale(Vector2 scale) {
		this.scale = scale.copy();
	}
	
	public void setScale(float x, float y) {
		this.setScale(new Vector2(x, y));
	}
	
	@Override
	public String toString() {
		return "Sprite [name=" + this.name 
				+ (scale != null ? ",scale=" + scale.toString() : "")
				+ ",flipped=" + this.flipped
				+ ",dim=[w=" + this.getWidth() + ",h=" + this.getHeight() + "]"
				+ ",anim=" + this.getAnimation().toString()
				+ "]";
	}
}
