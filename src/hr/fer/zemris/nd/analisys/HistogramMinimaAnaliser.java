/**
 * 
 */
package hr.fer.zemris.nd.analisys;

import hr.fer.zemris.nd.document.util.RectangularArea;
import hr.fer.zemris.nd.gui.ImageDisplay;
import hr.fer.zemris.nd.gui.ImageDrawerTest;
import hr.fer.zemris.nd.imagelib.Histogram;
import hr.fer.zemris.nd.imagelib.Picture;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
	private BufferedImage preprocessingImage;
	private WritableRaster raster;
	private int[] xHistogram;
	private int[] yHistogram;
	public RectangularArea boundingBox;
	private int[] distribution;
	private int distributionElementFactor;
	private int distributionMin;
	private int blankValueThreshold;
	
	
	
	
	
	public HistogramMinimaAnaliser(BufferedImage image) {
		this.image = image; 
		this.displayImage = new BufferedImage(
				image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
		displayImage.getGraphics().drawImage(image, 0, 0, null);
		this.raster = image.getRaster();
		
		
		int avg = (int)Picture.getImagePixelAverage(image);

		this.preprocessingImage = getPreprocessingImage(avg);
		ImageDisplay display = new ImageDisplay(preprocessingImage);
//		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		
//		removeNoise(preprocessingImage, 4, 0.1);
//		removeNoise(preprocessingImage, 4, 0.1);
//		removeNoise(preprocessingImage, 4, 0.1);
//		removeNoise(preprocessingImage, 4, 0.1);
		display.repaint();

		
	}
	
	
	private BufferedImage getPreprocessingImage(int threshold) {
		BufferedImage image = new BufferedImage(this.image.getWidth(), this.image.getHeight(), 
				10);
		int[] pixelValue = new int[1];
		int[] whiteValue = new int[]{255};
		int[] blackValue = new int[]{0};
		for(int i = 0; i < this.image.getWidth(); i++) {
			for(int j = 0; j < this.image.getHeight(); j++) {
				if(this.image.getRaster().getPixel(i, j, pixelValue)[0] < threshold) {
					image.getRaster().setPixel(i, j, blackValue);
				} else {
					image.getRaster().setPixel(i, j, whiteValue);
				}
				
			}
		}
//		removeNoise(image, 2, 0.5);
		return image;
	}


	private void removeNoise(BufferedImage image2, int distance, double threshold) {
		int[] pixelValue = new int[1];
		int[] pixelWhite = new int[]{255};
		for(int i = 0; i < this.image.getWidth(); i++) {
			for(int j = 0; j < this.image.getHeight(); j++) {
				if(image2.getRaster().getPixel(i, j, pixelValue)[0] == 0) {
					System.out.println(getNeighbourPixelCount(image2, distance, i, j));
					if(getNeighbourPixelCount(image2, distance, i, j) < 
							threshold * Math.pow(distance, 2)) {
						image2.getRaster().setPixel(i, j, pixelWhite);
					}
				}
				
			}
		}
		
	}


	private double getNeighbourPixelCount(BufferedImage image2, int distance, int x, int y) {
		int count = 0;
		int[] pixelValue = new int[1];
		for(int i = 0; i < distance; i++) {
			if(image2.getRaster().getPixel(x+i, y, pixelValue)[0] == 0) {
				count++;
			}
			if(image2.getRaster().getPixel(x-i, y, pixelValue)[0] == 0) {
				count++;
			}
			if(image2.getRaster().getPixel(x+i, y, pixelValue)[0] == 0) {
				count++;
			}
			if(image2.getRaster().getPixel(x-i, y, pixelValue)[0] == 0) {
				count++;
			}
			if(image2.getRaster().getPixel(x, y+i, pixelValue)[0] == 0) {
				count++;
			}
			if(image2.getRaster().getPixel(x, y-i, pixelValue)[0] == 0) {
				count++;
			}
		}
		return count;
	}


	@Override
	public List<RectangularArea> getDigitAreas() {
		System.out.println("Image type: "+this.image.getType());
		this.xHistogram = Histogram.getXValues(preprocessingImage);
		this.yHistogram = Histogram.getYValues(preprocessingImage);
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
		analyzeHistogramDistribution();
		this.blankValueThreshold = getBlankThreshold();
		showWhiteAreas();
		System.out.println("BlankThreshold: "+blankValueThreshold);
		int[] xRange = getxRange();
		int[] yRange = getyRange();
		
	}

	
	private int getBlankThreshold2() {
		int[] maxima = getMaximaIndexes();
		int thresholdIndex = (maxima[0] + maxima[1])/2;
		System.out.println("Maxima: "+Arrays.toString(maxima));
		System.out.println("ThresholdIndex = " + thresholdIndex);
		return thresholdIndex * this.distributionElementFactor + this.distributionMin;
	}


	private int[] getMaximaIndexes() {
		int[] indexes = new int[]{-1, -1};
		int leftLimit = 0;
		
		for(int i = this.xHistogram.length - 1; i > 5; i = i+5) {
			if(this.xHistogram[i] > this.xHistogram[i-1]) {
				indexes[0] = i;
				leftLimit = i;
				break;
			}
		}
		
		if(leftLimit == 0) {
			indexes[1] = 0;
			return indexes;
		}

		for(int i = leftLimit - 1; i > 0; i--) {
			if(this.xHistogram[i] < this.xHistogram[i-1]) {
				indexes[1] = i;
				break;
			}
		}
		
		
		return indexes;
	}


	private void showWhiteAreas() {
		boolean[] white = new boolean[this.xHistogram.length];
		for (int i = 0; i < white.length; i++) {
			if(xHistogram[i] > this.blankValueThreshold) {
				white[i] = true;
			} else {
				white[i] = false;
			}
		}
		BufferedImage image = new BufferedImage(this.xHistogram.length, 50, 10);
		int[] whiteColor = new int[]{255};
		int[] blackColor = new int[]{0};
		for (int i = 0; i < white.length; i++) {
			for(int j = 0; j < 50; j++) {
				if(white[i]) {
//					System.out.println("x, y: "+i+", "+j);
					image.getRaster().setPixel(i, j, whiteColor);
				} else {
					image.getRaster().setPixel(i, j, blackColor);
				}
			}
		}
		System.out.println("Display!!");;
		ImageDisplay.displayImage(image, new Point(50, 400));
		
	}


	private void analyzeHistogramDistribution() {
		this.distribution = getDistribution(this.xHistogram);
		Histogram.showHorizontalHistogram(
				distribution, 12, new Point(10, 500));
		System.out.println("Length: "+distribution.length);
	
		
	}
	
	
	private int[] getDistribution(int[] histogram) {
		
		int min = getMin(this.xHistogram);
		this.distributionMin = min;
		int max = getMax(this.xHistogram);
		int limit = max - min;
		
		int[] distribution = new int[limit/100];
		System.out.println("Min, max, limit: "+min+", "+max+", "+limit);
		int toAdd = limit/100;
		this.distributionElementFactor = toAdd;
		
		
		for (int i = 0; i < histogram.length; i++) {
			int low = min;
			int high = min + toAdd;
			
			for(int j = 0; j < distribution.length; j++) {
				if(isInInterval(histogram[i], low, high)) {
					distribution[j]++;
					break;
				}
				low = low + toAdd;
				high = high + toAdd;
			}
		}
		
		return distribution;
	}


	private boolean isInInterval(int number, int low, int high) {
		if(number >= low && number <= high) {
			return true;
		}
		return false;
	}


	private int getBlankThreshold() {
		int border = -1;
		int border0 = -1;
		int border1 = -1;
		
		boolean fall = false;
		
		for (int i = distribution.length-1; i > 0; i--) {
			if(!fall) {
				if(distribution[i] < distribution[i-1]) {
					fall = true;
					System.out.println("Fall at: "+i);
					border0 = i;
					i-=10;
					continue;
				}
				
			}

			if(distribution[i] > distribution[i-1]) {
				System.out.println("Rise at: "+i);
				border1 = i;
				break;
			}

		}
		border = (int)((double)(border0 + border1))/2;
		System.out.println("Border: "+border);
		border = border * this.distributionElementFactor + this.distributionMin;
		return border;
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
