/**
 * 
 */
package hr.fer.zemris.nd.learningset;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * @author goran
 *
 */
public class NumberFieldGetterTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		NumberFieldGetter getter = new NumberFieldGetter(null, 
				new File("/home/goran/Desktop/300dpi/1/"), null);
		getter.getNumberFields();
		
		
	}

}
