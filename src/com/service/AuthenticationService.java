
package com.service;

import java.util.Map;


/**
 * The Interface Authentication.
 *
 * @author VB8
 */
public interface AuthenticationService{


	/**
	 * Authenticate.
	 *
	 * @param userName the user name
	 * @param password the password
	 * @return the map
	 * @throws Exception the exception
	 */
	public Map <String,Object> authenticate(String userName,String password)throws Exception;
}
