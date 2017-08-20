package com.serviceImpl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.service.AuthenticationService;
import com.service.UserManagementService;

/**
 * The Class ServiceAware.
 */
public class ServiceAware {

	/** The logger. */
	protected final Log logger = LogFactory.getLog(getClass());

	/**
	 * Instantiates a new service aware.
	 */
	public ServiceAware() {
		super();
		logger.info("ServiceAware instantiated..");
	}

	/** The auth mgr service. */
	private AuthenticationService authMgrService;

	/** The user mgmt service. */
	private UserManagementService userMgmtService;

	/**
	 * Gets the auth mgr service.
	 *
	 * @return the auth mgr service
	 */
	public AuthenticationService getAuthMgrService() {
		return authMgrService;
	}

	/**
	 * Sets the auth mgr service.
	 *
	 * @param authMgrService the new auth mgr service
	 */
	public void setAuthMgrService(AuthenticationService authMgrService) {
		this.authMgrService = authMgrService;
	}

	/**
	 * Gets the user mgmt service.
	 *
	 * @return the user mgmt service
	 */
	public UserManagementService getUserMgmtService() {
		return userMgmtService;
	}

	/**
	 * Sets the user mgmt service.
	 *
	 * @param userMgmtService the new user mgmt service
	 */
	public void setUserMgmtService(UserManagementService userMgmtService) {
		this.userMgmtService = userMgmtService;
	}
}
