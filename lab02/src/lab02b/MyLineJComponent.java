package lab02b;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Line2D;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MyLineJComponent extends JComponent implements Runnable {
	private static final long serialVersionUID = 3L;
	private JPanel panel;
	private Thread t;
	private int x, y;
	private static int size = 10;
	private int counter = 0;
	
	public MyLineJComponent(JPanel panelParam) {
		this(panelParam, 40, 40);
		panel = panelParam;		
	}
	
	public MyLineJComponent(JPanel panelParam, int xParam, int yParam) {
		super();
		panel = panelParam;
		x = xParam; 
		y = yParam;
		this.setSize(new Dimension(size, size));
		this.setLocation(x, y);
	}
	
	public void start() {
		t = new Thread(this);
		t.start();
	}
	
	@Override
	public void paintComponent(Graphics g)
    {
		super.paintComponent(g);
        g.setColor(Color.blue);        
        g.drawLine(x, y, x + counter, y + counter);
    }
	
	@Override
	public void run() {		
		while(true) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			counter += 10;
			this.setSize(size + counter, size + counter);
			panel.revalidate();
			panel.repaint();
		}
	}
}
