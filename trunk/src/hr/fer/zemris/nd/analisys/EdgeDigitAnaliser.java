package hr.fer.zemris.nd.analisys;

import hr.fer.zemris.nd.document.DigitField;
import hr.fer.zemris.nd.document.util.Coordinate;
import hr.fer.zemris.nd.document.util.DoubleCoordinate;
import hr.fer.zemris.nd.document.util.RectangularArea;
import hr.fer.zemris.nd.document.util.enums.AxisOrientation;
import hr.fer.zemris.nd.gui.ImageDisplay;
import hr.fer.zemris.nd.imagelib.Picture;

import java.util.ArrayList;
import java.util.List;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;

import sun.util.BuddhistCalendar;


public class EdgeDigitAnaliser implements IDigitAnaliser {

	private List<BufferedImage> digitImages=null;

	
	int[] buffer=new int[4];
	
	
	public EdgeDigitAnaliser(List<BufferedImage> digits){
		this.digitImages=new ArrayList<BufferedImage>();
		for(BufferedImage newImage:digits){
			//newImage=Picture.doAntialiasing(newImage, 1);
			//newImage=Picture.automaticSigmoidalTransform(newImage, 50);
			digitImages.add(newImage);
			//new ImageDisplay(newImage);
		}
	}
	
	public EdgeDigitAnaliser(BufferedImage numberField, List<RectangularArea> boundingBoxes){
		this.digitImages=new ArrayList<BufferedImage>();
		this.digitImages.clear();
			
		
		for(RectangularArea currentDigitBox:boundingBoxes){
			BufferedImage newImage=numberField.getSubimage(currentDigitBox.getTopLeft().getX(),
															currentDigitBox.getTopLeft().getY(),
															currentDigitBox.getWidth(),
															currentDigitBox.getHeight());
			//newImage=Picture.doAntialiasing(newImage, 1);
			//newImage=Picture.automaticSigmoidalTransform(newImage, 50);
			digitImages.add(newImage);
			
		}		
		
	}
	
	@Override
	public IDigitTransformer getDigitTransformer() {
		
				
		EdgeDetectionDigitTransformer retTransformer= new EdgeDetectionDigitTransformer();
		
		List<Integer> topOrdinates= new ArrayList<Integer>();
		List<Integer> bottomOrdinates= new ArrayList<Integer>();
		List<Integer> topInnerOrdinates= new ArrayList<Integer>();
		List<Integer> bottomInnerOrdinates= new ArrayList<Integer>();
		List<Integer> middleTopOrdinates= new ArrayList<Integer>();
		List<Integer> middleBottomOrdinates= new ArrayList<Integer>();
		
		
		List<DoubleCoordinate> leftBorders = new ArrayList<DoubleCoordinate>();
		List<DoubleCoordinate> rightBorders = new ArrayList<DoubleCoordinate>();
		List<DoubleCoordinate> leftInnerBorders = new ArrayList<DoubleCoordinate>();
		List<DoubleCoordinate> rightInnerBorders = new ArrayList<DoubleCoordinate>();
		
		//GETTING OUTER BOUNDS
		for(int i=0;i<digitImages.size();i++){
		
		
			int topOrdinate=getTopOrdinate(i);
			topOrdinates.add(topOrdinate);
			
			int bottomOrdinate=getBottomOrdinate(i);
			bottomOrdinates.add(bottomOrdinate);
				
		
			DoubleCoordinate leftBorder=getLeftOuterLine(i, topOrdinate, bottomOrdinate);
			leftBorders.add(leftBorder);
			
			DoubleCoordinate rightBorder=getRightOuterLine(i, topOrdinate, bottomOrdinate);
			rightBorders.add(rightBorder);		
		
		}
		
		int topOrdinate = (int)getAvg(topOrdinates);
		int bottomOrdinate=(int)getAvg(bottomOrdinates);
		
		retTransformer.setTopBorderOrdinate(topOrdinate);
		retTransformer.setBottomBorderOrdinate(bottomOrdinate);
		
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
		
		retTransformer.setLeftBorderLine(leftBorder);
		retTransformer.setRightBorderLine(rightBorder);
		
		//GETTING INNER BOUNDS
		
		for(int i=0;i<digitImages.size();i++){
			
			
			int topInnerOrdinate=aproachLineFromBottom(i, topOrdinate+(bottomOrdinate-topOrdinate)/4);
			topInnerOrdinates.add(topInnerOrdinate);
			
			int bottomInnerOrdinate=aproachLineFromTop(i, topOrdinate+3*(bottomOrdinate-topOrdinate)/4);
			bottomInnerOrdinates.add(bottomInnerOrdinate);
			
			int middleTopOrdinate=aproachLineFromTop(i, topOrdinate+(bottomOrdinate-topOrdinate)/4);
			middleTopOrdinates.add(middleTopOrdinate);
			
			int middleBottomOrdinate=aproachLineFromBottom(i, topOrdinate+3*(bottomOrdinate-topOrdinate)/4);
			middleBottomOrdinates.add(middleBottomOrdinate);
				
		
			DoubleCoordinate leftInnerBorder=aproachYXLineFromRight(i,
					new DoubleCoordinate(leftBorder.getX(),(leftBorder.getY()+rightBorder.getY())/2));
			leftInnerBorders.add(leftInnerBorder);
			
			DoubleCoordinate rightInnerBorder=aproachYXLineFromLeft(i,
					new DoubleCoordinate(leftBorder.getX(),(leftBorder.getY()+rightBorder.getY())/2));
			rightInnerBorders.add(rightInnerBorder);		
		
		}
		
		int topInnerOrdinate = (int)getAvg(topInnerOrdinates);
		int bottomInnerOrdinate=(int)getAvg(bottomInnerOrdinates);
		int middleTopOrdinate = (int)getAvg(middleTopOrdinates);
		int middleBottomOrdinate=(int)getAvg(middleBottomOrdinates);
		
		double leftInnerCut=0;
		double rightInnerCut=0;
		
		for(int i=0;i<leftInnerBorders.size();i++){
			leftInnerCut+=leftInnerBorders.get(i).getY();
			rightInnerCut+=rightInnerBorders.get(i).getY();
		}
		leftInnerCut=leftInnerCut/leftInnerBorders.size();
		rightInnerCut=rightInnerCut/leftInnerBorders.size();
		
		DoubleCoordinate leftInnerBorder=new DoubleCoordinate(direction,leftInnerCut);
		DoubleCoordinate rightInnerBorder=new DoubleCoordinate(direction,rightInnerCut);
		
		int middleCenterOrdinate=middleTopOrdinate+(middleBottomOrdinate-middleTopOrdinate)/2-topOrdinate;
		
		int horizontalTicknessSum=middleBottomOrdinate-middleTopOrdinate+
									topInnerOrdinate-topOrdinate+
									bottomOrdinate-bottomInnerOrdinate;
		int horizontalTickness=horizontalTicknessSum/3;
		
		double verticalTicknessSum=leftInnerCut-leftCut+
								rightCut-rightInnerCut;
		
		verticalTicknessSum=verticalTicknessSum/Math.sqrt(1+direction*direction);
		int verticalTickness=(int)verticalTicknessSum/2;
		
		retTransformer.setMiddleSegmentCenter(middleCenterOrdinate);
		retTransformer.setHorizontalSegmentTickness(horizontalTickness);
		retTransformer.setVerticalSegmentTickness(verticalTickness);
		
	
		//only for testing
		/*for(BufferedImage digit:digitImages){
			
			BufferedImage colorImage=new BufferedImage(digit.getWidth(),digit.getHeight(),BufferedImage.TYPE_4BYTE_ABGR);
			colorImage.getGraphics().drawImage(digit, 0, 0, null);						
						
			WritableRaster r=colorImage.getRaster();
			buffer[0]=255;
			buffer[1]=0;
			buffer[2]=0;
			buffer[3]=255;
			for(int i=0;i<r.getWidth();i++){
				r.setPixel(i, topOrdinate, buffer);
				r.setPixel(i, bottomOrdinate, buffer);
				
				r.setPixel(i, bottomInnerOrdinate, buffer);
				r.setPixel(i, topInnerOrdinate, buffer);
				r.setPixel(i, middleBottomOrdinate, buffer);
				r.setPixel(i, middleTopOrdinate, buffer);
			}
		
			for(int i=0; i< r.getHeight();i++){
				double abscissaLeft=leftBorder.getX()*i+leftBorder.getY();
				double abscissaRight=rightBorder.getX()*i+rightBorder.getY();
				double abscissaLeftInner=leftInnerBorder.getX()*i+leftInnerBorder.getY();
				double abscissaRightInner=rightInnerBorder.getX()*i+rightInnerBorder.getY();
							
				
				if(abscissaLeft>0 && abscissaLeft <r.getWidth() ){
					r.setPixel((int)abscissaLeft, i, buffer);
				}
				if(abscissaRight>0 && abscissaRight <r.getWidth() ){
					r.setPixel((int)abscissaRight, i, buffer);
				}
				
				if(abscissaLeftInner>0 && abscissaLeftInner <r.getWidth() ){
					r.setPixel((int)abscissaLeftInner, i, buffer);
				}
				if(abscissaRightInner>0 && abscissaRightInner <r.getWidth() ){
					r.setPixel((int)abscissaRightInner, i, buffer);
				}
			
			}
			ImageDisplay disp=new ImageDisplay(colorImage);
		
		}*/
		
		return retTransformer;
	}
	
	
	private int getTopOrdinate(int digitIndex){
		double treshold=230;
		BufferedImage digit=digitImages.get(digitIndex);
		Raster r=digit.getRaster();
		for(int i=0;i<r.getHeight();i++){
			double avg=middleValueOnOrdinate(digitIndex, i);
			if(avg<treshold){
				return i;
			}
		}
		return -1;		
	}
	
	private int getBottomOrdinate(int digitIndex){
		double treshold=230;
		BufferedImage digit=digitImages.get(digitIndex);
		Raster r=digit.getRaster();
		for(int i=r.getHeight()-1;i>=0;i--){
			double avg=middleValueOnOrdinate(digitIndex, i);
			if(avg<treshold){
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
			int bound=aproachPointFromLeft(digitIndex, i, 2);
			Coordinate point=new Coordinate(bound,i);
			points.add(point);
		}
		
		for(int i=downMiddleOrdinate-ordinateOffset;i<=downMiddleOrdinate+ordinateOffset;i++){
			int bound=aproachPointFromLeft(digitIndex, i, 2);
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
			int bound=aproachPointFromRight(digitIndex, i, 2);
			Coordinate point=new Coordinate(bound,i);
			points.add(point);
		}
		
		for(int i=downMiddleOrdinate-ordinateOffset;i<=downMiddleOrdinate+ordinateOffset;i++){
			int bound=aproachPointFromRight(digitIndex, i, 2);
			Coordinate point=new Coordinate(bound,i);
			points.add(point);
		}
		
		return LinearRegresion.getAproximationLine(points, AxisOrientation.YX);
	}
	
	private int aproachPointFromLeft(int digitIndex, int ordinate, int offset){
		double treshold=200;
		Raster r=digitImages.get(digitIndex).getRaster();
		
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
	
	private int aproachPointFromRight(int digitIndex, int ordinate, int offset){
		double treshold=200;
		Raster r=digitImages.get(digitIndex).getRaster();
		
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
	
	private DoubleCoordinate aproachYXLineFromLeft(int digitIndex, DoubleCoordinate initialLine){
		double diferentialTreshold=15;
		
		double oldAvg=middleValueOnYXLine(digitIndex, initialLine);
		Raster r=digitImages.get(digitIndex).getRaster();
		
		for(double i=initialLine.getY();i<r.getWidth()+100;i=i+1){
			initialLine.setY(i);
			double avg=middleValueOnYXLine(digitIndex, initialLine);
			double diff=oldAvg-avg;
			//System.out.println("From left diff= "+diff);
			if(diff>diferentialTreshold){
				return initialLine;
			}
			oldAvg=avg;
		}		
		return null;
	}
	
	private DoubleCoordinate aproachYXLineFromRight(int digitIndex, DoubleCoordinate initialLine){
		double diferentialTreshold=15;
		
		double oldAvg=middleValueOnYXLine(digitIndex, initialLine);
		
		for(double i=initialLine.getY();i>-100;i=i-0.5){
			initialLine.setY(i);
			double avg=middleValueOnYXLine(digitIndex, initialLine);
			double diff=oldAvg-avg;
			//System.out.println("From right diff= "+diff);
			if(diff>diferentialTreshold){
				return initialLine;
			}
			oldAvg=avg;
		}		
		return null;
	}
	
	private int aproachLineFromTop(int digitIndex, int initialOrdinate){
		double diferentialTreshold=10;
		Raster r=digitImages.get(digitIndex).getRaster();
		
		double oldAvg=middleValueOnOrdinate(digitIndex, initialOrdinate);
		
		for(int j=initialOrdinate+1;j<r.getHeight();j++){
			double avg=middleValueOnOrdinate(digitIndex, j);
			double diff=oldAvg-avg;
			//System.out.println("From top diff= "+diff);
			if(diff>diferentialTreshold){
				return j;
			}
			oldAvg=avg;
		}
		return -1;
	}
	
	private int aproachLineFromBottom(int digitIndex, int initialOrdinate){
		double diferentialTreshold=10;
				
		double oldAvg=middleValueOnOrdinate(digitIndex, initialOrdinate);
		
		for(int j=initialOrdinate-1;j>=0;j--){
			double avg=middleValueOnOrdinate(digitIndex, j);
			double diff=oldAvg-avg;
			//System.out.println("From bottom diff= "+diff);
			if(diff>diferentialTreshold){
				return j;
			}
			oldAvg=avg;
		}
		return -1;
	}
	
	private double middleValueOnYXLine(int digitIndex, DoubleCoordinate line){
		
		int sum=0;
		int count=0;
		Raster r=digitImages.get(digitIndex).getRaster();
		
		for(int j=0;j<r.getHeight();j++){
			int i=(int)(line.getX()*j+line.getY());
			//protect
			if(i<0 || i>=r.getWidth()) continue;
			count++;
			r.getPixel(i, j, buffer);
			sum+=buffer[0];
		}
		
		if(count==0) return 0;
		
		return (double)sum/count;
	}
	
	private double middleValueOnOrdinate(int digitIndex, int ordinate ){
		
		int sum=0;
		Raster r=digitImages.get(digitIndex).getRaster();
		for(int i=0;i<r.getWidth();i++){
			r.getPixel(i, ordinate, buffer);
			sum+=buffer[0];
		}
		
		return (double)sum/r.getWidth();
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
