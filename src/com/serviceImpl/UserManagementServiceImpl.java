package com.serviceImpl;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.service.AbstractService;
import com.service.UserManagementService;

/**
 * The Class UserManagementServiceImpl.
 */
public class UserManagementServiceImpl implements UserManagementService{

	/** The logger. */
	protected final Log logger = LogFactory.getLog(getClass());

	/**
	 * Instantiates a new user management service impl.
	 */
	public UserManagementServiceImpl() {
		super();
		logger.info("UserManagementServiceImpl instantiated..");
	}

	/** The abs service. */
	private AbstractService absService;

	/**
	 * Gets the abs service.
	 *
	 * @return the abs service
	 */
	public AbstractService getAbsService() {
		return absService;
	}

	/**
	 * Sets the abs service.
	 *
	 * @param absService the new abs service
	 */
	public void setAbsService(AbstractService absService) {
		this.absService = absService;
	}

	/* (non-Javadoc)
	 * @see com.service.UserManagementService#changePasword(java.lang.String, java.lang.String, java.lang.String)
	 */
	public String changePasword(String userName, String newPassword,
			String oldPassword) throws Exception {
		return getAbsService().getUserMgmt().changePasword(userName, newPassword, oldPassword);
	}

	/* (non-Javadoc)
	 * @see com.service.UserManagementService#getUserProfile(java.lang.String)
	 */
	public Map<String, Object> getUserProfile(String userName)
	throws Exception {
		return getAbsService().getUserMgmt().getUserProfile(userName);
	}

	/* (non-Javadoc)
	 * @see com.service.UserManagementService#updateUser(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public String updateUser(String userName, String firstName,
			String lastName, String emailId, String role) throws Exception {
		return getAbsService().getUserMgmt().updateUser(userName, firstName, lastName, emailId, role);
	}

	/* (non-Javadoc)
	 * @see com.service.UserManagementService#createNewUser(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public String createNewUser(String userName,String firstName,
			String lastName, String emailId, String role) throws Exception {
		return getAbsService().getUserMgmt().createNewUser(userName, firstName, lastName, emailId, role);
	}

	/* (non-Javadoc)
	 * @see com.service.UserManagementService#getAllUsers(java.lang.String)
	 */
	@Override
	public Map<String, Object> getAllUsers(String role) throws Exception {
		return getAbsService().getUserMgmt().getAllUsers(role);
	}

}
