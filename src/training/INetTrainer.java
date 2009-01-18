package training;

import java.awt.image.BufferedImage;
import java.util.List;

public interface INetTrainer {
	public void addSegmentSaturationExample(List<Double> saturations, int digit);
	public void addScaledImageExample(BufferedImage image, int digit);
	public void startTrainingSaturation();
	public void startTrainingScaled();
}
