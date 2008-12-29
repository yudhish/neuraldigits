/**
 * 
 */
package hr.fer.zemris.nd.document;

import hr.fer.zemris.nd.document.util.RectangularArea;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * @author goran
 *
 */
public class OcrDocument {

	private BufferedImage scan;
	private OcrScheme scheme;
	private List<NumberField> numberFields;
	
	
	public OcrDocument(BufferedImage scan, OcrScheme scheme) {
		this.scan = scan;
		this.scheme = scheme;
		if(!scheme.sizeAppropriateFor(scan)) {
			throw new IllegalArgumentException("The sizes of the OCR Scheme and the image " +
					"need to be equal. ");
		}
		this.numberFields = new ArrayList<NumberField>();
		generateSegments();
	}
	
	
	private void generateSegments() {
		for(int i = 0; i < this.scheme.getInterestAreasNumber(); i++) {
			RectangularArea numberFieldArea = this.scheme.getInterestArea(i);
			this.numberFields.add(new NumberField(this.scan.getSubimage(
					numberFieldArea.getTopLeft().getX(), numberFieldArea.getTopLeft().getY(), 
					numberFieldArea.getWidth(), numberFieldArea.getHeight())));
		}
	}
	
	
	public NumberField getNumberField(int index) {
		if(this.numberFields == null) {
			this.generateSegments();
		}
		if(index < 0) {
			throw new IllegalArgumentException("The index of the number field " +
					"needs to be greater than or equal to zero. ");
		}
		return this.numberFields.get(index);
	}


	/**
	 * @return the scan
	 */
	public BufferedImage getScan() {
		return scan;
	}
	
	
}
