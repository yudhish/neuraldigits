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
public class Empty2Getter {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		RectangularArea[] interestAreas = new RectangularArea[9];

		
		OcrScheme page2Scheme = new OcrScheme(2480, 3507);
		
		
		interestAreas[0] = new RectangularArea(
				new Coordinate(232, 489), new Coordinate(1154, 687));
		
		
		interestAreas[1] = new RectangularArea(
				new Coordinate(230, 847), new Coordinate(1426, 1066));
		
		interestAreas[2] = new RectangularArea(
				new Coordinate(226, 1130), new Coordinate(1425, 1377));
		
		interestAreas[3] = new RectangularArea(
				new Coordinate(217, 1438), new Coordinate(1390, 1623));

		interestAreas[4] = new RectangularArea(
				new Coordinate(222, 1688), new Coordinate(1426, 1867));
		
		interestAreas[5] = new RectangularArea(
				new Coordinate(221, 1918), new Coordinate(1218, 2121));
		
		interestAreas[6] = new RectangularArea(
				new Coordinate(220, 2192), new Coordinate(1187, 2419));
		

		interestAreas[7] = new RectangularArea(
				new Coordinate(214, 2450), new Coordinate(1046, 2609));
		
		interestAreas[8] = new RectangularArea(
				new Coordinate(218, 2649), new Coordinate(1037, 2822));

		
		
		
		
		for (int i = 0; i < interestAreas.length; i++) {
			page2Scheme.addInterestArea(interestAreas[i]);
			page2Scheme.addAreaNumber(interestAreas[i], "Page two");
		}
		
		System.out.println("Rectangular areas: "+Arrays.toString(interestAreas));
		System.out.println("Scheme: "+page2Scheme);
		
		NumberFieldGetter getter = new NumberFieldGetter(page2Scheme, 
				new File("/home/goran/Desktop/300dpi/PRAZNI/2"), 
				new File("/home/goran/Desktop/300dpi/PrazniProcessed/"));
		getter.getNumberFields();
		


	}

}
