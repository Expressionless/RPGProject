package main.game.entities.mobs;

import static main.Constants.DOWN;
import static main.Constants.LEFT;
import static main.Constants.RIGHT;
import static main.Constants.UP;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import helix.utils.io.DataReader;
import helix.utils.io.DataWriter;
import helix.utils.math.Angle;
import helix.utils.math.Point;
import main.Constants;
import main.game.RpgGame;
import main.game.entities.Mob;
import main.game.inventory.Inventory;
import main.game.inventory.Slot;
import main.game.inventory.subtypes.ArmourInventory;
import main.game.inventory.subtypes.GenericInventory;
import main.game.inventory.subtypes.HotbarInventory;

public class Player extends Mob {
	public static final String PLAYER_RIGHT = "res/sprites/player/right.png";
	public static final String PLAYER_DOWN = "res/sprites/player/down.png";
	public static final String PLAYER_UP = "res/sprites/player/up.png";

	// Inventories
	private Inventory hotbar;
	private Inventory equipped;
	private Inventory armour;

	private int anim_duration = 750;
	private int movement = 0x00;

	public Player(RpgGame game, Point pos) {
		super(game, pos);
		float x = 40 - Constants.CAMERA_WIDTH / 4;
		float y = 30 - Constants.CAMERA_HEIGHT * .6f + Slot.SPRITE.getHeight() * (Constants.P_INV_HEIGHT + 1);
		Inventory newInv = new GenericInventory(game, new Point(x, y), Constants.P_INV_WIDTH, Constants.P_INV_HEIGHT);
		this.setInventory(newInv);

		this.addSprite(PLAYER_RIGHT, 4, anim_duration);
		this.addSprite(PLAYER_DOWN, 4, anim_duration);
		this.addSprite(PLAYER_UP, 4, anim_duration);

		this.hotbar = new HotbarInventory(game,
				new Point(40 - Constants.CAMERA_WIDTH / 4, 30 - Constants.CAMERA_HEIGHT * .6f));
		this.hotbar.setVisible(true);

		Point armourPos = this.getInventory().getPos().copy();
		armourPos.setX(armourPos.getX() - Slot.SPRITE.getWidth() - Constants.INVENTORY_MARGIN);
		armourPos.setY(armourPos.getY() - Slot.SPRITE.getHeight());
		this.armour = new ArmourInventory(game, armourPos);

		Point equipPos = this.getHotbar().getPos().copy();
		equipPos.setX(equipPos.getX() - Slot.SPRITE.getWidth() * 2.5f - Constants.INVENTORY_MARGIN);
		equipPos.setY(equipPos.getY());
		
		this.equipped = new GenericInventory(game, equipPos, 2, 1);
		this.equipped.clearAllowedTypes();
		this.equipped.addAllowedTypes("WEAPON", "TOOL");
		this.equipped.setVisible(true);

		this.setStat("speed", Constants.PLAYER_SPEED);

		this.updateCollider();
		game.getGameData().setPlayer(this);
	}

	@Override
	protected void preStep(float delta) {
		super.preStep(delta);
		this.hotbar.update(delta);
		this.equipped.update(delta);
		this.armour.update(delta);
	}

	@Override
	public void step(float delta) {
		// Update collider
		// Manage input
		this.handleMovement(delta);
	}

	@Override
	public void draw(SpriteBatch batch) {

		this.getInventory().render(batch);
		this.hotbar.render(batch);
		this.armour.render(batch);
		this.equipped.render(batch);
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

	@Override
	protected boolean handleState(float delta) {
		return false;
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
	public HotbarInventory getHotbar() {
		return (HotbarInventory) this.hotbar;
	}

	public ArmourInventory getArmour() {
		return (ArmourInventory) this.armour;
	}

	public Inventory getHands() {
		return this.equipped;
	}

	public int getMovement(int bit) {

		return ((movement & bit) > 0) ? 1 : 0;
	}

	public void setMovement(int bit, boolean val) {
		if (val)
			movement |= bit;
		else {
			bit = 0xF - bit;
			movement &= bit;
		}
	}

	// TODO: Implement Serialization of player object
	/*
	 * Save the position 
	 */
	@Override
	public boolean write(DataWriter writer, int pos) {
		return false;
	}

	@Override
	public boolean parse(DataReader reader, int pos) {
		return false;
	}
}
