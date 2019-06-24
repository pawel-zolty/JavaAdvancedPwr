package lab12;

import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.util.JAXBSource;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import lab12.Offer.Equipment;
import lab12.Offer.Seller;
import lab12.Offer.Seller.Address;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;

public class JaxbWindow {

	private JFrame frame;
	private final JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
	private JComponent panel2 = new JPanel();
	private JComponent panel1 = new JPanel();
	private JComponent panel3 = new JPanel();
	private final JLabel lblPrice = new JLabel("Price");
	private final JLabel lblEquipment = new JLabel("EQUIPMENT");
	private final JLabel lblName = new JLabel("Name");
	private final JLabel lblDescription = new JLabel("Description");
	private final JLabel lblSeller = new JLabel("SELLER");
	private final JLabel lblName_1 = new JLabel("First Name");
	private final JLabel lblLastName = new JLabel("Last Name");
	private final JLabel lblAddress = new JLabel("Address");
	private final JLabel lblFirstLine = new JLabel("First Line");
	private final JLabel lblSecLine = new JLabel("Sec Line");
	private final JLabel lblCountry = new JLabel("Country");
	private final JLabel lblZip = new JLabel("Zip");
	private JTextField idTextField;
	private JTextField priceTextField;
	private JTextField nameTextField;
	private JTextField firstNameTextField;
	private JTextField lastNameTextField;
	private JTextField firstTextField;
	private JTextField secondTextField;
	private JTextField countryTextField;
	private JTextField zipTextField;
	private static String OffersFile = "offers.xml";
	private final JTextField textField = new JTextField();
	private final JButton btnGeneratehtml = new JButton("generateHtml");
	private Offers offers;
	private JEditorPane jEditorPane;
	private JScrollPane scrollPane;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JaxbWindow window = new JaxbWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public JaxbWindow() {
		initialize();
	}

	private void initialize() {
		jEditorPane = new JEditorPane();
        jEditorPane.setEditable(false);
        scrollPane = new JScrollPane(jEditorPane);
        panel3.add(scrollPane);
        
		textField.setBounds(95, 126, 311, 48);
		textField.setColumns(10);
		frame = new JFrame();
		frame.setBounds(100, 100, 1034, 549);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		tabbedPane.setBounds(0, 0, 1016, 502);
		frame.getContentPane().add(tabbedPane);
		
		tabbedPane.addTab("1", panel1);
		panel1.setLayout(null);
		
		JLabel lblId = new JLabel("Id");
		lblId.setBounds(12, 13, 71, 16);
		panel1.add(lblId);
		lblPrice.setBounds(12, 42, 71, 16);
		
		panel1.add(lblPrice);
		lblEquipment.setBounds(12, 71, 71, 16);
		
		lblEquipment.setFont(new Font("Courier", Font.BOLD,12));
		panel1.add(lblEquipment);
		lblName.setBounds(12, 100, 71, 16);
		
		panel1.add(lblName);
		lblDescription.setBounds(12, 129, 71, 16);
		
		panel1.add(lblDescription);
		lblSeller.setBounds(12, 158, 71, 16);
		
		lblSeller.setFont(new Font("Courier", Font.BOLD,12));
		panel1.add(lblSeller);
		lblName_1.setBounds(12, 187, 71, 16);
		
		panel1.add(lblName_1);
		lblLastName.setBounds(12, 216, 71, 16);
		
		panel1.add(lblLastName);
		lblAddress.setBounds(12, 245, 56, 16);
		
		lblAddress.setFont(new Font("Courier", Font.BOLD,12));
		panel1.add(lblAddress);
		lblFirstLine.setBounds(12, 274, 71, 16);
		
		panel1.add(lblFirstLine);
		lblSecLine.setBounds(12, 303, 71, 16);
		
		panel1.add(lblSecLine);
		lblCountry.setBounds(12, 332, 71, 16);
		
		panel1.add(lblCountry);
		lblZip.setBounds(12, 361, 71, 16);
		
		panel1.add(lblZip);
		
		idTextField = new JTextField();
		idTextField.setBounds(95, 10, 311, 22);
		panel1.add(idTextField);
		idTextField.setColumns(10);
		
		priceTextField = new JTextField();
		priceTextField.setColumns(10);
		priceTextField.setBounds(95, 39, 311, 22);
		panel1.add(priceTextField);
		
		nameTextField = new JTextField();
		nameTextField.setColumns(10);
		nameTextField.setBounds(95, 97, 311, 22);
		panel1.add(nameTextField);
		
		firstNameTextField = new JTextField();
		firstNameTextField.setColumns(10);
		firstNameTextField.setBounds(95, 184, 311, 22);
		panel1.add(firstNameTextField);
		
		lastNameTextField = new JTextField();
		lastNameTextField.setColumns(10);
		lastNameTextField.setBounds(95, 213, 311, 22);
		panel1.add(lastNameTextField);
		
		firstTextField = new JTextField();
		firstTextField.setColumns(10);
		firstTextField.setBounds(95, 271, 311, 22);
		panel1.add(firstTextField);
		
		secondTextField = new JTextField();
		secondTextField.setColumns(10);
		secondTextField.setBounds(95, 300, 311, 22);
		panel1.add(secondTextField);
		
		countryTextField = new JTextField();
		countryTextField.setColumns(10);
		countryTextField.setBounds(95, 329, 311, 22);
		panel1.add(countryTextField);
		
		zipTextField = new JTextField();
		zipTextField.setColumns(10);
		zipTextField.setBounds(95, 358, 311, 22);
		panel1.add(zipTextField);
		
		JButton btnAddOffer = new JButton("Add Offer");
		btnAddOffer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Offer offer = createOffer();
				saveToFile(offer);
				appendFile(offer, OffersFile);
				clearForm();
			}
		});
		btnAddOffer.setBounds(95, 395, 97, 25);
		panel1.add(btnAddOffer);
		
		panel1.add(textField);
				
		tabbedPane.addTab("2", panel2);
		
		btnGeneratehtml.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					generateHTML(OffersFile);
				} catch (TransformerException e) {
					e.printStackTrace();
				} catch (JAXBException e) {
					e.printStackTrace();
				}
			}
		});
		btnGeneratehtml.setBounds(134, 190, 97, 25);
		
		panel2.add(btnGeneratehtml);
		
		tabbedPane.addTab("3", panel3);
	}

	protected void generateHTML(String offersFileName) throws TransformerException, JAXBException {
		File offersFile = new File(offersFileName);
		List<Offer> offersList;
        Offers offers;
        
        JAXBContext jaxbContext = JAXBContext.newInstance(Offers.class);
		if(offersFile.exists()) {            
	        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
	        offers = (Offers) unmarshaller.unmarshal(offersFile);
	        offersList = offers.getOffers();

	        if(offersList == null) {
	        	offersList = new ArrayList<Offer>();
	        }
         
		
			TransformerFactory factory = TransformerFactory.newInstance();
	        InputStream resourceAsStream = Offers.class.getResourceAsStream("..\\offer1.xslt");
	        StreamSource xslt = new StreamSource(resourceAsStream);
	        Transformer transformer = factory.newTransformer(xslt);
	
	        JAXBContext context = JAXBContext.newInstance(Offers.class);
	        JAXBSource source = new JAXBSource(context, offers);
	        StringWriter writer = new StringWriter();
	        StreamResult result = new StreamResult(writer);
	        transformer.transform(source, result);
	        
	        
	        HTMLEditorKit kit = new HTMLEditorKit();
	        jEditorPane.setEditorKit(kit);
	        Document doc = kit.createDefaultDocument();
	        jEditorPane.setDocument(doc);
	        jEditorPane.setText(writer.toString());	 
		}
	}

	protected void appendFile(Offer offer, String offersFileName) {		
		File offersFile = new File(offersFileName);
		List<Offer> offersList;
        Offers offers;
        
        try {        	
            JAXBContext jaxbContext = JAXBContext.newInstance(Offers.class);
                       
            if(offersFile.exists()) {            
		        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		        offers = (Offers) unmarshaller.unmarshal(offersFile);
		        offersList = offers.getOffers();

		        if(offersList == null) {
		        	offersList = new ArrayList<Offer>();
		        }
            } else {
            	offersList = new ArrayList<Offer>();
            	offers = new Offers();
            }
            
            offersList.add(offer);
            offers.setOffers(offersList);
            this.offers = offers;
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(offers, offersFile);
        } catch (JAXBException e) {
            e.printStackTrace();
        }		
	}

	protected void saveToFile(Offer offer) {
		JAXBContext context;
		try {
			context = JAXBContext.newInstance(Offer.class);
			Marshaller mar= context.createMarshaller();
		    mar.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		    mar.marshal(offer, new File("./" + "offer" + offer.getId() + ".xml"));		    
		} catch (JAXBException e) {
			e.printStackTrace();
		}		
	}

	protected Offer createOffer() {
		Offer offer = new Offer();
	    offer.setId((byte) Integer.parseInt(idTextField.getText()) );
	    offer.setPrice(Double.parseDouble(priceTextField.getText()));
	    
	    Equipment eq = new Equipment();
	    eq.setName(nameTextField.getText());
	    eq.setDescription(textField.getText());
	    offer.setEquipment(eq);
	    
	    Seller seller = new Seller();
	    seller.setFirstName(firstNameTextField.getText());
	    seller.setLastName(lastNameTextField.getText());
	    Address add = new Address();
	    add.setAddressLine1(firstTextField.getText());
	    add.setAddressLine2(secondTextField.getText());
	    add.setCountry(countryTextField.getText());
	    add.setZip(zipTextField.getText());
	    
	    seller.setAddress(add);
	    offer.setSeller(seller);
		return offer;
	}

	protected void clearForm() {
		idTextField.setText(null);
	    countryTextField.setText(null);
	    
	    nameTextField.setText(null);
	    textField.setText(null);
	    firstNameTextField.setText(null);
	    lastNameTextField.setText(null);
	    
	    firstTextField.setText(null);
	    secondTextField.setText(null);
	    countryTextField.setText(null);
	    zipTextField.setText(null);
	}
}
