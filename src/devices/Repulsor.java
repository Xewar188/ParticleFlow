package devices;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

import particles.Particle;
import particles.ParticleControler;

public class Repulsor extends Device {

	Color c;
    Point body;
    double r;
	final int r1=15;
	Repulsor(Point p, Point f) {
		super(p, f);
		body=new Point(p.x,p.y);
		r=p.distance(f);
		c=new Color((int)(180+Math.random()*40),(int)(180+Math.random()*40),(int)(180+Math.random()*40));
		
	}
	@Override
	void update(ParticleControler p) {
		
		for(Particle a:p.getParticles(b->body.getLocation().distance(b)<r))
				{
				
				if(body.distance(a)!=0)
				{
				a.velX-=Math.log(r)/3*(body.x-a.x)/body.distance(a)/(body.distance(a)+1);
				a.velY-=Math.log(r)/3*(body.y-a.y)/body.distance(a)/(body.distance(a)+1);
				}
				
				
				}
		
	}
	
	void draw(Graphics2D g) {
		g.setColor(c);
		g.fillOval(body.x-r1, body.y-r1, r1*2, r1*2);
		
	}
	@Override
	boolean onClick() {
		return true;
	}
	@Override
	boolean contains(Point p) {
		
		return body.getLocation().distance(p)<r1;
	}
	static Repulsor create(Point[] p)
	{
		
		return new Repulsor(p[0],p[1]);
	}
	public static Shape[] createAffectedShape(Point[] a) {
		double r=a[0].distance(a[1]);
		return new Shape[] {new Ellipse2D.Float((int)(a[0].x-r), (int)(a[0].y-r), (int)(2*r), (int)(2*r))};
	}
	public static Shape[] createMenuShape(Point a) {
		return new Shape[] {new Ellipse2D.Float((a.x-15), (a.y-15), 30, 30)};
	}
	

}
