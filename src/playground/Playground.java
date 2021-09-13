package playground;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.Serial;

import javax.swing.JPanel;

import devices.DeviceCreator;
import devices.DeviceStorage;
import particles.ParticleController;

public class Playground extends JPanel {


	@Serial
	private static final long serialVersionUID = 1L;

	private ParticleController controller;
	DeviceCreator creator; //deliberately package level access, so that mouseListener can use it
	DeviceStorage storage;

	public Playground() {}

	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		if (controller != null) {
			controller.move();
			controller.update();
			storage.update();
			controller.draw(g2d);
			storage.draw(g2d);
			creator.draw(g2d);
			
		}
	}

	public void postOpen(float x) {
		controller = new ParticleController(this.getBounds().width, x);
		PlaygroundMouseControl control = new PlaygroundMouseControl(this);
		this.addMouseListener(control);
		this.addMouseMotionListener(control);
		this.addMouseWheelListener(control);
		storage=new DeviceStorage(controller);
		creator= new DeviceCreator(storage);
	}
	
}
