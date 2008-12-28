package hr.fer.zemris.nd.document.util;

public class RectangularAreaOverlapTest {

	public static void main(String[] args) {
		RectangularArea r1 = new RectangularArea(new Coordinate(10, 10), 
				new Coordinate(20, 20));
		RectangularArea r2 = new RectangularArea(new Coordinate(35, 35), 
				new Coordinate(25, 25));
		System.out.println(r1.overlapsWith(r2));
	}
}
