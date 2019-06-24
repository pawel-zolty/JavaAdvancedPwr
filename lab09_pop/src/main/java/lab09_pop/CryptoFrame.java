package lab09_pop;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.Console;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import javax.crypto.NoSuchPaddingException;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class CryptoFrame extends JFrame {
	private CryptoographyService cryptosystem;
	private KeyGenerator keyGenerator;

	private String encryptedFilePath = "C:\\files\\loremEncrypted.txt";
	private String decryptedFilePath = "C:\\files\\loremDecrypted.txt";
	private String originalFilePath = "C:\\files\\lorem.txt";

	private JPanel panelCryptography;
	private JButton btnEncryptFile;
	private JButton btnDecryptFile;	
	private JButton btnGenerateKeys;
	private JTextArea textArea;
	private JScrollPane scrollPane;

	public CryptoFrame() {
		try {
			initFrame();
			initiBusinessServices();
			cryptosystem = new CryptoographyService();
			keyGenerator = new KeyGenerator(1024);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initiBusinessServices()
			throws NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, IOException {

		readFile(originalFilePath);
	}

	private void encrypt() throws IOException {
		try {
			initiBusinessServices();

			byte[] inputFileBytes = cryptosystem.getFileBytes(new File(originalFilePath));
			PrivateKey privateKey = cryptosystem.getPrivate("C:\\files\\privateKey");

			cryptosystem.encryptFile(inputFileBytes, new File(encryptedFilePath), privateKey);

			readFile(encryptedFilePath);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Encrypt error");
			// e.printStackTrace();
		}
	}

	private void decrypt() throws IOException {
		try {
			initiBusinessServices();

			byte[] inputFileBytes = cryptosystem.getFileBytes(new File(encryptedFilePath));
			PublicKey publicKey = cryptosystem.getPublic("C:\\files\\publicKey");

			cryptosystem.decryptFile(inputFileBytes, new File(decryptedFilePath), publicKey);
			
			readFile(decryptedFilePath);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Decrypt error");
			 e.printStackTrace();
		}
	}

	private void initFrame() {
		this.setBounds(100, 100, 750, 448);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		panelCryptography = new JPanel();
		panelCryptography.setLayout(null);
		getContentPane().add(panelCryptography);

		btnGenerateKeys = new JButton("Generuj klucze RSA");
		btnGenerateKeys.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					keyGenerator.createKeys();
					System.out.println("Klucze wygenerowano!");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnGenerateKeys.setBounds(15, 39, 220, 40);
		panelCryptography.add(btnGenerateKeys);

		btnEncryptFile = new JButton("Szyfruj plik kluczem RSA");
		btnEncryptFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					encrypt();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnEncryptFile.setBounds(15, 92, 220, 40);
		panelCryptography.add(btnEncryptFile);

		btnDecryptFile = new JButton("Odszyfruj plik kluczem RSA");
		btnDecryptFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					decrypt();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnDecryptFile.setBounds(15, 145, 220, 40);
		panelCryptography.add(btnDecryptFile);

		JPanel panel = new JPanel();
		panel.setBounds(290, 11, 429, 378);
		panelCryptography.add(panel);
		panel.setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(15, 25, 402, 342);
		panel.add(scrollPane);
		
		textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
	}

	private void readFile(String filePath) throws IOException {
		textArea.setText("");
		BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
		String tmp;

		while ((tmp = bufferedReader.readLine()) != null) {
			textArea.append(tmp);
			textArea.append("\n");
		}

		bufferedReader.close();
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CryptoFrame frame = new CryptoFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
