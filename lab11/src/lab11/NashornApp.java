package lab11;

import java.io.FileNotFoundException;
import java.io.FileReader;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class NashornApp {

	public static void main(String[] args) {
		ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
		try {
			testMessage(engine);
		} catch (FileNotFoundException | ScriptException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}		
	}

	private static void testMessage(ScriptEngine engine)
			throws ScriptException, FileNotFoundException, NoSuchMethodException {
		engine.eval(new FileReader("script1.js"));
		Invocable invocable = (Invocable) engine;
		Object result;
		result = invocable.invokeFunction("fun1", "Peter Parker");			
		System.out.println(result);
		System.out.println(result.getClass());
	}

}
