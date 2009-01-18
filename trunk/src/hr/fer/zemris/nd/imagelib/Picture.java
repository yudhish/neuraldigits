/**
 * 
 */
package hr.fer.zemris.nd.imagelib;

import java.awt.image.BufferedImage;

/**
 * @author goran
 *
 */
public class Picture {
	
	public static int getImagePixelSum(BufferedImage image) {
		int[] pixelValue = new int[1];
		int sum = 0;
		
		for(int i = 0; i < image.getWidth(); i++) {
			for(int j = 0; j < image.getHeight(); j++) {
				sum += image.getRaster().getPixel(i, j, pixelValue) [0];
			}
		}
		return sum;
		
	}
	
	
	public static double getImagePixelAverage(BufferedImage image) {
		return ((double)getImagePixelSum(image))/(image.getHeight()*image.getWidth());
	}

}
