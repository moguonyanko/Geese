package org.geese.ci.classifier;

import org.geese.config.ProfileInitializeException;

public interface TransactionClassifier extends Classifier{
	void start(String configPath) throws ProfileInitializeException;
	void end(boolean fail);
}
