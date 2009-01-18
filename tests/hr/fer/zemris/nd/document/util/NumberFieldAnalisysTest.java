/**
 * 
 */
package hr.fer.zemris.nd.document.util;

import hr.fer.zemris.nd.document.DigitField;
import hr.fer.zemris.nd.document.ENumberFieldDisplayMode;
import hr.fer.zemris.nd.document.NumberField;
import hr.fer.zemris.nd.imagelib.Picture;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * @author goran
 *
 */
public class NumberFieldAnalisysTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		BufferedImage image = null;
		System.out.println("Loading image.");
		try {
			image = ImageIO.read(new File(
					"/home/goran/Desktop/p0000001.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Average: "+Picture.getImagePixelAverage(image));
		
		
		
		System.out.println("Image loaded. ");
		
		NumberField field = new NumberField(image, ENumberFieldDisplayMode.VERBOSE);
		String name = "/home/goran/Desktop/folder/name";
		for(DigitField digit: field.getDigitFields()) {
			try {
				ImageIO.write(digit.getDigitImage(), "png", new File(name));
				name += "1";
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("Field initialzed");
		
	}

}
