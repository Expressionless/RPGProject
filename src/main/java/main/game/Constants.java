package main.game;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;

public class Constants {

	// Frame Dimensions
	public static int FRAME_WIDTH = 1280;
	public static int FRAME_HEIGHT = 720;
	
	// Viewport dimensions
	public static int CAMERA_WIDTH = 320;
	public static int CAMERA_HEIGHT = 180;
	
	// FPS Constants
	public static final int IDLE_FPS = 45;
	public static final int TARGET_FPS = 120;
	
	// Title of the game
	public static String TITLE = "RPG Game";
	
	// Background clear Color
	public static Color CLEAR_COLOR = new Color(0, 0.25f, 0, 1);

	// Items resource path
	public static final String ITEMS_DIRECTORY = "res/sprites/items.png";
		
	// Animation Constants
	public static final double ITEM_BREATHE_LENGTH = 50;
	
	// Movement
	public static final char KEY_RIGHT = Keys.D;
	public static final char KEY_LEFT = Keys.A;
	public static final char KEY_DOWN = Keys.S;
	public static final char KEY_UP = Keys.W;

	public static final float PLAYER_SPEED = 0.5f;
	public static final float ITEM_SPEED = 0.25f;
	public static final float DEFAULT_SPEED = 0.5f;
	
	// Basic Collision
	public static final float PICKUP_DISTANCE = 4;
	public static final float ITEM_SUCK_DISTANCE = 24;
	
}
