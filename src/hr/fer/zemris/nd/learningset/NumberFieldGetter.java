/**
 * 
 */
package hr.fer.zemris.nd.learningset;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;

import javax.imageio.ImageIO;

import hr.fer.zemris.nd.document.NumberField;
import hr.fer.zemris.nd.document.OcrDocument;
import hr.fer.zemris.nd.document.OcrScheme;
import hr.fer.zemris.nd.document.util.RectangularArea;

/**
 * @author goran
 *
 */
public class NumberFieldGetter {
	
	private OcrScheme scheme;
	private File inFolderLocation;
	private File outFolderLocation;
	
	public NumberFieldGetter(OcrScheme scheme, File inputFolderLocation, 
			File outFolderLocation) {
		this.scheme = scheme;
		this.inFolderLocation = inputFolderLocation;
		this.outFolderLocation = outFolderLocation;
	}
	
	
	public void getNumberFields() {
		File[] imageFiles = inFolderLocation.listFiles();
//		System.out.println(Arrays.toString(imageFiles));
		
		for (int i = 0; i < imageFiles.length; i++) {
			try {
				
				BufferedImage image = ImageIO.read(imageFiles[i]);
				OcrDocument document = new OcrDocument(image, scheme);
				int counter = 0;
				for (int j = 0; j < scheme.getInterestAreasNumber(); j++) {
					counter++;
					System.out.println(
							"Processing numberField "+counter+" of "+imageFiles[i].getName());
					NumberField field = document.getNumberField(j);
					RectangularArea area = scheme.getInterestArea(j);
					String name = scheme.getAreaName(area);
					
					File outFolder = new File(this.outFolderLocation, name);
					if(!outFolder.exists()) {
						System.out.println(outFolder.mkdir());
					}
					
					
					File outFile = new File(outFolder, imageFiles[i].getName());
					outFile = checkExistence(outFile, outFolder);
					System.out.println("Writing numberField to "+outFile);
					ImageIO.write(field.getImage(), "png", outFile);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}


	private File checkExistence(File outFile, File outFolder) {
		if(!outFile.exists()) {
			return outFile;
		} else {
			Random random = new Random();
			int toAppend = random.nextInt(1000);
			outFile = new File(outFolder, 
					outFile.getName().substring(0, outFile.getName().length()-4)
					+Integer.toString(toAppend)+".png");
			return checkExistence(outFile, outFolder);
		}
	}

}
