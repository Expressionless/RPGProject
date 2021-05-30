package main.game.entities.mobs;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import helix.utils.math.Angle;
import helix.utils.math.Point;
import main.Constants;
import main.game.RpgGame;
import main.game.entities.Mob;
import main.game.inventory.Inventory;

import static main.Constants.UP;
import static main.Constants.DOWN;
import static main.Constants.LEFT;
import static main.Constants.RIGHT;

public class Player extends Mob {
	public static final String PLAYER_RIGHT = "res/sprites/player/right.png";
	public static final String PLAYER_DOWN = "res/sprites/player/down.png";
	public static final String PLAYER_UP = "res/sprites/player/up.png";

	private Inventory hotbar;
	private int anim_duration = 750;
	private int movement = 0x00;

	public Player(RpgGame game, Point pos) {
		super(game, pos);
		game.getGameData().setPlayer(this);

		this.addSprite(PLAYER_RIGHT, 4, anim_duration);
		this.addSprite(PLAYER_DOWN, 4, anim_duration);
		this.addSprite(PLAYER_UP, 4, anim_duration);

		this.hotbar = new Inventory(8, 1);
		this.setStat("speed", Constants.PLAYER_SPEED);
	}

	@Override
	public void loadSprites(AssetManager manager) {
		manager.load(PLAYER_RIGHT, Texture.class);
		manager.load(PLAYER_DOWN, Texture.class);
		manager.load(PLAYER_UP, Texture.class);
	}

	@Override
	protected void preStep(float delta) {
		super.preStep(delta);
		this.hotbar.update();
	}

	@Override
	public void step(float delta) {
		// Update collider
		// Manage input
		this.handleMovement(delta);
		this.updateCollider();
	}

	@Override
	public void draw(SpriteBatch batch) {
		float LEFT = this.getGameData().getCamera().position.x;
		float TOP = this.getGameData().getCamera().position.y;
		float BOTTOM = this.getGameData().getCamera().position.y - Constants.CAMERA_HEIGHT * .6f;

		this.getInventory().render(batch, LEFT - Constants.CAMERA_WIDTH / 4 + 40,
				TOP - Constants.CAMERA_HEIGHT / 8 + 15);
		this.hotbar.render(batch, LEFT - Constants.CAMERA_WIDTH / 4 + 40, BOTTOM + 30);
	}

	private void handleMovement(float delta) {

		// Update Direction
		int xVal = this.getMovement(RIGHT) - this.getMovement(LEFT);
		int yVal = this.getMovement(UP) - this.getMovement(DOWN);

		this.getDirection().setX(xVal);
		this.getDirection().setY(yVal);

		// Update sprite
		if (this.getDirection().length() == 0) {
			this.getSprite().restart();
			this.getSprite().stop();
		} else {
			this.updateSprite();
		}

		if (this.getDirection().length() != 0)
			this.move(this.getStat("speed") * delta);
	}

	private void updateSprite() {
		double angle = this.getDirection().getAngle();

		boolean up = angle < Angle.TOP_LEFT.angle && angle > Angle.TOP_RIGHT.angle;
		boolean left = angle <= Angle.BOTTOM_LEFT.angle && angle >= Angle.TOP_LEFT.angle;
		boolean right = (angle >= 0 && angle <= Angle.TOP_RIGHT.angle)
				|| (angle < 0 && angle >= Angle.BOTTOM_RIGHT.angle);
		boolean down = angle < Angle.BOTTOM_RIGHT.angle && angle > Angle.BOTTOM_LEFT.angle;

		if (up) {
			setSprite(PLAYER_UP);
			getSprite().flip(false);
		} else if (right) {
			setSprite(PLAYER_RIGHT);
			getSprite().flip(false);
		} else if (down) {
			setSprite(PLAYER_DOWN);
			getSprite().flip(false);
		} else if (left) {
			setSprite(PLAYER_RIGHT);
			getSprite().flip(true);
		}
		this.getSprite().start();
	}

	private void updateCollider() {
		this.getCollider().setWidth(6);
		this.getCollider().setHeight(13);
		this.getCollider().setXOffset(5);
		this.getCollider().setYOffset(2);
	}

	// Getters and Setters
	public Inventory getHotbar() {
		return this.hotbar;
	}
	
	public int getMovement(int bit) {

		return ((movement & bit) > 0) ? 1 : 0;
	}

	/*
	 * setMovement(DOWN = 0, true) bit = (int) Math.pow(2, 0) -> 1
	 * 
	 * if(val=true) movement |= 1
	 */
	public void setMovement(int bit, boolean val) {
		if (val)
			movement |= bit;
		else {
			bit = 0xF - bit;
			movement &= bit;
		}
	}

}
