/**
 * 
 */
package hr.fer.zemris.nd.imagelib;

import hr.fer.zemris.nd.document.util.Coordinate;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;

/**
 * @author goran
 *
 */
public class Picture {
	
	private static int[] buffer= new int[4];
	
	public static int getImagePixelSum(BufferedImage image) {
		int[] pixelValue = new int[4];
		int sum = 0;
		
		for(int i = 0; i < image.getWidth(); i++) {
			for(int j = 0; j < image.getHeight(); j++) {
				sum += image.getRaster().getPixel(i, j, pixelValue) [0];
			}
		}
		return sum;
		
	}
	
	
	public static double getImagePixelAverage(BufferedImage image) {
		return ((double)getImagePixelSum(image))/(image.getHeight()*image.getWidth());
	}
	
	
	public static int getImageMinPixel(BufferedImage image){
		
		Raster r=image.getRaster();		
		int min = 255;
		for(int i = 0; i < image.getWidth(); i++) {
			for(int j = 0; j < image.getHeight(); j++) {
				r.getPixel(i, j, buffer);
				if(buffer[0]<min){
					min=buffer[0];
				}
			}
		}
		return min;
	}
	
	public static int getImageMaxPixel(BufferedImage image){
		
		Raster r=image.getRaster();		
		int max = 0;
		for(int i = 0; i < image.getWidth(); i++) {
			for(int j = 0; j < image.getHeight(); j++) {
				r.getPixel(i, j, buffer);
				if(buffer[0]>max){
					max=buffer[0];
				}
			}
		}
		return max;
	}	
	
	public static BufferedImage sigmoidalTransform(BufferedImage image, int treshold, double slope, Coordinate inputRange, Coordinate outputRange){
		
		WritableRaster wRaster=image.getRaster();
		for(int i=0;i<wRaster.getWidth();i++){
			for(int j=0;j<wRaster.getHeight();j++){
				wRaster.getPixel(i, j, buffer);
				
				if(buffer[0]==inputRange.getY()){
					i=i;
				}
				
				int newPix=sigmoidal(buffer[0], treshold, slope, inputRange, outputRange);
				
				buffer[0]=newPix;
				wRaster.setPixel(i, j, buffer);
			}
		}
		
		return image;
	}
	
	public static BufferedImage automaticSigmoidalTransform(BufferedImage image, double slope){
		
		int min=getImageMinPixel(image);
		int max=getImageMaxPixel(image);
		
		System.out.println("Min: "+min);
		System.out.println("Max: "+max);
		
		Coordinate inputRange=new Coordinate(min,max);
		Coordinate outputRange=new Coordinate(0,255);
		int avg=(int)getImagePixelAverage(image);
		
		System.out.println("Avg: "+avg);
		
		int treshold=avg-3*(max-avg)/4;
		
		System.out.println("Treshold: "+treshold);
		
		return  sigmoidalTransform(image, treshold, slope, inputRange, outputRange);	
		
		
	}
	
	private static int sigmoidal(int value, int treshold, double slope, Coordinate inputRange, Coordinate outputRange){
		
		int inputScale=inputRange.getY()-inputRange.getX();		
		
		int outputOffset=outputRange.getX();
		int outputScale=outputRange.getY()-outputRange.getX();
		
		double minimumOutput=(double)outputOffset+(double)(outputScale)/
								((double)1+Math.exp(slope*-1*(inputRange.getX()-treshold)/inputScale));
				
		double outputOffsetCorrecton=minimumOutput-outputRange.getX();
		
				
		double returnValue=(double)outputOffset+(double)(outputScale)/
								((double)1+Math.exp(slope*-1*(value-treshold)/inputScale));
		
		//correct if slope is to small
		
		if(((int)outputOffsetCorrecton)>0){
			returnValue=returnValue-(double)outputOffset;
			returnValue=returnValue*((double)outputScale/(double)(outputScale-2*outputOffsetCorrecton));
			returnValue=returnValue+(double)outputOffset;
		}
		
		return (int)returnValue;		
	}
	
	public static BufferedImage doAntialiasing(BufferedImage image, int depth){
		return null;
		
		
	}
	
	
	public static BufferedImage removeNoise(BufferedImage image, int distance, double threshold) {
		int[] pixelValue = new int[1];
		int[] pixelWhite = new int[]{255};
		BufferedImage im2 = null; //tu ikoprat trebas image, moja je bila počlje u klasi
		
		for(int i = 0; i < image.getWidth(); i++) {
			for(int j = 0; j < image.getHeight(); j++) {
				if(image.getRaster().getPixel(i, j, pixelValue)[0] == 0) {
					System.out.println(getNeighbourPixelCount(image, distance, i, j));
					if(getNeighbourPixelCount(image, distance, i, j) < 
							threshold * Math.pow(distance, 2)) {
						im2.getRaster().setPixel(i, j, pixelWhite);
					}
				}
				
			}
		}
		
		return null;
		
	}


	public static double getNeighbourPixelCount(BufferedImage image, int distance, int x, int y) {
		int count = 0;
		int[] pixelValue = new int[1];
		for(int i = 0; i < distance; i++) {
			if(image.getRaster().getPixel(x+i, y, pixelValue)[0] == 0) {
				count++;
			}
			if(image.getRaster().getPixel(x-i, y, pixelValue)[0] == 0) {
				count++;
			}
			if(image.getRaster().getPixel(x+i, y, pixelValue)[0] == 0) {
				count++;
			}
			if(image.getRaster().getPixel(x-i, y, pixelValue)[0] == 0) {
				count++;
			}
			if(image.getRaster().getPixel(x, y+i, pixelValue)[0] == 0) {
				count++;
			}
			if(image.getRaster().getPixel(x, y-i, pixelValue)[0] == 0) {
				count++;
			}
		}
		return count;
	}

}
