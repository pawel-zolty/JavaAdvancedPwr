package lab01;

import java.rmi.RemoteException;
import java.util.List;

public interface IRegister extends java.rmi.Remote {
	public int register(IMonitor o) throws RemoteException;

	public boolean unregister(int id) throws RemoteException;

	public List<IMonitor> getMonitors() throws RemoteException;
}