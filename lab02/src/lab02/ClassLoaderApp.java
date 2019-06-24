package lab02;

import java.awt.EventQueue;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import com.sun.jndi.toolkit.url.Uri;

public class ClassLoaderApp {

	private JFrame frame;	
	private JFileChooser fileChooser = new JFileChooser();
	private JPanel panel;
	private JTable classTable;
	private DefaultTableModel model;
	
	private JButton btnUseClass;
	private JButton btnLoadClass;
	private JButton btnUnloadClass;	
	private JButton btnSearchClasses;
	private JButton btnNewButton;
	
	private int selectedRow = -1;	
	private List<ClassListElement> classListElements = 
			new ArrayList<ClassListElement>();
	
	private ClassLoaderService service;
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClassLoaderApp window = new ClassLoaderApp();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public ClassLoaderApp() {
		initialize();
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fileChooser.setCurrentDirectory(new File("C:\\Users\\zolty\\workspace\\lab02\\bin\\lab02b"));
		service =  new ClassLoaderService(ClassLoaderApp.class.getClassLoader());		
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 588, 493);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JButton btnTestLoader = new JButton("Test Loader");
		btnTestLoader.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					service.loadClass("lab02b.MyClass");
				} catch (ClassNotFoundException e1) { 
					e1.printStackTrace();
				}
			}
		});
		frame.getContentPane().setLayout(null);
		btnTestLoader.setBounds(12, 397, 119, 25);
		frame.getContentPane().add(btnTestLoader);
		
		btnSearchClasses = new JButton("Search Classes");
		btnSearchClasses.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				searchClasses();
			}
		});
		btnSearchClasses.setBounds(365, 323, 193, 25);
		frame.getContentPane().add(btnSearchClasses);
		
		btnLoadClass = new JButton("Load");
		btnLoadClass.setEnabled(false);
		btnLoadClass.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {				
				loadClass();
			}
		});
		btnLoadClass.setBounds(365, 359, 86, 25);
		frame.getContentPane().add(btnLoadClass);
		
		btnUnloadClass = new JButton("Unload");
		btnUnloadClass.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				unloadClass();
			}
		});
		btnUnloadClass.setEnabled(false);
		btnUnloadClass.setBounds(472, 359, 86, 25);
		frame.getContentPane().add(btnUnloadClass);
		
		btnUseClass = new JButton("Use Class");
		btnUseClass.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				useClass();
			}
		});
		btnUseClass.setEnabled(false);
		btnUseClass.setBounds(365, 394, 193, 30);
		frame.getContentPane().add(btnUseClass);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(365, 13, 193, 303);
		frame.getContentPane().add(scrollPane);
		
		classTable = new JTable();
		classTable.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
	        public void valueChanged(ListSelectionEvent event) {
	        	rowSelected();	        	
	        }
	    });
		
		scrollPane.setViewportView(classTable);
		
		panel = new JPanel(null);
		panel.setBounds(12, 13, 342, 371);
		frame.getContentPane().add(panel);				
	}
	
	private void searchClasses() {		
		int fileChooserState = fileChooser.showOpenDialog(fileChooser);

		if (fileChooserState == JFileChooser.APPROVE_OPTION) {
			System.out.println("Wybrano folder: " 
					+ fileChooser.getSelectedFile().getAbsolutePath());
		}

		File directory = new File(fileChooser.getSelectedFile().getAbsolutePath());
		String[] extensions = new String[] { "class" };
		try {
			ClassFinder finder = new ClassFinder(); 
			classListElements = finder.searchAllClassesInDirectory(directory, extensions);
			
			updateJTable();
			btnSearchClasses.setEnabled(false);
		} catch (IllegalArgumentException e) {
			JOptionPane.showMessageDialog(frame, "Nie ma takiego katalogu!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void loadClass() {		
		if(selectedRow  < 0) {
			JOptionPane.showMessageDialog(frame, "Najpierw wybierz z listy klase do zaladowania");
			return;
		}
		ClassListElement classListElement = classListElements.get(selectedRow);
		try {
			String className =  classListElement.getClassFullName();
			
			service.loadClass(className);

			classListElement.setIsLoaded("Tak");		
			updateJTable();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void unloadClass() {
		if(selectedRow  < 0) {
			JOptionPane.showMessageDialog(frame, "Najpierw wybierz z listy klase do zaladowania");
			return;
		}
		ClassListElement classListElement = classListElements.get(selectedRow);
		try {
			String className =  classListElement.getClassFullName();
			panel.removeAll();
			updateJTable();
			
			//service.unloadClass(className);
			service.unloadAllClass();
			
			for(ClassListElement el : classListElements) {
				el.setIsLoaded("Nie");
			}
			btnUnloadClass.setEnabled(false);
			btnLoadClass.setEnabled(true);
			updateJTable();
			//classListElement.setIsLoaded("Nie");			
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	private void useClass()
	{    	
		ClassListElement classListElement = classListElements.get(selectedRow);
		String className =  classListElement.getClassFullName();
		JComponent component;
		
		try {
			panel.removeAll();
			
			component = service.useClass(className, frame, panel);			
			
			panel.add(component);		
			panel.revalidate();
			panel.repaint();			
		} catch (InstantiationException e) {
			JOptionPane.showMessageDialog(frame, "Blad tworzenia instancji");
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			JOptionPane.showMessageDialog(frame, "Illegal Access Exception");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			JOptionPane.showMessageDialog(frame, "Nie ma takiej klasy zaladowanej");
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			JOptionPane.showMessageDialog(frame, "Nie ma takiej metody ctora");
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			JOptionPane.showMessageDialog(frame, "Nie ma ctora z takim parametrem");
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
		
	private void updateJTable() {
		String column[] = { "Nazwa", "Zaladowany" };
		model = new DefaultTableModel(column, 0);
		
		for (ClassListElement classListElement : classListElements) {
			Object[] content = { classListElement.getClassFullName(), 
					classListElement.getIsLoaded() };
			model.addRow(content);
		}						
		classTable.setModel(model);
		classTable.getColumnModel().getColumn(0).setPreferredWidth(141);
		classTable.getColumnModel().getColumn(0).setMaxWidth(300);
		classTable.getColumnModel().getColumn(1).setPreferredWidth(40);
		classTable.getColumnModel().getColumn(1).setMinWidth(40);
	}
	
	private void rowSelected() {
		selectedRow = classTable.getSelectedRow();
    	if(selectedRow < 0 ) {
    		btnLoadClass.setEnabled(false);
    		btnUseClass.setEnabled(false);
    		btnUnloadClass.setEnabled(false);
    		return;
    	}
    	Object loaded = classTable.getModel().getValueAt(selectedRow, 1).toString();
    	boolean isLoaded = loaded.equals("tak") || loaded.equals("Tak") ?
    			true : false;
    	btnLoadClass.setEnabled(!isLoaded);
		btnUseClass.setEnabled(isLoaded);
		btnUnloadClass.setEnabled(isLoaded);
	}
}
