/**
 * 
 */
package hr.fer.zemris.nd.document.util;

import hr.fer.zemris.nd.document.util.enums.EPaperSize;
import hr.fer.zemris.nd.document.util.enums.ELengthUnit;

import java.util.HashMap;
import java.util.Map;

/**
 * @author goran
 *
 */
public final class PaperSizes {

	/**
	 * Contains paper sizes in milimeters
	 */
	private Map<EPaperSize, Coordinate> sizes;
	private static PaperSizes instance;
	
	
	private PaperSizes() {
		this.sizes = new HashMap<EPaperSize, Coordinate>();
		initSizes();
	}
	
	public static PaperSizes instance() {
		if(instance == null) {
			instance = new PaperSizes();
		}
		return instance;
		
	}
	
	
	/**
	 * @return paper size in milimeters
	 */
	public Coordinate getSize(EPaperSize size) {
		return sizes.get(size);
	}

	
	public DoubleCoordinate getSize(EPaperSize size, ELengthUnit unit) {
		switch (unit) {
		case MILIMETER:
			return new DoubleCoordinate(
					sizes.get(size).getX(), sizes.get(size).getY());
		case INCH:
			return new DoubleCoordinate(
					sizes.get(size).getX()/25.4, sizes.get(size).getY()/25.4);
		default:
			return null;
		}
	}
	
	
	private void initSizes() {
		this.sizes.put(EPaperSize.A2, new Coordinate(420, 594));
		this.sizes.put(EPaperSize.A3, new Coordinate(297, 420));
		this.sizes.put(EPaperSize.A4, new Coordinate(210, 297));
		this.sizes.put(EPaperSize.A5, new Coordinate(148, 210));
	}
	
	
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("Supported paper sizes: \n");
		buffer.append(this.sizes);
		return buffer.toString();
	}
	
}
