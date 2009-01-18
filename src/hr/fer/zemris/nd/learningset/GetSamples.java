/**
 * 
 */
package hr.fer.zemris.nd.learningset;

import java.io.File;

/**
 * @author goran
 *
 */
public class GetSamples {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		File inFolder = new File("/home/goran/Desktop/processedBUBII");
	    File outFolder = new File("/home/goran/Desktop/Samples");
	    DigitFieldGetter getter = new DigitFieldGetter(inFolder, outFolder);
	    getter.getDigitFields();

	}

}
