package devices;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Arc2D;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import particles.Particle;
import particles.ParticleControler;

public class Mover extends Repulsor {

	Color c2;
	Arc2D.Double attract, repel, first,second;
	Mover(Point p, Point f) {
		super(p, f);
		Vector2D v=new Vector2D(f.x-p.x, f.y-p.y);
		c2=new Color(255-c.getRed(),255-c.getGreen(),255-c.getBlue());
		double angle=Vector2D.angle(v, new Vector2D(1,0))*180/Math.PI;
		if(v.getY()>0)
		{
			angle*=-1;
		}
		attract=new Arc2D.Double(2*p.x-f.x-r,2*p.y-f.y-r,2*r,2*r,angle-90,180,Arc2D.PIE);
		repel=new Arc2D.Double(f.x-r,f.y-r,2*r,2*r,angle+90,180,Arc2D.PIE);
		first=new Arc2D.Double(p.x-r1,p.y-r1,2*r1,2*r1,angle+90,180,Arc2D.PIE);
		second=new Arc2D.Double(p.x-r1,p.y-r1,2*r1,2*r1,angle-90,180,Arc2D.PIE);
	}

	@Override
	void update(ParticleControler p) {
		
		for(Particle a:p.getParticles(b->attract.contains(b)))
		{
		
			if(body.distance(a)!=0&&!this.contains(a))
			{
			a.velX+=Math.log(r)/3*(body.x-a.x)/body.distance(a)/(body.distance(a)+1);
			a.velY+=Math.log(r)/5*(body.y-a.y)/body.distance(a)/(body.distance(a)+1);
			}
		
		}
		for(Particle a:p.getParticles(b->repel.contains(b)))
		{
		
			if(body.distance(a)!=0)
			{
			a.velX-=Math.log(r)/2*(body.x-a.x)/body.distance(a)/(body.distance(a)+1);
			a.velY-=Math.log(r)/2*(body.y-a.y)/body.distance(a)/(body.distance(a)+1);
			}
		
		}
	}
	
	@Override
	void draw(Graphics2D g) {
		
		g.setColor(c);
		g.fill(first);
		g.setColor(c2);
		g.fill(second);
		
	}
	static Mover create(Point[] p)
	{
		
		return new Mover(p[0],p[1]);
	}
	public static Shape[] createAffectedShape(Point[] a) {
		if(a[1].x-a[0].x==0&& a[1].y-a[0].y==0)
		{
			return null;
		}
		Vector2D v=new Vector2D(a[1].x-a[0].x, a[1].y-a[0].y);
		double angle=Vector2D.angle(v, new Vector2D(1,0))*180/Math.PI;
		if(v.getY()>0)
		{
			angle*=-1;
		}
		return new Shape[]{new Arc2D.Double(2*a[0].x-a[1].x-a[0].distance(a[1]),2*a[0].y-a[1].y-a[0].distance(a[1]),2*a[0].distance(a[1]),2*a[0].distance(a[1]),angle-90,180,Arc2D.PIE)
				,new Arc2D.Double(a[1].x-a[0].distance(a[1]),a[1].y-a[0].distance(a[1]),2*a[0].distance(a[1]),2*a[0].distance(a[1]),angle+90,180,Arc2D.PIE)};
	}
	public static Shape[] createMenuShape(Point a) {
		
		double angle=45;
		return new Shape[]{new Arc2D.Double(a.x-15,a.y-15,2*15,2*15,angle+90,180,Arc2D.PIE)
				,new Arc2D.Double(a.x-15,a.y-15,2*15,2*15,angle-90,180,Arc2D.PIE)};
	}
}
