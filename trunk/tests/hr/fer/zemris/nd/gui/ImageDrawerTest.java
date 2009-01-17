/**
 * 
 */
package hr.fer.zemris.nd.gui;

import hr.fer.zemris.nd.document.NumberField;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

/**
 * @author goran
 *
 */
public class ImageDrawerTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		BufferedImage image1 = null;
		BufferedImage image2 = null;
		System.out.println("Loading image.");
		try {
			image1 = ImageIO.read(new File(
					"/media/data/Documents/fer/Peta godina/Deveti semestar/" +
					"Neuronske mreze/Neuronske mreze uzorci rani/scan01.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Image loaded. ");
		
		ImageDisplay drawer = new ImageDisplay(image1);
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		try {
			image2 = ImageIO.read(new File(
					"/media/data/Documents/fer/Peta godina/Deveti semestar/"+
					"Neuronske mreze/Neuronske mreze uzorci rani/02bw.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			reader.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		drawer.setImage(image2);
		
		try {
			reader.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		drawer.setImage(image1);
		
		try {
			reader.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(int i = 0; i < image1.getWidth(); i++) {
			for(int j = 20; j < 30; j++) {
				image1.getRaster().setPixel(i, j, new int[]{0});
			}
			
		}
		
		
		
		drawer.repaint();
		
		BufferedImage displayImage = new BufferedImage(
				image1.getWidth(), image1.getHeight(), 12);
		displayImage.getGraphics().drawImage(image1, 0, 0, null);
		
		for(int i = 0; i < image1.getWidth(); i++) {
			for(int j = 20; j < 30; j++) {
				displayImage.getRaster().setPixel(i, j, new int[]{0, 255, 0});
			}
			
		}
		
		drawer.setImage(displayImage);
	}
	

}
