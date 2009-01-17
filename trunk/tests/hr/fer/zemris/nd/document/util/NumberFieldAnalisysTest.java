/**
 * 
 */
package hr.fer.zemris.nd.document.util;

import hr.fer.zemris.nd.document.NumberField;

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
					"/media/data/Documents/fer/Peta godina/Deveti semestar/" +
					"Neuronske mreze/Neuronske mreze uzorci rani/scan01.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		System.out.println("Image loaded. ");
		
		NumberField field = new NumberField(image);
		System.out.println("Field initialzed");
		
	}

}
