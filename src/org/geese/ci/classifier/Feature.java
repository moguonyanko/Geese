package org.geese.ci.classifier;

import java.util.Objects;

public class Feature {
	private final String word;
	private final String categoryName;

	public Feature(String word, String categoryName) {
		this.word = word;
		this.categoryName = categoryName;
	}

	public String getWord() {
		return word;
	}

	public String getCategoryName() {
		return categoryName;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj == null || word == null || categoryName == null){
			return false;
		}

		if(obj instanceof Feature){
			Feature other = (Feature)obj;
			return word.equals(other.getWord()) &&
					categoryName.equals(other.getCategoryName());
		}else{
			return false;
		}
	}

	@Override
	public int hashCode() {
		int hash = 5;
		hash = 43 * hash + Objects.hashCode(this.word);
		hash = 43 * hash + Objects.hashCode(this.categoryName);
		return hash;
	}

	@Override
	public String toString() {
		return "word : " + word + ", category name : " + categoryName;
	}
}
