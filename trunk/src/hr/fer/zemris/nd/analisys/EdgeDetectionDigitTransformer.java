package hr.fer.zemris.nd.analisys;

import hr.fer.zemris.nd.document.DigitField;
import hr.fer.zemris.nd.document.DigitFieldScheme;
import hr.fer.zemris.nd.document.util.Coordinate;
import hr.fer.zemris.nd.document.util.DoubleCoordinate;
import hr.fer.zemris.nd.document.util.RectangularArea;
import hr.fer.zemris.nd.document.util.enums.ESegment;
import hr.fer.zemris.nd.gui.ImageDisplay;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;

public class EdgeDetectionDigitTransformer implements IDigitTransformer {

	private DoubleCoordinate leftBorderLine;
	public DoubleCoordinate getLeftBorderLine() {
		return leftBorderLine;
	}
	
	private DoubleCoordinate rightBorderLine;
	private int topBorderOrdinate;
	private int bottomBorderOrdinate;
	private int horizontalSegmentTickness;
	private int verticalSegmentTickness;
	private int middleSegmentCenter;
	
	private int[] buffer= new int[4];
	
	
	public void setLeftBorderLine(DoubleCoordinate leftBorderLine) {
		this.leftBorderLine = leftBorderLine;
	}

	public DoubleCoordinate getRightBorderLine() {
		return rightBorderLine;
	}

	public void setRightBorderLine(DoubleCoordinate rightBorderLine) {
		this.rightBorderLine = rightBorderLine;
	}

	public int getTopBorderOrdinate() {
		return topBorderOrdinate;
	}

	public void setTopBorderOrdinate(int topBorderOrdinate) {
		this.topBorderOrdinate = topBorderOrdinate;
	}

	public int getBottomBorderOrdinate() {
		return bottomBorderOrdinate;
	}

	public void setBottomBorderOrdinate(int bottomBorderOrdinate) {
		this.bottomBorderOrdinate = bottomBorderOrdinate;
	}

	public int getHorizontalSegmentTickness() {
		return horizontalSegmentTickness;
	}

	public void setHorizontalSegmentTickness(int horizontalSegmentTickness) {
		this.horizontalSegmentTickness = horizontalSegmentTickness;
	}

	public int getVerticalSegmentTickness() {
		return verticalSegmentTickness;
	}

	public void setVerticalSegmentTickness(int verticalSegmentTickness) {
		this.verticalSegmentTickness = verticalSegmentTickness;
	}

	public int getMiddleSegmentCenter() {
		return middleSegmentCenter;
	}

	public void setMiddleSegmentCenter(int middleSegmentCenter) {
		this.middleSegmentCenter = middleSegmentCenter;
	}	
	 
	
	@Override
	public DigitField transformImageToDigit(BufferedImage digitImage){
		
		double leftCut=leftBorderLine.getY();
		double rightCut=rightBorderLine.getY();
		
		double direction=leftBorderLine.getX();
		
		
		int width=(int)(rightCut-leftCut);
		int height=bottomBorderOrdinate-topBorderOrdinate;
		Coordinate leftUpOrigin = new Coordinate((int)Math.round((double)topBorderOrdinate*direction+leftCut),
				topBorderOrdinate);
		BufferedImage newDigitImage=new BufferedImage(width,height,BufferedImage.TYPE_BYTE_GRAY);
		WritableRaster wr=newDigitImage.getRaster();
		Raster rr=digitImage.getRaster();
		
		for(int i=0;i<width;i++){
			for(int j=0;j<height;j++){
				int x=leftUpOrigin.getX()+i+(int)Math.round(direction*j);
				//protect
				if(x<0) x=0;
				if(x>rr.getWidth()-1) x=rr.getWidth()-1;
				
				int y=leftUpOrigin.getY()+j;
				//protect
				if(y<0) y=0;
				if(y>rr.getHeight()-1) y=rr.getHeight()-1;
				
				rr.getPixel(x, y, buffer);
				wr.setPixel(i, j, buffer);
				}
			
		}
		
		DigitField newDigit=new DigitField(newDigitImage);
		DigitFieldScheme scheme=new DigitFieldScheme(width,height);
		
		//defining box for upper segment
		RectangularArea topBox= new RectangularArea(
				new Coordinate(verticalSegmentTickness,0),
				new Coordinate(width-1-verticalSegmentTickness,horizontalSegmentTickness));
		scheme.defineSegmentBox(ESegment.UPPER, topBox);
		
		//defining box for down segment
		RectangularArea bottomBox= new RectangularArea(
				new Coordinate(verticalSegmentTickness,height-1-horizontalSegmentTickness),
				new Coordinate(width-1-verticalSegmentTickness,height-1));
		scheme.defineSegmentBox(ESegment.BOTTOM, bottomBox);
		
		//defining box for upper left segment
		RectangularArea topLeftBox= new RectangularArea(
				new Coordinate(0,horizontalSegmentTickness),
				new Coordinate(verticalSegmentTickness,middleSegmentCenter-horizontalSegmentTickness/2));
		scheme.defineSegmentBox(ESegment.UPPER_LEFT, topLeftBox);
		
		//defining box for upper right segment
		RectangularArea topRightBox= new RectangularArea(
				new Coordinate(width-1-verticalSegmentTickness,horizontalSegmentTickness),
				new Coordinate(width-1,middleSegmentCenter-horizontalSegmentTickness/2));
		scheme.defineSegmentBox(ESegment.UPPER_RIGHT, topRightBox);
		
		//defining box for lower left segment
		RectangularArea bottomLeftBox= new RectangularArea(
				new Coordinate(0,middleSegmentCenter+horizontalSegmentTickness/2),
				new Coordinate(verticalSegmentTickness,height-1-horizontalSegmentTickness));
		scheme.defineSegmentBox(ESegment.BOTTOM_LEFT, bottomLeftBox);
		
		//defining box for lower right segment
		RectangularArea bottomRightBox= new RectangularArea(
				new Coordinate(width-1-verticalSegmentTickness,middleSegmentCenter+horizontalSegmentTickness/2),
				new Coordinate(width-1,height-1-horizontalSegmentTickness));
		scheme.defineSegmentBox(ESegment.BOTTOM_RIGHT, bottomRightBox);
		
		//defining box for middle segment
		RectangularArea middleBox= new RectangularArea(
				new Coordinate(verticalSegmentTickness,middleSegmentCenter-horizontalSegmentTickness/2),
				new Coordinate(width-1-verticalSegmentTickness,middleSegmentCenter+horizontalSegmentTickness/2));
		scheme.defineSegmentBox(ESegment.MIDDLE, middleBox);
		
		newDigit.setScheme(scheme);
		return newDigit;
}		

	

}
