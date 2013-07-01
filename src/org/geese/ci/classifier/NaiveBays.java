package org.geese.ci.classifier;

import java.sql.SQLException;
import java.util.*;

import org.geese.ci.classifier.filter.WordFilterTask;
import org.geese.util.Logging;

public class NaiveBays extends AbstractClassifier{

	private static final double DEFAULT_THRESHOLDS = 1.0;

	public NaiveBays(WordFilterTask task){
		super(task);
	}

	private double docProb(String doc, String categoryName) throws ClassifyException{
		Map<String, Integer> result = task.get(doc);

		double prob = 1.0;

		for(String word : result.keySet()){
			prob *= weightProb(word, categoryName);
		}

		return prob;
	}

	@Override
	public double prob(String word, String categoryName) throws ClassifyException{
		double categoryp = 0.0;
		double docp = 0.0;
		
		try{
			double cc = getCategoryCount(categoryName);
			double total = getTotalCategoryCount();
			categoryp = cc / total;
			docp = docProb(word, categoryName);
		}catch(SQLException sqle){
			Logging.error(sqle);
			throw new ClassifyException(word, categoryName, "Misstake calculate prob.");
		}
		
		return categoryp * docp;
	}

	@Override
	public void setThresholds(String categoryName, double thres){
		thresholds.put(categoryName, thres);
	}

	@Override
	public double getThresholds(String categoryName){
		if(!thresholds.containsKey(categoryName)){
			return DEFAULT_THRESHOLDS;
		}else{
			return thresholds.get(categoryName);
		}
	}

	@Override
	public String classify(String word) throws ClassifyException{
		Map<String, Double> probs = new HashMap<>();

		double max = 0.0;
		String best = defaultClass;

		String nowCategory = null;
		try{
			for(String category : getCategorieNames()){
				nowCategory = category;
				probs.put(category, prob(word, category));
				if(probs.get(category) > max){
					max = probs.get(category);
					best = category;
				}
			}
		}catch(SQLException te){
			Logging.error(te);
			throw new ClassifyException(word, nowCategory, "Misstake classify.");
		}

		for(String category : probs.keySet()){
			if(!best.equals(category)
				&& probs.get(category) * getThresholds(best) > probs.get(best)){
				return defaultClass;
			}
		}

		return best;
	}
}
