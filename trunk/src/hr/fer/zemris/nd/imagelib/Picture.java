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
	
	
	public static BufferedImage removeNoise(BufferedImage image, int distance, double threshold) {
		int[] pixelValue = new int[1];
		int[] pixelWhite = new int[]{255};
		BufferedImage im2 = null; //tu ikoprat trebas image, moja je bila počlje u klasi
		
		for(int i = 0; i < image.getWidth(); i++) {
			for(int j = 0; j < image.getHeight(); j++) {
				if(image.getRaster().getPixel(i, j, pixelValue)[0] == 0) {
					System.out.println(getNeighbourPixelCount(image, distance, i, j));
					if(getNeighbourPixelCount(image, distance, i, j) < 
							threshold * Math.pow(distance, 2)) {
						im2.getRaster().setPixel(i, j, pixelWhite);
					}
				}
				
			}
		}
		
		return null;
		
	}


	public static double getNeighbourPixelCount(BufferedImage image, int distance, int x, int y) {
		int count = 0;
		int[] pixelValue = new int[1];
		for(int i = 0; i < distance; i++) {
			if(image.getRaster().getPixel(x+i, y, pixelValue)[0] == 0) {
				count++;
			}
			if(image.getRaster().getPixel(x-i, y, pixelValue)[0] == 0) {
				count++;
			}
			if(image.getRaster().getPixel(x+i, y, pixelValue)[0] == 0) {
				count++;
			}
			if(image.getRaster().getPixel(x-i, y, pixelValue)[0] == 0) {
				count++;
			}
			if(image.getRaster().getPixel(x, y+i, pixelValue)[0] == 0) {
				count++;
			}
			if(image.getRaster().getPixel(x, y-i, pixelValue)[0] == 0) {
				count++;
			}
		}
		return count;
	}

}
