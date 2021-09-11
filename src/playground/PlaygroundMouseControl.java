package playground;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class PlaygroundMouseControl implements MouseListener, MouseWheelListener, MouseMotionListener {

	
	Playground play;
	public PlaygroundMouseControl(Playground p)
	{
		play= p;
	}
	@Override
	public void mouseDragged(MouseEvent e) {
		
			play.creator.onDragged(e.getPoint());
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		
		play.creator.p=e.getPoint();
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		
		if(e.getPreciseWheelRotation()>0)
		{
			play.creator.next(e.getPoint());
		}
		else
		{
			play.creator.previous(e.getPoint());
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	
		play.storage.onClick(e.getPoint());
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getButton()==MouseEvent.BUTTON1)
		play.creator.onPress(e.getPoint());
		else
		if(e.getButton()==MouseEvent.BUTTON3)
			play.creator.stop();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.getButton()==MouseEvent.BUTTON3)
			play.creator.stop();
		else
		if(e.getButton()==MouseEvent.BUTTON1)
		play.creator.onRelease(e.getPoint());
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
		
	}

}
