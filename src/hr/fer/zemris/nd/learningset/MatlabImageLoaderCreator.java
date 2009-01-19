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
		size.add(186); // 0
		size.add(134); // 1
		size.add(223); // 2
		size.add(163); // 3
		size.add(266);  // 4
		size.add(108); // 5
		size.add(167); // 6 
		size.add(187); // 7
		size.add(90); // 8
		size.add(232); // 9
		
		MatlabImageLoad.generateImageLoadFile(new File("Load.m"), size);

	}

}
