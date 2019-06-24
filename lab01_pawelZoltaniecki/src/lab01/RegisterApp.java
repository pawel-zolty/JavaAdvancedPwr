package lab01;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.*;

/*
 * an rmi server program needs to
 * 1 Create and install a security manager
 * 2 Create and export one or more remote objects
 * 3 Register at least one remote object with the RMI registry 
 */

public class RegisterApp {
	private static int Port = 0;
	private static String Name = "Register";

	public static void main(String[] args) {
		// 1 create and install Security manager
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}
		try {
			// 2 create
			IRegister register = new Register();
			// 2 and export remote object
			IRegister stubRegister = (IRegister) UnicastRemoteObject.exportObject(register, Port);
			Registry registry = LocateRegistry.getRegistry();// ("host",port)default 1099
			// GIVE POLICY TO LET APP WORKS WITH REMOTE FILES
			System.setProperty("java.security.policy",
					"file:./C:/Users/zolty/workspace/lab01/lab01_pawelZoltaniecki/server.policy");

			// 3 register one remote object
			registry.rebind(Name, stubRegister);

			System.out.println("Register bound");
		} catch (Exception e) {
			System.err.println("Register exception:");
			e.printStackTrace();
		}
	}
}