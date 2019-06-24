package lab02b;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

public class Lines extends JPanel implements Runnable {

	private static final long serialVersionUID = 4L;
	private final ArrayList<Line> lines = new ArrayList<Line>();
	private JPanel panel;
	private int x, y;
	private int size = 10, counter = 0;
	private Thread t;
	
	public Lines(JPanel panelParam)
	{
		this(panelParam, 40, 40);
		panel = panelParam;
	}
	
	public Lines(JPanel panelParam, int xParam, int yParam) {
		super();
		panel = panelParam;
		x = xParam; 
		y = yParam;
		addLine(x, y, size, size);
		this.setSize(new Dimension(size, size));
		this.setLocation(x, y);
	}
	
	public void start() {
		t = new Thread(this);
		t.start();
	}
	
    public void addLine(int x1, int y1, int x2, int y2) {
        this.lines.add(new Line(x1, y1, x2, y2));
    }

    public void paintComponent(Graphics g) {
    	int col = 0;
        for(final Line r : lines) {
        	switch(col) {
        	case 0 : g.setColor(Color.green); break;
        	case 1 : g.setColor(Color.blue); break;
        	case 2 : g.setColor(Color.black); break;
        	case 3 : g.setColor(Color.yellow); break;
        	case 4 : g.setColor(Color.cyan); break;
        	case 5 : g.setColor(Color.darkGray); break;
        	case 6 : g.setColor(Color.magenta); break;
        	case 7 : g.setColor(Color.gray); break;
        	case 8 : g.setColor(Color.white); break;
        	case 9 : g.setColor(Color.LIGHT_GRAY); break;
        	case 10 : g.setColor(Color.orange); break;
        	case 11 : g.setColor(Color.pink); break;
        	default: g.setColor(Color.red); 
        	}
        	r.paint(g);
        	col++;
        }
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
			for(Line l : lines) {
				l.x2 += counter;
				l.y2 += counter;
			}
			addLine(x, y, x + counter, y + counter * 5);
			this.setSize(size + counter, size + counter * 5);
			panel.revalidate();
			panel.repaint();
		}
	}
}

class Line {
    public final int x1;
    public int x2;
    public final int y1;
    public  int y2;
    public Line(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
    }
    public void paint(Graphics g) {
        g.drawLine(0, 0, this.x2, this.y2);
    }
}
