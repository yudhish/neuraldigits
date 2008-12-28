package hr.fer.zemris.nd.document;

import hr.fer.zemris.nd.document.util.RectangularArea;

import java.awt.image.BufferedImage;
import java.util.List;

public class NumberFieldScheme {

	private int width;
	private int height;
	private List<RectangularArea> interestAreas;
	
	private NumberFieldScheme(int width, int height) {
		if(this.width <= 0 || this.height <= 0) {
			throw new IllegalArgumentException("The width and height need to be " +
					"positive integers");
		}
		
		this.width = width;
		this.height = height;
	}
	
	
	public void addInterestArea(RectangularArea area) {
		if(!isWithinBounds(area)) {
			throw new IllegalArgumentException(
					"The specified area is not within the NumberField Scheme document area ("+
					this.width+", "+this.height+"). ");
		}
		for(RectangularArea element: this.interestAreas) {
			if(element.overlapsWith(area)) {
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
	
	
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("NumberField Scheme, ");
		buffer.append(this.width);
		buffer.append(" x ");
		buffer.append(this.height);
		buffer.append(", interest areas: ");
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
