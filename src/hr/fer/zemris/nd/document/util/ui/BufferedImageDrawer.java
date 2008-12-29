/**
 * 
 */
package hr.fer.zemris.nd.document.util.ui;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 * @author goran
 *
 */
public class BufferedImageDrawer extends Component {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4002641088588197886L;
	BufferedImage image;
	
	
	public BufferedImageDrawer(BufferedImage image) {
		if (image == null) {
			throw new IllegalArgumentException("The image to paint cannot be null. ");
		}
		this.image = image;
	}
	
	
	
	/* (non-Javadoc)
	 * @see java.awt.Component#getHeight()
	 */
	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
		return this.image.getHeight();
	}



	/* (non-Javadoc)
	 * @see java.awt.Component#getWidth()
	 */
	@Override
	public int getWidth() {
		// TODO Auto-generated method stub
		return this.image.getWidth();
	}



	/* (non-Javadoc)
	 * @see java.awt.Component#paint(java.awt.Graphics)
	 */
	@Override
	public void paint(Graphics g) {
		g.drawImage(image, 0, 0, null);
	}

	

}
