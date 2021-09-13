package devices;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.util.ArrayList;
import java.util.Vector;
import java.util.function.Function;



public class DeviceCreator {

	private Point drawingStart = null;
	private final DeviceStorage target;
	private final ArrayList<initialCreator> creators = new ArrayList<>();
	private Shape[] toDraw = null;
	private int nr = 0;
	private int menuDrawCounter = 0;
	private Point currentMousePoint = null;
	private final Vector<Color> menuColors = new Vector<>();
	
	public DeviceCreator(DeviceStorage t)
	{
		creators.add(new initialCreator(Wall::create, Wall::createAffectedShape, Wall::createMenuShape, Wall.toFill));
		creators.add(new initialCreator(Circle::create, Circle::createAffectedShape, Circle::createMenuShape, Circle.toFill));
		creators.add(new initialCreator(Repulsor::create, Repulsor::createAffectedShape, Repulsor::createMenuShape, Repulsor.toFill));
		creators.add(new initialCreator(Attractor::create, Attractor::createAffectedShape, Attractor::createMenuShape, Attractor.toFill));
		creators.add(new initialCreator(Mover::create, Mover::createAffectedShape, Mover::createMenuShape, Mover.toFill));
		target = t;
	}
	public void onPress(Point p)
	{
		drawingStart = p;
	}
	public void setCurrentMousePoint(Point p) {
		currentMousePoint = p;
	}
	public void onRelease(Point p)
	{
		if (drawingStart != null && p != null && p.distance(drawingStart) > 14)
		{
			target.add(creators.get(nr).creator.apply(new Point[] {drawingStart, p}));
		}
		drawingStart = null;
		toDraw = null;
	}
	public void stop()
	{
		drawingStart = null;
		toDraw = null;
	}
	public void onDragged(Point point) {
		toDraw = null;
		
		if(drawingStart != null)
		{	
			currentMousePoint = point;
			toDraw = creators.get(nr).drawer.apply(new Point[] {drawingStart,point});
			
		}
		
	}
	public void next()
	{
		menuDrawCounter = 100;
		nr++;
		nr %= creators.size();
		setMenuColors();
	}
	public void previous()
	{
		menuDrawCounter = 100;
		nr--;
		nr += creators.size();
		nr %= creators.size();
		setMenuColors();
	}

	public void setMenuColors() {

		menuColors.clear();
		for(int i = 0; i < creators.get(nr).menu.apply(new Point(currentMousePoint.x, currentMousePoint.y - 50)).length; i++)
		{
			menuColors.add(new Color((int)(100 + Math.random() * 100),
					(int)(100 + Math.random() * 100),
					(int)(100 + Math.random() * 100),
					255 * menuDrawCounter / 100));
		}
	}
	public void draw(Graphics2D g)
	{
		if(toDraw != null)
		{
			g.setColor(new Color(255, 255, 255, 123));
			for(Shape s : toDraw)
			g.fill(s);
		}
		if(menuDrawCounter > 0)
		{
			Shape[] menuToDraw = creators.get(nr).menu.apply(new Point(currentMousePoint.x, currentMousePoint.y - 50));
			g.setColor(new Color(255,255,255,255 * menuDrawCounter /100));
			g.fill(new Rectangle(currentMousePoint.x-25, currentMousePoint.y - 75, 50, 50));
			int i = 0;
			for(Shape s : menuToDraw)
			{
				g.setColor(new Color(menuColors.get(i).getRed(),
						menuColors.get(i).getGreen(),
						menuColors.get(i++).getBlue(),
						255 * menuDrawCounter /100));
				if(creators.get(nr).toFill)
					g.fill(s);
				else
				{
					g.setStroke(new BasicStroke(2));
					g.draw(s);
				}
				
			}
			menuDrawCounter--;
		}
		
		
	}
	static class initialCreator
	{
		Function<Point[],Device> creator;
		Function<Point[],Shape[]> drawer;
		Function<Point,Shape[]> menu;
		boolean toFill;
		initialCreator(Function<Point[],Device> c,Function<Point[],Shape[]> d,Function<Point,Shape[]> m,boolean t)
		{
			creator = c;
			drawer = d;
			menu = m;
			toFill = t;
		}
	}
	
}
