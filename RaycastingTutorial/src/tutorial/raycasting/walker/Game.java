/* AUTHOR: Jonathan Walker
 * COURSE: Extracurricular
 * PURPOSE: Following a tutorial about raycasting graphics.
 * STARTDATE: 02/19/21
 */
package tutorial.raycasting.walker;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import javax.swing.JFrame;
public class Game extends JFrame implements Runnable
{
	// attributes
	private static final long serialVersionUID = 1L;
	public int mapWidth = 15;
	public int mapHeight = 15;
	private Thread thread;
	private boolean running;
	private BufferedImage image;
	public int[] pixels;
	public static int[][] map =
		{
				{1,1,1,1,1,1,1,1,2,2,2,2,2,2,2},
				{1,0,0,0,0,0,0,0,2,0,0,0,0,0,2},
				{1,0,3,3,3,3,3,0,0,0,0,0,0,0,2},
				{1,0,3,0,0,0,3,0,2,0,0,0,0,0,2},
				{1,0,3,0,0,0,3,0,2,2,2,0,2,2,2},
				{1,0,3,0,0,0,3,0,2,0,0,0,0,0,2},
				{1,0,3,3,0,3,3,0,2,0,0,0,0,0,2},
				{1,0,0,0,0,0,0,0,2,0,0,0,0,0,2},
				{1,1,1,1,1,1,1,1,4,4,4,0,4,4,4},
				{1,0,0,0,0,0,1,4,0,0,0,0,0,0,4},
				{1,0,0,0,0,0,1,4,0,0,0,0,0,0,4},
				{1,0,0,2,0,0,1,4,0,3,3,3,3,0,4},
				{1,0,0,0,0,0,1,4,0,3,3,3,3,0,4},
				{1,0,0,0,0,0,0,0,0,0,0,0,0,0,4},
				{1,1,1,1,1,1,1,4,4,4,4,4,4,4,4}
			};
	
	// constructor
	public Game()
	{
		thread = new Thread(this);
		image = new BufferedImage(640, 480, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
		setSize(640, 480);
		setResizable(false);
		setTitle("3D Engine");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBackground(Color.black);
		setLocationRelativeTo(null);
		setVisible(true);
		start();
	}// END constructor
	
	// behaviors
	private synchronized void start()
	{
		running = true;
		thread.start();
	}// END start
	
	public synchronized void stop()
	{
		running = false;
		try
		{
			thread.join();
		}// END try
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}// END catch
	}// END stop
	
	public void render()
	{
		BufferStrategy bs = getBufferStrategy();
		if (bs == null)
		{
			createBufferStrategy(3);
			return;
		}// END if
		Graphics g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
		bs.show();
	}// END render
	
	public void run()
	{
		long lastTime = System.nanoTime();
		final double ns = 1000000000.0 / 60.0;// 60 times per second
		double delta = 0.0;
		requestFocus();
		while (running)
		{
			long now = System.nanoTime();
			delta = delta + ((now - lastTime) / ns);
			lastTime = now;
			while (delta >= 1)// Make sure update is only happening 60 times a second
			{
				delta--;// Handles all of the logic restricted time
			}// END while
			render();// Displays to the screen unrestricted time
		}// END while
	}// END run
	
}//END Game class
