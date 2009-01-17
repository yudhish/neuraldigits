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
		
		interestAreas[1] = new RectangularArea(
				new Coordinate(253, 1920), new Coordinate(1134, 2065));
		numbers[1] = "4907242494";
		
		interestAreas[2] = new RectangularArea(
				new Coordinate(247, 2111), new Coordinate(1135, 2279));
		numbers[2] = "0217865152";
		
		interestAreas[3] = new RectangularArea(
				new Coordinate(251, 2308), new Coordinate(1130, 2445));
		numbers[3] = "5364781210";

		interestAreas[4] = new RectangularArea(
				new Coordinate(241, 2488), new Coordinate(1147, 2621));
		numbers[4] = "9260149181";
		
		interestAreas[5] = new RectangularArea(
				new Coordinate(235, 2644), new Coordinate(1128, 2788));
		numbers[5] = "0726297904";
		
		interestAreas[6] = new RectangularArea(
				new Coordinate(220, 2810), new Coordinate(1125, 2965));
		numbers[6] = "5796007564";
		
		System.out.println("Rectangular areas: "+Arrays.toString(interestAreas));
		System.out.println("Numbers: " +Arrays.toString(numbers));
		

	}

}
