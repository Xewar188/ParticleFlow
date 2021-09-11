package devices;

import java.awt.Graphics2D;
import java.awt.Point;
import java.util.LinkedList;

import particles.ParticleControler;

public class DeviceStorage {

	ParticleControler main;
	LinkedList<Device> devices=new LinkedList<Device>();
	public DeviceStorage(ParticleControler c)
	{
		main=c;
	}
	void add(Device x)
	{
		devices.add(x);
	}
	public void onClick(Point p)
	{
		Device toRemove = null;
		for(Device d:devices)
		{
			if(d.contains(p))
			if(d.onClick())
			{
				toRemove=d;
			}
			
		}
		
			devices.remove(toRemove);
	}
	public void draw(Graphics2D g)
	{
		for(Device d:devices)
		{
			d.draw(g);
		}
	}
	public void update()
	{
		for(Device d:devices)
		{
			d.update(main);
		}
	}
}
