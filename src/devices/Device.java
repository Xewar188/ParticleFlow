package devices;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import particles.ParticleControler;

public abstract class Device {

	
	Device(Point p,Point f)
	{
		
	}
	
	static boolean toFill=true;
	static Device create(Point[] p)
	{
		return null;
	}
	public static Shape[] createAffectedShape(Point[] a) {
		return null;
	}
	public static Shape[] createMenuShape(Point a) {
		return null;
	}
	abstract void update(ParticleControler p);
	abstract void draw(Graphics2D g);
	abstract boolean onClick();
	abstract boolean contains(Point p);
}
