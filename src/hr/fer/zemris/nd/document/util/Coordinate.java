/**
 * 
 */
package hr.fer.zemris.nd.document.util;

/**
 * @author goran
 *
 */
public class Coordinate {
	private int x;
	private int y;
	
	
	/**
	 * Creates a new <code>Coordinate</code>, based on the x and y coordinates. 
	 * 
	 * @param x x coordinate
	 * @param y y coordinate
	 */
	public Coordinate(int x, int y) {
		if(x < 0) {
			throw new IllegalArgumentException("The x coordinate needs to be " +
					"an integer, greater than or equal to zero. ");
		}
		if(x < 0) {
			throw new IllegalArgumentException("The x coordinate needs to be " +
					"an integer, greater than or equal to zero. ");
		}
		
		this.x = x;
		this.y = y;
		
	}


	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}


	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Coordinate))
			return false;
		Coordinate other = (Coordinate) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}
	
	
	

}
