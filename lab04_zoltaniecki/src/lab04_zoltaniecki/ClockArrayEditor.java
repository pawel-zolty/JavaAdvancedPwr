package lab04_zoltaniecki;

import java.awt.Component;
import java.beans.PropertyEditorSupport;

public class ClockArrayEditor extends PropertyEditorSupport  {
	private int index = 0;
	//private ClockArrayEditorPanel panel = new ClockArrayEditorPanel(this);
	
	public Component getCustomEditor() {
		return new ClockArrayEditorPanel(this);//panel;
	}

	public boolean supportsCustomEditor() {
		return true;
	}

	public String getJavaInitializationString() {
		Clock[] clockArray = (Clock[]) getValue();

		StringBuffer strBuff = new StringBuffer();
		strBuff.append("new Clock[]{");
		for (int i = 0; i < clockArray.length; i++) {
			strBuff.append("new Clock(" + clockArray[i].hours 
					+ ", " + clockArray[i].minutes + ", " 
					+ clockArray[i].seconds + ")");
			if(i < clockArray.length - 1)
				strBuff.append(",");
		}
		strBuff.append("}");
		return strBuff.toString();
	}

	public boolean isPaintable() {
		return false;
	}

	/*public void setValue(Object value) {		
		super.setValue(value);
		//((ClockArrayEditorPanel)panel).setArray((Clock[])value);
	}*/
	
	public String getAsText() {
		return "test to do";
	}
}	
