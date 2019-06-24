package lab04_zoltaniecki;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyEditorSupport;
import java.text.NumberFormat;
import java.text.ParseException;

import javax.swing.AbstractListModel;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


public class ClockArrayEditorPanel2 extends JPanel {

	private static final long serialVersionUID = 1L;

	private PropertyEditorSupport editor;
	private Clock[] array;
	private NumberFormat fmt = NumberFormat.getNumberInstance();
	
	private JList<Clock> elementList = new JList<Clock>();
	private ClockArrayListModel2 model = new ClockArrayListModel2();
	
	private JButton changeAlarmClock = new JButton("Ustaw budzik");
	private JTextField hourTxt = new JTextField("wpisz liczbe", 2);
	private JTextField minTxt = new JTextField("wpisz liczbe", 2);
	private JTextField secTxt = new JTextField("wpisz liczbe", 2);
	
	public ClockArrayEditorPanel2(PropertyEditorSupport clockEditor) {
		editor = clockEditor;
		setArray((Clock[]) clockEditor.getValue());
				
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		gbc.weightx = 100;
		gbc.weighty = 0;
		gbc.fill = GridBagConstraints.HORIZONTAL;	

		gbc.fill = GridBagConstraints.NONE;
	
		
		JOptionPane.showMessageDialog(this, "elo", "", JOptionPane.WARNING_MESSAGE);
		
		Box hoursBox = Box.createHorizontalBox();
		Box minBox = Box.createHorizontalBox();
		Box secBox = Box.createHorizontalBox();
		hoursBox.add(new Label("Godzina: "));
		minBox.add(new Label("Minuta: "));
		secBox.add(new Label("Sekunda: "));
		hoursBox.add(hourTxt);
		minBox.add(minTxt);
		secBox.add(secTxt);
		JPanel northPanel = new JPanel();
		Box formBox = Box.createVerticalBox();		
		formBox.add(hoursBox);
		formBox.add(minBox);
		formBox.add(secBox);
		northPanel.add(formBox);
		northPanel.add(changeAlarmClock);
		add(northPanel);

	
		changeAlarmClock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				changeValue();
			}
		});

		gbc.weighty = 100;
		gbc.fill = GridBagConstraints.BOTH;

		add(new JScrollPane(elementList), gbc, 0, 2, 2, 1);

		elementList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		elementList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				int i = elementList.getSelectedIndex();
				if (i < 0)
					return;
				hourTxt.setText(Integer.toString(array[i].hours));
				minTxt.setText(Integer.toString(array[i].minutes));
				secTxt.setText(Integer.toString(array[i].seconds));

			}
		});

		elementList.setModel((ListModel<Clock>) model);
		elementList.setSelectedIndex(0);
	}

	public void add(Component c, GridBagConstraints gbc, int x, int y, int w, int h) {
		gbc.gridx = x;
		gbc.gridy = y;
		gbc.gridwidth = w;
		gbc.gridheight = h;
		add(c, gbc);
	}

	public void changeValue() {
		//int indexClk = ((AlarmsEditor)editor).index;
		//if(indexClk == -1)
			//return;
		
		Clock clk = new Clock();
		fmt.setParseIntegerOnly(false);
		try {
			int hour = fmt.parse(hourTxt.getText()).intValue();
			int min = fmt.parse(minTxt.getText()).intValue();
			int sec = fmt.parse(secTxt.getText()).intValue();
			clk.hours = hour; clk.minutes = min; clk.seconds = sec;
		} catch (ParseException e) {
			JOptionPane.showMessageDialog(this, "" + e, "Input Error hh:mm:ss", JOptionPane.WARNING_MESSAGE);
			minTxt.requestFocus();
			return;
		}
		int currentIndex = elementList.getSelectedIndex();
		setArray(currentIndex, clk);
		editor.firePropertyChange();
	}


	public void setArray(Clock[] v) {
		if (v == null)
			array = new Clock[0];
		else
			array = v;
		model.setArray(array);
		if (array.length > 0) {
			hourTxt.setText(Integer.toString(array[0].hours));
			minTxt.setText(Integer.toString(array[0].minutes));
			secTxt.setText(Integer.toString(array[0].seconds));
			elementList.setSelectedIndex(0);
		} else {
			hourTxt.setText("");
			minTxt.setText("");
			secTxt.setText("");
		}
	}


	public Clock[] getArray() {
		return (Clock[]) array.clone();
	}


	public void setArray(int i, Clock value) {
		if (0 <= i && i < array.length) {
			model.setValue(i, value);
			elementList.setSelectedIndex(i);

			hourTxt.setText(Integer.toString(array[i].hours));
			minTxt.setText(Integer.toString(array[i].minutes));
			secTxt.setText(Integer.toString(array[i].seconds));
		}
	}


	public Clock getArray(int i) {
		if (0 <= i && i < array.length)
			return array[i];
		return new Clock();
	}
}

class ClockArrayListModel2 extends AbstractListModel<Clock> {

	private static final long serialVersionUID = 1L;

	public int getSize() {
		return array.length;
	}

	public Clock getElementAt(int i) {
		return  array[i];
	}


	public void setArray(Clock[] a) {
		int oldLength = array == null ? 0 : array.length;
		array = a;
		int newLength = array == null ? 0 : array.length;
		if (oldLength > 0)
			fireIntervalRemoved(this, 0, oldLength);
		if (newLength > 0)
			fireIntervalAdded(this, 0, newLength);
	}


	public void setValue(int i, Clock value) {
		array[i] = value;
		fireContentsChanged(this, i, i);
	}

	private Clock[] array;

}
