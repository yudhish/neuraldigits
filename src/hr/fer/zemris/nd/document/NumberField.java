/**
 * 
 */
package hr.fer.zemris.nd.document;

import hr.fer.zemris.nd.analisys.HistogramMinimaAnaliser;
import hr.fer.zemris.nd.document.util.RectangularArea;
import hr.fer.zemris.nd.gui.BufferedImageDrawer;
import hr.fer.zemris.nd.gui.ImageDisplay;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.WindowConstants;


/**
 * @author goran
 *
 */
public class NumberField {

	private BufferedImage scan;
	private NumberFieldScheme scheme;
	private List<DigitField> digits;
	private ENumberFieldDisplayMode displayMode;
	
	
	public NumberField(BufferedImage numberImage) {
		this.scan = numberImage;
		this.digits = new ArrayList<DigitField>();
		this.displayMode = ENumberFieldDisplayMode.VERBOSE;
		createScheme();
		createSegments();
		
	}
	
	
	

	public NumberField(BufferedImage numberImage, NumberFieldScheme scheme) {
		this.scan = numberImage;
		this.scheme = scheme;
		this.digits = new ArrayList<DigitField>();
		this.displayMode = ENumberFieldDisplayMode.VERBOSE;
		createSegments();
		
	}
	
	
	
	
	
	private void createScheme() {
		System.out.println("Initializing scheme.");
		this.scheme = new NumberFieldScheme(this.scan.getWidth(), this.scan.getHeight());
		
		ImageDisplay.displayImage(this.scan, new Point(150, 0));
		
		NumberFieldScheme histogramAnalisysScheme = 
			this.getHistogramAnalisysScheme();
		NumberFieldScheme edgeDetectionScheme = getEdgeDetectionScheme();
		System.out.println("Histogram analisys scheme: \n"+histogramAnalisysScheme);
		System.out.println();
		System.out.println(scheme);
		
	}

	
	
	private NumberFieldScheme getEdgeDetectionScheme() {
		// TODO Auto-generated method stub
		return null;
	}




	private NumberFieldScheme getHistogramAnalisysScheme() {
		HistogramMinimaAnaliser analyser = new HistogramMinimaAnaliser(this.scan);
		NumberFieldScheme scheme = new NumberFieldScheme(
				this.scan.getWidth(), this.scan.getHeight());
		for(RectangularArea area: analyser.getDigitAreas()) {
			scheme.addInterestArea(area);
		}
		
		return scheme;
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


	private NumberFieldScheme getScheme() {
		return this.scheme;
		
	}
	
	
	public void setDisplayMode(ENumberFieldDisplayMode mode) {
		this.displayMode = mode;
	}
	


	/**
	 * @return the scan
	 */
	public BufferedImage getImage() {
		return scan;
	}




	public List<DigitField> getDigitFields() {
		return this.digits;
	}
}
