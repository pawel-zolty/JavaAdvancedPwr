package exampleform;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import app.DocumentFile;
import app.MyFormField;

public class FileForm {

	@MyFormField
	@NotNull(message = " nazwa pliku nie poprawna")
	@DocumentFile()	
	private String name = null;	
	
	@MyFormField
	@NotNull(message = " podaj date w formacie yyyy-MM-dd")
	private LocalDate creationDate;
	
	public FileForm() {	
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {		
		this.name = name.equals("") ? null : name;
	}

	public LocalDate getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDate creationDate) {		
		this.creationDate = creationDate;
	}
}