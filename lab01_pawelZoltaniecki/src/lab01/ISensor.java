package lab01;

import java.rmi.RemoteException;

public interface ISensor extends java.rmi.Remote {
	public void start() throws RemoteException;

	public void stop() throws RemoteException;

	public int getId() throws RemoteException;

	public void setOutput(IMonitor o) throws RemoteException;
}