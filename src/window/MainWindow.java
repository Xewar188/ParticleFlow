package window;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.Timer;

import playground.Playground;

public class MainWindow extends JFrame{

	private static final long serialVersionUID = 1L;
	public static final int width=800,height=800;
	Timer reDrawer;
	Playground play;
	long lastTime=0;
	public MainWindow()
	{
		this.getContentPane().setPreferredSize(new Dimension(width,height));
		
		this.addKeyListener(new KeyboardListener());
		this.setResizable(false);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.addWindowListener(new PostOpenActivator(this) );
		play=new Playground();
		this.add(play);
		reDrawer=new Timer(15,new ActionListener() {
		      public void actionPerformed(ActionEvent evt) {
		    	 play.repaint();
		    	// System.out.println(1000000000/(System.nanoTime()-lastTime));
		    	// lastTime=System.nanoTime();
		 
		      }
		      });
		this.pack();
	}
	public void postOpen()
	{
		play.postOpen();
		reDrawer.start();
	}
}
class PostOpenActivator extends WindowAdapter
{
	MainWindow window;
	PostOpenActivator(MainWindow w)
	{
		window=w;
	}
	
 	public void windowOpened(WindowEvent e)
 	{
 		window.postOpen();
 	}
}