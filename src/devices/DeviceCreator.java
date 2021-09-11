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

	Point start=null,tempPos=null;
	DeviceStorage target;
	ArrayList<initialCreator> creators= new ArrayList<initialCreator>();
	Shape[] toDraw=null;
	Shape[] menuToDraw=null;
	int nr=0;
	int counter=0;
	public Point p=null;
	Vector<Color> menuColors=new Vector<Color>();
	
	public DeviceCreator(DeviceStorage t)
	{
		creators.add(new initialCreator(a->Wall.create(a),a->Wall.createAffectedShape(a),a->Wall.createMenuShape(a),Wall.toFill));
		creators.add(new initialCreator(a->Circle.create(a),a->Circle.createAffectedShape(a),a->Circle.createMenuShape(a),Circle.toFill));
		creators.add(new initialCreator(a->Repulsor.create(a),a->Repulsor.createAffectedShape(a),a->Repulsor.createMenuShape(a),Repulsor.toFill));
		creators.add(new initialCreator(a->Attractor.create(a),a->Attractor.createAffectedShape(a),a->Attractor.createMenuShape(a),Attractor.toFill));
		creators.add(new initialCreator(a->Mover.create(a),a->Mover.createAffectedShape(a),a->Mover.createMenuShape(a),Mover.toFill));
		target=t;
	}
	public void onPress(Point p)
	{
		start=p;
	}
	public void onRelease(Point p)
	{
		

		if(start!=null&&p!=null&& p.distance(start)>14)
		{
			
			target.add(creators.get(nr).creator.apply(new Point[] {start,p}));
		}
		start=null;
		toDraw=null;
	}
	public void stop()
	{
		start=null;
		toDraw=null;
	}
	public void onDragged(Point point) {
		toDraw=null;
		
		if(start!=null)
		{	
			p=point;
			toDraw=creators.get(nr).drawer.apply(new Point[] {start,point});
			
		}
		
	}
	public void next(Point point)
	{
		counter=200;
	nr++;
	nr+=creators.size();
	nr%=creators.size();
	menuColors.clear();
	for(@SuppressWarnings("unused") Shape s:creators.get(nr).menu.apply(new Point(p.x,p.y-50)))
	{
		menuColors.add(new Color((int)(100+Math.random()*100),(int)(100+Math.random()*100),(int)(100+Math.random()*100),255*counter/200));
	}
	}
	public void previous(Point point)
	{
		counter=200;
		nr--;
		nr+=creators.size();
		nr%=creators.size();
		menuColors.clear();
		for(@SuppressWarnings("unused") Shape s:creators.get(nr).menu.apply(new Point(p.x,p.y-50)))
		{
			menuColors.add(new Color((int)(100+Math.random()*100),(int)(100+Math.random()*100),(int)(100+Math.random()*100),255*counter/200));
		}
		
	}
	
	public void draw(Graphics2D g)
	{
		
		if(toDraw!=null)
		{
		g.setColor(new Color(255,255,255,123));
		for(Shape s : toDraw)
		g.fill(s);
		}
		if(counter>0)
		{
			menuToDraw=creators.get(nr).menu.apply(new Point(p.x,p.y-50));
			g.setColor(new Color(255,255,255,255*counter/200));
			g.fill(new Rectangle(p.x-25,p.y - 75,50,50));
			int i=0;
			for(Shape s: menuToDraw)
			{
				g.setColor(new Color(menuColors.get(i).getRed(),menuColors.get(i).getGreen(),menuColors.get(i++).getBlue(),255*counter/200));
				if(creators.get(nr).toFill)
				g.fill(s);
				else
				{
					g.setStroke(new BasicStroke(2));
				g.draw(s);
				}
				
			}
			counter--;
		}
		
		
	}
	class initialCreator
	{
		Function<Point[],Device> creator;
		Function<Point[],Shape[]> drawer;
		Function<Point,Shape[]> menu;
		boolean toFill=true;
		initialCreator(Function<Point[],Device> c,Function<Point[],Shape[]> d,Function<Point,Shape[]> m,boolean t)
		{
			creator=c;
			drawer=d;
			menu=m;
			toFill=t;
		}
	}
	
}
