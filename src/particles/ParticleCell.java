package particles;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.LinkedList;

public class ParticleCell {

	public final Rectangle body; //can only be read
	private final HashMap<Integer,Particle> content = new HashMap<>();

	public ParticleCell(Rectangle body)
	{
		this.body = body;
	}

	public boolean contains(Particle a)
	{
		return body.contains(a);
	}

	public void add(Particle a)
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
		LinkedList<Particle> deleted= new LinkedList<>();
		LinkedList<Integer> toRemove= new LinkedList<>();

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

	public LinkedList<Particle> getParticles(ParticleController.I condition)
	{	
		LinkedList<Particle> particles = new LinkedList<>();
	
		for(Particle a : content.values())
		{
			if(condition.check(a))
				particles.add(a);
		}
		return particles;
	}

	public float getMaxVel()
	{
		float maxVel = 0;
		for(Particle a : content.values())
		{
			if(a.getVel() > maxVel)
				maxVel = a.getVel();
		}
		return maxVel;
	}

	public void setColor(float maxVel)
	{
		for(Particle a : content.values())
		{
			a.setColor(maxVel);
		}
	}
	
	
}
