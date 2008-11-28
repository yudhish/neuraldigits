/**
 * 
 */
package hr.fer.zemris.nd.document;

import hr.fer.zemris.nd.document.util.Coordinate;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;

/**
 * @author goran
 *
 */
public class OcrDocument {

	private BufferedImage scan;
	private OcrScheme scheme;
	private List<NumberField> numberFields;
	private Map<Integer, Coordinate> fieldCoordinates;
	
	
	public OcrDocument(BufferedImage scan, OcrScheme scheme) {
		this.scan = scan;
		this.scheme = scheme;
	}
	
	
	private void generateSegments() {
		// TODO do the simple segmentation here
	}
	
	
	private NumberField getNumberField(int index) {
		if(index < 0) {
			throw new IllegalArgumentException("The index of the number field " +
					"needs to be greater than or equal to zero. ");
		}
		return this.numberFields.get(index);
	}
	
	
//	private 
	
	
}
