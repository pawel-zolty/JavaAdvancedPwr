package lab02;

public class ClassListElement {

	private String classUri = null;
	private String classFullName = null;
	private String isLoaded;

	public ClassListElement(String url, String className) {
		setClassUri(url);
		this.setClassFullName(className);
		setIsLoaded("Nie");
	}

	public String getClassFullName() {
		return classFullName;
	}

	public void setClassFullName(String classFullName) {
		this.classFullName = classFullName;
	}

	public String getIsLoaded() {
		return isLoaded;
	}

	public void setIsLoaded(String isLoaded) {
		this.isLoaded = isLoaded;
	}

	public String getClassUri() {
		return classUri;
	}

	public void setClassUri(String uri) {
		this.classUri = uri;
	}

}