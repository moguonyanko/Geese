package org.geese.ci.classifier;

import java.util.Objects;

public class Category {

	private final String name;

	public Category(String name){
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj == null){
			return false;
		}

		if(obj instanceof Category){
			Category other = (Category)obj;
			return name.equals(other.getName());
		}else{
			return false;
		}
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 89 * hash + Objects.hashCode(this.name);
		return hash;
	}

	@Override
	public String toString() {
		return "name : " + name;
	}
}
