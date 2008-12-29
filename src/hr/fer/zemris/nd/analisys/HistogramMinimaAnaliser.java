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
		double[] xValues = Histogram.getXValues(image);
		System.out.println("X Values: \n");
		System.out.println(Arrays.toString(xValues));
		
		return null;
	}

	
	
}
