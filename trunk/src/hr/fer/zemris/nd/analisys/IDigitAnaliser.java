package hr.fer.zemris.nd.analisys;

import hr.fer.zemris.nd.document.util.*;
import java.util.List;

public interface IDigitAnaliser {
	
	
	public RectangularArea getBoundingBox();
	public List<RectangularArea> getSegmentsBoxes();	
	

}
