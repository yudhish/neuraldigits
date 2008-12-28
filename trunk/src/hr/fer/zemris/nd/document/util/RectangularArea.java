/**
 * 
 */
package hr.fer.zemris.nd.document.util;

/**
 * The <code>RectangularArea</code> in a Cartesian coordinate system. 
 * By convention, the starting point of the screen coordinate system is the
 * top left corner.  
 * 
 * @author goran
 *
 */
public class RectangularArea {
	private Coordinate topLeft;
	private Coordinate bottomLeft;
	private Coordinate topRight;
	private Coordinate bottomRight;
	
	
	public RectangularArea(Coordinate topLeft, Coordinate bottomRight) {
		this.topLeft = topLeft;
		this.bottomRight = bottomRight;
		this.topRight = new Coordinate(bottomRight.getX(), topLeft.getY());
		this.bottomLeft = new Coordinate(topLeft.getX(), bottomRight.getY());
		
	}
	
	
	public int getWidth() {
		return (this.topRight.getX() - this.topLeft.getX());
	}
	
	
	public int getHeight() {
		return (this.topRight.getY() - this.bottomRight.getX());
	}


	/**
	 * @return the topLeft
	 */
	public Coordinate getTopLeft() {
		return new Coordinate(topLeft);
	}


	/**
	 * @param topLeft the topLeft to set
	 */
	public void setTopLeft(Coordinate topLeft) {
		if(topLeft.getX() == this.topRight.getX()) {
			throw new IllegalArgumentException("The left X coordinate must " +
					"be different from the right one. Otherwise, the area " +
					"degenerates " +
					"to a line. ");
		}
		if(topLeft.getY() == this.topRight.getY()) {
			throw new IllegalArgumentException("The upper Y coordinate must " +
					"be different from the lower one. Otherwise, the area " +
					"degenerates " +
					"to a line. ");
		}
		
		this.topLeft.set(topLeft);
		this.topRight.setY(this.topLeft.getY());
		this.bottomLeft.setX(topLeft.getX());
		adjustCoordinates();
		
	}
	
	
	public void setTopLeft(int x, int y) {
		if(x == this.topRight.getX()) {
			throw new IllegalArgumentException("The left X coordinate must " +
					"be different from the right one. Otherwise, the area " +
					"degenerates " +
					"to a line. ");
		}
		if(y == this.topRight.getY()) {
			throw new IllegalArgumentException("The upper Y coordinate must " +
					"be different from the lower one. Otherwise, the area " +
					"degenerates " +
					"to a line. ");
		}
		
		this.topLeft.setX(x);
		this.topLeft.setY(y);
		this.topRight.setY(y);
		this.bottomLeft.setX(x);
		adjustCoordinates();
	}


	private void adjustCoordinates() {
		if(this.topLeft.getX() > this.topRight.getX()) {
			switchX();
		}
		if(this.topLeft.getY() > this.bottomLeft.getY()) {
			switchY();
		}
		
	}


	private void switchY() {
		System.out.println("switch y");
		Coordinate topLeftOld = this.topLeft;
		this.topLeft = this.bottomLeft;
		this.bottomLeft = topLeftOld;
		
		Coordinate topRightOld = this.topRight;
		this.topRight = this.bottomRight;
		this.bottomRight = topRightOld;
		
	}


	private void switchX() {
		System.out.println("switch x");
		Coordinate topLeftOld = this.topLeft;
		this.topLeft = this.topRight;
		this.topRight = topLeftOld;
		
		Coordinate bottomLeftOld = this.bottomLeft;
		this.bottomLeft = this.bottomRight;
		this.bottomRight = bottomLeftOld;
		
	}


	/**
	 * @return the bottomLeft
	 */
	public Coordinate getBottomLeft() {
		return new Coordinate(bottomLeft);
	}


	/**
	 * @param bottomLeft the bottomLeft to set
	 */
	public void setBottomLeft(Coordinate bottomLeft) {
		if(bottomLeft.getX() == this.bottomRight.getX()) {
			throw new IllegalArgumentException("The left X coordinate must " +
					"be different from the right one. Otherwise, the area " +
					"degenerates " +
					"to a line. ");
		}
		if(bottomLeft.getY() == this.topLeft.getY()) {
			throw new IllegalArgumentException("The upper Y coordinate must " +
					"be different from the lower one. Otherwise, the area " +
					"degenerates " +
					"to a line. ");
		}
		
		this.bottomLeft.set(bottomLeft);
		this.topLeft.setX(this.bottomLeft.getX());
		this.bottomRight.setY(bottomLeft.getY());
		adjustCoordinates();
	}


	/**
	 * @return the topRight
	 */
	public Coordinate getTopRight() {
		return new Coordinate(topRight);
	}


	/**
	 * @param topRight the topRight to set
	 */
	public void setTopRight(Coordinate topRight) {
		if(topLeft.getX() == topRight.getX()) {
			throw new IllegalArgumentException("The left X coordinate must " +
					"be different from the right one. Otherwise, the area " +
					"degenerates " +
					"to a line. ");
		}
		if(bottomRight.getY() == topRight.getY()) {
			throw new IllegalArgumentException("The upper Y coordinate must " +
					"be different from the lower one. Otherwise, the area " +
					"degenerates " +
					"to a line. ");
		}
		
		this.topRight.set(topRight);
		this.topLeft.setY(this.topRight.getY());
		this.bottomRight.setX(topRight.getX());
		adjustCoordinates();
	}


	/**
	 * @return the bottomRight
	 */
	public Coordinate getBottomRight() {
		return new Coordinate(bottomRight);
	}


	/**
	 * @param bottomRight the bottomRight to set
	 */
	public void setBottomRight(Coordinate bottomRight) {
		if(bottomRight.getX() == bottomLeft.getX()) {
			throw new IllegalArgumentException("The left X coordinate must " +
					"be different from the right one. Otherwise, the area " +
					"degenerates " +
					"to a line. ");
		}
		if(bottomRight.getY() == topRight.getY()) {
			throw new IllegalArgumentException("The upper Y coordinate must " +
					"be different from the lower one. Otherwise, the area " +
					"degenerates " +
					"to a line. ");
		}
		
		this.bottomRight.set(bottomRight);
		this.topRight.setX(this.bottomRight.getX());
		this.bottomLeft.setY(bottomRight.getY());
		adjustCoordinates();
	}
	
	
	public boolean overlapsWith(RectangularArea area) {
		// y axis in first, x axis in second condition check
		if (isBoundedBy(area.bottomLeft.getX(), this.bottomLeft.getX(),
				this.bottomRight.getX())
				&& isBoundedBy(area.bottomLeft.getY(), this.topLeft.getY(),
						this.bottomLeft.getY())) {
			return true;
		}
		if (isBoundedBy(area.topLeft.getX(), this.bottomLeft.getX(),
				this.bottomRight.getX())
				&& isBoundedBy(area.topLeft.getY(), this.topLeft.getY(),
						this.bottomLeft.getY())) {
			return true;
		}
		if (isBoundedBy(area.bottomRight.getX(), this.bottomLeft.getX(),
				this.bottomRight.getX())
				&& isBoundedBy(area.bottomRight.getY(), this.topLeft.getY(),
						this.bottomLeft.getY())) {
			return true;
		}
		if (isBoundedBy(area.topRight.getX(), this.bottomLeft.getX(),
				this.bottomRight.getX())
				&& isBoundedBy(area.topRight.getY(), this.topLeft.getY(),
						this.bottomLeft.getY())) {
			return true;
		}
		
		if(area.overlapsWithAuxilliary(this)) {
			return true;
		}
		
		return false;
	}
	
	
	private boolean overlapsWithAuxilliary(RectangularArea area) {
		// y axis in first, x axis in second condition check
		if (isBoundedBy(area.bottomLeft.getX(), this.bottomLeft.getX(),
				this.bottomRight.getX())
				&& isBoundedBy(area.bottomLeft.getY(), this.topLeft.getY(),
						this.bottomLeft.getY())) {
			return true;
		}
		if (isBoundedBy(area.topLeft.getX(), this.bottomLeft.getX(),
				this.bottomRight.getX())
				&& isBoundedBy(area.topLeft.getY(), this.topLeft.getY(),
						this.bottomLeft.getY())) {
			return true;
		}
		if (isBoundedBy(area.bottomRight.getX(), this.bottomLeft.getX(),
				this.bottomRight.getX())
				&& isBoundedBy(area.bottomRight.getY(), this.topLeft.getY(),
						this.bottomLeft.getY())) {
			return true;
		}
		if (isBoundedBy(area.topRight.getX(), this.bottomLeft.getX(),
				this.bottomRight.getX())
				&& isBoundedBy(area.topRight.getY(), this.topLeft.getY(),
						this.bottomLeft.getY())) {
			return true;
		}
		
		return false;
	}


	private boolean isBoundedBy(int position, int min, int max) {
		if(position >= min && position <= max) {
			return true;
		}
		return false;
	}
	
	
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("((");
		buffer.append(this.topLeft);
		buffer.append(", ");
		buffer.append(this.topRight);
		buffer.append(", \n");
		buffer.append(this.bottomLeft);
		buffer.append(", ");
		buffer.append(this.bottomRight);
		return buffer.toString();
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((bottomLeft == null) ? 0 : bottomLeft.hashCode());
		result = prime * result
				+ ((bottomRight == null) ? 0 : bottomRight.hashCode());
		result = prime * result + ((topLeft == null) ? 0 : topLeft.hashCode());
		result = prime * result
				+ ((topRight == null) ? 0 : topRight.hashCode());
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
		if (!(obj instanceof RectangularArea))
			return false;
		RectangularArea other = (RectangularArea) obj;
		if (bottomLeft == null) {
			if (other.bottomLeft != null)
				return false;
		} else if (!bottomLeft.equals(other.bottomLeft))
			return false;
		if (bottomRight == null) {
			if (other.bottomRight != null)
				return false;
		} else if (!bottomRight.equals(other.bottomRight))
			return false;
		if (topLeft == null) {
			if (other.topLeft != null)
				return false;
		} else if (!topLeft.equals(other.topLeft))
			return false;
		if (topRight == null) {
			if (other.topRight != null)
				return false;
		} else if (!topRight.equals(other.topRight))
			return false;
		return true;
	}
		
	
}