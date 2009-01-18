package hr.fer.zemris.nd.document;

import hr.fer.zemris.nd.document.util.RectangularArea;
import hr.fer.zemris.nd.document.util.enums.ESegment;
import hr.fer.zemris.nd.gui.ImageDisplay;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
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
	
	public void showMe(){
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
		
		ImageDisplay disp=new ImageDisplay(image2);
	}
	
	public BufferedImage getScaledImage(int width, int height){
		
		Image original=digitImage;
		
		BufferedImage image=new BufferedImage(width,height,BufferedImage.TYPE_BYTE_GRAY);
		Graphics2D bg = image.createGraphics();
		bg.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
		         RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		bg.scale((double)width/(double)scheme.getWidth(), (double)height/(double)scheme.getHeight());
		
		bg.drawImage(original, 0, 0, null);
		bg.dispose();
		
		return image;
		   
		
		
	}
	
	
	

}
