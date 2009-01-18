/**
 * 
 */
package hr.fer.zemris.nd.document.util;

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
					"/home/goran/Desktop/PrazniProcessed/Page two/p0000002712.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Average: "+Picture.getImagePixelAverage(image));
		
		
		
		System.out.println("Image loaded. ");
		
		NumberField field = new NumberField(image);
		System.out.println("Field initialzed");
		
	}

}
