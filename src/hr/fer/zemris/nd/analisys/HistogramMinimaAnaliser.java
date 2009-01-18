/**
 * 
 */
package hr.fer.zemris.nd.analisys;

import hr.fer.zemris.nd.document.util.Coordinate;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
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
	private boolean[] xWhite;
	private boolean[] yWhite;
	public RectangularArea boundingBox;
	private int[] distribution;
	private int distributionElementFactor;
	private int distributionMin;
	private int blankValueThreshold;
	private int yBlankThreshold;
	private int[] rangeX;
	private int[] rangesY;
	private ImageDisplay display;
	
	
	
	
	
	public HistogramMinimaAnaliser(BufferedImage image) {
		this.image = image; 
		this.displayImage = new BufferedImage(
				image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
		displayImage.getGraphics().drawImage(image, 0, 0, null);
		this.raster = image.getRaster();
		
		
		int avg = (int)Picture.getImagePixelAverage(image);

		this.preprocessingImage = getPreprocessingImage(avg);
		display = new ImageDisplay(displayImage);
		
//		removeNoise(preprocessingImage, 4, 0.1);
//		removeNoise(preprocessingImage, 4, 0.1);
//		removeNoise(preprocessingImage, 4, 0.1);
//		removeNoise(preprocessingImage, 4, 0.1);
		//display.repaint();

		
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
	
		return drawSegments();
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
		this.yBlankThreshold = getBlankThresholdY();
		showWhiteAreas();
		System.out.println("BlankThreshold: "+blankValueThreshold);
		rangeX = getxRange();
		rangesY = getyRange();
		drawBoundingBox();
//		drawSegments();
		
	}
	
	
	private List<RectangularArea> drawSegments() {
		List<int[]> segmentLimits = getSegmentXLimits();
		List<RectangularArea> l = new ArrayList<RectangularArea>();
		for (int[] array: segmentLimits) {
			drawVerticalLine(array[0], new int[]{255, 0, 0});
			drawVerticalLine(array[1], new int[]{255, 0, 0});
			RectangularArea a = new RectangularArea(new Coordinate(array[0], this.rangesY[0]), 
					new Coordinate(array[1], this.rangesY[1]));
			l.add(a);
		}
		return l;
	}




	private void drawVerticalLine(int x, int[] js) {
		for(int i = 0; i < this.displayImage.getHeight(); i++) {
			this.displayImage.getRaster().setPixel(x, i, js);
			this.displayImage.getRaster().setPixel(x, i, js);
		}
		this.display.repaint();
		
	}


	private List<int[]> getSegmentXLimits() {
		List<int[]> lista = new ArrayList<int[]>();
		
		boolean second = false;
		int[] limit = new int[2];
		for (int i = 1; i < xWhite.length-1; i++) {
			if(xWhite[i-1] != xWhite[i]) {
				if(second) {
					limit[1] = i;
					lista.add(limit);
				} else {
					limit = new int[2];
					limit[0] = i;
				}
				second = !second;
			}
		}
		return lista;
	}


	private void drawBoundingBox() {
		System.out.println("Drawing the bounding box");
		int[] blue = new int[]{0, 0, 255};
		for(int i = 0; i < this.displayImage.getHeight(); i++) {
			this.displayImage.getRaster().setPixel(this.rangeX[0], i, blue);
			this.displayImage.getRaster().setPixel(this.rangeX[1], i, blue);
		}
		for(int i = 0; i < this.displayImage.getWidth(); i++) {
			this.displayImage.getRaster().setPixel(i, this.rangesY[0], blue);
			this.displayImage.getRaster().setPixel(i, this.rangesY[1], blue);
		}
		this.display.repaint();
		
	}


	private int getBlankThresholdY() {
		int border = -1;
		int border0 = -1;
		int border1 = -1;
		int[] distribution = getDistribution(this.yHistogram);
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
		this.xWhite = white;
		
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
		int[] range = new int[2];
		this.yWhite = new boolean[this.yHistogram.length];
		for (int i = 0; i < yWhite.length; i++) {
			if(yHistogram[i] > this.blankValueThreshold) {
				yWhite[i] = true;
			} else {
				yWhite[i] = false;
			}
		}
		for (int i = 0; i < this.yWhite.length; i++) {
			if(!yWhite[i]) {
				range[0] = i;
				break;
			}	
		}
		
		for (int i = yWhite.length-1; i > 0; i--) {
			if(!yWhite[i]) {
				range[1] = i;
				break;
			}
		}
		
		return range;
	}


	private int[] getxRange() {
		int[] range = new int[2];
		for (int i = 0; i < this.xWhite.length; i++) {
			if(!xWhite[i]) {
				range[0] = i;
				break;
			}	
		}
		
		for (int i = xWhite.length-1; i > 0; i--) {
			if(!xWhite[i]) {
				range[1] = i;
				break;
			}
		}
		
		return range;
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
