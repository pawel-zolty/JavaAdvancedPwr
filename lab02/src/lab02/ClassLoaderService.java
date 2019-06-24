package lab02;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ClassLoaderService {
	private JComponentClassLoader myClassLoader;
	private ClassLoader parent;
	
	public ClassLoaderService(ClassLoader parentParam) {
		myClassLoader = new JComponentClassLoader(parentParam);
		parent = parentParam;
	}
	
	public void loadClass(String className) throws ClassNotFoundException {
		Class<?> loadedClass = myClassLoader.loadClass(className);
		System.out.println(className + " loaded by " 
			+ loadedClass.getClassLoader());
	}
	
	public JComponent useClass(String className, JFrame frame, JPanel panel) 
			throws ClassNotFoundException, InstantiationException, 
			IllegalAccessException, IllegalArgumentException, 
			InvocationTargetException, NoSuchMethodException, SecurityException 
	{
		Class c = myClassLoader.findClass(className);
		
		Constructor ctor;
		Object o;
		try {
			ctor = c.getConstructor(JFrame.class);
			o = ctor.newInstance(frame);
		} catch (NoSuchMethodException e) {
			ctor = c.getConstructor(JPanel.class);
			o = ctor.newInstance(panel);
		}
		Method met = c.getMethod("start");
		met.invoke(o);
		return (JComponent)o;
	}
	
	public void unloadClass(String className) throws InterruptedException {
		myClassLoader.unloadClass(className);
		Thread.sleep(5000);
		System.out.println(className + " unloaded? Wait for garbage");
	}
	
	public void unloadAllClass() throws InterruptedException {
		myClassLoader.unloadAllClass();
		Thread.sleep(1000);
		myClassLoader = new JComponentClassLoader(parent);
		System.out.println("Unloaded? Wait for garbage");
	}
}
