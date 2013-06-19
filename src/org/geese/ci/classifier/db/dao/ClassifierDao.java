package org.geese.ci.classifier.db.dao;

import org.geese.ci.classifier.db.ClassifierConnection;

public abstract class ClassifierDao {

	private final ClassifierConnection connection;

	public ClassifierDao(ClassifierConnection connection) {
		this.connection = connection;
	}

	public ClassifierConnection getConnection() {
		return connection;
	}
	
	//abstract <T> double select(T target);
}
