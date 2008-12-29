package hr.fer.zemris.nd.document.util;

import hr.fer.zemris.nd.document.NumberField;
import hr.fer.zemris.nd.document.OcrDocument;
import hr.fer.zemris.nd.document.OcrScheme;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class OcrDocumentTest {

	public static void main(String[] args) {
		OcrScheme scheme = new OcrScheme(849, 1099);
		scheme.addInterestArea(new RectangularArea(
				new Coordinate(70, 512), new Coordinate(386, 562)));
		System.out.println(scheme);
		
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File(
					"/media/data/Documents/fer/Peta godina/" +
					"Deveti semestar/Neuronske mreze/Projekt/Test Samples/Form B.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		System.out.println("Image loaded, creating scheme. ");
		
		OcrDocument document = new OcrDocument(image, scheme);
		NumberField field = document.getNumberField(0);
		BufferedImage fieldImage = field.getScan();
		File fieldImageFile = new File("fieldImage.png");
		try {
			ImageIO.write(fieldImage, "png", fieldImageFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JFrame frame = new JFrame();
		frame.setTitle("Cropped field");
		frame.setSize(new Dimension(fieldImage.getWidth(), fieldImage.getHeight()));
		System.out.println("end");
		
		
		
	}
}
