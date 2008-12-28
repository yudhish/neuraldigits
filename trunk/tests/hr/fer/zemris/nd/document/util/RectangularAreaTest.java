package hr.fer.zemris.nd.document.util;

public class RectangularAreaTest {

	
	public static void main(String[] args) {
		RectangularArea r = new RectangularArea(new Coordinate(10, 10), 
				new Coordinate(20, 20));
		System.out.println(r);
		System.out.println(r.getBottomRight());
		r.setBottomRight(new Coordinate(5, 25));
		System.out.println(r);
	}
}

