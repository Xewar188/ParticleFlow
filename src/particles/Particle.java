package particles;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.Serial;

import window.MainWindow;

public class Particle extends Point {

	
	

	@Serial
	private static final long serialVersionUID = 1L;
	private float velX;
	private float velY;
	private Color color;
	private static int count = 0;
	public final int id; //since it is final it can only be read
	private float realX;
	private float realY;
	private final ParticleController master;

	public Particle(int x, int y, ParticleController m)
	{
		id = count++;
		this.x = x;
		this.y = y;
		realX = x;
		realY = y;
		color = Color.getHSBColor(1f/3f,1, 1);
		velX = (float) Math.random() * 2 - 1;
		velY = (float) Math.random() * 2 - 1;
		master = m;
	}

	public void draw(BufferedImage img)
	{
		for (int i = -1; i <= 1; i++)
			for (int j = -1; j < 2; j++)
				if (j * i != 0)
					img.setRGB(x + i, y + j, color.darker().getRGB());
				else
					img.setRGB(x + i, y + j, color.getRGB());
	}
	public void move()
	{
		realX += velX;
		realY += velY;
		x = (int) realX;
		y = (int) realY;
		velX *= 0.99;
		velY *= 0.99;
		if (realX >= MainWindow.side - 2)
		{
			setX(2 * MainWindow.side - realX - 4);
			velX *= -1;
		}
		if (realY >= MainWindow.side - 2)
		{
			setY(2 * MainWindow.side - realY - 4);
			velY *= -1;
		}
		if (realX < 1)
		{
			setX(-realX + 2);
			velX *= -1;
		}
		if(realY < 1)
		{
			setY(-realY + 2);
			velY *= -1;
		}
		if(Math.abs(velX) < 0.01)
		{
			velX = 0;
		}
		if(Math.abs(velY) < 0.01)
		{
			velY = 0;
		}
	}
	public float getVel()
	{
		return (float) Math.sqrt(velX * velX + velY * velY);
	}
	public void setColor(float maxVel)
	{
		color = Color.getHSBColor(1f/3f - 1f/3f * getVel() / maxVel, 1,  1);
	}
	public void setX(float pos)
	{
		realX = Math.max(1, Math.min(MainWindow.side - 2, pos));
		x = (int) realX;
	}
	public void setY(float pos)
	{
		realY = Math.max(1, Math.min(MainWindow.side - 2, pos));
		y = (int) realY;
	}
	public float getXVel() {
		return velX;
	}
	public float getYVel() {
		return velY;
	}
	public void changeXVel(float delta) {
		this.velX += delta;
	}
	public void changeYVel(float delta) {
		this.velY += delta;
	}
	public void reverseXVel() {
		this.velX *= -1;
	}
	public void reverseYVel() {
		this.velY *= -1;
	}
	void update()
	{
		
			
		for (Particle a : master.getParticles(b -> b.distance(this) < 15,this))
		{
			float cos = Math.signum(a.realX - realX), sin = Math.signum(a.realY - realY);
			if (a.distance(this) > 0)
			{
				 sin = (float) ((a.realY-realY) / a.distance(this));
				 cos = (float) ((a.realX-realX) / a.distance(this));
			}
			
			a.velX += 1.0 / (a.distance(this) + 1) * cos;
			a.velY += 1.0 / (a.distance(this) + 1) * sin;
		}
	}
	
}
