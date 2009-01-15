package hr.fer.zemris.nd.analisys;

import hr.fer.zemris.nd.document.util.RectangularArea;

import java.util.ArrayList;
import java.util.List;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;

public class EdgeDigitAnaliser implements IDigitAnaliser {

	private List<BufferedImage> digits=null;
	private RectangularArea boundingBox=null;
	private List<RectangularArea> segmentBoxes=null;
	double treshold=0.3;
	
	
	public EdgeDigitAnaliser(List<BufferedImage> digits){
		this.digits=new ArrayList<BufferedImage>(digits);
	}
	
	public EdgeDigitAnaliser(BufferedImage numberField, List<RectangularArea> boundingBoxes){
		this.digits=new ArrayList<BufferedImage>();
		this.digits.clear();
		for(RectangularArea currentDigitBox:boundingBoxes){
			BufferedImage newImage=numberField.getSubimage(currentDigitBox.getTopLeft().getX(),
															currentDigitBox.getTopLeft().getY(),
															currentDigitBox.getWidth(),
															currentDigitBox.getHeight());
			digits.add(newImage);
		}
		
	}
	@Override
	public RectangularArea getBoundingBox() {
		if(boundingBox!=null){
			return boundingBox;
		}
		
		return null;
	}

	@Override
	public List<RectangularArea> getSegmentsBoxes() {
		
		if(segmentBoxes!=null){
			return segmentBoxes;
		}
		return null;
	}
	
	private int getLeftOuterBound(int digitIndex, int digitOrdinate){
		
		Raster r=digits.get(digitIndex).getRaster();
		double value=0;
		for(int i=0;i<r.getWidth();i++){
			
		}
		return 0;
	}
	
	private int getRightOuterBound(int digitIndex, int digitOrdinate){
		return 0;
	}
	
	private int getTopOuterBound(int digitIndex, int digitAbscissa){
		return 0;
	}
	
	private int getBottomOuterBound(int digitIndex, int digitAbscissa){
		return 0;
	}
	
	private int getLeftInnerBound(int digitIndex, int digitOrdinate){
		return 0;
	}
	
	private int getRightInnerBound(int digitIndex, int digitOrdinate){
		return 0;
	}
	
	private int getTopInnerBound(int digitIndex, int digitAbscissa){
		return 0;
	}
	
	private int getBottomInnerBound(int digitIndex, int digitAbscissa){
		return 0;
	}
	
	private double filter(double value){
		return value*value;
	}
	
	
	

}
