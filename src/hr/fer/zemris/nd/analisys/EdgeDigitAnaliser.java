package hr.fer.zemris.nd.analisys;

import hr.fer.zemris.nd.document.util.Coordinate;
import hr.fer.zemris.nd.document.util.DoubleCoordinate;
import hr.fer.zemris.nd.document.util.RectangularArea;
import hr.fer.zemris.nd.document.util.enums.AxisOrientation;
import hr.fer.zemris.nd.gui.ImageDisplay;

import java.util.ArrayList;
import java.util.List;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;

import sun.util.BuddhistCalendar;


public class EdgeDigitAnaliser implements IDigitAnaliser {

	private List<BufferedImage> originalDigits=null;
	private List<BufferedImage> digits=new ArrayList<BufferedImage>();
	private RectangularArea boundingBox=null;
	private List<RectangularArea> segmentBoxes=null;	
	
	int[] buffer=new int[4];
	
	
	public EdgeDigitAnaliser(List<BufferedImage> digits){
		this.originalDigits=new ArrayList<BufferedImage>(digits);		
	}
	
	public EdgeDigitAnaliser(BufferedImage numberField, List<RectangularArea> boundingBoxes){
		this.originalDigits=new ArrayList<BufferedImage>();
		this.originalDigits.clear();
		for(RectangularArea currentDigitBox:boundingBoxes){
			BufferedImage newImage=numberField.getSubimage(currentDigitBox.getTopLeft().getX(),
															currentDigitBox.getTopLeft().getY(),
															currentDigitBox.getWidth(),
															currentDigitBox.getHeight());
			originalDigits.add(newImage);
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
	
	public void align(){
		
		List<Integer> topOrdinates= new ArrayList<Integer>();
		List<Integer> bottomOrdinates= new ArrayList<Integer>();
		List<DoubleCoordinate> leftBorders = new ArrayList<DoubleCoordinate>();
		List<DoubleCoordinate> rightBorders = new ArrayList<DoubleCoordinate>();
		
		for(int i=0;i<originalDigits.size();i++){
		
			BufferedImage digit=originalDigits.get(0);
		
			int topOrdinate=getTopOrdinate(0);
			topOrdinates.add(topOrdinate);
			
			int bottomOrdinate=getBottomOrdinate(0);
			bottomOrdinates.add(bottomOrdinate);
		
			DoubleCoordinate leftBorder=getLeftOuterLine(0, topOrdinate, bottomOrdinate);
			leftBorders.add(leftBorder);
			
			DoubleCoordinate rightBorder=getRightOuterLine(0, topOrdinate, bottomOrdinate);
			rightBorders.add(rightBorder);
		
		
		}
		
		int topOrdinate = (int)getAvg(topOrdinates);
		int bottomOrdinate=(int)getAvg(bottomOrdinates);
		
		double direction=0;
		double leftCut=0;
		double rightCut=0;
		
		for(int i=0;i<leftBorders.size();i++){
			direction+=leftBorders.get(i).getX();
			direction+=rightBorders.get(i).getX();
			leftCut+=leftBorders.get(i).getY();
			rightCut+=rightBorders.get(i).getY();
		}
		
		direction=direction/(2*leftBorders.size());
		leftCut=leftCut/leftBorders.size();
		rightCut=rightCut/leftBorders.size();
		
		DoubleCoordinate leftBorder=new DoubleCoordinate(direction,leftCut);
		DoubleCoordinate rightBorder=new DoubleCoordinate(direction,rightCut);
	
		//only for testing
		for(BufferedImage digit:originalDigits){
			WritableRaster r=digit.getRaster();
			buffer[0]=0;
			for(int i=0;i<r.getWidth();i++){
				r.setPixel(i, topOrdinate, buffer);
				r.setPixel(i, bottomOrdinate, buffer);
			}
		
			for(int i=0; i< r.getHeight();i++){
				double abscissaLeft=leftBorder.getX()*i+leftBorder.getY();
				double abscissaRight=rightBorder.getX()*i+rightBorder.getY();
				if(abscissaLeft>0 && abscissaLeft <r.getWidth() ){
					r.setPixel((int)abscissaLeft, i, buffer);
				}
				if(abscissaRight>0 && abscissaRight <r.getWidth() ){
					r.setPixel((int)abscissaRight, i, buffer);
				}
			
			}
		
			ImageDisplay disp=new ImageDisplay(digit);
		}
		int width=(int)(rightCut-leftCut);
		int height=bottomOrdinate-topOrdinate;
		Coordinate leftUpOrigin = new Coordinate((int)Math.round((double)topOrdinate*direction+leftCut),topOrdinate);
		
		for(BufferedImage digit:originalDigits){
			BufferedImage newDigit=new BufferedImage(width,height,BufferedImage.TYPE_BYTE_GRAY);
			WritableRaster wr=newDigit.getRaster();
			Raster rr=digit.getRaster();
		
		
			for(int i=0;i<width;i++){
				for(int j=0;j<height;j++){
					int x=leftUpOrigin.getX()+i+(int)(direction*j);
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
			digits.add(newDigit);
		}
		
		//testing again
		for(BufferedImage digit:digits){
			ImageDisplay disp=new ImageDisplay(digit);
		}
	}

	
	
	private int getTopOrdinate(int digitIndex){
		double treshold=220;
		BufferedImage digit=originalDigits.get(digitIndex);
		Raster r=digit.getRaster();
		for(int i=0;i<r.getHeight();i++){
			int sum=0;
			for(int j=0;j<r.getWidth();j++){
				r.getPixel(j, i, buffer);
				sum+=buffer[0];				
			}
			if((double)sum/r.getWidth()<treshold){
				return i;
			}
		}
		return -1;		
	}
	
	private int getBottomOrdinate(int digitIndex){
		double treshold=220;
		BufferedImage digit=originalDigits.get(digitIndex);
		Raster r=digit.getRaster();
		for(int i=r.getHeight()-1;i>=0;i--){
			int sum=0;
			for(int j=0;j<r.getWidth();j++){
				r.getPixel(j, i, buffer);
				sum+=buffer[0];				
			}
			if((double)sum/r.getWidth()<treshold){
				return i;
			}
		}
		return -1;		
	}	
	
	private DoubleCoordinate getLeftOuterLine(int digitIndex, int topOrdinate, int bottomOrdinate){
		
		int upperMiddleOrdinate=topOrdinate+(bottomOrdinate-topOrdinate)/4;
		int downMiddleOrdinate=upperMiddleOrdinate+(bottomOrdinate-topOrdinate)/2;
		
		int ordinateOffset=(bottomOrdinate-topOrdinate)/8;
		
		List<Coordinate> points=new ArrayList<Coordinate>();
		
		for(int i=upperMiddleOrdinate-ordinateOffset;i<=upperMiddleOrdinate+ordinateOffset;i++){
			int bound=aproachFromLeft(digitIndex, i, 2);
			Coordinate point=new Coordinate(bound,i);
			points.add(point);
		}
		
		for(int i=downMiddleOrdinate-ordinateOffset;i<=downMiddleOrdinate+ordinateOffset;i++){
			int bound=aproachFromLeft(digitIndex, i, 2);
			Coordinate point=new Coordinate(bound,i);
			points.add(point);
		}
		
		return LinearRegresion.getAproximationLine(points, AxisOrientation.YX);
		
		
	}
	
	private DoubleCoordinate getRightOuterLine(int digitIndex, int topOrdinate, int bottomOrdinate){
		
		int upperMiddleOrdinate=topOrdinate+(bottomOrdinate-topOrdinate)/4;
		int downMiddleOrdinate=upperMiddleOrdinate+(bottomOrdinate-topOrdinate)/2;
		
		int ordinateOffset=(bottomOrdinate-topOrdinate)/8;
		
		List<Coordinate> points=new ArrayList<Coordinate>();
		
		for(int i=upperMiddleOrdinate-ordinateOffset;i<=upperMiddleOrdinate+ordinateOffset;i++){
			int bound=aproachFromRight(digitIndex, i, 2);
			Coordinate point=new Coordinate(bound,i);
			points.add(point);
		}
		
		for(int i=downMiddleOrdinate-ordinateOffset;i<=downMiddleOrdinate+ordinateOffset;i++){
			int bound=aproachFromRight(digitIndex, i, 2);
			Coordinate point=new Coordinate(bound,i);
			points.add(point);
		}
		
		return LinearRegresion.getAproximationLine(points, AxisOrientation.YX);
	}
	
	private int aproachFromLeft(int digitIndex, int ordinate, int offset){
		double treshold=140;
		Raster r=originalDigits.get(digitIndex).getRaster();
		
		for(int i=0;i<r.getWidth();i++){
			int sum=0;
			for(int j=ordinate-offset;j<=ordinate+offset;j++){
				r.getPixel(i, j, buffer);
				sum+=buffer[0];
			}
			
			if((double)sum/(2*offset+1)<treshold){
				return i;
			}
		}
		
		return -1;
	}
	
	private int aproachFromRight(int digitIndex, int ordinate, int offset){
		double treshold=140;
		Raster r=originalDigits.get(digitIndex).getRaster();
		
		for(int i=r.getWidth()-1;i>=0;i--){
			int sum=0;
			for(int j=ordinate-offset;j<=ordinate+offset;j++){
				r.getPixel(i, j, buffer);
				sum+=buffer[0];
			}
			
			if((double)sum/(2*offset+1)<treshold){
				return i;
			}
		}
		
		return -1;
	}	
	
	
	
	
	
	
	
	
	
	
	private int getTopOuterBound(int digitIndex, int digitAbscissa){
		Raster r=originalDigits.get(digitIndex).getRaster();
		for(int i=0;i<r.getHeight();i++){
			r.getPixel(digitAbscissa,i, buffer);
			int value=buffer[0];
			value=filter(value);
			if(treshold(value)){
				return i;
			}
		}
		return -1;
	}
	
	private int getBottomOuterBound(int digitIndex, int digitAbscissa){
		Raster r=originalDigits.get(digitIndex).getRaster();
		for(int i=r.getHeight()-1;i>=0;i--){
			r.getPixel(digitAbscissa,i, buffer);
			int value=buffer[0];
			value=filter(value);
			if(treshold(value)){
				return i;
			}
		}
		return -1;
	}
	
	private int getLeftInnerBound(int digitIndex, int digitOrdinate){
		Raster r=originalDigits.get(digitIndex).getRaster();
		int middle=r.getWidth()/2;
		int testValue=0;
		r.getPixel(middle,digitOrdinate, buffer);
		testValue=buffer[0];
		testValue=filter(testValue);
		if(treshold(testValue)){
			return -1;
		}
		
		for(int i=middle;i>=0;i--){
			r.getPixel(i,digitOrdinate, buffer);
			int value=buffer[0];
			value=filter(value);
			if(treshold(value)){
				return i;
			}
		}
		return -1;
	}
	
	private int getRightInnerBound(int digitIndex, int digitOrdinate){
		Raster r=originalDigits.get(digitIndex).getRaster();
		int middle=r.getWidth()/2;
		int testValue=0;
		r.getPixel(middle,digitOrdinate, buffer);
		testValue=buffer[0];
		testValue=filter(testValue);
		if(treshold(testValue)){
			return -1;
		}
		
		for(int i=middle;i<r.getWidth();i++){
			r.getPixel(i,digitOrdinate, buffer);
			int value=buffer[0];
			value=filter(value);
			if(treshold(value)){
				return i;
			}
		}
		return -1;
	}
	
	private int getTopInnerBound(int digitIndex, int digitAbscissa, int startOrdinate){
		Raster r=originalDigits.get(digitIndex).getRaster();
		int testValue=0;
		r.getPixel(digitAbscissa,startOrdinate, buffer);
		testValue=buffer[0];
		testValue=filter(testValue);
		if(treshold(testValue)){
			return -1;
		}
		
		for(int i=startOrdinate;i>=0;i--){
			r.getPixel(digitAbscissa,i, buffer);
			int value=buffer[0];
			value=filter(value);
			if(treshold(value)){
				return i;
			}
		}
		return -1;
	}
	
	private int getBottomInnerBound(int digitIndex, int digitAbscissa, int startOrdinate){
		Raster r=originalDigits.get(digitIndex).getRaster();
		int testValue=0;
		r.getPixel(digitAbscissa,startOrdinate, buffer);
		testValue=buffer[0];
		testValue=filter(testValue);
		if(treshold(testValue)){
			return -1;
		}
		
		for(int i=startOrdinate;i<r.getHeight();i++){
			r.getPixel(digitAbscissa,i, buffer);
			int value=buffer[0];
			value=filter(value);
			if(treshold(value)){
				return i;
			}
		}
		return -1;
	}	
	
	private int filter(int value){
		return value;
	}
	
	private boolean treshold(int value){
		return value<100;
	}
	
	private double getAvg(List<Integer> values){
		double sum=0;
		for(Integer number:values){
			sum+=number;
		}
		return sum/(double)values.size();
	}
	
	private double getAvgDistance(List<Integer> values, double avg){
		double distance=0;
		for(Integer number:values){
			distance+=Math.abs(avg-(double)number);
		}
		return distance/(double)values.size();
	}
	
	
	

}
