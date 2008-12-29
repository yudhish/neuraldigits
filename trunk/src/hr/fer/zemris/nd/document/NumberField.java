/**
 * 
 */
package hr.fer.zemris.nd.document;

import hr.fer.zemris.nd.analisys.HistogramMinimaAnaliser;
import hr.fer.zemris.nd.document.util.RectangularArea;
import hr.fer.zemris.nd.document.util.ui.BufferedImageDrawer;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.renderable.ParameterBlock;
import java.util.ArrayList;
import java.util.List;

import javax.media.jai.ROI;
import javax.swing.JFrame;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;


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
		createScheme();
		createSegments();
		
	}
	
	
	

	public NumberField(BufferedImage numberImage, NumberFieldScheme scheme) {
		this.scan = numberImage;
		this.scheme = scheme;
		this.digits = new ArrayList<DigitField>();
		createSegments();
		
	}
	
	
	private void createScheme() {
		System.out.println("Initializing scheme.");
		this.scheme = new NumberFieldScheme(this.scan.getWidth(), this.scan.getHeight());
		JFrame frame = new JFrame();
		frame.add(new BufferedImageDrawer(this.scan));
		frame.setSize(new Dimension(this.scan.getWidth(), this.scan.getHeight()));
		frame.setVisible(true);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
		NumberFieldScheme histogramAnalisysScheme = 
			this.hetHistogramAnalisysScheme();
		NumberFieldScheme edgeDetectionScheme = getEdgeDetectionScheme();
		System.out.println("Histogram analisys scheme: \n"+histogramAnalisysScheme);
		System.out.println();
		System.out.println(scheme);
		
	}

	
	
	private NumberFieldScheme getEdgeDetectionScheme() {
		// TODO Auto-generated method stub
		return null;
	}




	private NumberFieldScheme hetHistogramAnalisysScheme() {
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
