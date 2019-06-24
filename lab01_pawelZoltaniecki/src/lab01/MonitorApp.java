package lab01;

import java.awt.EventQueue;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.DefaultListModel;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.io.Serializable;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;

public class MonitorApp implements IMonitor, Serializable {
	private static final long serialVersionUID = 1L;
	private static final String name = "Register";

	private JFrame frame;
	private JButton stopStartBtn;
	private JButton btnStopSensor;
	private JScrollPane textPane;
	private DefaultListModel<String> model = new DefaultListModel<>();
	private JList<String> list = new JList<String>(model);
	// RMI
	private int id;
	private ISensor sensor;
	private Registry registry;
	private IRegister register;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MonitorApp window = new MonitorApp();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MonitorApp() {
		super();
		initialize();
		initRMICommunication();
	}

	// RMI APP LOGIC

	private void initRMICommunication() {
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}
		try {
			// get registry
			registry = LocateRegistry.getRegistry();
			// get register from registry
			register = (IRegister) registry.lookup(name);

			IMonitor stubMonitor = (IMonitor) UnicastRemoteObject.exportObject(this, 0);
			int monitorId = register.register(stubMonitor);

			id = monitorId;
		} catch (Exception e) {
			showErrorDialog("Error while init RMI - getRegistry/export/register Monitor");
			e.printStackTrace();
		}
	}

	public void setReadings(String readings) throws RemoteException {
		model.addElement(readings);
	}

	public void setInput(ISensor sensorParam) throws RemoteException {
		if (sensorParam != null) {
			canStartSensor();
		}
		sensor = sensorParam;
		model.addElement("sensor przypisany: " + sensor.getId());
	}

	public int getId() throws RemoteException {
		return id;
	}

	public int getSensorId() throws RemoteException {
		if (sensor == null)
			return -1;
		return sensor.getId();
	}

	// INIT GUI SWING
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				closeWindow();
			}
		});

		stopStartBtn = new JButton("Start Sensor");
		stopStartBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				startSensor();
			}
		});
		stopStartBtn.setBounds(12, 13, 115, 25);
		frame.getContentPane().add(stopStartBtn);

		btnStopSensor = new JButton("Stop Sensor");
		btnStopSensor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stopSensor();
			}
		});
		btnStopSensor.setBounds(12, 51, 115, 25);
		frame.getContentPane().add(btnStopSensor);

		textPane = new JScrollPane(list);
		textPane.setBounds(139, 16, 281, 224);
		frame.getContentPane().add(textPane);

		lackOfSensor();
	}

	private void startSensor() {
		if (sensor == null) {
			lackOfSensor();
			JOptionPane.showMessageDialog(frame, "Monitor zosta³ pozbawiony sensora!");
		} else {
			try {
				sensor.start();
				canNotStartSensor();
			} catch (RemoteException e) {
				showErrorDialog("Error while starting Sensor");
				sensor = null;
				lackOfSensor();
			}
		}
	}

	private void stopSensor() {
		if (sensor == null) {
			lackOfSensor();
			JOptionPane.showMessageDialog(frame, "Monitor zosta³ pozbawiony sensora!");
		} else {
			try {
				sensor.stop();
				canStartSensor();
			} catch (RemoteException e) {
				showErrorDialog("Warning Sensor does not exists");
				// e.printStackTrace();
				sensor = null;
				lackOfSensor();
			}
		}
	}

	private void closeWindow() {
		try {
			register.unregister(id);
			if (sensor != null) {
				sensor.stop();
				sensor.setOutput(null);
			}
		} catch (RemoteException e) {
			showErrorDialog("Error while unregister/setOutputNull Sensor");
			e.printStackTrace();
		}
		System.exit(0);
	}

	private void lackOfSensor() {
		stopStartBtn.setEnabled(false);
		btnStopSensor.setEnabled(false);
	}

	private void canStartSensor() {
		stopStartBtn.setEnabled(true);
		btnStopSensor.setEnabled(false);
	}

	private void canNotStartSensor() {
		stopStartBtn.setEnabled(false);
		btnStopSensor.setEnabled(true);
	}

	private void showErrorDialog(String errorText) {
		JOptionPane.showMessageDialog(frame, errorText);
	}
}