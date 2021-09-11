package particles;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.LinkedList;

public class ParticleCell {

	Rectangle body;
	HashMap<Integer,Particle> content=new HashMap<Integer,Particle>();
	int locx,locy;
	ParticleControler master;
	ParticleCell(Rectangle body,int i, int j,ParticleControler m)
	{
		this.body=body;
		locx=i;
		locy=j;
		master=m;
	}
	boolean contains(Particle a)
	{
		
		return body.contains(a);
	}
	void add(Particle a)
	{
		
	    content.put(a.id, a);
	}
	public void draw(BufferedImage img) {
		for(Particle a : content.values())
		{
			a.draw(img);
		}
		
	}
	public LinkedList<Particle> move()
	{
		LinkedList<Particle> deleted=new LinkedList<Particle>();
		LinkedList<Integer> toRemove=new LinkedList<Integer>();
		for(Particle a : content.values())
		{
			a.move();
			if(!contains(a))
			{
				deleted.add(a);
				toRemove.add(a.id);
			}
			
		}
		for(Integer a : toRemove)
		{
			content.remove(a);
		}
		return deleted;
	}
	public void update()
	{
		
		for(Particle a : content.values())
		{
			a.update();
		}
		
	}
	public LinkedList<Particle> getParticles(ParticleControler.I condition)
	{	
		LinkedList<Particle> particles = new LinkedList<Particle>();
	
		for(Particle a : content.values())
		{
			if(condition.check(a))
				particles.add(a);
			
		}
		
		return particles;
		
	}
	public float getMaxVel()
	{
		float f=0;
		for(Particle a : content.values())
		{
			if(a.getVel()>f)
				f=a.getVel();
		}
		return f;
	}
	public void setColor(float maxVel)
	{
		for(Particle a : content.values())
		{
			a.setColor(maxVel);
			
			
		}
	}
	
	
}
