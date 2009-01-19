/**
 * 
 */
package hr.fer.zemris.nd.learningset;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @author goran
 *
 */
public class MatlabImageLoad {

	public static void generateImageLoadFile(File file, List<Integer> imageNumber) {
		try {
			BufferedWriter writer = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
			for(int i = 0; i < 10; i++) {
				for(int j = 0; j <= imageNumber.get(0); j++) {
					write(i, j, writer);
				}
			}
			
			writer.close();
			

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void write(int folderIndex, int fileIndex, BufferedWriter writer) throws IOException {
		writer.write("p=imresize(imread('"
				+String.valueOf(folderIndex)+File.separator+
				String.valueOf(fileIndex)+".bmp'),1);\n");
		writer.write("P=[P p(:)];\n");
		writer.write(getOutput(folderIndex));
		writer.write("T=[T t];\n");
		
	}

	
	private static String getOutput(int folderIndex) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("t=[");
		for(int i = 0; i < 10; i++) {
			if(i == folderIndex) {
				buffer.append("1 ");
			} else {
				buffer.append("0 ");
			}
		}
		buffer.append("]';\n");
		return buffer.toString();
	}
}
