/**
 * 
 */
package hr.fer.zemris.nd.document.util;

/**
 * @author goran
 *
 */
public class DoubleCoordinate {
	private double x;
	private double y;
	
	
	/**
	 * Creates a new <code>Coordinate</code>, based on the x and y coordinates. 
	 * 
	 * @param x x coordinate
	 * @param y y coordinate
	 */
	public DoubleCoordinate(int x, int y) {
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
	 * Creates a new <code>Coordinate</code>, based on the x and y coordinates. 
	 * 
	 * @param x x coordinate
	 * @param y y coordinate
	 */
	public DoubleCoordinate(double x, double y) {
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
	
	
	public DoubleCoordinate (DoubleCoordinate coordinate) {
		this.x = coordinate.x;
		this.y = coordinate.y;
	}


	/**
	 * @param x the x to set
	 */
	public void setX(double x) {
		this.x = x;
	}
	
	

	/**
	 * @param y the y to set
	 */
	public void setY(double y) {
		this.y = y;
	}
	
	
	/**
	 * Sets <code>x</code> and <code>y</code> coordinates to the ones defined 
	 * in <code>coordinate</code>. 
	 * 
	 * @param coordinate
	 */
	public void set(DoubleCoordinate coordinate) {
		this.x = coordinate.getX();
		this.y = coordinate.getY();
	}

	
	/**
	 * @return the x
	 */
	public double getX() {
		return x;
	}


	/**
	 * @return the y
	 */
	public double getY() {
		return y;
	}

	


	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(x);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		if (!(obj instanceof DoubleCoordinate))
			return false;
		DoubleCoordinate other = (DoubleCoordinate) obj;
		if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
			return false;
		if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y))
			return false;
		return true;
	}
	
	
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("(");
		buffer.append(x);
		buffer.append(", ");
		buffer.append(y);
		buffer.append(")");
		return buffer.toString();
	}

}
