package org.geese.ci.classifier;

import java.nio.charset.Charset;

public interface Trainer {

	void train(Classifier cl) throws TrainException;

	void train(Classifier cl, String categoryName, String fileName, Charset charset) throws TrainException;

	void train(Classifier cl, String categoryName, String fileName) throws TrainException;
}
