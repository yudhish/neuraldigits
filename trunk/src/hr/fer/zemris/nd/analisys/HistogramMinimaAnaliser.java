/**
 * 
 */
package hr.fer.zemris.nd.analisys;

import hr.fer.zemris.nd.document.util.RectangularArea;
import hr.fer.zemris.nd.imagelib.Histogram;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFrame;

/**
 * @author goran
 *
 */
public class HistogramMinimaAnaliser implements ISchemeAnaliser{

	BufferedImage image; 
	WritableRaster raster;
	
	
	public HistogramMinimaAnaliser(BufferedImage image) {
		this.image = image; 
		this.raster = image.getRaster();
	}
	
	
	@Override
	public List<RectangularArea> getDigitAreas() {
		System.out.println("Image type: "+this.image.getType());
		int[] xValues = Histogram.getXValues(image);
		System.out.println("X Values: \n");
		Histogram.showXHistogram(xValues, image.getType());
		System.out.println(Arrays.toString(xValues));
		
		// TODO finish segmentation here
		
		return null;
	}


	private void drawHorizontalHistogram(double[] values) {
		
	}




	
	
}
