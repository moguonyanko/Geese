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
import org.geese.ci.classifier.Trainers;

public class TestClassifiers {

	private static final String CONFIG_FILE_PATH = "application.properties";

	@BeforeClass
	public static void beforeSetUp() {
	}

	@Test
	public void test_NaiveBaysClassify() {
		WordFilterTask task = WordFilterTasks.DEFAULT.getTask();

		String[] targets = new String[]{
			"quick rabbit", "quick money", "quick money", "quick money"
		};
		String[] results = new String[4];
		
		try (TransactionClassifier nbClassifier = new NaiveBaysClassifier(task);) {
			nbClassifier.start(CONFIG_FILE_PATH);

			Trainer trainer = Trainers.newTrainer();
			trainer.train(nbClassifier);

			results[0] = nbClassifier.classify(targets[0]);
			results[1] = nbClassifier.classify(targets[1]);

			nbClassifier.setThresholds("bad", 3.0);

			results[2] = nbClassifier.classify(targets[2]);

			for (int i = 0; i < 10; i++) {
				trainer.train(nbClassifier);
			}

			results[3] = nbClassifier.classify(targets[3]);

			printClassifyResult(targets, results);
		} catch (InitializeException | OperateException ex) {
			fail();
		}
	}

	@Test
	public void test_OnlyClassifierOperation() {
		WordFilterTask task = WordFilterTasks.DEFAULT.getTask();

		try (TransactionClassifier classifier = new NaiveBaysClassifier(task);) {
			classifier.start(CONFIG_FILE_PATH);
			String target = "I am Linux user";
			String result = classifier.classify(target);

			assertNotNull(result);

			printClassifyResult(target, result);
		} catch (InitializeException | ClassifyException ce) {
			fail();
		}
	}

	@Test
	public void test_FisherClassify() {
		WordFilterTask task = WordFilterTasks.DEFAULT.getTask();

		String[] targets = new String[]{
			"quick rabbit", "quick money", "quick money", "quick money"
		};
		String[] results = new String[4];

		try (TransactionClassifier fishClassifier = new FisherClassifier(task);) {
			fishClassifier.start(CONFIG_FILE_PATH);

			Trainer trainer = Trainers.newTrainer();
			trainer.train(fishClassifier);

			results[0] = fishClassifier.classify(targets[0]);
			results[1] = fishClassifier.classify(targets[1]);

			fishClassifier.setThresholds("bad", 0.8);

			results[2] = fishClassifier.classify(targets[2]);

			fishClassifier.setThresholds("good", 0.4);

			results[3] = fishClassifier.classify(targets[3]);

			printClassifyResult(targets, results);
		} catch (InitializeException | OperateException ex) {
			fail();
		}
	}

	@Test
	public void trainMultiByteSample() {
		WordFilterTask task = WordFilterTasks.JP.getTask();

		try (TransactionClassifier nbClassifier = new NaiveBaysClassifier(task);) {
			nbClassifier.start(CONFIG_FILE_PATH);

			String base = "./test/test/org/geese/ci/classifier/sample";

			String badFilePath = base + "/bad/sample0.txt";
			Trainer trainer = Trainers.newTrainer();

			trainer.train(nbClassifier, "bad", badFilePath);

			String goodFilePath = base + "/good/sample0.txt";
			trainer.train(nbClassifier, "good", goodFilePath);

		} catch (InitializeException | TrainException ex) {
			fail();
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
		System.out.println("classify [" + target + "] to [" + result + "]");
	}

	private void printClassifyResult(String[] targets, String[] results) {
		for (int i = 0, len = targets.length; i < len; i++) {
			printClassifyResult(targets[i], results[i]);
		}
	}
}
