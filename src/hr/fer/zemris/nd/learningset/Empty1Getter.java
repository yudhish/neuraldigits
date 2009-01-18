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
public class Empty1Getter {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		RectangularArea[] interestAreas = new RectangularArea[7];
		
		
		OcrScheme page1Scheme = new OcrScheme(2480, 3507);
		
		
		interestAreas[0] = new RectangularArea(
				new Coordinate(237, 1727), new Coordinate(1180, 1893));
		
		interestAreas[1] = new RectangularArea(
				new Coordinate(253, 1920), new Coordinate(1134, 2065));
		
		interestAreas[2] = new RectangularArea(
				new Coordinate(247, 2111), new Coordinate(1135, 2279));
		
		interestAreas[3] = new RectangularArea(
				new Coordinate(251, 2308), new Coordinate(1130, 2445));

		interestAreas[4] = new RectangularArea(
				new Coordinate(241, 2488), new Coordinate(1147, 2621));
		
		interestAreas[5] = new RectangularArea(
				new Coordinate(235, 2644), new Coordinate(1128, 2788));
		
		interestAreas[6] = new RectangularArea(
				new Coordinate(220, 2810), new Coordinate(1125, 2965));
		
		for (int i = 0; i < interestAreas.length; i++) {
			page1Scheme.addInterestArea(interestAreas[i]);
			page1Scheme.addAreaNumber(interestAreas[i], "Page one");
		}
		
		System.out.println("Rectangular areas: "+Arrays.toString(interestAreas));
		System.out.println("Scheme: "+page1Scheme);
		
		NumberFieldGetter getter = new NumberFieldGetter(page1Scheme, 
				new File("/home/goran/Desktop/300dpi/PRAZNI/1"), 
				new File("/home/goran/Desktop/300dpi/PrazniProcessed/"));
		getter.getNumberFields();
		

	}

}
