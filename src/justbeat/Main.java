package justbeat;

import database.DBConnection;

// JavaSE-1.8

public class Main {

	public static final int SCREEN_WIDTH = 1280;
	public static final int SCREEN_HEIGHT = 720;
	public static int NOTE_SPEED = 4;
	public static int MODE_SELECT = 0;
	public static final int SLEEP_TIME = 10;
	public static final int REACH_TIME = 2;
	
	public static void main(String[] args) {
		
		new JustBeat();
	}
}
