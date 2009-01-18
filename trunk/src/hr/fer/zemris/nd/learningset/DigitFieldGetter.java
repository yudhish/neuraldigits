/**
 * 
 */
package hr.fer.zemris.nd.learningset;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

import hr.fer.zemris.nd.document.DigitField;
import hr.fer.zemris.nd.document.NumberField;
import hr.fer.zemris.nd.document.NumberFieldScheme;
import hr.fer.zemris.nd.document.OcrDocument;
import hr.fer.zemris.nd.document.OcrScheme;
import hr.fer.zemris.nd.document.util.RectangularArea;

/**
 * @author goran
 *
 */
public class DigitFieldGetter {
	
	private NumberFieldScheme scheme;
	private File inFolderLocation;
	private File outFolderLocation;
	private List<File> directoriesToProcess;
	
	public DigitFieldGetter(NumberFieldScheme scheme, File inputFolderLocation, 
			File outFolderLocation) {
		this.scheme = scheme;
		this.inFolderLocation = inputFolderLocation;
		this.outFolderLocation = outFolderLocation;
		this.directoriesToProcess = new ArrayList<File>();
	}
	
	
	public void getDigitFields() {
		addDirectoriesToProcess(this.inFolderLocation);
		System.out.println("Processing the following direstories: "+this.directoriesToProcess);
		for (File folder: directoriesToProcess) {
			try {
				System.out.println("Processing: "+folder.getCanonicalPath());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//			processFields(folder);
		}
	}


	private void processFields(File folder) {
		File[] files = folder.listFiles();
		File outFolder = checkExistence(new File(this.outFolderLocation, 
				folder.getName()), this.outFolderLocation);
		
		for (int i = 0; i < files.length; i++) {
			if(files[i].isFile()) {
				BufferedImage image;
				System.out.println("Saving digit to "+outFolder);
//				try {
//					image = ImageIO.read(files[i]);
//					NumberField field = new NumberField(image);
//					for (DigitField digitField: field.getDigitFields()) {
//						ImageIO.write(digitField.getDigitImage(), "png", outFolder);
//					}
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
				
			}
		}
		
	}


	private void addDirectoriesToProcess(File folder) {
		File[] files = folder.listFiles();
		for (int i = 0; i < files.length; i++) {
			if(files[i].isDirectory()) {
				this.directoriesToProcess.add(files[i]);
				addDirectoriesToProcess(files[i]);
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
