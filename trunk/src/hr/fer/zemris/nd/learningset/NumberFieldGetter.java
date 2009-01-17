/**
 * 
 */
package hr.fer.zemris.nd.learningset;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;

import javax.imageio.ImageIO;

import hr.fer.zemris.nd.document.NumberField;
import hr.fer.zemris.nd.document.OcrDocument;
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
		File[] imageFiles = inFolderLocation.listFiles();
//		System.out.println(Arrays.toString(imageFiles));
		
		for (int i = 0; i < imageFiles.length; i++) {
			try {
				BufferedImage image = ImageIO.read(imageFiles[i]);
				OcrDocument document = new OcrDocument(image, scheme);
				for (NumberField field: document.getNumberFields()) {
					
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}

}
