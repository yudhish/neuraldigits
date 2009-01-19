/**
 * 
 */
package hr.fer.zemris.nd.learningset;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author goran
 *
 */
public class MatlabImageLoaderCreator {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<Integer> size = new ArrayList<Integer>();
		size.add(10); // 0
		size.add(15); // 1
		size.add(25); // 2
		size.add(33); // 3
		size.add(4);  // 4
		size.add(37); // 5
		size.add(32); // 6 
		size.add(37); // 7
		size.add(23); // 8
		size.add(27); // 9
		
		MatlabImageLoad.generateImageLoadFile(new File("Load.m"), size);

	}

}
