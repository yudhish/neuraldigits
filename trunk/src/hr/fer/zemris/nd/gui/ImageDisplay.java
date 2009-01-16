/**
 * 
 */
package hr.fer.zemris.nd.gui;


import java.awt.Dimension;
import java.awt.Point;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

/**
 * @author goran
 *
 */
public class ImageDisplay {
	private BufferedImage image;
	private BufferedImageDrawer imageDrawer;
	private JFrame frame;
	
	public ImageDisplay(BufferedImage image) {
		this.image = image;
		this.imageDrawer = new BufferedImageDrawer(image);
		this.frame = new JFrame();
		frame.add(imageDrawer);
		frame.setSize(new Dimension(image.getWidth(), image.getHeight()+40));
		frame.setVisible(true);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
	}
	
	
	public void setImage(BufferedImage image) {
		this.image = image;
		imageDrawer.setImage(image);
		this.frame.repaint();
		this.frame.setSize(image.getWidth(), image.getHeight()+20);
	}
	
	
	public void repaint() {
		this.frame.repaint();
	}
	
	
	
	public static void displayImage(BufferedImage image) {
		JFrame frame = new JFrame();
		frame.add(new BufferedImageDrawer(image));
		frame.setSize(new Dimension(image.getWidth(), image.getHeight()+40));
		frame.setVisible(true);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	
	}
		
	
	
	
	public static void displayImage(BufferedImage image, Point location) {
		JFrame frame = new JFrame();
		frame.setLocation(location);
		frame.add(new BufferedImageDrawer(image));
		frame.setSize(new Dimension(image.getWidth(), image.getHeight()+40));
		frame.setVisible(true);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
	}
	
}
