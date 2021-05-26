package main;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;

public class Constants {

	// Width of the Frame
	public static int FRAME_WIDTH = 1280;
	// Width of the camera viewport
	public static int CAMERA_WIDTH = 160;
	
	// Height of the Frame
	public static int FRAME_HEIGHT = 720;
	// Height of the camera viewport
	public static int CAMERA_HEIGHT = 90;
	
	// FPS Constants
	public static final int IDLE_FPS = 45;
	public static final int TARGET_FPS = 120;
	
	// Title of the game
	public static String TITLE = "RPG Game";
	
	// Background clear Color
	public static Color CLEAR_COLOR = new Color(0, 0.25f, 0, 1);

	// Movement
	public static final char KEY_RIGHT = Keys.D;
	public static final char KEY_LEFT = Keys.A;
	public static final char KEY_DOWN = Keys.W;
	public static final char KEY_UP = Keys.S;
	
	public static final float PLAYER_SPEED = 0.5f;
}
