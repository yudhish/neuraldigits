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

import sun.util.BuddhistCalendar;


/**
 * @author goran
 *
 */
public class HistogramMinimaAnaliser implements ISchemeAnaliser{

	BufferedImage image;
	BufferedImage displayImage;
	WritableRaster raster;
	
	
	public HistogramMinimaAnaliser(BufferedImage image) {
		this.image = image; 
		this.displayImage = new BufferedImage(
				image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
		displayImage.getGraphics().drawImage(image, 0, 0, null);
		this.raster = image.getRaster();
		
	}
	
	
	@Override
	public List<RectangularArea> getDigitAreas() {
		System.out.println("Image type: "+this.image.getType());
		int[] xValues = Histogram.getXValues(image);
		int[] yValues = Histogram.getYValues(image);
		System.out.println("X Values: \n");
		Histogram.showXHistogram(xValues, image.getType());
		Histogram.showYHistogram(yValues, image.getType());
		System.out.println(Arrays.toString(xValues));
		performSegmentation();
		// TODO finish segmentation here
		
		return null;
	}


	private void performSegmentation() {
		defineBoundingBox();
		getMinima();
		processMinima();
		
	}


	private void processMinima() {
		// TODO Auto-generated method stub
		
	}


	private void getMinima() {
		// TODO Auto-generated method stub
		
	}


	private void defineBoundingBox() {
		// TODO Auto-generated method stub
		
	}

	
	
}
