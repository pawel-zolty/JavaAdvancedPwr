package lab01;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/*
 * class that implements a remote interface should at least do the following:
 * 1 Declare the remote interfaces being implemented
 * 2 Define the constructor for each remote object
 * 3 Provide an implementation for each remote method in the remote interfaces
 * 
 */

public class Register implements IRegister // 1 Declare the remote interfaces being implemented
{
	private List<IMonitor> monitorList = new ArrayList<IMonitor>();
	private static int monitorCounter = 0;

	// 2 define ctor
	public Register() {
		super();
	}

	// 3 provide implementations for interface methods:

	public int register(IMonitor monitor) throws RemoteException {
		monitorList.add(monitor);
		return monitorCounter++;
	}

	public boolean unregister(int id) throws RemoteException {
		if (monitorList.size() > 0) {
			int index = getIndexOfMonitorWithId(id);
			if (index > -1) {
				monitorList.remove(index);
				return true;
			}
		}
		return false;
	}

	public List<IMonitor> getMonitors() throws RemoteException {
		return monitorList;
	}

	private int getIndexOfMonitorWithId(int id) {
		return monitorList.stream().map(m -> {
			try {
				return m.getId();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			return id;
		}).collect(Collectors.toList()).indexOf(id);
	}
}