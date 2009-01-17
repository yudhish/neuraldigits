/**
 * 
 */
package hr.fer.zemris.nd.document;

import hr.fer.zemris.nd.document.util.PaperSizes;
import hr.fer.zemris.nd.document.util.RectangularArea;
import hr.fer.zemris.nd.document.util.enums.EPaperSize;
import hr.fer.zemris.nd.document.util.enums.EResolutionUnit;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author goran
 *
 */
public class OcrScheme {
	private int width;
	private int height;
	private List<RectangularArea> interestAreas;
	private Map<RectangularArea, String> interestAreaValue;
	
	
	public OcrScheme(int width, int height) {
		if(width <= 0 || height <= 0) {
			throw new IllegalArgumentException("The width and height need to be " +
					"positive integers");
		}
		this.width = width;
		this.height = height;
		this.interestAreas = new ArrayList<RectangularArea>();
	}
	

	
	public OcrScheme(EPaperSize size, int resolution, EResolutionUnit resolutionUnit) {
		double dotsperMM = calculateDPMM(resolution, resolutionUnit);
		PaperSizes sizes = PaperSizes.instance();
		this.height = (int)Math.round(sizes.getSize(size).getY() * dotsperMM);
		this.width = (int)Math.round(sizes.getSize(size).getY() * dotsperMM);
		this.interestAreas = new ArrayList<RectangularArea>();
	}
	
	
	public void addInterestArea(RectangularArea area) {
		if(!isWithinBounds(area)) {
			throw new IllegalArgumentException(
					"The specified area is not within the OCR Scheme document area ("+
					this.width+", "+this.height+"). ");
		}
		for(RectangularArea element: this.interestAreas) {
			if(element.overlapsWith(area)) {System.out.println(this.width+", "+(this.width<=0));
				throw new IllegalArgumentException(
						"The area "+area+" overlaps with allready defined area "+element+". ");
			}
		}
		this.interestAreas.add(area);
	}
	
	
	private boolean isWithinBounds(RectangularArea area) {
		if(area.getBottomRight().getX() > this.width || 
				area.getBottomRight().getY() > this.height) {
			return false;
		}
		return true;
	}

	
	/**
	 * Calculates the number of dots per milimeter for each of the resolution units. 
	 * 
	 * @param resolution resolution numerical factor
	 * @param unit resolution measurement unit
	 * @return resolution in dots per milimeter 
	 */
	public double calculateDPMM(int resolution, EResolutionUnit unit) {
		switch (unit) {
		case DPMM:
			return resolution;
		case DPCM:
			return resolution/10;
		case DPI:
			return resolution/25.4;
		default:
			return -1;
		}
	}
	
	
	public void addAreaNumber(RectangularArea area, String value) {
		this.interestAreaValue.put(area, value);
	}
	
	
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("OCR Scheme, ");
		buffer.append(this.width);
		buffer.append(" x ");
		buffer.append(this.height);
		buffer.append(",\n interest areas: \n");
		buffer.append(this.interestAreas);
		return buffer.toString();
	}
	
	


	public boolean sizeAppropriateFor(BufferedImage scan) {
		if(this.height == scan.getHeight() && this.width == scan.getWidth()) {
			return true;
		}
		return false;
	}

	
	public int getInterestAreasNumber() {
		return this.interestAreas.size();
	}
	
	
	public RectangularArea getInterestArea(int i) {
		return this.interestAreas.get(i);
	}
	
}
