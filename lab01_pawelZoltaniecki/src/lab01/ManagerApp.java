package lab01;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.awt.event.ActionEvent;

public class ManagerApp {

	private JFrame frame;
	private JScrollPane textPane;
	private DefaultListModel<String> model = new DefaultListModel<>();
	private JList<String> list = new JList<String>(model);

	private Manager manager;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ManagerApp window = new ManagerApp();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public ManagerApp() {
		initialize();
		try {
			manager = new Manager();
			manager.startThread();
		} catch (RemoteException e) {
			showErrorDialog("Error while init rmi - getting registry");
			e.printStackTrace();
		} catch (NotBoundException e) {
			showErrorDialog("Error while init rmi lookup registry");
			e.printStackTrace();
		}
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JButton btnAddSensor = new JButton("Add Sensor");
		btnAddSensor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addSensor();
			}
		});
		btnAddSensor.setBounds(12, 13, 115, 25);
		frame.getContentPane().add(btnAddSensor);

		JButton btnDeleteSensor = new JButton("Delete Sensor");
		btnDeleteSensor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteSensor();
			}
		});
		btnDeleteSensor.setBounds(12, 52, 115, 25);
		frame.getContentPane().add(btnDeleteSensor);

		textPane = new JScrollPane(list);
		textPane.setBounds(138, 13, 282, 227);
		frame.getContentPane().add(textPane);
	}

	private void addSensor() {
		ISensor sensor = manager.addSensor();
		if (sensor != null) {
			putAddSensorMessageOnGui(sensor);
		}
	}

	private void deleteSensor() {
		int removedSensorId;
		try {
			removedSensorId = manager.deleteLastSensorFromList();
			if (removedSensorId != -1) {
				model.addElement("Sensor usuniety: " + removedSensorId);
			}
		} catch (RemoteException e) {
			showErrorDialog("Error while deleting sensor. (deleteSensor)");
			e.printStackTrace();
		}
	}

	private void putAddSensorMessageOnGui(ISensor sensor) {
		try {
			model.addElement("SensorId: " + ((Sensor) sensor).getNotRemoteId() + " - monitor: "
					+ ((Sensor) sensor).getMonitorId());
		} catch (RemoteException e) {
			showErrorDialog("Error while getMonitorId for sensor. (putAddSensorMessageOnGui)");
			e.printStackTrace();
		}
	}

	private void showErrorDialog(String errorText) {
		JOptionPane.showMessageDialog(frame, errorText);
	}
}