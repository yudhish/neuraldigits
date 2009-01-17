/**
 * 
 */
package hr.fer.zemris.nd.learningset;

import java.io.File;

import hr.fer.zemris.nd.document.OcrScheme;

/**
 * @author goran
 *
 */
public class NumberFieldGetter {
	
	private OcrScheme scheme;
	private File inFolderLocation;
	private File outFolderLocation;
	
	public NumberFieldGetter(OcrScheme scheme, File inputFolderLocation, 
			File outFolderLocation) {
		this.scheme = scheme;
		this.inFolderLocation = inputFolderLocation;
		this.outFolderLocation = outFolderLocation;
	}
	
	
	public void getNumberFields() {
		
	}

}
