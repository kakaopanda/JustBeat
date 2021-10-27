package justbeat;

import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Note extends Thread {
	
	private Image noteBasicImage = new ImageIcon(Main.class.getResource("../images/noteBasic.png")).getImage();
	private int x, y = 550 - (1000 / Main.SLEEP_TIME * Main.NOTE_SPEED) * Main.REACH_TIME;
	private String noteType;
	private boolean proceeded = true;
	public Game game_miss;
	
	public String getNoteType() {
		return noteType;
	}
	
	public boolean isProceeded() {
		return proceeded;
	}
	
	public void close() {
		proceeded = false;
	}
	
	public Note(String noteType) {
		if(noteType.contentEquals("S")) {
			x = 191;
		}
		else if(noteType.contentEquals("D")) {
			x = 299;
		}
		else if(noteType.contentEquals("F")) {
			x = 407;
		}
		else if(noteType.contentEquals("Space")) {
			x = 515;
		}
		else if(noteType.contentEquals("J")) {
			x = 731;
		}
		else if(noteType.contentEquals("K")) {
			x = 839;
		}
		else if(noteType.contentEquals("L")) {
			x = 947;
		}
		this.noteType = noteType;
	}
	
	public void screenDraw(Graphics2D g) {
		if(noteType.equals("Space"))
		{
			g.drawImage(noteBasicImage, x, y, null);
			g.drawImage(noteBasicImage, x + 108, y, null);
		}
		else
		{
			g.drawImage(noteBasicImage, x, y, null);
		}
	}
	
	public void drop() {
		y += Main.NOTE_SPEED;
		if(y > 620) {
			close();
		}
	}
	
	@Override
	public void run() {
		try {
			while(true) {
				drop();
				if(proceeded) {
					Thread.sleep(Main.SLEEP_TIME);
				}
				else {
					interrupt();
					break;
				}
			}
		}catch(Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	public String judge() {
		if(y >= 590) {
			System.out.println("Late");
			close();
			return "Late";
		}
		else if(y >= 570) {
			System.out.println("Good");
			close();
			return "Good";
		}
		else if(y >= 550) {
			System.out.println("Great");
			close();
			return "Great";
		}
		else if(y >= 535) {
			System.out.println("Perfect");
			close();
			return "Perfect";
		}
		else if(y >= 490) {
			System.out.println("Early");
			close();
			return "Early";
		}
		return "None";
	}
	
	public int getY() {
		return y;
	}
}
