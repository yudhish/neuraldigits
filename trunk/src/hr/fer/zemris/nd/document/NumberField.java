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
public class NumberField {

	private BufferedImage scan;
	private NumberFieldScheme scheme;
	private List<DigitField> digits;
	
	
	public NumberField(BufferedImage numberImage) {
		this.scan = numberImage;
		this.digits = new ArrayList<DigitField>();
		createSegments();
		
	}
	
	
	public NumberField(BufferedImage numberImage, NumberFieldScheme scheme) {
		this.scan = numberImage;
		this.scheme = scheme;
		this.digits = new ArrayList<DigitField>();
		createSegments();
		
	}
	
	
	private void createSegments() {
		if(this.scheme == null) {
			getScheme();
			// TODO remove return after getScheme is finished!
			return;
		}
		for(int i = 0; i < this.scheme.getInterestAreasNumber(); i++) {
			RectangularArea numberFieldArea = this.scheme.getInterestArea(i);
			this.digits.add(new DigitField(this.scan.getSubimage(
					numberFieldArea.getTopLeft().getX(), numberFieldArea.getTopLeft().getY(), 
					numberFieldArea.getWidth(), numberFieldArea.getHeight())));
		}
	}


	private void getScheme() {
		// TODO Auto-generated method stub
		
	}


	/**
	 * @return the scan
	 */
	public BufferedImage getScan() {
		return scan;
	}
}
