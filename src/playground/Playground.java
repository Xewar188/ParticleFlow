package playground;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import devices.DeviceCreator;
import devices.DeviceStorage;
import particles.ParticleControler;

public class Playground extends JPanel {


	private static final long serialVersionUID = 1L;

	PlaygroundMouseControl control;
	ParticleControler controler;
	DeviceCreator creator;
	DeviceStorage storage;
	public Playground()
	{
		
		
		
		
	}
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		if(controler!=null)
		{
			controler.move();
			controler.update();
			storage.update();
			controler.draw(g2d);
			storage.draw(g2d);
			creator.draw(g2d);
			
		}
	}
	public void postOpen() {
		controler=new ParticleControler(this.getBounds());
		control=new PlaygroundMouseControl(this);
		this.addMouseListener(control);
		this.addMouseMotionListener(control);
		this.addMouseWheelListener(control);
		storage=new DeviceStorage(controler);
		creator= new DeviceCreator(storage);
	}
	
}
