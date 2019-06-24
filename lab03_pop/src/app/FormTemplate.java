package app;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import com.google.gson.Gson;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.String;

public class FormTemplate extends GridPane {

	private Field[] fields;
	private List<TextField> textFieldList = new ArrayList<>();
	private List<Text> textList = new ArrayList<>();
	private List<Text> errorList = new ArrayList<>();

	private Button btnSave;
	private Label labelForm = new Label();
	private Class<?> clazz;

	private static String dateFormat = "yyyy-MM-dd";

	public FormTemplate(ClassLoader loader, String name) throws ClassNotFoundException {

		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();

		resetForm();
		setFieldsOfClass(name);
		createFormFromFields();

		btnSave = new Button("Save");
		btnSave.setOnAction(e -> {
			saveForm(validator);
		});

		getChildren().clear();
		for (int i = 0; i < textList.size(); i++) {
			add(textList.get(i), 0, i);
			add(textFieldList.get(i), 1, i);
			add(errorList.get(i), 2, i);
		}

		add(btnSave, 0, textList.size());
		add(labelForm, 1, textList.size());
	}

	private void createFormFromFields() {
		for (Field field : fields) {
			Annotation[] annotations = field.getDeclaredAnnotations();
			createFormFieldIfFormFieldAnnotation(field, annotations);
		}
	}

	private void createFormFieldIfFormFieldAnnotation(Field field, Annotation[] annotations) {
		for (Annotation annotation : annotations) {
			if (annotation instanceof MyFormField) {
				textList.add(new Text(field.getName()));
				textFieldList.add(new TextField(field.getName()));
				errorList.add(new Text(""));
			}
		}
	}

	private void saveForm(Validator validator) {
		Object obj = saveInstanceOfObject();
		Set<ConstraintViolation<Object>> violations = validator.validate(obj);
		for (Text err : errorList) {
			err.setText("");
		}
		if (violations.isEmpty()) {
			Writer writer;
			try {

				writer = new FileWriter("c:\\Users\\zolty\\workspace\\lab03_pop\\bin\\lab03\\forms.json");
				Gson gson = new Gson();
				writer.write(gson.toJson(obj));
				writer.close();
				labelForm.setText("Form successfully saved");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} else {
			String mes = null;
			for (ConstraintViolation<Object> v : violations) {
				String fieldName = v.getPropertyPath().toString();
				int fieldIndex = getFieldIndex(fieldName);
				if (fieldIndex == -1)
					mes += v.getMessage() + "; ";
				else {
					errorList.get(fieldIndex).setText(v.getMessage());
				}
			}
			labelForm.setText(mes);
		}
	}

	private int getFieldIndex(String fieldName) {
		String s = textList.get(0).getText();
		Optional<Text> tekst = textList.stream().filter(textField -> textField.getText().equals(fieldName)).findFirst();
		if (tekst.isPresent())
			return textList.indexOf(tekst.get());
		return -1;
	}

	private void setFieldsOfClass(String name) throws ClassNotFoundException {
		ClassLoader loader;
		name = name.replace("\\", ".").replace(".class", "");
		loader = this.getClass().getClassLoader();
		clazz = loader.loadClass(name);
		fields = clazz.getDeclaredFields();
	}

	private void resetForm() {
		labelForm.setText("");
		textFieldList.clear();
		textList.clear();
		fields = null;
		clazz = null;
		setAlignment(Pos.CENTER_LEFT);
		setPadding(new Insets(10, 10, 10, 10));
	}

	private Object saveInstanceOfObject() {
		Object obj = null;
		try {
			obj = clazz.newInstance();

			Method[] methods = clazz.getDeclaredMethods();
			for (int index = 0; index < textFieldList.size(); index++) {
				String setterName = "set" + fields[index].getName();
				tryInvokeProperSetter(obj, methods, index, setterName);
			}
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			System.err.print("ERROR reflection" + "\n" + e.toString());
			// e.printStackTrace();
		}
		return obj;
	}

	private void tryInvokeProperSetter(Object obj, Method[] methods, int index, String setterName)
			throws IllegalAccessException, InvocationTargetException {
		for (Method method : methods) {
			if (methodIsProperSetter(method, setterName)) {
				String setterParameterValue = textFieldList.get(index).getText();
				String parameterTypeName = fields[index].getType().getSimpleName();
				try {
					invokeSetterWithParsing(obj, method, setterParameterValue, parameterTypeName);
				} catch (NumberFormatException e1) {
					System.err.print("ERROR Podaj Liczbe" + "\n" + e1.toString());
					// e1.printStackTrace();
				} catch (DateTimeParseException e2) {
					System.err.print("ERROR Podaj Date" + "\n" + e2.toString());
					// e2.printStackTrace();
				} 
			}
		}
	}

	private boolean methodIsProperSetter(Method method, String setterName) {
		return method.getName().equalsIgnoreCase(setterName);
	}

	private void invokeSetterWithParsing(Object obj, Method setterMethod, String setterParameterValue,
			String parameterTypeName) throws IllegalAccessException, InvocationTargetException {

		if (parameterTypeName.equals("String")) {
			setterMethod.invoke(obj, setterParameterValue);
		} else if (parameterTypeName.equals("int")) {
			setterMethod.invoke(obj, Integer.parseInt(setterParameterValue));
		} else if (parameterTypeName.equals("boolean")) {
			setterMethod.invoke(obj, Boolean.parseBoolean(setterParameterValue));
		} else if (parameterTypeName.equals("LocalDate")) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
			LocalDate localDate = LocalDate.parse(setterParameterValue, formatter);
			setterMethod.invoke(obj, localDate);
		}
	}

}