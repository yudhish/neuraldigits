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
public class NumberFieldAnalisysTestSchemeAdjust {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		BufferedImage image = null;
		System.out.println("Loading image.");
		try {
			image = ImageIO.read(new File(
					"/home/goran/Desktop/p0000008.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Average: "+Picture.getImagePixelAverage(image));
		NumberField field = new NumberField(image, ENumberFieldDisplayMode.VERBOSE);	
		
		BufferedImage image1 = null;
		System.out.println("Loading image.");
		try {
			image1 = ImageIO.read(new File(
					"/home/goran/Desktop/p0000021.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Drugo polje sad");
		
		NumberField field1 = new NumberField(image1, field.getScheme());
		System.out.println(field1.getScheme());
		
	}

}
