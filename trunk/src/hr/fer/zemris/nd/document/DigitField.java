package hr.fer.zemris.nd.document;

import hr.fer.zemris.nd.document.util.RectangularArea;
import hr.fer.zemris.nd.document.util.enums.ESegment;
import hr.fer.zemris.nd.gui.ImageDisplay;
import hr.fer.zemris.nd.imagelib.Picture;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.util.ArrayList;
import java.util.List;


/**
 * 
 * @author tomislav.herman
 *
 */
public class DigitField {
	
	/**
	 * Image of the digit, placed in predefined frame size
	 */
	private BufferedImage digitImage;
	private DigitFieldScheme scheme;
	int[] buffer=new int[4];
	
	
	/**
	 * ctor
	 * Constructs empty image with predefined size
	 * @param width width of the image
	 * @param height height of the image
	 */
	public DigitField(int width, int height){
		digitImage=new BufferedImage(width,height,BufferedImage.TYPE_BYTE_GRAY);		
	}
	
	/**
	 * ctor
	 * Construct new DigitFIeld object with predefined image 
	 * @param image predefined image 
	 */
	public DigitField(BufferedImage image){
		digitImage=image;		
	}
	
	public BufferedImage getDigitImage() {
		return digitImage;
	}

	public void setDigitImage(BufferedImage digitImage) {
		this.digitImage = digitImage;
	}

	public DigitFieldScheme getScheme() {
		return scheme;
	}

	public void setScheme(DigitFieldScheme scheme) {
		this.scheme = scheme;
	}
	
	public void showMe(ImageDisplay display){
		BufferedImage image2=new BufferedImage(scheme.getWidth(),scheme.getHeight(),BufferedImage.TYPE_4BYTE_ABGR);
		Graphics g=image2.getGraphics();
		g.drawImage(digitImage, 0, 0, null);
		
		g.setColor(new Color(255,0,0));
		
		RectangularArea ra=scheme.getSegmentBox(ESegment.BOTTOM);
		g.drawRect(ra.getTopLeft().getX(), ra.getTopLeft().getY(), ra.getWidth(), ra.getHeight());
		
		ra=scheme.getSegmentBox(ESegment.BOTTOM_LEFT);
		g.drawRect(ra.getTopLeft().getX(), ra.getTopLeft().getY(), ra.getWidth(), ra.getHeight());
		
		ra=scheme.getSegmentBox(ESegment.BOTTOM_RIGHT);
		g.drawRect(ra.getTopLeft().getX(), ra.getTopLeft().getY(), ra.getWidth(), ra.getHeight());
		
		ra=scheme.getSegmentBox(ESegment.MIDDLE);
		g.drawRect(ra.getTopLeft().getX(), ra.getTopLeft().getY(), ra.getWidth(), ra.getHeight());
		
		ra=scheme.getSegmentBox(ESegment.UPPER);
		g.drawRect(ra.getTopLeft().getX(), ra.getTopLeft().getY(), ra.getWidth(), ra.getHeight());
		
		ra=scheme.getSegmentBox(ESegment.UPPER_LEFT);
		g.drawRect(ra.getTopLeft().getX(), ra.getTopLeft().getY(), ra.getWidth(), ra.getHeight());
		
		ra=scheme.getSegmentBox(ESegment.UPPER_RIGHT);
		g.drawRect(ra.getTopLeft().getX(), ra.getTopLeft().getY(), ra.getWidth(), ra.getHeight());
		
		display.setImage(image2);
	}
	
	public BufferedImage getScaledImage(int width, int height){
		
				
		BufferedImage filteredImage=new BufferedImage(digitImage.getWidth(),digitImage.getHeight(),BufferedImage.TYPE_BYTE_GRAY);
		filteredImage.getGraphics().drawImage(digitImage, 0, 0, null);
		
		for(int i=0;i<10;i++){
			filteredImage=Picture.doAntialiasing(filteredImage, 3);
		}
		filteredImage=Picture.automaticSigmoidalTransform(filteredImage, 50);
		
		BufferedImage image=new BufferedImage(width,height,BufferedImage.TYPE_BYTE_BINARY);
		Graphics2D bg = image.createGraphics();
		bg.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
		         RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		bg.scale((double)width/(double)scheme.getWidth(), (double)height/(double)scheme.getHeight());
		bg.drawImage(filteredImage, 0, 0, null);
		bg.dispose();
		
		BufferedImage bwImage=new BufferedImage(width,height,BufferedImage.TYPE_BYTE_BINARY);
		bwImage.getGraphics().drawImage(image, 0, 0, null);
		
		return image;		
		
	}
	
	public List<Double> getSegmentSaturatins(){
		
		List<Double> values=new ArrayList<Double>();
		
		ESegment[] segments = ESegment.values();
		
		double min=1;
		double max=0;
		double avg=0;
		for(ESegment segment:segments){		
			RectangularArea rect=scheme.getSegmentBox(segment);
		
			BufferedImage segmentImage=digitImage.getSubimage(rect.getTopLeft().getX(),
														rect.getTopLeft().getY(),
														rect.getWidth(),
														rect.getHeight());
			
			double sum=0;
			double value=0;
			Raster r=segmentImage.getRaster();
			for(int i=0;i<r.getWidth();i++){
				for (int j=0;j<r.getHeight();j++){
					r.getPixel(i, j, buffer);
					sum+=(double)buffer[0]/255;
				}
			}
			
			value=sum/(double)(r.getWidth()*r.getHeight());
			
			if(value<min){
				min=value;			
			}
			
			if( value>max){
				max=value;
			}
			
			avg=avg+value;
			
			values.add(value);
		}
		
		avg=avg/7;
		
		double dev=(max-avg);
		if((avg-min)>dev){
			dev=avg-min;
		}
		
		if(max-min<0.4){
			max=1;
		}
		
		List<Double> scaledValues=new ArrayList<Double>();
		
		for(Double value:values){
			scaledValues.add((value-min)/(max-min));
		}
		
		//System.out.println("Max: "+max);
		//System.out.println("Min: "+ min);
		//System.out.println("Avg: "+ avg);
		//System.out.println("MaxDev: " +dev );
		
		return scaledValues;		
	}
	
	
	

}
