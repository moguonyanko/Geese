package test.org.geese.ci.classifier;

import java.util.*;
import java.util.regex.Pattern;

import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

import org.geese.ci.classifier.FisherClassifier;
import org.geese.ci.classifier.NaiveBays;
import org.geese.ci.classifier.ClassifyException;
import org.geese.ci.classifier.TrainException;
import org.geese.ci.classifier.TransactionClassifier;
import org.geese.ci.classifier.filter.DefaultWordFilter;
import org.geese.ci.classifier.filter.FilterTaskFactory;
import org.geese.ci.classifier.filter.WordFilter;
import org.geese.ci.classifier.filter.WordFilterTask;
import org.geese.ci.classifier.filter.WordFilterTasks;
import org.geese.ci.classifier.util.TrainUtil;

public class TestDocumentFiltering {

	@BeforeClass
	public static void beforeSetUp() {
	}

	@Test
	public void test_NaiveBaysClassify() {
		WordFilterTask task = WordFilterTasks.DEFAULT.getTask();
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
		WordFilterTask task = WordFilterTasks.DEFAULT.getTask();
		TransactionClassifier classifier = new NaiveBays(task);
		String result = "";
		boolean isFail = false;
		try {
			classifier.start();
			result = classifier.classify("quick rabbit");
			assertNotNull(result);
		} catch (ClassifyException ce) {
			isFail = true;
		} finally {
			classifier.end(isFail);
			assertFalse(isFail);
		}
	}

	@Test
	public void test_FisherClassify() {
		WordFilterTask task = WordFilterTasks.DEFAULT.getTask();
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

	@Test
	public void trainMultiByteSample() {
		WordFilterTask task = WordFilterTasks.JP.getTask();

		TransactionClassifier nbClassifier = new NaiveBays(task);

		boolean isFail = false;

		try {
			nbClassifier.start();

			String base = "./test/test/org/geese/ci/classifier/sample";

			String badFilePath = base + "/bad/sample0.txt";
			TrainUtil.train(nbClassifier, "bad", badFilePath);

			String goodFilePath = base + "/good/sample0.txt";
			TrainUtil.train(nbClassifier, "good", goodFilePath);

		} catch (TrainException ex) {
			isFail = true;
		} finally {
			nbClassifier.end(isFail);
			assertFalse(isFail);
		}
	}

	@Test
	public void customFilterTask() {
		WordFilter customFilter = new DefaultWordFilter();
		Pattern customSplitter = Pattern.compile("[\\s\\p{Punct}]");
		WordFilterTask customTask = FilterTaskFactory.createWordFilterTask(customFilter, customSplitter);

		String sample = "the quick brown fox jumps over the lazy dog";
		Map<String, Integer> result = customTask.get(sample);

		assertTrue(result.get("the") == 2);
	}
}
