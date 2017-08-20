/**
 *
 */
package com.dao;

import java.sql.SQLException;
import java.util.Map;

/**
 * The Interface UserManagement.
 *
 * @author Abhinav
 */
public interface UserManagementDao {


	/**
	 * Update user.
	 *
	 * @param userName the user name
	 * @param firstName the first name
	 * @param lastName the last name
	 * @param emailId the email id
	 * @param role the role
	 * @return the string
	 * @throws SQLException the sQL exception
	 */
	public String updateUser(String userName,String firstName,String lastName,String emailId,String role)throws SQLException;

	/**
	 * Gets the user profile.
	 *
	 * @param userName the user name
	 * @return the user profile
	 * @throws SQLException the sQL exception
	 */
	public Map<String,Object> getUserProfile(String userName)throws SQLException;


	/**
	 * Change pasword.
	 *
	 * @param userName the user name
	 * @param newPassword the new password
	 * @param oldPassword the old password
	 * @param emailId the email id
	 * @return the string
	 * @throws SQLException the sQL exception
	 */
	public String changePasword(String userName,String newPassword,String oldPassword)throws SQLException;

	/**
	 * Creates the new user.
	 *
	 * @param userName the user name
	 * @param firstName the first name
	 * @param lastName the last name
	 * @param emailId the email id
	 * @param role the role
	 * @return the string
	 * @throws SQLException the sQL exception
	 */
	public String createNewUser(String userName,String firstName,String lastName,String emailId,String role)throws SQLException;

	/**
	 * Gets the all users.
	 *
	 * @param role the role
	 * @return the all users
	 * @throws SQLException the sQL exception
	 */
	public Map<String,Object> getAllUsers(String role)throws SQLException;

}
