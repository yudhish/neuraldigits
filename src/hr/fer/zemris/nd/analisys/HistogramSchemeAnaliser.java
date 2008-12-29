/**
 * 
 */
package hr.fer.zemris.nd.analisys;

import hr.fer.zemris.nd.document.util.RectangularArea;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.Arrays;
import java.util.List;

/**
 * @author goran
 *
 */
public class HistogramSchemeAnaliser implements ISchemeAnaliser{

	BufferedImage image; 
	WritableRaster raster;
	
	
	public HistogramSchemeAnaliser(BufferedImage image) {
		this.image = image; 
		this.raster = image.getRaster();
	}
	
	
	@Override
	public List<RectangularArea> getDigitAreas() {
		double[] xValues = this.getXValues();
		System.out.println("X Values: \n");
		System.out.println(Arrays.toString(xValues));
		
		return null;
	}


	private double[] getXValues() {
		double[] xValues = new double[this.image.getWidth()];
		for(int i = 0; i < xValues.length; i++) {
			xValues[i] = getXSum(i);
		}
		return xValues;
	}


	private double getXSum(int x) {
		double sum = 0;
		for(int i = 0; i < this.raster.getHeight(); i++) {
			sum += this.raster.getPixel(x, i, new double[1])[0];
		}
		
		return sum;
	}
	
}
