package window;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardListener implements KeyListener {

	public KeyboardListener()
	{
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	
	}

	@Override
	public void keyPressed(KeyEvent e) {

		if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
		{
			System.exit(0);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
		
	}

}
