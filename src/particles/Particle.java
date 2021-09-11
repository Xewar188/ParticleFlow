package particles;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;

import window.MainWindow;

public class Particle extends Point {

	
	

	private static final long serialVersionUID = 1L;
	public float velX=0;
	public float velY=0;
	Color color;
	static int count=0;
	public final int id;
	 float realX;
	 float realY;
	ParticleControler master;
	public Particle(int x,int y,ParticleControler m)
	{
		
		id=count++;
		this.x=x;
		this.y=y;
		realX=x;
		realY=y;
		color=Color.getHSBColor(1f/3f,1, 1);
		velX=(float) Math.random()*2-1;
		velY=(float) Math.random()*2-1;
		master=m;
	}

	public void draw(BufferedImage img)
	{
	
		for(int i=-1;i<2;i++)
			for(int j=-1;j<2;j++)
				if(j*i!=0)
		img.setRGB((int)x+i, (int)y+j, color.darker().getRGB());
				else
		img.setRGB((int)x+i, (int)y+j, color.getRGB());
		
		
		
		
	}
	public void move()
	{
		
		realX+=velX;
		realY+=velY;
		x=(int) realX;
		y=(int) realY;
		velX*=0.99;
		velY*=0.99;
		if(realX>=MainWindow.width-2)
		{
			setX(2*MainWindow.width-realX-4);
			velX*=-1;
		}
		if(realY>=MainWindow.height-2)
		{
			setY(2*MainWindow.height-realY-4);
			velY*=-1;
		}
		if(realX<1)
		{
			setX(-realX+2);
			velX*=-1;
		}
		if(realY<1)
		{
			setY(-realY+2);
			velY*=-1;
		}
		if(Math.abs(velX)<0.01)
		{
			velX=0;
		}
		if(Math.abs(velY)<0.01)
		{
			velY=0;
		}
		
	}
	public float getVel()
	{
		return (float) Math.sqrt(velX*velX+velY*velY);
	}
	void setColor(float maxVel)
	{
		color=Color.getHSBColor(1f/3f-1f/3f*getVel()/maxVel,1, 1);
	}
	public void setX(float pos)
	{
		realX=Math.max(1,Math.min(MainWindow.width-2, pos));
		x=(int)realX;
	}
	public void setY(float pos)
	{
		realY=Math.max(1,Math.min(MainWindow.height-2, pos));
		y=(int)realY;
	}
	void update()
	{
		
			
		for(Particle a : master.getParticles(b->b.distance(this)<15,this))
		{
			
			float cos=Math.signum(a.realX-realX),sin=Math.signum(a.realY-realY);
			if(a.distance(this)>0)
			{
				 sin=(float) ((a.realY-realY)/a.distance(this));
				 cos=(float) ((a.realX-realX)/a.distance(this));
			}
			
			a.velX+=2/(a.distance(this)+1)*cos;
			a.velY+=2/(a.distance(this)+1)*sin;
		}
	}
	
}
