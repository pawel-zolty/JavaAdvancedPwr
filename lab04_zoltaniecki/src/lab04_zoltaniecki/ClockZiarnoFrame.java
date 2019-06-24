package lab04_zoltaniecki;

import java.awt.EventQueue;

import javax.swing.JFrame;

public class ClockZiarnoFrame extends JFrame {

	private static final long serialVersionUID = 155L;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClockZiarnoFrame frame = new ClockZiarnoFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ClockZiarnoFrame() {
		initialize();
	}

	private void initialize() {
		this.setBounds(100, 100, 640, 480);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(null);
		
		ClockZiarno clockZiarno = new ClockZiarno();
		clockZiarno.setBounds(0, 0, 622, 433);
		getContentPane().add(clockZiarno);
		

		
/*		ChartBean chartBean = new ChartBean();
		chartBean.setBounds(10, 11, 583, 300);
		chartBean.setGraphColor(new Color(98, 30, 100));
		frame.getContentPane().add(chartBean);*/
	}
}
