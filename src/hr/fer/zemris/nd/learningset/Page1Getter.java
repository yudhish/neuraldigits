/**
 * 
 */
package hr.fer.zemris.nd.learningset;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;

import hr.fer.zemris.nd.document.OcrScheme;
import hr.fer.zemris.nd.document.util.Coordinate;
import hr.fer.zemris.nd.document.util.RectangularArea;

/**
 * @author goran
 *
 */
public class Page1Getter {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		RectangularArea[] interestAreas = new RectangularArea[7];
		String[] numbers = new String[7];
		
		OcrScheme page1Scheme = new OcrScheme(2480, 3507);
		
		
		interestAreas[0] = new RectangularArea(
				new Coordinate(237, 1727), new Coordinate(1180, 1893));
		numbers[0] = "9034283619";
		
		
		
		
		System.out.println("Rectangular areas: "+Arrays.toString(interestAreas));
		System.out.println("Numbers: " +Arrays.toString(numbers));
		

	}

}
