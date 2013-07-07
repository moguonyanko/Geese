package test.org.geese.ci.classifier;

import java.util.*;
import java.util.regex.Pattern;

import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

import org.geese.ci.classifier.FisherClassifier;
import org.geese.ci.classifier.NaiveBaysClassifier;
import org.geese.ci.classifier.ClassifyException;
import org.geese.ci.classifier.InitializeException;
import org.geese.ci.classifier.OperateException;
import org.geese.ci.classifier.TrainException;
import org.geese.ci.classifier.TransactionClassifier;
import org.geese.ci.classifier.filter.DefaultWordFilter;
import org.geese.ci.classifier.filter.FilterTaskFactory;
import org.geese.ci.classifier.filter.WordFilter;
import org.geese.ci.classifier.filter.WordFilterTask;
import org.geese.ci.classifier.filter.WordFilterTasks;
import org.geese.ci.classifier.Trainer;

public class TestClassifiers {

	private static final String CONFIG_FILE_PATH = "application.properties";
	
	@BeforeClass
	public static void beforeSetUp() {
	}

	@Test
	public void test_NaiveBaysClassify() {
		WordFilterTask task = WordFilterTasks.DEFAULT.getTask();
		TransactionClassifier nbClassifier = new NaiveBaysClassifier(task);
		boolean isFail = false;

		String[] targets = new String[]{
			"quick rabbit", "quick money", "quick money", "quick money"
		};
		String[] results = new String[4];
		try {
			nbClassifier.start(CONFIG_FILE_PATH);

			Trainer.train(nbClassifier);

			results[0] = nbClassifier.classify(targets[0]);
			results[1] = nbClassifier.classify(targets[1]);

			nbClassifier.setThresholds("bad", 3.0);

			results[2] = nbClassifier.classify(targets[2]);

			for (int i = 0; i < 10; i++) {
				Trainer.train(nbClassifier);
			}

			results[3] = nbClassifier.classify(targets[3]);
			
			printClassifyResult(targets, results);
		} catch (InitializeException | OperateException ex) {
			isFail = true;
		} finally {
			nbClassifier.end(isFail);
			assertFalse(isFail);
		}
	}

	@Test
	public void test_OnlyClassifierOperation() {
		WordFilterTask task = WordFilterTasks.DEFAULT.getTask();
		TransactionClassifier classifier = new NaiveBaysClassifier(task);
		boolean isFail = false;
		try {
			classifier.start(CONFIG_FILE_PATH);
			String target = "quick rabbit";
			String result = classifier.classify("quick rabbit");
			
			assertNotNull(result);
			
			printClassifyResult(target, result);
		} catch (InitializeException | ClassifyException ce) {
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

		String[] targets = new String[]{
			"quick rabbit", "quick money", "quick money", "quick money"
		};
		String[] results = new String[4];
		try {
			fishClassifier.start(CONFIG_FILE_PATH);

			Trainer.train(fishClassifier);

			results[0] = fishClassifier.classify(targets[0]);
			results[1] = fishClassifier.classify(targets[1]);

			fishClassifier.setThresholds("bad", 0.8);

			results[2] = fishClassifier.classify(targets[2]);

			fishClassifier.setThresholds("good", 0.4);

			results[3] = fishClassifier.classify(targets[3]);
			
			printClassifyResult(targets, results);
		} catch (InitializeException | OperateException ex) {
			isFail = true;
		} finally {
			fishClassifier.end(isFail);
			assertFalse(isFail);
		}
	}

	@Test
	public void trainMultiByteSample() {
		WordFilterTask task = WordFilterTasks.JP.getTask();

		TransactionClassifier nbClassifier = new NaiveBaysClassifier(task);

		boolean isFail = false;

		try {
			nbClassifier.start(CONFIG_FILE_PATH);

			String base = "./test/test/org/geese/ci/classifier/sample";

			String badFilePath = base + "/bad/sample0.txt";
			Trainer.train(nbClassifier, "bad", badFilePath);

			String goodFilePath = base + "/good/sample0.txt";
			Trainer.train(nbClassifier, "good", goodFilePath);

		} catch (InitializeException | TrainException ex) {
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

	private void printClassifyResult(String target, String result) {
		System.out.println("classify "+ target + " to [" + result + "]");
	}
	
	private void printClassifyResult(String[] targets, String[] results) {
		for(int i = 0, len = targets.length; i < len; i++){
			printClassifyResult(targets[i], results[i]);
		}
	}
}
