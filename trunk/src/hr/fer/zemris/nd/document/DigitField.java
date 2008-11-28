package hr.fer.zemris.nd.document;

import java.awt.image.BufferedImage;


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
	
	
	/**
	 * ctor
	 * Constructs empty image with predefined size
	 * @param width width of the image
	 * @param height height of the image
	 */
	public DigitField(int width, int height){
		digitImage=new BufferedImage(10,10,BufferedImage.TYPE_BYTE_GRAY);		
	}
	
	/**
	 * ctor
	 * Construct new DigitFIeld object with predefined image 
	 * @param image predefined image 
	 */
	public DigitField(BufferedImage image){
		digitImage=image;
	}
	
	/**
	 * Create a object for every of the 7 segments placed in the digit
	 */
	public void createSegments(){
		
	}	

}
