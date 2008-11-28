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
	private List<NumberField> numberFields;
	private Map<Integer, Coordinate> fieldCoordinates;
	
	
}
