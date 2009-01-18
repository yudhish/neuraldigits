/**
 * 
 */
package hr.fer.zemris.nd.imagelib;

import hr.fer.zemris.nd.gui.ImageDisplay;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.Arrays;

/**
 * @author goran
 *
 */
public class Histogram {

	public static int getXSum(BufferedImage image, int x) {
		if(x < 0 || x > image.getWidth()) {
			throw new IndexOutOfBoundsException("The x coordinate needs to be " +
					"within the image bounds. ");
		}
		int sum = 0;
		int[] pixelValue = new int[3];
		for(int i = 0; i < image.getRaster().getHeight(); i++) {
			sum += image.getRaster().getPixel(x, i, pixelValue)[0];
		}
		return sum;
	}
	
	
	public double getXAverage(BufferedImage image, int x) {
		return getXSum(image, x)/image.getHeight();
	}
	
	
	public static int[] getXValues(BufferedImage image) {
		int[] xValues = new int[image.getWidth()];
		for(int i = 0; i < xValues.length; i++) {
			xValues[i] = getXSum(image, i);
		}
		return xValues;
	}
	
	
	public static int getYSum(BufferedImage image, int y) {
		if(y < 0 || y > image.getHeight()) {
			throw new IndexOutOfBoundsException("The x coordinate needs to be " +
					"within the image bounds. ");
		}
		int sum = 0;
		int [] pixelValue = new int[3];
		for(int i = 0; i < image.getRaster().getHeight(); i++) {
			sum += image.getRaster().getPixel(i, y, pixelValue)[0];
		}
		return sum;
	}
	
	
	public double getYAverage(BufferedImage image, int y) {
		return getXSum(image, y)/image.getWidth();
	}
	
	
	public static int[] getYValues(BufferedImage image) {
		int[] yValues = new int[image.getHeight()];
		for(int i = 0; i < yValues.length; i++) {
			yValues[i] = getYSum(image, i);
		}
		return yValues;
	}
	
	
	public static void showXHistogram(BufferedImage image) {
		int[] xValues = getXValues(image);
		showXHistogram(xValues, image.getType());
	}
	
	
	public static void showXHistogram(int[] histogramArray, int imageType) {
		int max = getMax(histogramArray);
		int windowHeight = getXHistogramHeight(max);
		double scale = max/windowHeight;
		BufferedImage histogramImage = Histogram.getHorizontalHistogramGraph(
				histogramArray, windowHeight, scale, imageType);
		ImageDisplay.displayImage(histogramImage, new Point(150, 300));
		
	}
	
	
	public static void showHorizontalHistogram(
			int[] histogramArray, int imageType, Point location) {
		int max = getMax(histogramArray);
		int windowHeight = getXHistogramHeight(max);
		double scale = (double)max/windowHeight;
		System.out.println("skejla: "+scale);
		BufferedImage histogramImage = Histogram.getHorizontalHistogramGraph(
				histogramArray, windowHeight, scale, imageType);
		ImageDisplay.displayImage(histogramImage, location);
		
	}
	
	
	private static BufferedImage getHorizontalHistogramGraph(
			int[] histogramArray, int windowHeight, double scale, int imageType) {
		if(imageType != 10 && imageType != 12) {
			throw new IllegalArgumentException("The image type is not supported");
		}
		
		BufferedImage image;
		
		if(imageType == 10) {
			image = drawType10ImageHorizontal(histogramArray, windowHeight, scale);
		} else {
			image = drawType12ImageHorizontal(histogramArray, windowHeight, scale);
		}
		
		return image;
	}


	private static BufferedImage drawType10ImageVertical(int[] histogramArray, int windowWidth, 
			double scale) {
		System.out.println("izvrsi se");
		BufferedImage image = new BufferedImage(
				windowWidth, histogramArray.length, BufferedImage.TYPE_BYTE_GRAY); 
		int[] pixelValueBlack = new int[]{0};
		int[] pixelValueWhite = new int[]{255};
		
		for (int i = 0; i < histogramArray.length; i++) {
			for(int j = 1; j < windowWidth; j++) {
				if(j < histogramArray[i] * 1/scale) {
					image.getRaster().setPixel(windowWidth - j, i, pixelValueWhite);
				} else {
					image.getRaster().setPixel(windowWidth - j, i, pixelValueBlack);
				}
			}
		}
		return image;
	}
	
	
	private static BufferedImage drawType12ImageVertical(int[] histogramArray, int windowWidth, 
			double scale) {
		BufferedImage image = new BufferedImage(
				histogramArray.length, windowWidth, BufferedImage.TYPE_BYTE_GRAY); 
		int[] pixelValueBlack = new int[]{0};
		int[] pixelValueWhite = new int[]{255};
		
		for (int i = 0; i < histogramArray.length; i++) {
			for(int j = windowWidth - 1; j >= 0; j--) {
				if(j < histogramArray[i] * 1/scale) {
					image.getRaster().setPixel(i, windowWidth - j - 1, pixelValueBlack);
				} else {
					image.getRaster().setPixel(i, windowWidth - j - 1, pixelValueWhite);
				}
			}
		}
		
		return image;
	}
	
	
	private static BufferedImage drawType10ImageHorizontal(int[] histogramArray,
			int windowHeight, double scale) {
		BufferedImage image = new BufferedImage(
				histogramArray.length, windowHeight, BufferedImage.TYPE_BYTE_GRAY); 
		int[] pixelValueBlack = new int[]{0};
		int[] pixelValueWhite = new int[]{255};
		System.out.println(Arrays.toString(pixelValueWhite));
		
		for (int i = 0; i < histogramArray.length; i++) {
			for(int j = 0; j < windowHeight; j++) {
				if(j < histogramArray[i] * 1/scale) {
					image.getRaster().setPixel(i, j, pixelValueWhite);
				} else {
					image.getRaster().setPixel(i, j, pixelValueBlack);
				}
			}
		}
		return image;
	}


	private static BufferedImage drawType12ImageHorizontal(int[] histogramArray,
			int windowHeight, double scale) {
		BufferedImage image = new BufferedImage(
				histogramArray.length, windowHeight, BufferedImage.TYPE_BYTE_GRAY); 
		int[] pixelValueBlack = new int[]{0};
		int[] pixelValueWhite = new int[]{255};
		System.out.println(Arrays.toString(pixelValueWhite));
		
		for (int i = 0; i < histogramArray.length; i++) {
			for(int j = windowHeight - 1; j >= 0; j--) {
				if(j < histogramArray[i] * 1/scale) {
					image.getRaster().setPixel(i, windowHeight - j - 1, pixelValueBlack);
				} else {
					image.getRaster().setPixel(i, windowHeight - j - 1, pixelValueWhite);
				}
			}
		}
		
		return image;
	}


	private static int getXHistogramHeight(int max) {
		int windowHeight;
		if(max < 50) {
			windowHeight = 50;
		} else {
			windowHeight = (int)max;
		}
		if(max > 100) {
			windowHeight = 100;
		}
		return windowHeight;
	}


	private static int getMax(int[] values) {
		int max = -Integer.MAX_VALUE;
		
		for (int i = 0; i < values.length; i++) {
			if(values[i] > max) {
				max = values[i];
			}
		}return max;
	}


	public static void showYHistogram(int[] values, int type) {
		int max = getMax(values);
		int windowHeight = getYHistogramWidth(max);
		double scale = max/windowHeight;
		BufferedImage histogramImage = Histogram.getVerticalHistogramGraph(
				values, windowHeight, scale, type);
		ImageDisplay.displayImage(histogramImage, new Point(0, 0));
		
	}


	private static int getYHistogramWidth(int max) {
		int windowHeight;
		if(max < 50) {
			windowHeight = 50;
		} else {
			windowHeight = (int)max;
		}
		if(max > 100) {
			windowHeight = 100;
		}
		return windowHeight;
	}


	private static BufferedImage getVerticalHistogramGraph(int[] values,
			int windowHeight, double scale, int type) {
		BufferedImage image;
		
		if(type == 10) {
			image = drawType10ImageVertical(values, windowHeight, scale);
		} else {
			image = drawType12ImageVertical(values, windowHeight, scale);
		}
		
		return image;
	}

}
