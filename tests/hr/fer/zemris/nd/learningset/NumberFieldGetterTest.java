/**
 * 
 */
package hr.fer.zemris.nd.learningset;

import hr.fer.zemris.nd.document.OcrScheme;
import hr.fer.zemris.nd.document.util.Coordinate;
import hr.fer.zemris.nd.document.util.RectangularArea;

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
		OcrScheme page1Scheme = new OcrScheme(2480, 3507);
		RectangularArea r = new RectangularArea(
				new Coordinate(237, 1727), new Coordinate(1180, 1893));
		page1Scheme.addInterestArea(r);
		page1Scheme.addAreaNumber(r, "12345");
		NumberFieldGetter getter = new NumberFieldGetter(page1Scheme, 
				new File("/home/goran/Desktop/300dpi/1/"), 
				new File("/home/goran/Desktop/300dpi/1Processed/"));
		getter.getNumberFields();
		
		
	}

}
