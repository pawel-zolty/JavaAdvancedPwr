package lab04_zoltaniecki;


import java.awt.Image;
import java.beans.BeanDescriptor;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
//import java.beans.MethodDescriptor;
//import java.beans.PropertyChangeListener;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ClockZiarnoBeanInfo extends SimpleBeanInfo {

	public BeanDescriptor getBeanDescriptor() {
		 return new BeanDescriptor( ClockZiarno.class, ClockZiarnoCustomizer.class);
	}
	
	public Image getIcon(int iconType) {
		String name = "";
		if (iconType == BeanInfo.ICON_COLOR_16x16)
			name = "COLOR_16x16";
		else if (iconType == BeanInfo.ICON_COLOR_32x32)
			name = "COLOR_32x32";
		else if (iconType == BeanInfo.ICON_MONO_16x16)
			name = "MONO_16x16";
		else if (iconType == BeanInfo.ICON_MONO_32x32)
			name = "MONO_32x32";
		else
			return null;
		Image im = null;
		try {
			im = ImageIO.read(ClockZiarnoBeanInfo.class.getClassLoader().getResourceAsStream("ClockBean_" + name + ".gif"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return im;
	}
	
	public PropertyDescriptor[] getPropertyDescriptors() {
		try {
			PropertyDescriptor clockName = new PropertyDescriptor("clockName", ClockZiarno.class);
			
			PropertyDescriptor alarms = new PropertyDescriptor("alarms", ClockZiarno.class);
			
			alarms.setPropertyEditorClass(ClockArrayEditor.class);			

			return new PropertyDescriptor[] { clockName, alarms };
		} catch (IntrospectionException e) {
			return null;
		}
	}

	/*public MethodDescriptor[] getMethodDescriptors() {
		Method addPropertyChangeListenerMethod, removePropertyChangeListenerMethod;
		@SuppressWarnings("rawtypes")
		Class args[] = { PropertyChangeListener.class };
		  
		try {
			/*addPropertyChangeListenerMethod = 
					ClockZiarno.class.getMethod("addPropertyChangeListener", args);
			removePropertyChangeListenerMethod = 
					ClockZiarno.class.getMethod("removePropertyChangeListener", args);
			MethodDescriptor addDescMethod = new MethodDescriptor( addPropertyChangeListenerMethod);
			MethodDescriptor removeDescMethod = new MethodDescriptor( removePropertyChangeListenerMethod);
			
			return new MethodDescriptor[] { addDescMethod, removeDescMethod};	
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}*/
}