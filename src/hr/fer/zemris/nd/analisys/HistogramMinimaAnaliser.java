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

	private BufferedImage image;
	private BufferedImage displayImage;
	private WritableRaster raster;
	private int[] xHistogram;
	private int[] yHistogram;
	public RectangularArea boundingBox;
	
	
	
	
	
	
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
		this.xHistogram = Histogram.getXValues(image);
		this.yHistogram = Histogram.getYValues(image);
		System.out.println("X Values: \n");
		Histogram.showXHistogram(xHistogram, image.getType());
		Histogram.showYHistogram(yHistogram, image.getType());
		System.out.println(Arrays.toString(xHistogram));
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
		System.out.println("Define bounding box");
		drawHistogramDistribution();
		int blankValueTreshold = getBlankThreshold();
		int[] xRange = getxRange();
		int[] yRange = getyRange();
		
	}

	
	private void drawHistogramDistribution() {
		int min = getMin(this.xHistogram);
		int max = getMax(this.xHistogram);
		int limit = max - min;
		System.out.println("Min, max, limit: "+min+", "+max+", "+limit);
		double[] distribution = new double[limit];
	
		int levelsGroup[] = new int[this.xHistogram.length];
		
		
//		System.out.println("Levels: " + Arrays.toString(levels));
//		System.out.println("Distribution: " + Arrays.toString(distribution));


		
		System.out.println("Distribution: " + Arrays.toString(distribution));
		System.out.println(Arrays.toString(levelsGroup));
		
		
		
	}


	private int getBlankThreshold() {
		
		for (int i = 0; i < this.xHistogram.length; i++) {
			
		}
		return 0;
	}


	private int[] getyRange() {
		// get left limit
		
		return null;
	}


	private int[] getxRange() {
		// TODO Auto-generated method stub
		return null;
	}


	private int getMin(int[] histogram) {
		int min = Integer.MAX_VALUE;
		for (int i = 0; i < histogram.length; i++) {
			if(histogram[i] < min) {
				min = histogram[i];
			}
		}
		
		return min;
	}
	
	

	private int getMax(int[] histogram) {
		int max = -Integer.MAX_VALUE;
		for (int i = 0; i < histogram.length; i++) {
			if(histogram[i] > max) {
				max = histogram[i];
			}
		}
		
		return max;
	}
	
	
	
	
}
