package org.geese.util;

import java.sql.Connection;
import java.sql.SQLException;

public class Resources{

	public static void close(Connection con){
		try{
			con.close();
		}catch(SQLException ex){
			Logging.error("Fail to close database connection." + ex.getMessage());
		}
	}
}
