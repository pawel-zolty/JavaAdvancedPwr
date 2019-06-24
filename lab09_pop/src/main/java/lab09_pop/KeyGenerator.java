package lab09_pop;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.*;

public class KeyGenerator {

	private KeyPairGenerator keyPairGenerator;
	private KeyPair pair;
	private PrivateKey privateKey;
	private PublicKey publicKey;

	public KeyGenerator(int keylength) throws NoSuchAlgorithmException, NoSuchProviderException {
		this.keyPairGenerator = KeyPairGenerator.getInstance("RSA");
		this.keyPairGenerator.initialize(keylength);
	}

	public static void main(String[] args) {
		KeyGenerator keyGenerator;
		try {
			keyGenerator = new KeyGenerator(1024);
			keyGenerator.createKeys();
			
			System.out.println(keyGenerator);			
			System.out.println("A PublicKey has been created in 'C:\\files\\publicKey'");
			System.out.println("A PrivateKey key has been created in 'C:\\files\\publicKey'");
		} catch (NoSuchAlgorithmException | NoSuchProviderException | IOException e) {
			System.err.println(e.getMessage());
		}
	}

	public void createKeys() throws IOException {
		this.pair = this.keyPairGenerator.generateKeyPair();
		this.privateKey = pair.getPrivate();
		this.publicKey = pair.getPublic();
		writeToFile("C:\\files\\publicKey", getPublicKey().getEncoded());
		writeToFile("C:\\files\\privateKey", getPrivateKey().getEncoded());
	}

	public PrivateKey getPrivateKey() {
		return this.privateKey;
	}

	public PublicKey getPublicKey() {
		return this.publicKey;
	}

	public void writeToFile(String filePath, byte[] key) throws IOException {
		File file = new File(filePath);
		file.getParentFile().mkdirs();

		FileOutputStream fileOutputStream = new FileOutputStream(file);
		fileOutputStream.write(key);
		fileOutputStream.flush();
		fileOutputStream.close();
	}
}