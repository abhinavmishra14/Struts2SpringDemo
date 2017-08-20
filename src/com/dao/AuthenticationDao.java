
package com.dao;


import java.sql.SQLException;
import java.util.Map;


/**
 * The Interface Authentication.
 *
 * @author VB8
 */
public interface AuthenticationDao{


	/**
	 * Authenticate.
	 *
	 * @param userName the user name
	 * @param password the password
	 * @return the map
	 * @throws SQLException the sQL exception
	 */
	public Map <String,Object> authenticate(String userName,String password)throws SQLException;
}
