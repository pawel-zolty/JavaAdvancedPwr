package lab01;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/*
 * 1 the client begins by installing a security manager
 * 2 look up the remote object by name in the server host's registry.
 */

public class Manager implements Runnable {
	private int maxSensorAmount = 10;
	private Thread thread;

	public final ArrayList<ISensor> sensorsList = new ArrayList<ISensor>();
	public List<IMonitor> monitorList;

	private static int sensorCounter = 0;
	private IRegister register;
	private String name = "Register";

	public Manager() throws RemoteException, NotBoundException {
		initRMICommunication();
	}

	public void startThread() {
		thread = new Thread(this);
		thread.start();
	}

	private void initRMICommunication() throws RemoteException, NotBoundException {
		// 1 the client begins by installing a security manager
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}
		
		// 2 look up the remote object by name in the server host's registry.
		Registry registry = LocateRegistry.getRegistry();
		register = (IRegister) registry.lookup(name);
	}

	public ISensor addSensor() {
		if (sensorsList.size() < maxSensorAmount) {
			ISensor sensor = new Sensor(sensorCounter++);
			sensorsList.add(sensor);
			return sensor;
		}
		// za duzo sensorow
		return null;
	}

	public int deleteLastSensorFromList() throws RemoteException {
		if (sensorsList.size() > 0) {
			int lastSensorIndex = sensorsList.size() - 1;
			Sensor sensorToDelete = ((Sensor) sensorsList.get(lastSensorIndex));
			int id = sensorToDelete.getNotRemoteId();

			((ISensor) sensorToDelete).stop();
			UnicastRemoteObject.unexportObject((ISensor) sensorToDelete, true);
			sensorsList.remove(lastSensorIndex);

			RefreshMonitors();
			return id;
		}
		return -1;
	}

	private void RefreshMonitors() throws RemoteException {
		monitorList = getAllMonitors();
	}

	private List<IMonitor> getAllMonitors() throws RemoteException {
		return register.getMonitors();
	}

	public int getIndexOfSensorWithId(int sensorId) {
		return sensorsList.stream().map(s -> ((Sensor) s).getNotRemoteId()).collect(Collectors.toList())
				.indexOf(sensorId);
	}

	@Override
	public void run() {
		while (true) {
			try {
				RefreshMonitors();
				PairSensorsWithMonitors();
				Thread.sleep(5000);
			} catch (RemoteException e) {
				System.err.println("Error while refreshing monitors");
				e.printStackTrace();
			} catch (InterruptedException e) {
				System.err.println("Interrupted Exception");
				e.printStackTrace();
			}
		}

	}

	private void PairSensorsWithMonitors() {
		for (ISensor sensor : sensorsList) {
			try {
				int monitorId = ((Sensor) sensor).getMonitorId();
				if (monitorId == -1) {
					boolean success = TryPairSensorWithMonitor(sensor);
					if (!success)
						break;// no more monitors
				}
			} catch (RemoteException e) {
				System.err.println("Pairing error");
				e.printStackTrace();
				continue;
			}
		}

	}

	private boolean TryPairSensorWithMonitor(ISensor sensor) throws RemoteException {
		for (IMonitor monitor : monitorList) {
			int sensorId = monitor.getSensorId();
			if (sensorId == -1) {

				if (((Sensor) sensor).exportedSensor == null) {
					ISensor stubSensor = (ISensor) UnicastRemoteObject.exportObject(sensor, 0);
					((Sensor) sensor).exportedSensor = stubSensor;
				}

				sensor.setOutput(monitor);
				monitor.setInput(((Sensor) sensor).exportedSensor);
				return true;
			}
		}
		return false;
	}
}