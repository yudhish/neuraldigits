package hr.fer.zemris.nd.document.util;

import hr.fer.zemris.nd.analisys.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;



public class EdgeDigitAnaliserTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String name="prazna";
		
		
		
		List<BufferedImage> digits=new ArrayList<BufferedImage>();
		BufferedImage image=null;
		
		for(int i=0;i<8;i++){
		///////////////////////////////////////////////////////
			try {
				image = ImageIO.read(new File(name+i+".png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			BufferedImage image2=new BufferedImage(image.getWidth(),image.getHeight(),BufferedImage.TYPE_BYTE_GRAY);
			image2.getGraphics().drawImage(image, 0, 0, null);
		
			digits.add(image2);
		}
				
		
		EdgeDigitAnaliser analis=new EdgeDigitAnaliser(digits);
		
		analis.align();
		
		
		
		

	}

}
