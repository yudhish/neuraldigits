/**
 * 
 */
package hr.fer.zemris.nd.document.util;

import java.awt.Point;

import hr.fer.zemris.nd.imagelib.Histogram;

/**
 * @author goran
 *
 */
public class HistogramDrawingTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int[] testHistogram = new int[]{2, 0,
				0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 1, 
				0, 2, 2, 0, 0, 2, 1, 2, 3, 2, 5, 4, 2, 1, 
				2, 4, 1, 4, 3, 3, 2, 2, 0, 2, 6, 1, 5, 2, 
				3, 4, 12, 8, 9, 7, 5, 8, 5, 5, 14, 7, 6, 
				5, 7, 10, 9, 13, 7, 13, 14, 11, 13, 10, 14, 
				12, 18, 15, 29, 20, 29, 42, 37, 31, 27, 26, 
				17, 10, 11, 7, 10, 10, 13, 18, 21, 27, 13, 
				17, 13, 7, 8, 9, 12, 9, 17, 38, 39, 0, 0, 0, 
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		int[] test1 = new int[]{0, 0, 1, 2, 2, 1, 2, 5, 1, 1, 1, 1, 0, 1,1,1, 4, 1, 
				0, 0, 1, 2, 2, 1, 2, 5, 1, 1, 1, 1, 0, 1,1,1, 4, 1};
		
		Histogram.showHorizontalHistogram(test1, 12, new Point(10, 50));
		
	}

}