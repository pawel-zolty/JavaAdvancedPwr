package lab09_pop;

import org.apache.commons.codec.binary.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.nio.file.Files;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class CryptoographyService {                                                                          

	private Cipher cipher;
	private String methode = "RSA";
	
	public CryptoographyService() throws NoSuchAlgorithmException, NoSuchPaddingException {
		this.cipher = Cipher.getInstance(methode);
	}

	public static void main(String[] args) throws Exception {
		String originalName = "C:\\files\\lorem.txt";
		String encryptedFileName = "C:\\files\\loremEncrypted.txt";
		String decryptedFileName = "C:\\files\\loremDecrypted.txt";
		
		CryptoographyService cryptoService = new CryptoographyService();
		
		PrivateKey privateKey = cryptoService.getPrivate("C:\\files\\privateKey");
		PublicKey publicKey = cryptoService.getPublic("C:\\files\\publicKey");
		
		if (new File(originalName).exists()) {
			
			byte[] originalFileBytes = cryptoService.getFileBytes(new File(originalName));
			cryptoService.encryptFile(originalFileBytes, new File(encryptedFileName), privateKey);
			
			byte[] enrcyptedBytes = cryptoService.getFileBytes(new File(encryptedFileName));
			cryptoService.decryptFile(enrcyptedBytes, new File(decryptedFileName), publicKey);
		} else {
			System.out.println("Create a file text.txt under folder KeyPair");
		}
	}

	public PrivateKey getPrivate(String filename) throws Exception {
		byte[] keyBytes = Files.readAllBytes(new File(filename).toPath());
		
		PKCS8EncodedKeySpec encodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(methode);
		
		return keyFactory.generatePrivate(encodedKeySpec);
	}

	public PublicKey getPublic(String filename) throws Exception {
		byte[] keyBytes = Files.readAllBytes(new File(filename).toPath());
		X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(methode);
		
		return keyFactory.generatePublic(spec);
	}

	public void encryptFile(byte[] inputBytes, File outputFile, PrivateKey key) throws IOException, GeneralSecurityException {
		
		this.cipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] encodedBytes = Base64.encodeBase64(inputBytes);
		writeFile(outputFile, this.cipher.doFinal(inputBytes));
	}

	public void decryptFile(byte[] inputBytes, File outputFile, PublicKey key) throws IOException, GeneralSecurityException {
		
		this.cipher.init(Cipher.DECRYPT_MODE, key);
		
		writeFile(outputFile, this.cipher.doFinal(inputBytes));
	}

	public String encryptText(String text, PrivateKey key) throws NoSuchAlgorithmException, NoSuchPaddingException,
			UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
		
		this.cipher.init(Cipher.ENCRYPT_MODE, key);
		
		return Base64.encodeBase64String(cipher.doFinal(text.getBytes("UTF-8")));
	}

	public String decryptText(String text, PublicKey key)
			throws InvalidKeyException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException {
		
		this.cipher.init(Cipher.DECRYPT_MODE, key);
		
		return new String(cipher.doFinal(Base64.decodeBase64(text)), "UTF-8");
	}

	public byte[] getFileBytes(File inputFile) throws IOException {
		FileInputStream fileInputStream = new FileInputStream(inputFile);
		byte[] outputBytes = new byte[(int) inputFile.length()];
		
		fileInputStream.read(outputBytes);
		fileInputStream.close();
		
		return outputBytes;
	}
	
	private void writeFile(File outputFile, byte[] inputBytes)
			throws IllegalBlockSizeException, BadPaddingException, IOException {
		
		FileOutputStream outputStream = new FileOutputStream(outputFile);
		
		outputStream.write(inputBytes);
		outputStream.flush();
		outputStream.close();
	}
}