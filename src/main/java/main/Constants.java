package main;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;

public class Constants {

	// Frame Dimensions
	public static int FRAME_WIDTH = 1280;
	public static int FRAME_HEIGHT = 720;
	
	// Viewport dimensions
	public static int CAMERA_WIDTH = 320;
	public static int CAMERA_HEIGHT = 180;
	
	// > 1
	public static float RATIO_X = FRAME_WIDTH / CAMERA_WIDTH;
	public static float RATIO_Y = FRAME_HEIGHT / CAMERA_HEIGHT;
	
	public static boolean SAFE_RES = (RATIO_X == RATIO_Y);
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

	// Player Constants
	public static final int DOWN  = 0x01;
	public static final int LEFT  = 0x02;
	public static final int RIGHT = 0x04;
	public static final int UP    = 0x08;
	
	// Movement and Input
	public static final char KEY_RIGHT = Keys.D;
	public static final char KEY_LEFT = Keys.A;
	public static final char KEY_DOWN = Keys.S;
	public static final char KEY_UP = Keys.W;
	
	public static final char KEY_INV = Keys.P;
	public static final char KEY_SHIFT = Keys.SHIFT_LEFT;
	public static final char KEY_SHIFT_R = Keys.SHIFT_RIGHT;

	public static final float PLAYER_SPEED = 60f;
	public static final float ITEM_SPEED = 15f;
	
	// Basic Collision (px)
	public static final float PICKUP_DISTANCE = 8;
	public static final float ITEM_SUCK_DISTANCE = 12;
	
	// Inventory Constants
	public static final int DEF_INV_WIDTH = 8;
	public static final int DEF_INV_HEIGHT = 6;
	// Inventory slot margin (in px)
	public static final float INVENTORY_MARGIN = 1;

	// World constants
	public static final float TILE_WIDTH  = 8;
	public static final float TILE_HEIGHT = 8;

	public static final int CHUNK_WIDTH  = 10;
	public static final int CHUNK_HEIGHT = 10;
	
	public static final float CHUNK_WIDTH_PX = CHUNK_WIDTH * TILE_WIDTH;
	public static final float CHUNK_HEIGHT_PX = CHUNK_HEIGHT * TILE_HEIGHT;
	
	// Entity limits
	public static final int MAX_ENTITIES_PER_TILE = 4;
	public static final int MAX_ENTITIES_PER_CHUNK = MAX_ENTITIES_PER_TILE * CHUNK_WIDTH * CHUNK_HEIGHT;
	
	// Item offsets (in px)
	public static final int INV_ITEM_OFFSET_X = 1;
	public static final int INV_ITEM_OFFSET_Y = 1;
	
	public static final int HOTBAR_WIDTH = 1;
	public static final int HOTBAR_HEIGHT = 10;
	
	public static final int MAX_STACK = 40;

	// ITEM INFO CONSTANTS
	// Size in bits (1 byte)
	public static final int INT_SIZE = 4;
	public static final int NO_ITEM = -1;
	public static final int MAX_ITEM_NAME_LEN = 10;
	public static final int MAX_ITEM_TYPE_LEN = 10;
	
	// Item Data positions
	public static final int ID_POS = 0;
	public static final int TYPE_POS = ID_POS + 1;
	public static final int NAME_POS = TYPE_POS + MAX_ITEM_TYPE_LEN;
	public static final int STACK_POS = NAME_POS + MAX_ITEM_NAME_LEN;
	public static final int FLAG_POS = STACK_POS + 1;
	public static final int ITEM_SIZE = FLAG_POS + 1;
	
	// Tree Growth Cycle (in seconds)
	public static final int TREE_GROWTH_CYCLE = 15;
	
}
