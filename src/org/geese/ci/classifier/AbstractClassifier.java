package org.geese.ci.classifier;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

import org.geese.ci.classifier.store.ClassifierConnection;
import org.geese.ci.classifier.store.StoreAccess;
import org.geese.ci.classifier.store.StoreAccessFactory;
import org.geese.ci.classifier.store.StoreInitializeException;
import org.geese.ci.classifier.store.dao.DaoFactory;
import org.geese.ci.classifier.store.dao.FeatureCountDao;
import org.geese.ci.classifier.store.dao.CategoryCountDao;
import org.geese.ci.classifier.filter.WordFilterTask;
import org.geese.ci.classifier.probability.WordProbability;
import org.geese.config.Profile;
import org.geese.util.Logging;

public abstract class AbstractClassifier implements TransactionClassifier {

	private final static double WEIGHT = 1.0;
	private final static double AP = 0.5;
	private final WordProbability defaultProbability = new WordProbability() {
		@Override
		public double prob(String word, String categoryName) throws ClassifyException {
			try {
				double catCount = getCategoryCount(categoryName);

				if (catCount > 0) {
					return getFeatureCount(word, categoryName) / catCount;
				} else {
					return 0.0;
				}

			} catch (SQLException sqle) {
				Logging.error(sqle);
				throw new ClassifyException(word, categoryName, "Fail to calculate probability.");
			}
		}
	};
	final WordFilterTask task;
	final String defaultClass;
	final Map<String, Double> thresholds = new HashMap<>();
	private String dbType;
	protected Profile appProfile;
	private ClassifierConnection con;
	
	public AbstractClassifier(WordFilterTask task) {
		this(task, "unknown");
	}

	public AbstractClassifier(WordFilterTask task, String defaultClass) {
		this.task = task;
		this.defaultClass = defaultClass;
	}

	/**
	 * Start classifier operation.
	 *
	 * Get connection and start transaction. The connection is disabled auto
	 * commit mode. This method is working without Class.forName because of
	 * using JDBC4 driver.
	 *
	 */
	@Override
	public void start(String configPath) throws InitializeException {
		String classifierName = this.getName();

		appProfile = new Profile(configPath);
		dbType = appProfile.getStoreType();

		try {
			StoreAccess dba = StoreAccessFactory.create(appProfile);
			con = dba.open();
			con.init();
			Logging.info(classifierName + " operation started.");
		} catch (SQLException | IOException ex) {
			Logging.error(appProfile.toLocalize("error.start"), ex);
			throw new StoreInitializeException(dbType, ex);
		}
	}

	private void incFeatureCount(String word, String categoryName) throws SQLException {
		FeatureCountDao dao = DaoFactory.createFeatureCountDao(dbType, con);

		Feature feature = new Feature(word, categoryName);
		double count = getFeatureCount(word, categoryName);

		if (count <= 0) {
			dao.insert(feature);
		} else {
			dao.update(count + 1, feature);
		}
	}

	private void incCategoryCount(String categoryName) throws SQLException {
		CategoryCountDao dao = DaoFactory.createCategoryCountDao(dbType, con);

		Category category = new Category(categoryName);
		double count = getCategoryCount(categoryName);

		if (count <= 0) {
			dao.insert(category);
		} else {
			dao.update(count + 1, category);
		}
	}

	double getFeatureCount(String word, String categoryName) throws SQLException {
		FeatureCountDao dao = DaoFactory.createFeatureCountDao(dbType, con);
		Feature feature = new Feature(word, categoryName);
		double count = dao.select(feature);
		return count;
	}

	double getCategoryCount(String categoryName) throws SQLException {
		CategoryCountDao dao = DaoFactory.createCategoryCountDao(dbType, con);
		Category category = new Category(categoryName);
		double count = dao.select(category);
		return count;
	}

	double getTotalCategoryCount() throws SQLException {
		CategoryCountDao dao = DaoFactory.createCategoryCountDao(dbType, con);
		List<Double> counts = dao.findAllCounts();

		double total = 0;

		for (Double count : counts) {
			total += Double.valueOf(count);
		}

		return total;
	}

	Set<String> getCategorieNames() throws SQLException {
		CategoryCountDao dao = DaoFactory.createCategoryCountDao(dbType, con);
		return dao.findAllCategories();
	}

	double featureProb(String word, String categoryName) throws SQLException {
		double catCount = getCategoryCount(categoryName);

		if (catCount > 0) {
			return getFeatureCount(word, categoryName) / catCount;
		} else {
			return 0.0;
		}
	}

	double weightProb(String word, String categoryName, WordProbability probability) throws ClassifyException {
		double totalFeatureCount = 0.0;

		try {
			for (String existingCategory : getCategorieNames()) {
				totalFeatureCount += getFeatureCount(word, existingCategory);
			}
		} catch (SQLException sqle) {
			Logging.error(sqle);
			throw new ClassifyException(word, categoryName, "Fail to calculate probabillity.");
		}

		double nowProb = probability.prob(word, categoryName);

		double weightedProb = ((WEIGHT * AP) + (totalFeatureCount * nowProb)) / (WEIGHT + totalFeatureCount);

		return weightedProb;
	}

	double weightProb(String word, String categoryName) throws ClassifyException {
		return weightProb(word, categoryName, defaultProbability);
	}

	@Override
	public void train(String doc, String category) throws TrainException {
		Map<String, Integer> result = task.get(doc);

		String word = null;
		try {
			Iterator<String> wordIter = result.keySet().iterator();

			while (wordIter.hasNext()) {
				word = wordIter.next();
				incFeatureCount(word, category);
			}

			incCategoryCount(category);
		} catch (SQLException sqle) {
			Logging.error(sqle);
			throw new TrainException(word, category, "Fail training.");
		}
	}
	
	@Override
	public void end(boolean fail) {
		String classifierName = this.getName();

		try (ClassifierConnection connection = con) {
			if (!fail) {
				connection.commit();
				Logging.info(classifierName + " operation finished.");
			} else {
				connection.rollback();
				Logging.info(classifierName + " operation failed and rollbacked.");
			}
		} catch (IOException | SQLException ex) {
			Logging.error(classifierName + " failed trainning and close connection.");
			Logging.error(ex.getMessage());
		}
	}

	@Override
	public String getName() {
		return this.getClass().getSimpleName();
	}

	@Override
	public void close() {
		end(false);
	}
}
