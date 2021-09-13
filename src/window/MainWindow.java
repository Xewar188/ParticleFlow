package window;

import playground.Playground;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.Serial;
import java.util.Scanner;

public class MainWindow extends JFrame{

	@Serial
	private static final long serialVersionUID = 1L;
	public static int side = 800;
	private final Timer reDrawer;
	private final Playground play;
	private float particlesPerCell = 4;
	//long lastTime = 0;

	public MainWindow()
	{
		try {
			File config = new File("config.txt");
			Scanner scanner = new Scanner(config);
			side = Integer.parseInt((scanner.next()));
			particlesPerCell = Float.parseFloat(scanner.next());
		}
		catch (Exception ignored) {}
		this.getContentPane().setPreferredSize(new Dimension(side, side));
		
		this.addKeyListener(new KeyboardListener());
		this.setResizable(false);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.addWindowListener(new PostOpenActivator(this));
		this.setLocation(Toolkit.getDefaultToolkit().getScreenSize().width/2 - side/2,
				Toolkit.getDefaultToolkit().getScreenSize().height/2 - side/2);

		play = new Playground();
		this.add(play);

		reDrawer = new Timer(15, evt -> {
		   play.repaint();
		  // System.out.println(1000000000/(System.nanoTime()-lastTime));
		  // lastTime=System.nanoTime();

		});
		this.pack();
	}

	public void postOpen()
	{
		play.postOpen(particlesPerCell);
		reDrawer.start();
	}
}
class PostOpenActivator extends WindowAdapter
{
	private final MainWindow window;

	PostOpenActivator(MainWindow w)
	{
		window = w;
	}
	
 	public void windowOpened(WindowEvent e)
 	{
 		window.postOpen();
 	}
}