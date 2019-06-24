package lab04_zoltaniecki;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
//import java.beans.PropertyChangeListener;
//import java.beans.PropertyChangeSupport;
//import java.beans.VetoableChangeSupport;
import java.io.Serializable;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ClockZiarno extends JPanel implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final int XPREFSIZE = 622;
	private static final int YPREFSIZE = 433;
	
	//private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
	// private VetoableChangeSupport vetoableSupport = new
	// VetoableChangeSupport(this);
	private String clockName = "Moje budziki";
	private Clock defaultClock = new Clock();
	private Clock defaultClock2 = new Clock();
	private Clock defaultClock3 = new Clock();
	private Clock[] alarms = { defaultClock, defaultClock2, defaultClock3};

	public ClockZiarno() {
		defaultClock.hours = 7;
		defaultClock.minutes = 0;
		defaultClock.seconds = 0;
		defaultClock2.hours = 8;
		defaultClock2.minutes = 30;
		defaultClock2.seconds = 0;
		defaultClock3.hours = 11;
		defaultClock3.minutes = 0;
		defaultClock3.seconds = 0;
		this.setClockName("Moje budziki: " + ": Masz " + (alarms.length) + " budziki");
	}

	/*public void addPropertyChangeListener(PropertyChangeListener listener) {
		changeSupport.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		changeSupport.removePropertyChangeListener(listener);
	}*/

	public String getClockName() {
		return clockName;
	}

	public void setClockName(String clockName) {
		this.clockName = clockName;
	}

	public Clock[] getAlarms() {
		return alarms;
	}

	public void setAlarms(Clock[] clockParam) {	
		JOptionPane.showConfirmDialog(this, "here");
		this.alarms = new Clock[clockParam.length];
		for(int i=0; i<clockParam.length;i++) {
			this.alarms[i]=new Clock();
			this.alarms[i].hours = clockParam[i].hours;
			this.alarms[i].minutes = clockParam[i].minutes;
			this.alarms[i].seconds = clockParam[i].seconds;
		}
		
	}

	public void setAlarms(int i, Clock clk) {
		if (0 <= i && i < alarms.length) {
			this.alarms[i] = new Clock();
			this.alarms[i].hours = clk.hours;
			this.alarms[i].minutes = clk.minutes;
			this.alarms[i].seconds = clk.seconds;
		}
	}
	
	public Clock getAlarms(int i) {
		if (0 <= i && i < alarms.length)
			return alarms[i];
		Clock newClk = new Clock();
		this.alarms[i].hours = 1;
		this.alarms[i].minutes = 1;
		this.alarms[i].seconds = 1;
		return newClk;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);		
		Graphics2D g2 = (Graphics2D) g;

		Rectangle2D componentBounds = this.getBounds();
		double componentWidth = componentBounds.getWidth();
		double componentHeight = componentBounds.getHeight();

		if (alarms == null || alarms.length == 0) {
			drawClockInMiddle(g2, componentWidth, componentHeight);
			return;
		}
		
		Rectangle2D titleBounds = addTitleHeader(g2, componentWidth);

		double top = titleBounds.getHeight();
		int paddingX = 10, paddingY = 15;
		
		for (int i = 0; i < alarms.length; i++) {
			Rectangle2D clockRect = createClockRectangle(componentBounds, titleBounds, top, paddingX, paddingY, i);
			g2.setColor(i % 2 == 0 ? Color.green : Color.red);			
			addAlarm(g2, alarms[i], clockRect);
		}

	}

	private Rectangle2D createClockRectangle(Rectangle2D bounds, Rectangle2D titleBounds, double top, int paddingX,
			int paddingY, int i) {
		double width = bounds.getWidth() - 2 * paddingX;
		double height = (bounds.getHeight() - top - 4 * paddingY) / (alarms.length);
		
		double x1 = titleBounds.getMinX() + paddingX;
		double y1 = top + (height + paddingY) * (i) + paddingY;
		
		Rectangle2D clockRect = new Rectangle2D.Double(x1, y1, width, height);
		return clockRect;
	}

	private void addAlarm(Graphics2D g2, Clock alarm, Rectangle2D clockRect) {
		Font clockFont = new Font("SansSerif", Font.BOLD, 120);
		FontRenderContext context = g2.getFontRenderContext();
		
		String alarmTxt = alarm.toString();
		Rectangle2D alarmBounds = clockFont.getStringBounds(alarmTxt, context);

		double alarmWidth = alarmBounds.getWidth();
		//double alarmHeight = alarmBounds.getHeight();
		double clockY = clockRect.getY() + clockRect.getHeight();// + (0.5 * (clockRect.getHeight() - alarmHeight ));
		double clockX = (clockRect.getWidth() - alarmWidth) / 2; 
				
		g2.setFont(clockFont);
		g2.drawString(alarmTxt, (float) clockX, (float) clockY);
		
		Stroke oldStroke = g2.getStroke();
		g2.setStroke(new BasicStroke(5));
		g2.draw(clockRect);
		g2.setStroke(oldStroke);
	}	
	
	private Rectangle2D addTitleHeader(Graphics2D g2, double clientWidth) {
		Font titleFont = new Font("SansSerif", Font.BOLD, 20);
		FontRenderContext context = g2.getFontRenderContext();
		Rectangle2D titleBounds = titleFont.getStringBounds(clockName, context);

		double titleWidth = titleBounds.getWidth();
		double nameY = -titleBounds.getY();
		double nameX;
		nameX = (clientWidth - titleWidth) / 2;

		g2.setFont(titleFont);
		g2.drawString(clockName, (float) nameX, (float) nameY);
		return titleBounds;
	}

	private void drawClockInMiddle(Graphics2D g2, double clientWidth, double clientHeight) {
		double clientSmallerSize = clientWidth > clientHeight ? clientHeight : clientWidth;
		clientSmallerSize /= 2;
		
		g2.fillOval( (int) ((clientWidth / 2) - (clientSmallerSize / 2)), 
				(int) ((clientHeight / 2) - (clientSmallerSize / 2)), 
				(int) clientSmallerSize, 
				(int) clientSmallerSize);
	}
	
	public Dimension getPreferredSize() {
		return new Dimension(XPREFSIZE, YPREFSIZE);
	}
}

 class Clock {
	public int hours;
	public int minutes;
	public int seconds;
	
	
	@Override
	public String toString() {
		String hourTxt = Integer.toString(this.hours);
		hourTxt = toTwoDigits(hourTxt); 
		String minTxt = Integer.toString(this.minutes);
		minTxt = toTwoDigits(minTxt); 
		String secTxt = Integer.toString(this.seconds);
		secTxt = toTwoDigits(secTxt);
		
		String alarmTxt =  hourTxt + ":" + minTxt + ":" + secTxt;
		return alarmTxt;
	}
	private String toTwoDigits(String number) {
		return number.length() == 2 ? number : "0" + number;
	}
}
