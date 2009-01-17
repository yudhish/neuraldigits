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
public class Page2Getter {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		RectangularArea[] interestAreas = new RectangularArea[9];
		String[] numbers = new String[9];
		
		OcrScheme page2Scheme = new OcrScheme(2480, 3507);
		
		
		interestAreas[0] = new RectangularArea(
				new Coordinate(232, 489), new Coordinate(1154, 687));
		numbers[0] = "3829916194";
		
		interestAreas[1] = new RectangularArea(
				new Coordinate(230, 847), new Coordinate(1426, 1066));
		numbers[1] = "6305447843";
		
		interestAreas[2] = new RectangularArea(
				new Coordinate(226, 1130), new Coordinate(1425, 1377));
		numbers[2] = "7697325606";
		
		interestAreas[3] = new RectangularArea(
				new Coordinate(217, 1438), new Coordinate(1390, 1623));
		numbers[3] = "1230479203";

		interestAreas[4] = new RectangularArea(
				new Coordinate(222, 1688), new Coordinate(1426, 1867));
		numbers[4] = "2784253475";
		
		interestAreas[5] = new RectangularArea(
				new Coordinate(221, 1918), new Coordinate(1218, 2121));
		numbers[5] = "3599585398";
		
		interestAreas[6] = new RectangularArea(
				new Coordinate(220, 2192), new Coordinate(1187, 2419));
		numbers[6] = "9334488020";
		

		interestAreas[7] = new RectangularArea(
				new Coordinate(214, 2450), new Coordinate(1046, 2609));
		numbers[7] = "8777504380";
		
		interestAreas[8] = new RectangularArea(
				new Coordinate(218, 2649), new Coordinate(1037, 2822));
		numbers[8] = "8546530576";
		
		
		
		
		System.out.println("Rectangular areas: "+Arrays.toString(interestAreas));
		System.out.println("Numbers: " +Arrays.toString(numbers));
		

	}

}
