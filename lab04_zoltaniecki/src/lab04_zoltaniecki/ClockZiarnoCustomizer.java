package lab04_zoltaniecki;

import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import javax.swing.*;
import javax.swing.event.*;

public class ClockZiarnoCustomizer extends JTabbedPane implements Customizer {
	static {
		//PropertyEditorManager.registerEditor(Clock[].class, ClockArrayEditor.class);
	}
	
	private static final long serialVersionUID = 1L;

	private static final int XPREFSIZE = 200;
	private static final int YPREFSIZE = 120;
	private ClockZiarno bean;
	private PropertyEditor alarmsEditor;
	//private PropertyChangeSupport support = new PropertyChangeSupport(this);
	
	ButtonGroup groupBtn;
	
	private JTextField titleField;
	
	private JRadioButton[] clockBoxes;
	private JPanel alarmsPane;
	private JPanel groupPanel;
	
	private JTextField hourTxt = new JTextField("wpisz liczbe");
	private JTextField minTxt = new JTextField("wpisz liczbe");
	private JTextField secTxt = new JTextField("wpisz liczbe");
	
	public ClockZiarnoCustomizer() {
		//((AlarmsEditor)alarmsEditor).index = 0;
		JPanel ALARMSPANE = new JPanel();
		alarmsPane = new JPanel();
		alarmsPane.setLayout(new BorderLayout());
	
		groupBtn = new ButtonGroup();
		groupPanel = new JPanel();
		alarmsPane.add(groupPanel, BorderLayout.CENTER);		
		
		JButton addAlarmBtn = new JButton("Edit alarm");
		addAlarmBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				setAlarm( Integer.parseInt( ((JRadioButton)groupBtn.getSelection()).getText().substring(0,1)) );
			}
		});
		
			
		/*alarmsEditor = PropertyEditorManager.findEditor(Clock[].class);	
		ALARMSPANE.add(alarmsPane, BorderLayout.NORTH);
		if(alarmsEditor != null) {
			
			alarmsEditor.addPropertyChangeListener(new PropertyChangeListener() {
				public void propertyChange(PropertyChangeEvent evt) {
					int i = ((ClockArrayEditor)alarmsEditor).index;
					setClock(i, alarmsEditor.getValue());
				}				
			});
			
			ALARMSPANE.add(alarmsEditor.getCustomEditor(), BorderLayout.CENTER);
		}*/
		
				
		
		
		
		JPanel helperPanel = new JPanel();
		helperPanel.add(addAlarmBtn);
		alarmsPane.add(helperPanel, BorderLayout.SOUTH);

		JPanel titlePane = new JPanel();
		titlePane.setLayout(new BorderLayout());

		titleField = new JTextField();
		titleField.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent evt) {
				setClockName(titleField.getText());
			}

			public void insertUpdate(DocumentEvent evt) {
				setClockName(titleField.getText());
			}

			public void removeUpdate(DocumentEvent evt) {
				setClockName(titleField.getText());
			}
		});

		titlePane.add(titleField, BorderLayout.NORTH);
		addTab("Title", titlePane);

		addTab("Alarms", ALARMSPANE);

		// obejœcie b³êdu komponentu JTabbedPane w JDK 1.2
		addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent event) {
				validate();
			}
		});
	}

	public void setAlarm(int index) {		
		if(bean == null)
			return;
		Clock oldVal = bean.getAlarms()[index];
		Clock alarm = new Clock();
		alarm.hours = Integer.parseInt(hourTxt.getText());
		alarm.minutes = Integer.parseInt(minTxt.getText());
		alarm.seconds = Integer.parseInt(secTxt.getText());
		//String alarmTxt = alarm.toString();
		bean.setAlarms(index, alarm);
		firePropertyChange("alarms", oldVal, alarm);
	}
	
	public void setObject(Object obj) {
		bean = (ClockZiarno) obj;

		Clock[] alarms = bean.getAlarms();				
		clockBoxes = new JRadioButton[alarms.length];
		Box verticalBox = Box.createVerticalBox();
		for (int i = 0; i < alarms.length; i++) {
			clockBoxes[i] = new JRadioButton(i + ": " + alarms[i].toString());
			verticalBox.add(clockBoxes[i]);
			groupBtn.add(clockBoxes[i]);
		}
		groupPanel.add(verticalBox);
		
		titleField.setText(bean.getClockName());
		
		//((PropertyEditorSupport)alarmsEditor).setSource(bean);
		((ClockArrayEditor)alarmsEditor).setValue(bean.getAlarms());
		
	}	

	public void setClockName(String newValue) {
		if (bean == null)
			return;
		String oldValue = bean.getClockName();
		bean.setClockName(newValue);
		firePropertyChange("clockName", oldValue, newValue);
	}

	public Dimension getPreferredSize() {
		return new Dimension(XPREFSIZE, YPREFSIZE);
	}
	
	private void setClock(int i, Object newValue) {
		if (bean == null)
			return;
		Clock oldValue = bean.getAlarms()[i];
		bean.setAlarms(i, (Clock)newValue);
		firePropertyChange("alarms", oldValue, (Clock)newValue);
	}
	
	/*public void addPropertyChangeListener(PropertyChangeListener l) {
		 support.addPropertyChangeListener(l);
	}
	
	public void removePropertyChangeListener(PropertyChangeListener l) {
		support.removePropertyChangeListener(l);
	} */
}