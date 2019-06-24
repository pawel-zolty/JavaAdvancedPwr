package app;

import java.io.File;
import java.util.Collection;
import org.apache.commons.io.FileUtils;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class FormsWithAnnotationsApp extends Application {

	private ListView<String> listView = new ListView<String>();

	final private ObservableList<String> names = FXCollections.observableArrayList();

	private String initialDirectoryName = "c:\\Users\\zolty\\workspace\\lab03_pop\\target\\classes\\";
	private String classPath;
	static FormTemplate field = null;

	private static VBox mainPane;
	private Button btnBrowse;
	private Button btnLoadClass;
	private HBox horizontalBox;
	private VBox formBox;;
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		mainPane = new VBox(15);
		mainPane.setPadding(new Insets(5));
		mainPane.setAlignment(Pos.TOP_CENTER);

		listView.setEditable(false);
		listView.setItems(names);

		btnBrowse = new Button("Przeglądaj");
		btnBrowse.setOnAction(e -> {
			browseClassesFromDirectory(primaryStage);
		});

		btnLoadClass = new Button("Load class");
		btnLoadClass.setDisable(true);
		btnLoadClass.setOnAction(e -> {
			addField();
		});

		HBox hb1 = new HBox();
		hb1.getChildren().add(btnBrowse);
		hb1.getChildren().add(btnLoadClass);

		HBox hb2 = new HBox();
		hb2.getChildren().addAll(listView);

		VBox verticalBox = new VBox(); 
		verticalBox.getChildren().addAll(hb1, hb2);
		
		horizontalBox = new HBox();
		horizontalBox.getChildren().add(verticalBox);
		
		mainPane.getChildren().addAll(horizontalBox);

		primaryStage.setScene(new Scene(mainPane, 1000, 400));
		primaryStage.show();

	}

	public void addField() {
		horizontalBox.getChildren().remove(formBox);
		ClassLoader loader = this.getClass().getClassLoader();
		String name = listView.getSelectionModel().getSelectedItem();
		
		try {
			field = new FormTemplate(loader, name);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		formBox = new VBox();
		formBox.getChildren().addAll(field);
		horizontalBox.getChildren().addAll(formBox);
	}

	private void browseClassesFromDirectory(Stage primaryStage) {
		DirectoryChooser directoryChooser = new DirectoryChooser();
		directoryChooser.setInitialDirectory(new File(initialDirectoryName));

		File selectedDirectory = directoryChooser.showDialog(primaryStage);
		
		if (selectedDirectory != null) {
			classPath = selectedDirectory.getAbsolutePath();
			getClassesNames(classPath);
			btnLoadClass.setDisable(false);
			btnBrowse.setDisable(true);
		}
	}

	private void getClassesNames(String directoryName) {
		names.removeAll(names);

		if (!directoryName.isEmpty()) {
			File directory = new File(directoryName);
			String[] fileFilter = new String[] { "class" };
			boolean directoryFilter = true;

			Collection<File> files = FileUtils.listFiles(directory, fileFilter, directoryFilter);
			fillCollectionWithFileNames(directory, files);
		}
	}

	private void fillCollectionWithFileNames(File directory, Collection<File> files) {
		for (File file : files) {
			String replacingString = directory.getAbsolutePath().toString() + "\\";
			names.add(file.getAbsolutePath().toString().replace(replacingString, "")
					.replace("\\", "."));
		}
	}
}
