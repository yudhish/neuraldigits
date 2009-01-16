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
	int[] buffer=new int[3];
	
	
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
		
		List<Integer> leftOuterBorders=new ArrayList<Integer>();
		List<Integer> rightOuterBorders=new ArrayList<Integer>();
		List<Integer> topOuterBorders=new ArrayList<Integer>();
		List<Integer> bottomOuterBorders=new ArrayList<Integer>();
		List<Integer> leftInnerBorders=new ArrayList<Integer>();
		List<Integer> rightInnerBorders=new ArrayList<Integer>();
		List<Integer> topInnerBorders=new ArrayList<Integer>();
		List<Integer> bottomInnerBorders=new ArrayList<Integer>();
 		List<Integer> middleUpBorders=new ArrayList<Integer>();
 		List<Integer> middleDownBorders=new ArrayList<Integer>();
		
		
		List<Integer> tmpValues=new ArrayList<Integer>();
		tmpValues.clear();
		
		
		
		for(int i=0;i<digits.size();i++){
			
			//getting left border
			for(int height=0;height<digits.get(i).getHeight();height+=height/50){
				Integer tmpValue=getLeftOuterBound(i, height);
				if (tmpValue!=-1){
					tmpValues.add(tmpValue);
				}
				
			}
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
		for(int i=0;i<r.getWidth();i++){
			r.getPixel(i,digitOrdinate, buffer);
			int value=buffer[0];
			value=filter(value);
			if(treshold(value)){
				return i;
			}
		}
		return -1;
	}
	
	private int getRightOuterBound(int digitIndex, int digitOrdinate){
		Raster r=digits.get(digitIndex).getRaster();
		for(int i=r.getWidth()-1;i>=0;i--){
			r.getPixel(i,digitOrdinate, buffer);
			int value=buffer[0];
			value=filter(value);
			if(treshold(value)){
				return i;
			}
		}
		return -1;
	}
	
	private int getTopOuterBound(int digitIndex, int digitAbscissa){
		Raster r=digits.get(digitIndex).getRaster();
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
		Raster r=digits.get(digitIndex).getRaster();
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
		Raster r=digits.get(digitIndex).getRaster();
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
		Raster r=digits.get(digitIndex).getRaster();
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
		Raster r=digits.get(digitIndex).getRaster();
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
		Raster r=digits.get(digitIndex).getRaster();
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
