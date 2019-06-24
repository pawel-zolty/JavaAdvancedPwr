package lab02;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.sun.jndi.toolkit.url.Uri;

public class ClassFinder {
	
	public List<ClassListElement> searchAllClassesInDirectory(File directory, String[] extensions)
			throws IOException, MalformedURLException 
	{
		System.out.println(
				"Szukanie plikow z rozszerzeniem .class w " + directory.getCanonicalPath() 
				+ " wrac z podfolderami");
		
		List<ClassListElement> listElements = new ArrayList<ClassListElement>();
		List<File> filesList = (List<File>) FileUtils.listFiles(directory, extensions, true);

		for (File file : filesList) {
			String path = file.getAbsolutePath().replace("\\", "/");
			Uri uriPath = new Uri(path);
			String[] segments = uriPath.getPath().split("/");
			String packageName = segments[segments.length-2];
			
			ClassListElement classListElement = 
					new ClassListElement("file:/" + file.getAbsolutePath(), 
							packageName + "." + file.getName().replace(".class", ""));
			classListElement.setClassUri("file:/" + file.getAbsolutePath());
			listElements.add(classListElement);
			System.out.println(file.getPath());
		}
		
		return listElements;
	}			

}
