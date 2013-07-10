package org.geese.ci.classifier.store;

import java.sql.SQLException;

public interface StoreAccess {

	ClassifierConnection open() throws SQLException;
	
}
