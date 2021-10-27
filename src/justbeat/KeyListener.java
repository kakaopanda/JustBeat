package justbeat;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyListener extends KeyAdapter{

	@Override
	public void keyPressed(KeyEvent e) {
		if(JustBeat.game == null) {
			return;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_S) {
			JustBeat.game.pressS();
		}
		else if(e.getKeyCode() == KeyEvent.VK_D) {
			JustBeat.game.pressD();
		}
		else if(e.getKeyCode() == KeyEvent.VK_F) {
			JustBeat.game.pressF();
		}
		else if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			JustBeat.game.pressSpace();
		}
		else if(e.getKeyCode() == KeyEvent.VK_J) {
			JustBeat.game.pressJ();
		}
		else if(e.getKeyCode() == KeyEvent.VK_K) {
			JustBeat.game.pressK();
		}
		else if(e.getKeyCode() == KeyEvent.VK_L) {
			JustBeat.game.pressL();
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		if(JustBeat.game == null) {
			return;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_S) {
			JustBeat.game.releaseS();
		}
		else if(e.getKeyCode() == KeyEvent.VK_D) {
			JustBeat.game.releaseD();
		}
		else if(e.getKeyCode() == KeyEvent.VK_F) {
			JustBeat.game.releaseF();
		}
		else if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			JustBeat.game.releaseSpace();
		}
		else if(e.getKeyCode() == KeyEvent.VK_J) {
			JustBeat.game.releaseJ();
		}
		else if(e.getKeyCode() == KeyEvent.VK_K) {
			JustBeat.game.releaseK();
		}
		else if(e.getKeyCode() == KeyEvent.VK_L) {
			JustBeat.game.releaseL();
		}
	}
}
