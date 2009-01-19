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
import hr.fer.zemris.nd.document.ENumberFieldDisplayMode;
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
	
	private File inFolderLocation;
	private File outFolderLocation;
	private List<File> directoriesToProcess;
	private NumberFieldScheme currentScheme;
	
	public DigitFieldGetter(File inputFolderLocation, 
			File outFolderLocation) {
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
			processFields(folder);
		}
	}


	private void processFields(File folder) {
		try {
			System.out.println("Processing folder: "+folder.getCanonicalPath());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		File[] files = folder.listFiles();
		File outFolder = checkFolderExistence(new File(this.outFolderLocation, 
				folder.getName()), this.outFolderLocation);
		if(!outFolder.exists()) {
			outFolder.mkdir();
		}
		
		File firstFile = findFirstFile(files);
		if(firstFile == null) {
			System.err.println("The folder "+folder+" contains no files. ");
			return;
		}
		
		segmentFirstFile(firstFile, outFolder);
		createFolderStructure(outFolder);
		segmentFiles(files, outFolder);
	}


	private void segmentFiles(File[] files, File outputFolder) {
		for (int i = 0; i < files.length; i++) {
			segmentFile(files[i], outputFolder);
		}
		
	}


	private void segmentFile(File file, File outFolder) {
		try {
			BufferedImage fieldImage = ImageIO.read(file);
			if(fieldImage == null) {
				System.err.println("Unable to read file: "+file);
				return;
			}
			
			NumberField field = new NumberField(fieldImage, this.currentScheme);
			field.setDisplayMode(ENumberFieldDisplayMode.NONE);
			String folderName = outFolder.getName();
			if(folderName.length()<10){
				folderName += "aaaaaaaaaa";
			}
			
			int iterator = 0;
			for(DigitField digit: field.getDigitFields()) {
				File digitOutFolder = new File(outFolder, 
						String.valueOf(folderName.toCharArray()[iterator]));
				File outFile = checkExistence(new File(
						digitOutFolder, folderName.toCharArray()[iterator]+".png"), digitOutFolder);
				System.out.println("Writing segments of "+file.getCanonicalPath()+" to "+
						outFile);
				ImageIO.write(digit.getDigitImage(), "png", outFile);
				if(iterator < 9) {
					iterator++;
				}
			}
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


	private void createFolderStructure(File outFolder) {
		for(int i = 0; i < 10; i++) {
			File folder = new File(outFolder, String.valueOf(i));
			if(folder.exists()) {
				System.err.println("The destination folder allready exists!");
				System.exit(44);
			}
			folder.mkdir();
		}
		File folder = new File(outFolder, "a");
		folder.mkdir();
		folder = new File(outFolder, "PRAZNI");
		folder.mkdir();
		
	}


	private void segmentFirstFile(File firstFile, File outFolder) {
		try {
			BufferedImage fieldImage = ImageIO.read(firstFile);
			NumberField field = new NumberField(fieldImage);
			this.currentScheme = field.getScheme();
			field.setDisplayMode(ENumberFieldDisplayMode.NONE);
			String folderName = outFolder.getName();
			if(folderName.length()<10){
				folderName += "aaaaaaaaaa";
			}
			int iterator = 0;
			for(DigitField digit: field.getDigitFields()) {
				
				File outFile = checkExistence(new File(
						outFolder, folderName.toCharArray()[iterator]+".png"), outFolder);
				System.out.println("Writing segments of "+firstFile.getCanonicalPath()+" to "+
						outFile);
				ImageIO.write(digit.getDigitImage(), "png", outFile);
				if(iterator < 9) {
					iterator++;
				}
			}
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


	private File findFirstFile(File[] files) {
		for (int i = 0; i < files.length; i++) {
			if(files[i].isFile()) {
				return files[i];
			}
		}
		return null;
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
	
	
	private File checkFolderExistence(File outFile, File outFolder) {
		if(!outFile.exists()) {
			return outFile;
		} else {
			Random random = new Random();
			int toAppend = random.nextInt(1000);
			outFile = new File(outFolder, 
					outFile.getName().substring(0, outFile.getName().length()-4)
					+Integer.toString(toAppend));
			return checkExistence(outFile, outFolder);
		}
	}

}
