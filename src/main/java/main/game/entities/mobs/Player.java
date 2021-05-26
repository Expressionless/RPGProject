package main.game.entities.mobs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;

import helix.GameData;
import helix.utils.Point;
import main.Constants;
import main.game.entities.Mob;

public class Player extends Mob {
	public static final String PLAYER_RIGHT = "res/sprites/player/right.png";
	public static final String PLAYER_DOWN = "res/sprites/player/down.png";
	public static final String PLAYER_UP = "res/sprites/player/up.png";

	private int anim_duration = 750;

	private int up = 0, down = 0, left = 0, right = 0;

	public Player(GameData data, Point pos) {
		super(data, pos);
		this.addSprite("res/sprites/player/right.png", 4, anim_duration);
		this.addSprite("res/sprites/player/down.png", 4, anim_duration);
		this.addSprite("res/sprites/player/up.png", 4, anim_duration);

		this.setStat("speed", Constants.PLAYER_SPEED);
	}

	@Override
	public void step() {
		// Manage input
		Gdx.input.setInputProcessor(new InputAdapter() {

			@Override
			public boolean keyDown(int keycode) {
				switch (keycode) {
				case Constants.KEY_DOWN:
					down = 1;
					getSprite().flip(false);
					setSprite(PLAYER_DOWN);
					break;
				case Constants.KEY_RIGHT:
					right = 1;
					getSprite().flip(false);
					setSprite(PLAYER_RIGHT);
					break;
				case Constants.KEY_LEFT:
					left = 1;
					getSprite().flip(true);
					setSprite(PLAYER_RIGHT);
					break;
				case Constants.KEY_UP:
					setSprite(PLAYER_UP);
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
		this.getDirection().setY(down - up);

		// Update sprite
		if (this.getDirection().length() == 0) {
			this.getSprite().restart();
			this.getSprite().stop();
		} else {
			this.getDirection().getAngle();
			if (this.getDirection().getX() == 1) {

			}
			this.getSprite().start();
		}

		this.getPos().add(this.getDirection().multiply(this.getStat("speed")));
	}

	@Override
	public void draw() {
	}
}
