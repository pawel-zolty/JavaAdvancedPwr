package lab02b;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class MyMessageDialogJComponent extends JComponent {

	private static final long serialVersionUID = 1L;
	private JFrame frame;
	
	public MyMessageDialogJComponent(JFrame frameParam) {
		super();
		frame = frameParam;		
	}	
	
	public void start() {
		JOptionPane.showMessageDialog(frame, "Hej 1");
	}
	
}
