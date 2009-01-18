package hr.fer.zemris.nd.document;

import java.util.HashMap;
import java.util.Map;



import hr.fer.zemris.nd.document.util.RectangularArea;
import hr.fer.zemris.nd.document.util.enums.ESegment;

public class DigitFieldScheme {

	private int width;
	private int height;
	

	private Map<ESegment, RectangularArea> segmentBoxes;
	
	public DigitFieldScheme(int width, int height){
		if(width <= 0 || height <= 0) {
			throw new IllegalArgumentException("The width and height need to be " +
					"positive integers");
		}
		segmentBoxes=new HashMap<ESegment, RectangularArea>();
		this.width=width;
		this.height=height;
	}
	
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	public void defineSegmentBox(ESegment segment, RectangularArea box){
		if(box.getBottomRight().getX()>width ||
				box.getBottomRight().getY()>height ||
				box.getTopLeft().getX()<0 ||
				box.getTopLeft().getY()<0) {
			throw new IllegalArgumentException(
					"The specified area is not within the DigitField Scheme area ("+
					this.width+", "+this.height+"). ");
		}
		
		segmentBoxes.put(segment, box);
	}
	
	public RectangularArea getSegmentBox(ESegment segment){
		RectangularArea ret=segmentBoxes.get(segment);
		if(ret==null){
			throw new IllegalArgumentException(
					"Specified segment ( "+segment.toString()+" ) is not yet defined" +
							" in Digit scheme");
		}
		
		return ret;
	}
	
	
}
