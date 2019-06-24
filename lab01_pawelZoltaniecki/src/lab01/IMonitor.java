package lab01;

import java.rmi.RemoteException;

public interface IMonitor extends java.rmi.Remote {
	public void setReadings(String readings) throws RemoteException;

	public void setInput(ISensor o) throws RemoteException;

	public int getId() throws RemoteException;

	public int getSensorId() throws RemoteException;
}