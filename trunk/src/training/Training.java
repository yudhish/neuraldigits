package training;

import hr.fer.zemris.nd.analisys.EdgeDigitAnaliser;
import hr.fer.zemris.nd.analisys.IDigitAnaliser;
import hr.fer.zemris.nd.analisys.IDigitTransformer;
import hr.fer.zemris.nd.document.DigitField;
import hr.fer.zemris.nd.gui.ImageDisplay;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class Training {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		String rootTrainingFolder="D:/neural/Samples";
		
		String matlabExternalFolder="D:/neural/matlab";
		
		INetTrainer trainer =new ExternalMatlabTrainer(matlabExternalFolder);
		
		ImageDisplay disp=new ImageDisplay(new BufferedImage(10,10,BufferedImage.TYPE_BYTE_GRAY));
		
		
		
		File root=new File(rootTrainingFolder);
		if(!root.isDirectory()){
			System.err.println("Root must be directory");
		}
		
		File[] fields=root.listFiles();
		
		//for every number field
		for(File field:fields){
			
			System.out.println("Entering: "+field.getName());
			
			if(!field.isDirectory()){
				continue;
			}
			List<File> analisisExamples=new ArrayList<File>();
			List<File> digitNumberFolders=new ArrayList<File>();
			List<BufferedImage> analisisImages=new ArrayList<BufferedImage>();
			
			//list content
			File[] content=field.listFiles();
			
			//separate analisis examples from learning folders
			for(File f:content){
				if(f.isDirectory()){
					digitNumberFolders.add(f);					
				}
				else{
					analisisExamples.add(f);
				}
			}
			
			//get analisis digit images
			for(File digitFile:analisisExamples){
				try {
					BufferedImage im=ImageIO.read(digitFile);
					if(im!=null){
						BufferedImage grayImage=new BufferedImage(im.getWidth(),im.getHeight(),BufferedImage.TYPE_BYTE_GRAY);
						grayImage.getGraphics().drawImage(im, 0, 0, null);						
						analisisImages.add(grayImage);
					}					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			//perform digit analisis
			IDigitAnaliser analiser=new EdgeDigitAnaliser(analisisImages);			
			IDigitTransformer digitTransformer=analiser.getDigitTransformer();
			
			//use transformer for every instance of digits from 0 to 9
			
			for(File digitLearnFolder:digitNumberFolders){
				
				String name=digitLearnFolder.getName();
							
				int digitValue=0;
				
				try{
					digitValue=Integer.parseInt(name);
				}
				catch (NumberFormatException e){
					continue;
				}
				
				File[] digitImages=digitLearnFolder.listFiles();
				
				//for every instance: create digit field and get data from it
				
				
				for(File digitImage:digitImages){
					BufferedImage digit=null;
					try {
						digit=ImageIO.read(digitImage);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					if(digit==null){
						continue;
					}
					
					BufferedImage grayImage=new BufferedImage(digit.getWidth(),digit.getHeight(),BufferedImage.TYPE_BYTE_GRAY);
					grayImage.getGraphics().drawImage(digit, 0, 0, null);						
										
					DigitField dField=digitTransformer.transformImageToDigit(grayImage);
					dField.showMe(disp);
					trainer.addSegmentSaturationExample(dField.getSegmentSaturatins(), digitValue);
					trainer.addImageExample(dField, digitValue);
					
				}
			}			
			
		}
		
		trainer.startTrainingSaturation();
		trainer.startTrainingScaled(10, 20);

	}

}
