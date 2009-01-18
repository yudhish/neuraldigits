package hr.fer.zemris.nd.document.util;

import hr.fer.zemris.nd.analisys.*;
import hr.fer.zemris.nd.document.DigitField;
import hr.fer.zemris.nd.gui.ImageDisplay;
import hr.fer.zemris.nd.imagelib.Picture;

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
		
		String name1="duh.png";
		BufferedImage imageT=null;
		
		try {
			imageT = ImageIO.read(new File(name1));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		BufferedImage imageTT=new BufferedImage(imageT.getWidth(),imageT.getHeight(),BufferedImage.TYPE_BYTE_GRAY);
		imageTT.getGraphics().drawImage(imageT, 0, 0, null);
		
		ImageDisplay.displayImage(imageT);
		
		imageTT=Picture.automaticSigmoidalTransform(imageTT, 100);
		ImageDisplay.displayImage(imageTT);
		
		
		
		
		
		if(true) return;
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
				
		
		IDigitAnaliser analis=new EdgeDigitAnaliser(digits);
		
		IDigitTransformer transformer= analis.getDigitTransformer();
		
		try {
			image = ImageIO.read(new File(name+"0"+".png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		BufferedImage image2=new BufferedImage(image.getWidth(),image.getHeight(),BufferedImage.TYPE_BYTE_GRAY);
		image2.getGraphics().drawImage(image, 0, 0, null);
		
		
		DigitField df=transformer.transformImageToDigit(image2);
		df.showMe();
		
		BufferedImage scaled =df.getScaledImage(40, 80);
		
		try {
			ImageIO.write(scaled, "bmp", new File("d:\\neural\\test3.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		new ImageDisplay(scaled);
		
		List<Double> values=df.getSegmentSaturatins();
		
		System.out.println(values);
		
		
		
		

	}

}
