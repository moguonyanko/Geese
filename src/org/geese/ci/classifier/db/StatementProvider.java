package org.geese.ci.classifier.db;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface StatementProvider {
	
	PreparedStatement prepareStatement(String sql) throws SQLException;
	
}
