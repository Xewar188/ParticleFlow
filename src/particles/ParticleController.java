package particles;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

import window.MainWindow;

public class ParticleController {

	private final int cellGridSideLength;
	private final ParticleCell[][] grid;

	public ParticleController(int sideLength, float x)
	{
		cellGridSideLength = sideLength / 50; //50 is found experimentally value in which particles behaves in the best way
		grid = new ParticleCell[cellGridSideLength][cellGridSideLength];
		int cellSideLength = sideLength / cellGridSideLength;
		for (int i = 0; i < cellGridSideLength; i++)
			for (int j = 0; j < cellGridSideLength; j++)
			{
				grid[i][j] = new ParticleCell(new Rectangle(i * cellSideLength,
						j * cellSideLength,
						cellSideLength,
						cellSideLength));
			}
		for (int i = 0; i < cellGridSideLength * x; i++)
			for (int j = 0; j < cellGridSideLength * x; j++)
			{
				grid[(int) (i / x)][(int) (j / x)].add(new Particle((int)(i * cellSideLength / x),
														(int)(j * cellSideLength / x),
														this));
			}
		
	}

	public void draw(Graphics2D g)
	{
		BufferedImage img = new BufferedImage(MainWindow.side, MainWindow.side, BufferedImage.TYPE_INT_RGB);
		for (int i = 0; i < cellGridSideLength; i++)
			for (int j = 0; j < cellGridSideLength; j++)
			{
				grid[i][j].draw(img);
			}
		g.drawImage(img, 0, 0,null);
	}

	public void move()
	{
		float maxVel = 0;
		LinkedList<Particle> toMove = new LinkedList<>();
		for (int i = 0; i < cellGridSideLength; i++)
			for (int j = 0; j < cellGridSideLength; j++)
			{
				if (grid[i][j].getMaxVel() > maxVel)
				{
					maxVel = grid[i][j].getMaxVel();
				}
				toMove.addAll(grid[i][j].move());
			}
		if (!toMove.isEmpty())
		{
			int cellSideLength = MainWindow.side / cellGridSideLength;
			for (Particle a : toMove)
			{
				grid[a.x / cellSideLength][a.y / cellSideLength].add(a);
			}
		}
		
		for (int i = 0; i < cellGridSideLength; i++)
			for (int j = 0; j < cellGridSideLength; j++)
				grid[i][j].setColor(maxVel);
	}

	public void update()
	{
	
		for (int i = 0; i < cellGridSideLength; i++)
			for (int j = 0; j < cellGridSideLength; j++)
				grid[i][j].update();
	}
	
	public LinkedList<Particle> getParticles(I condition,Point center)
	{
		LinkedList<Particle> particles = new LinkedList<>();
		int cellSideLength = MainWindow.side / cellGridSideLength;
		int i = center.x / cellSideLength;
		int j = center.y / cellSideLength;

		if ( grid[i][j].body.contains(center))
		{
			for (int k = i - 1; k <= i + 1; k++)
				for (int l = j - 1; l <= j + 1; l++)
				{
					if (k >= 0 && k < cellGridSideLength && l >= 0 && l < cellGridSideLength)
						particles.addAll(grid[k][l].getParticles(condition));
				}
		}
		return particles;
	}

	public LinkedList<Particle> getParticles(I condition)
	{
		LinkedList<Particle> particles = new LinkedList<>();
		
		for (int i = 0; i < cellGridSideLength; i++)
			for (int j = 0; j < cellGridSideLength; j++)
			{
				if (condition.check(new Point((int)grid[i][j].body.getX(), (int)grid[i][j].body.getY()))
					|| condition.check(new Point((int)grid[i][j].body.getMaxX(), (int)grid[i][j].body.getY()))
					|| condition.check(new Point((int)grid[i][j].body.getX(), (int)grid[i][j].body.getMaxY()))
					|| condition.check(new Point((int)grid[i][j].body.getMaxX(), (int)grid[i][j].body.getMaxY())))
				{
					particles.addAll(grid[i][j].getParticles(condition));
				}
			}
		
		return particles;
	}

	public LinkedList<Particle> getParticles(I condition,Rectangle range)
	{
		LinkedList<Particle> particles = new LinkedList<>();
		
		for (int i = 0; i < cellGridSideLength; i++)
			for (int j = 0; j < cellGridSideLength; j++)
			{
				if(grid[i][j].body.intersects(range))
				{
					particles.addAll(grid[i][j].getParticles(condition));
				}
			}
		
		return particles;
	}

	@FunctionalInterface
	public interface I
	{
		boolean check(Point a);
	}
}
