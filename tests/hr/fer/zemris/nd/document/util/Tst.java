/**
 * 
 */
package hr.fer.zemris.nd.document.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * @author th41642
 *
 */
public class Tst {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			BufferedImage im = ImageIO.read(new File("C:\\Documents and Settings\\th41642\\Desktop\\0.bmp"));
			System.out.println(im.getType());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

	}

}
