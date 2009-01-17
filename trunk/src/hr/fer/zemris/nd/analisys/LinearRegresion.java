package hr.fer.zemris.nd.analisys;

import hr.fer.zemris.nd.document.util.Coordinate;
import hr.fer.zemris.nd.document.util.DoubleCoordinate;

import java.util.List;
import hr.fer.zemris.nd.document.util.enums.*;

public class LinearRegresion {
		
	
	public static DoubleCoordinate getAproximationLine(List<Coordinate> points, AxisOrientation orientation){
		//n
		int n=points.size();
		
		//a		
		int a=0;
		for(Coordinate point:points){
			a+=Abscissa(point, orientation);
		}
		
		//b
		int b=0;
		for(Coordinate point:points){
			b+=Abscissa(point, orientation)*Abscissa(point, orientation);
		}
		
		//c
		int c=0;
		for(Coordinate point:points){
			c+=Ordinate(point, orientation);
		}
		
		//d
		int d=0;
		for(Coordinate point:points){
			d+=Abscissa(point, orientation)*Ordinate(point, orientation);
		}
		
		//k
		double k=(double)(d*n-a*c)/(b*n-a*a);
		
		//l
		double l=((double)c-k*a)/n;
		
		return new DoubleCoordinate(k,l);
		
	}
	
	private static int Abscissa(Coordinate c, AxisOrientation orientation){
		switch( orientation ){
			case XY:return c.getX();
			case YX:return c.getY();
		}
		return 0;
	}
	
	private static int Ordinate(Coordinate c, AxisOrientation orientation){
		switch( orientation ){
		case XY:return c.getY();
		case YX:return c.getX();
		}
		return 0;
	}
	

}
