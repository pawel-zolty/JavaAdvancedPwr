package app;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = DocumentFileValidation.class)
public @interface DocumentFile {

	Class<?>[] groups() default {};

	String message() default "Plik musi mieć rozszerzenie .(docx/doc/pdf/txt)";

	Class<? extends Payload>[] payload() default {};

}