/**
 * 
 */
package hr.fer.zemris.nd.gui;


import java.awt.Dimension;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

/**
 * @author goran
 *
 */
public class ImageDisplay {

	
	
	public static void displayImage(BufferedImage image) {
		JFrame frame = new JFrame();
		frame.add(new BufferedImageDrawer(image));
		frame.setSize(new Dimension(image.getWidth(), image.getHeight()+40));
		frame.setVisible(true);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	}
}
