package lab11;

import java.awt.EventQueue;
import java.awt.Point;
import java.awt.Transparency;

import javax.imageio.ImageIO;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBuffer;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class ImageJSApp {

	private JFrame frame;
	private ScriptEngine engine;
	private JLabel imageLabel;
	private BufferedImage image;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ImageJSApp window = new ImageJSApp();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public ImageJSApp() throws IOException {
		initialize();
		engine = new ScriptEngineManager().getEngineByName("nashorn");
	}

	private void initialize() throws IOException {
		frame = new JFrame();
		frame.setBounds(100, 100, 678, 443);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		imageLabel = new JLabel("");
		image = ImageIO.read(new File("image.jpg"));
		ImageIcon icon = new ImageIcon(image);
		imageLabel.setIcon(icon);

		frame.getContentPane().add(imageLabel, BorderLayout.CENTER);

		JButton btnJs = new JButton("JS");
		btnJs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					testBuffers();
				} catch (FileNotFoundException | NoSuchMethodException | ScriptException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		frame.getContentPane().add(btnJs, BorderLayout.NORTH);
	}

	protected void testBuffers() throws IOException, ScriptException, NoSuchMethodException {
		int width = image.getWidth();
		int height = image.getHeight();
		// pobierz bajty zdjecia - tylko te ktore tworza pixele
		byte[] originalImageBytes = getDataBytes();

		// Nashorn Engine - wywolanie operacji na bajtach w JS
		engine.eval(new FileReader("script1.js"));
		Invocable invocable = (Invocable) engine;
		byte[] blackWhiteImageBytes = (byte[]) (invocable.invokeFunction("grayscale", originalImageBytes, width,
				height));

		// stworzenie nowego obrazka z zwroconych bajtow
		BufferedImage blackWhiteImage = bytesToBufferedImage(width, height, blackWhiteImageBytes);

		setLabel(blackWhiteImage);

		saveToFile(blackWhiteImage);
	}

	private byte[] getDataBytes() {
		return ((DataBufferByte) ((BufferedImage) image).getRaster().getDataBuffer()).getData();
	}

	private BufferedImage bytesToBufferedImage(int width, int height, byte[] blackWhiteImageBytes) {
		DataBuffer dataBuffer4BlackWhiteImage = new DataBufferByte(blackWhiteImageBytes, blackWhiteImageBytes.length);
		WritableRaster raster4BlackWhiteImage = Raster.createInterleavedRaster(dataBuffer4BlackWhiteImage, width,
				height, 3 * width, 3, new int[] { 2, 1, 0 }, (Point) null);// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		ColorModel colorModel4BlackWhiteImage = new ComponentColorModel(ColorModel.getRGBdefault().getColorSpace(),
				false, true, Transparency.OPAQUE, DataBuffer.TYPE_BYTE);
		BufferedImage blackWhiteImage = new BufferedImage(colorModel4BlackWhiteImage, raster4BlackWhiteImage, true,
				null);
		return blackWhiteImage;
	}

	private void saveToFile(BufferedImage blackWhiteImage) throws IOException {
		// zapis zdjecia do pliku
		ImageIO.write(blackWhiteImage, "jpg", new File("output.jpg"));
		System.out.println("image created");
	}

	private void setLabel(BufferedImage blackWhiteImage) {
		// ustaw label z nowym zdjeciem
		image = blackWhiteImage;
		ImageIcon icon = new ImageIcon(image);
		imageLabel.setIcon(icon);
	}
}