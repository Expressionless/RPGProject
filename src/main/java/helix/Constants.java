package helix;

import java.io.File;

public class Constants {
	
	// Absolute Path
	public static final String ABS_PATH = new File("").getAbsolutePath();
	
	// Animation Constants
	public static final int DEF_FRAMES = 1;
	public static final int NO_ANIM = -1;
	public static final String DEFAULT_SPRITE = "DEFAULT";
	
	// Size in bytes
	public static final int INT_SIZE = 4;
	public static final int MAX_ITEM_NAME_LEN = 10;
	public static final int ITEM_SIZE = 1 + MAX_ITEM_NAME_LEN + 1;
}
