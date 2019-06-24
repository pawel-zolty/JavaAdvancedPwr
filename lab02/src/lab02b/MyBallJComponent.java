package lab02b;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MyBallJComponent extends JComponent implements Runnable {
	private static final long serialVersionUID = 2L;
	private JPanel panel;
	private Thread t;
	private int x, y;
	private static int size = 40;
	private int dx = 5, dy = 5;
	
	public MyBallJComponent(JPanel panelParam) {
		this(panelParam, 40, 40);
		panel = panelParam;		
	}
	
	public MyBallJComponent(JPanel panelParam, int xParam, int yParam) {
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
        g.setColor(Color.red);
        g.fillOval(0, 0, size, size);
    }
	
	@Override
	public void run() {		
		while(true) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			this.setLocation(x += dx, y += dy);
			panel.revalidate();
			panel.repaint();
		}
	}
}
