package hr.fer.zemris.nd.analisys;

import java.awt.image.BufferedImage;

import hr.fer.zemris.nd.document.DigitField;

public interface IDigitTransformer {

	public DigitField transformImageToDigit(BufferedImage digitImage);	
	
}
