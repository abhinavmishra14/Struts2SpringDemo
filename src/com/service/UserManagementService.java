package com.service;

import java.util.Map;

/**
 * The Interface UserManagementService.
 */
public interface UserManagementService {

	/**
	 * Update user.
	 *
	 * @param userName the user name
	 * @param firstName the first name
	 * @param lastName the last name
	 * @param emailId the email id
	 * @param role the role
	 * @return the string
	 * @throws Exception the exception
	 */
	public String updateUser(String userName,String firstName,String lastName,String emailId,String role)throws Exception;

	/**
	 * Gets the user profile.
	 *
	 * @param userName the user name
	 * @return the user profile
	 * @throws Exception the exception
	 */
	public Map<String,Object> getUserProfile(String userName)throws Exception;


	/**
	 * Change pasword.
	 *
	 * @param userName the user name
	 * @param newPassword the new password
	 * @param oldPassword the old password
	 * @param emailId the email id
	 * @return the string
	 * @throws Exception the exception
	 */
	public String changePasword(String userName,String newPassword,String oldPassword)throws Exception;

	/**
	 * Creates the new user.
	 *
	 * @param userName the user name
	 * @param firstName the first name
	 * @param lastName the last name
	 * @param emailId the email id
	 * @param role the role
	 * @return the string
	 * @throws Exception the exception
	 */
	public String createNewUser(String userName,String firstName,String lastName,String emailId,String role)throws Exception;

	/**
	 * Gets the all users.
	 *
	 * @param role the role
	 * @return the all users
	 * @throws Exception the exception
	 */
	public Map<String,Object> getAllUsers(String role)throws Exception;

}
