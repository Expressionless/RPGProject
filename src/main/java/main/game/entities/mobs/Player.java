package main.game.entities.mobs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import helix.game.GameData;
import helix.game.objects.entity.mob.Mob;
import helix.utils.math.Angle;
import helix.utils.math.Point;
import main.game.Constants;

public class Player extends Mob {
	public static final String PLAYER_RIGHT = "res/sprites/player/right.png";
	public static final String PLAYER_DOWN = "res/sprites/player/down.png";
	public static final String PLAYER_UP = "res/sprites/player/up.png";
	
	@Override
	public void loadSprites(AssetManager manager) {
		manager.load(PLAYER_RIGHT, Texture.class);
		manager.load(PLAYER_DOWN, Texture.class);
		manager.load(PLAYER_UP, Texture.class);
		
	}

	private int anim_duration = 750;

	private int up = 0, down = 0, left = 0, right = 0;

	public Player(GameData data, Point pos) {
		super(data, pos);
		data.setPlayer(this);
		
		this.addSprite(PLAYER_RIGHT, 4, anim_duration);
		this.addSprite(PLAYER_DOWN, 4, anim_duration);
		this.addSprite(PLAYER_UP, 4, anim_duration);

		this.setStat("speed", Constants.PLAYER_SPEED);
		
	}

	@Override
	public void step() {
		this.getCollider().setWidth(6);
		this.getCollider().setHeight(13);
		this.getCollider().setXOffset(5);
		this.getCollider().setYOffset(2);
		// Manage input
		Gdx.input.setInputProcessor(new InputAdapter() {

			@Override
			public boolean keyDown(int keycode) {
				switch (keycode) {
				case Constants.KEY_DOWN:
					down = 1;
					break;
				case Constants.KEY_RIGHT:
					right = 1;
					break;
				case Constants.KEY_LEFT:
					left = 1;
					break;
				case Constants.KEY_UP:
					up = 1;
					break;
				}
				return true;
			}

			@Override
			public boolean keyUp(int keycode) {
				switch (keycode) {
				case Constants.KEY_DOWN:
					down = 0;
					break;
				case Constants.KEY_RIGHT:
					right = 0;
					break;
				case Constants.KEY_LEFT:
					left = 0;
					break;
				case Constants.KEY_UP:
					up = 0;
					break;
				}
				return true;
			}
		});

		// Update Direction
		this.getDirection().setX(right - left);
		this.getDirection().setY(up - down);

		// Update sprite
		if (this.getDirection().length() == 0) {
			this.getSprite().restart();
			this.getSprite().stop();
		} else {
			double angle = this.getDirection().getAngle();
			
			boolean up    = angle < Angle.TOP_LEFT.angle     && angle > Angle.TOP_RIGHT.angle;
			boolean left  = angle <= Angle.BOTTOM_LEFT.angle && angle >= Angle.TOP_LEFT.angle;
			boolean right = (angle >= 0 && angle <= Angle.TOP_RIGHT.angle) || (angle < 0  && angle >= Angle.BOTTOM_RIGHT.angle);
			boolean down  = angle < Angle.BOTTOM_RIGHT.angle && angle > Angle.BOTTOM_LEFT.angle;
			
			if(up) {
				setSprite(PLAYER_UP);
				getSprite().flip(false);
			} else if(right) {
				setSprite(PLAYER_RIGHT);
				getSprite().flip(false);
			} else if(down) {
				setSprite(PLAYER_DOWN);
				getSprite().flip(false);
			} else if(left) {
				setSprite(PLAYER_RIGHT);
				getSprite().flip(true);
			}
			this.getSprite().start();
		}

		this.move(this.getStat("speed"));
	}

	@Override
	public void draw(SpriteBatch batch) {
		font.draw(batch, this.getPos().toString(), this.getPos().getX() - 30, this.getPos().getY());
	}
}
