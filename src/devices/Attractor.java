package devices;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Point;

import particles.Particle;
import particles.ParticleControler;

public class Attractor extends Repulsor{
	static boolean toFill=false;
	Attractor(Point p, Point f) {
		super(p, f);
		
	}
	@Override
	void update(ParticleControler p) {
		
		for(Particle a:p.getParticles(b->body.getLocation().distance(b)<r))
				{
				
				if(body.distance(a)!=0&&!this.contains(a))
				{
				a.velX+=Math.log(r)/5*(body.x-a.x)/body.distance(a)/(body.distance(a)+1);
				a.velY+=Math.log(r)/5*(body.y-a.y)/body.distance(a)/(body.distance(a)+1);
				}
				
				
				}
		
	}
	static Attractor create(Point[] p)
	{
		
		return new Attractor(p[0],p[1]);
	}
	
	void draw(Graphics2D g) {
		g.setColor(c);
		g.setStroke(new BasicStroke(2));
		g.drawOval(body.x-r1, body.y-r1, r1*2, r1*2);
		
	}
}
