package lab02;
//jfromdesigner FORm
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

//https://www.javaworld.com/article/2077260/learn-java-the-basics-of-java-class-loaders.html
//https://www.baeldung.com/java-classloaders
//code src
//https://www.journaldev.com/349/java-classloader#java-custom-classloader
public class JComponentClassLoader extends ClassLoader {
	
	private List<Class> classList = new ArrayList<Class>();
	
	public JComponentClassLoader(ClassLoader parentParam) {
		super(parentParam);
	}
	
    @Override
    public Class loadClass(String name) throws ClassNotFoundException {    	
        System.out.println("Loading Class '" + name + "'");     
        try {
        	if (name.startsWith("lab02")) { //symulacja szukania klas np w sieci lub BD
        		throw new ClassNotFoundException();
        	}
        	System.out.println("Loading Class using Bootstrap loader");
        	return super.loadClass(name);        	        	
        } catch(ClassNotFoundException e) {}
        try {
	        Class alreadyExistedClass = findClass(name);
	        if(alreadyExistedClass != null ){
	        	System.out.println("Juz istnieje");
	        	return alreadyExistedClass;
	        }
        } catch (ClassNotFoundException e) { }
        System.out.println("Loading Class using Custom loader");
    	return getClass(name); //this method throws        
    }
	
    @Override
    public Class findClass(String name) throws ClassNotFoundException {
    	try {
    		return classList.stream().filter(clas -> clas.getName().equals(name))
    				.findFirst().get();
    	} catch (NullPointerException e) {
    		
    	} catch (NoSuchElementException e) {
    		
    	}
    	throw new ClassNotFoundException();
    }
    
	private Class getClass(String name) throws ClassNotFoundException {
        
		String file = name.replace('.', File.separatorChar) + ".class";
        byte[] classDataByteArray = null;
        try {
            classDataByteArray = loadClassFileData(file);
            // defineClass/resolveClass is inherited and is Final. so we cannot override it
            Class loadedClass = defineClass(name, classDataByteArray, 0, classDataByteArray.length);
            resolveClass(loadedClass);
            classList.add(loadedClass);
            return loadedClass;
        } catch (IOException e) {
            throw new ClassNotFoundException();
        } catch (Exception e) {
        	throw new ClassNotFoundException();
        }
    }
    
    private byte[] loadClassFileData(String name) throws IOException {
        InputStream stream = this.getClass().getClassLoader()
        		.getResourceAsStream(name);
        int size = stream.available();
        byte buff[] = new byte[size];
        
        DataInputStream dataStream = new DataInputStream(stream);
        dataStream.readFully(buff);
        dataStream.close();
        return buff;
    }

	public void unloadClass(String name) throws InterruptedException {
		System.out.println("Unloading Class '" + name + "'");     
        
        try {
	        Class classToUnload = findClass(name);
	        if(classToUnload != null ){
	        	Class c = classList.stream()
	        			.filter(clas -> clas.getName().equals(name))
	        			.findFirst().get();
	        	classList.remove(c);
	        	System.gc();
	        	Thread.sleep(5000);
	        }
        } catch (ClassNotFoundException e) { }
        System.out.println("Class Unloaded");
	}   
	
	public void unloadAllClass() {
		classList.clear();
		classList = new ArrayList<Class>();
	}
}


