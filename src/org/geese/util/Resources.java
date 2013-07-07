package org.geese.util;

import java.sql.Connection;
import java.sql.SQLException;

public class Resources{

	/**
	 * @deprecated 
	 * This method force closing exception down.
	 * @param con 
	 */
	public static void close(Connection con){
		try{
			con.close();
		}catch(SQLException ex){
			Logging.error("Fail to close database connection." + ex.getMessage());
		}
	}
}
