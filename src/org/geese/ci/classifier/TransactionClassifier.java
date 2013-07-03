package org.geese.ci.classifier;

public interface TransactionClassifier extends Classifier{
	void start(String configPath) throws InitializeException;
	void end(boolean fail);
}
