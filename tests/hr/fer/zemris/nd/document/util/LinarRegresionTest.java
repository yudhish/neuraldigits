package hr.fer.zemris.nd.document.util;

import java.util.List;
import java.util.ArrayList;
import hr.fer.zemris.nd.analisys.*;
import hr.fer.zemris.nd.document.util.enums.*;

public class LinarRegresionTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<Coordinate> points=new ArrayList<Coordinate>();
		
		points.add(new Coordinate(0,0));
		points.add(new Coordinate(2,4));
		points.add(new Coordinate(4,8));
		points.add(new Coordinate(5,15));
		points.add(new Coordinate(6,12));
		points.add(new Coordinate(7,14));
		
		DoubleCoordinate line=LinearRegresion.getAproximationLine(points, AxisOrientation.YX);
		
		int i=0;
		i++;
		

	}

}
