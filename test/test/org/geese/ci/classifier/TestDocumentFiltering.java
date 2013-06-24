package test.org.geese.ci.classifier;

import java.util.*;
import org.geese.ci.classifier.ClassifyException;

import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

import org.geese.ci.classifier.DocumentFiltering;
import org.geese.ci.classifier.FisherClassifier;
import org.geese.ci.classifier.NaiveBays;
import org.geese.ci.classifier.TrainException;
import org.geese.ci.classifier.TransactionClassifier;
import org.geese.ci.classifier.filter.WordFilterTask;
import org.geese.ci.classifier.util.StringUtil;
import org.geese.ci.classifier.util.TrainUtil;

public class TestDocumentFiltering {

	@BeforeClass
	public static void beforeSetUp() {
	}

	@Test
	public void test_getWords() {
		String sample = "the quick brown fox jumps over the lazy dog";
		DocumentFiltering docFilter = new DocumentFiltering();
		Map<String, Integer> result = docFilter.get(sample);
		assertTrue(result.get("the") == 2);
	}

	@Test
	public void test_NaiveBaysClassify() {
		WordFilterTask task = new DocumentFiltering();
		TransactionClassifier nbClassifier = new NaiveBays(task);
		boolean isFail = false;

		String[] results = new String[4];
		try {
			nbClassifier.start();

			TrainUtil.train(nbClassifier);

			results[0] = nbClassifier.classify("quick rabbit");
			results[1] = nbClassifier.classify("quick money");

			nbClassifier.setThresholds("bad", 3.0);

			results[2] = nbClassifier.classify("quick money");

			for (int i = 0; i < 10; i++) {
				TrainUtil.train(nbClassifier);
			}

			results[3] = nbClassifier.classify("quick money");
		} catch (ClassifyException | TrainException ex) {
			isFail = true;
		} finally {
			nbClassifier.end(isFail);
			assertFalse(isFail);
		}
	}

	@Test
	public void test_OnlyClassifierOperation() {
		WordFilterTask task = new DocumentFiltering();
		TransactionClassifier classifier = new NaiveBays(task);
		String result = "";
		boolean isFail = false;
		try {
			classifier.start();
			result = classifier.classify("quick rabbit");
			assertNotNull(result);
			isFail = true;
		} catch (ClassifyException ce) {
			assertFalse(StringUtil.isNullOrEmpty(result));
		} finally {
			classifier.end(isFail);
		}
	}

	@Test
	public void test_FisherClassify() {
		WordFilterTask task = new DocumentFiltering();
		TransactionClassifier fishClassifier = new FisherClassifier(task);
		boolean isFail = false;

		String[] results = new String[4];
		try {
			fishClassifier.start();

			TrainUtil.train(fishClassifier);

			results[0] = fishClassifier.classify("quick rabbit");
			results[1] = fishClassifier.classify("quick money");

			fishClassifier.setThresholds("bad", 0.8);

			results[2] = fishClassifier.classify("quick money");

			fishClassifier.setThresholds("good", 0.4);

			results[3] = fishClassifier.classify("quick money");
		} catch (ClassifyException | TrainException ex) {
			isFail = true;
		} finally {
			fishClassifier.end(isFail);
			assertFalse(isFail);
		}
	}
}
