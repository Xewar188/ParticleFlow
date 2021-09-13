package devices;

import particles.ParticleController;

import java.awt.*;

public abstract class Device {

	protected static boolean toFill = true;
	public abstract void update(ParticleController p);
	public abstract void draw(Graphics2D g);
	public abstract boolean onClick();
	public abstract boolean contains(Point p);
}
