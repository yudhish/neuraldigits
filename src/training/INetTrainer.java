package training;

import hr.fer.zemris.nd.document.DigitField;

import java.awt.image.BufferedImage;
import java.util.List;

public interface INetTrainer {
	public void addSegmentSaturationExample(List<Double> saturations, int digit);
	public void addImageExample(DigitField image, int digit);
	public void startTrainingSaturation();
	public void startTrainingScaled(int width, int height);
}
