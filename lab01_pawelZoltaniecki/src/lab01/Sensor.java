package lab01;

import java.rmi.RemoteException;

public class Sensor implements ISensor, Runnable {
	public ISensor exportedSensor = null;

	private IMonitor monitor;
	private int id;
	private int counter = 0;
	private boolean isRunning = false;
	private int timeInterval = 2000;
	private Thread thread;

	Sensor(int idParam) {
		super();
		id = idParam;
	}

	public void start() throws RemoteException {
		isRunning = true;
		thread = new Thread(this);
		thread.start();
	}

	public void stop() throws RemoteException {
		isRunning = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			System.err.println("Interrupted exception - stop sensor thread");
			e.printStackTrace();
		}
	}

	public int getId() throws RemoteException {
		return id;
	}

	public int getMonitorId() throws RemoteException {
		if (monitor == null)
			return -1;
		return monitor.getId();
	}

	public int getNotRemoteId() {
		return id;
	}

	public void setOutput(IMonitor monitorParam) throws RemoteException {
		monitor = monitorParam;
	}

	@Override
	public void run() {
		int i = 0;
		while (isRunning) {
			try {
				Thread.sleep(timeInterval);
				if (monitor != null) {
					monitor.setReadings("Odczyt z " + id + "nr wzgl: " + i++ + " nr bezw: " + counter++);
				}
			} catch (InterruptedException e) {
				System.err.println("Interrupted exception - running sensor thread");
				e.printStackTrace();
			} catch (RemoteException e) {
				System.err.println("Error while set Readings");
				isRunning = false;
				monitor = null;
			}
		}
	}
}