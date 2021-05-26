package helix.gfx;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Sprite {

	private final Animation animation;
	private final String name;
	
	private boolean flipped = false;
	
	// Bounds relative to top left of sprite
	private final Rectangle bounds;
	
	public Sprite(Animation animation) {
		this.animation = animation;
		this.name = animation.getName();
		
		bounds = new Rectangle(0, 0, this.animation.getWidth(), this.animation.getHeight());
	}
	
	public void draw(SpriteBatch batch, float x, float y) {
		this.animation.update();
		if(flipped) {
			batch.draw(animation.getFrame(), x + animation.getWidth(), y, -animation.getWidth(), animation.getHeight());
		} else
			batch.draw(animation.getFrame(), x, y);
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
	
	public void setBounds(float x, float y, float width, float height) {
		bounds.x = x;
		bounds.y = y;
		bounds.width = width;
		bounds.height = height;
	}

	public String getName() {
		return name;
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
}
