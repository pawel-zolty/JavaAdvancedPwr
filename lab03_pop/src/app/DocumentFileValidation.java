package app;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DocumentFileValidation implements ConstraintValidator<DocumentFile, String> {
	public static boolean validate(String fileName) {
		String regex = ".+(.docx|.doc|.txt|.pdf)$";
		Pattern mypattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		Matcher matcher = mypattern.matcher(fileName);
		return matcher.matches();
	}

	public void initialize(DocumentFile gap) {
		// No-op.
	}

	public boolean isValid(String fileName, ConstraintValidatorContext cac) {
		// The fileName field should be annotated with @NotNull, so we return true
		// because
		// lack of a (null) password field is valid in certain validation scenarios.
		return fileName == null || validate(fileName);
	}

}