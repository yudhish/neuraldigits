/**
 * 
 */
package hr.fer.zemris.nd.imagelib;

import java.awt.image.BufferedImage;

/**
 * @author goran
 *
 */
public class Histogram {

	public static double getXSum(BufferedImage image, int x) {
		if(x < 0 || x > image.getWidth()) {
			throw new IndexOutOfBoundsException("The x coordinate needs to be " +
					"within the image bounds. ");
		}
		double sum = 0;
		for(int i = 0; i < image.getRaster().getHeight(); i++) {
			sum += image.getRaster().getPixel(x, i, new double[1])[0];
		}
		return sum;
	}
	
	
	public double getXAverage(BufferedImage image, int x) {
		return getXSum(image, x)/image.getHeight();
	}
	
	
	public static double[] getXValues(BufferedImage image) {
		double[] xValues = new double[image.getWidth()];
		for(int i = 0; i < xValues.length; i++) {
			xValues[i] = getXSum(image, i);
		}
		return xValues;
	}
	
	
	public static double getYSum(BufferedImage image, int y) {
		if(y < 0 || y > image.getHeight()) {
			throw new IndexOutOfBoundsException("The x coordinate needs to be " +
					"within the image bounds. ");
		}
		double sum = 0;
		for(int i = 0; i < image.getRaster().getHeight(); i++) {
			sum += image.getRaster().getPixel(i, y, new double[1])[0];
		}
		return sum;
	}
	
	
	public double getYAverage(BufferedImage image, int y) {
		return getXSum(image, y)/image.getWidth();
	}
	
	
	public static double[] getYValues(BufferedImage image) {
		double[] yValues = new double[image.getHeight()];
		for(int i = 0; i < yValues.length; i++) {
			yValues[i] = getYSum(image, i);
		}
		return yValues;
	}
	
	
	public static void showXHistogram(BufferedImage image) {
//		Histogram
	}

}
