package lab14;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class AccessibleApp {

	private JFrame frame;
	private JTextField aTextField = new JTextField();
	private JTextField bTextField = new JTextField();
	private JTextField cTextField = new JTextField();
	//private JTextField dTextField = new JTextField();
	private JTextField x1textField = new JTextField();
	private JTextField x2textField = new JTextField();
	//private JTextField x3textField = new JTextField();

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AccessibleApp window = new AccessibleApp();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public AccessibleApp() {
		initialize();
	}

	private void initialize() {
		initFrame();

		addCountButton();

		addCubicRootField();

		addCubicParam(aTextField, "Parameter A Text Field", "Parameter A of Cubic Equation");
		aTextField.setBounds(12, 13, 55, 20);

		//JLabel labelA = new JLabel("x\u00B2 +");
		//labelA.getAccessibleContext().setAccessibleDescription("Parameter A label");
		//labelA.setBounds(79, 19, 24, 14);
		//frame.getContentPane().add(labelA);

		addCubicParam(bTextField, "Parameter B Text Field", "Parameter B of Cubic Equation");
		bTextField.setBounds(115, 13, 55, 20);

		JLabel labelB = new JLabel("x\u00B2 +");
		labelB.getAccessibleContext().setAccessibleDescription("Parameter B label");
		labelB.setBounds(79, 19, 24, 14);
		//labelB.setBounds(182, 16, 24, 14);
		frame.getContentPane().add(labelB);

		addCubicParam(cTextField, "Parameter D Text Field", "Parameter C of Cubic Equation");
		cTextField.setBounds(218, 13, 55, 20);
		
		JLabel labelC = new JLabel("x +");
		labelC.getAccessibleContext().setAccessibleDescription("Parameter C label");
		labelC.setBounds(182, 16, 24, 14);
		//labelC.setBounds(285, 16, 24, 14);
		frame.getContentPane().add(labelC);

		//addCubicParam(dTextField, "Parameter D Text Field", "Parameter D of Cubic Equation");
		//dTextField.setBounds(321, 13, 55, 20);
	}

	private JTextField addCubicParam(JTextField textField, String name, String desc) {
		frame.getContentPane().add(textField);
		textField.getAccessibleContext().setAccessibleName(name);
		textField.getAccessibleContext().setAccessibleName(desc);
		textField.setColumns(10);

		return textField;
	}

	private void addCubicRootField() {
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "First root",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_1.setBounds(10, 82, 369, 43);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);

		addRootTextFieldToPanel(x1textField, panel_1, "First Cubic Root");

		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Second root",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_2.setBounds(10, 127, 369, 43);
		frame.getContentPane().add(panel_2);
		panel_2.setLayout(null);

		addRootTextFieldToPanel(x2textField, panel_2, "Second Cubic Root");

		/*JPanel panel_3 = new JPanel();
		panel_3.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Third root",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_3.setBounds(10, 175, 369, 43);
		frame.getContentPane().add(panel_3);
		panel_3.setLayout(null);
*/
		//addRootTextFieldToPanel(x3textField, panel_3, "Third Cubic Root");		
	}

	private void addRootTextFieldToPanel(JTextField textField, JPanel panel_2, String desc) {
		textField.getAccessibleContext().setAccessibleName(desc);
		textField.getAccessibleContext().setAccessibleDescription(desc);
		textField.setText(desc);
		textField.setEditable(false);
		textField.setColumns(10);
		textField.setBounds(5, 16, 364, 20);
		panel_2.add(textField);
	}

	private void addCountButton() {
		JButton btnSolveEquation = new JButton("Count equation roots");
		btnSolveEquation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				solveEquation();
			}
		});
		btnSolveEquation.setBounds(89, 46, 197, 23);
		btnSolveEquation.getAccessibleContext().setAccessibleName("Button Count Roots");
		btnSolveEquation.getAccessibleContext().setAccessibleDescription("Count Cubic Equation Roots");

		frame.getContentPane().add(btnSolveEquation);
	}

	private void initFrame() {
		frame = new JFrame();
		frame.setTitle("Accessibility App");
		frame.setResizable(false);
		frame.setBounds(100, 100, 397, 263);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.getAccessibleContext().setAccessibleName("Cubic Equation Application");
		frame.getAccessibleContext().setAccessibleDescription("Application which count cubic equation");
	}

	private String getStringNumber(JTextField textField) {
		String stringNumber = textField.getText();

		if (stringNumber.equals(""))
			stringNumber = "0";

		stringNumber.replace(',', '.');

		return stringNumber;
	}

	private void solveEquation() {
		String aString = getStringNumber(aTextField);
		String bString = getStringNumber(bTextField);
		String cString = getStringNumber(cTextField);
		//String dString = getStringNumber(dTextField);

		try {
			BigDecimal a = new BigDecimal(aString);
			BigDecimal b = new BigDecimal(bString);
			BigDecimal c = new BigDecimal(cString);
			//BigDecimal d = new BigDecimal(dString);

			CubicEquation cubicEquationSolver = new CubicEquation(a, b, c);

			ArrayList<BigDecimal> roots = cubicEquationSolver.getRoots();

			switch (roots.size()) {
			/*case 3:
				x1textField.setEnabled(true);
				x2textField.setEnabled(true);
				x3textField.setEnabled(true);

				x1textField.setText(roots.get(0).toString());
				x2textField.setText(roots.get(1).toString());
				x3textField.setText(roots.get(2).toString());
				break;*/
			case 2:
				x1textField.setEnabled(true);
				x2textField.setEnabled(true);

				x1textField.setText(roots.get(0).toString());
				x2textField.setText(roots.get(1).toString());
				break;
			case 1:
				x1textField.setEnabled(true);
				x2textField.setEnabled(false);

				x1textField.setText(roots.get(0).toString());
				x2textField.setText("");
				break;
			case 0:
				x1textField.setEnabled(false);
				x2textField.setEnabled(false);

				x1textField.setText("");
				x2textField.setText("");

				JOptionPane.showMessageDialog(null, "Podany trójmian kwadratowy nie ma pierwiastków.", "Uwaga",
						JOptionPane.INFORMATION_MESSAGE);
			}
		} catch (NumberFormatException e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage(), "B³¹d", JOptionPane.ERROR_MESSAGE);
		}
	}
}
