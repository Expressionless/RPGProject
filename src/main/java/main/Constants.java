package main;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class Constants {

	// Width of the Frame
	public static int FRAME_WIDTH = 1280;
	// Width of the camera viewport
	public static int CAMERA_WIDTH = 320;
	
	// Height of the Frame
	public static int FRAME_HEIGHT = 720;
	// Height of the camera viewport
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
	public static final String DEFAULT_SPRITE = "DEFAULT";
	
	// Box2D Constants
	public static final Vector2 GRAVITY = new Vector2(0, 0);
	public static final float BOX2D_STEP_TIME = 1/0.45f;
	
	// Animation Constants
	public static final int DEF_FRAMES = 1;
	public static final int NO_ANIM = -1;
	
	
	// Movement
	public static final char KEY_RIGHT = Keys.D;
	public static final char KEY_LEFT = Keys.A;
	public static final char KEY_DOWN = Keys.S;
	public static final char KEY_UP = Keys.W;
	
	public static final float PLAYER_SPEED = 0.5f;
}
