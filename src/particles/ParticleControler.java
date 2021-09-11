package particles;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

import window.MainWindow;

public class ParticleControler {

	int n =16,k=4;
	ParticleCell[][] grid=new ParticleCell[n][n];
	public ParticleControler(Rectangle size)
	{
		for(int i=0;i<n;i++)
			for(int j=0;j<n;j++)
			{
				grid[i][j]=new ParticleCell(new Rectangle(i*size.width/n,j*size.height/n,size.width/n,size.height/n),i,j,this);
			}
		for(int i=0;i<n*k;i++)
			for(int j=0;j<n*k;j++)
			{
				grid[i/k][j/k].add(new Particle(i*size.width/n/k,j*size.height/n/k,this));
			}
		
	}
	public void draw(Graphics2D g)
	{
		BufferedImage img=new BufferedImage(MainWindow.width,MainWindow.height,BufferedImage.TYPE_INT_RGB);
		for(int i=0;i<n;i++)
			for(int j=0;j<n;j++)
			{
				grid[i][j].draw(img);
			}
		g.drawImage(img, 0, 0,null);
	}
	public void move()
	{
		float maxVel=0;
		LinkedList<Particle> toMove= new LinkedList<Particle>();
		for(int i=0;i<n;i++)
			for(int j=0;j<n;j++)
			{
				if(grid[i][j].getMaxVel()>maxVel)
				{
					maxVel=grid[i][j].getMaxVel();
				}
				toMove.addAll(grid[i][j].move());
				
			}
		if(!toMove.isEmpty())
		{
			for(Particle a:toMove)
			{
				grid[a.x*n/MainWindow.width][a.y*n/MainWindow.height].add(a);
			}
		}
		
		for(int i=0;i<n;i++)
			for(int j=0;j<n;j++)
				grid[i][j].setColor(maxVel);
	}
	public void update()
	{
	
	for(int i=0;i<n;i++)
		for(int j=0;j<n;j++)
			grid[i][j].update();
	}
	
	public LinkedList<Particle> getParticles(I condition,Point center)
	{
		LinkedList<Particle> particles = new LinkedList<Particle>();
		
		for(int i=0;i<n;i++)
			for(int j=0;j<n;j++)
			{
		if( grid[i][j].body.contains(center))
		{
			
			
			for(int k = i-1;k<i+2;k++)
				for(int l = j-1;l<j+2;l++)
				{
					if(k>=0&&k<n&&l>=0&&l<n)
					particles.addAll(grid[k][l].getParticles(condition));
				}
			break;
		}
			}
		
		return particles;
	}
	public LinkedList<Particle> getParticles(I condition)
	{
		LinkedList<Particle> particles = new LinkedList<Particle>();
		
		for(int i=0;i<n;i++)
			for(int j=0;j<n;j++)
			{
		if(condition.check(new Point((int)grid[i][j].body.getX(),(int)grid[i][j].body.getY()))
			||condition.check(new Point((int)grid[i][j].body.getX()+grid[i][j].body.width,(int)grid[i][j].body.getY()))
			||condition.check(new Point((int)grid[i][j].body.getX(),(int)grid[i][j].body.getY()+grid[i][j].body.height))
			||condition.check(new Point((int)grid[i][j].body.getX()+grid[i][j].body.width,(int)grid[i][j].body.getY()+grid[i][j].body.height)))
		{
			particles.addAll(grid[i][j].getParticles(condition));
		}
			}
		
		return particles;
	}
	public LinkedList<Particle> getParticles(I condition,Rectangle range)
	{
		LinkedList<Particle> particles = new LinkedList<Particle>();
		
		for(int i=0;i<n;i++)
			for(int j=0;j<n;j++)
			{
		if(grid[i][j].body.intersects(range))
		{
			particles.addAll(grid[i][j].getParticles(condition));
		}
			}
		
		return particles;
	}
	@FunctionalInterface 
	static public interface I
	{
		boolean check(Point a);
	}
}
