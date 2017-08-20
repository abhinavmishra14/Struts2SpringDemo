package com.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dao.AuthenticationDao;
import com.dao.UserManagementDao;

/**
 * The Class AbstractService.
 */
public class AbstractService {

	/** The logger. */
	protected final Log logger = LogFactory.getLog(getClass());

	/**
	 * Instantiates a new abstract service.
	 */
	public AbstractService() {
		super();
		logger.info("AbstractService instantiated..");
	}

	/** The auth mgr. */
	private AuthenticationDao authMgr;

	/** The user mgmt. */
	private UserManagementDao userMgmt;

	/**
	 * Gets the auth mgr.
	 *
	 * @return the auth mgr
	 */
	public AuthenticationDao getAuthMgr() {
		return authMgr;
	}

	/**
	 * Sets the auth mgr.
	 *
	 * @param authMgr the new auth mgr
	 */
	public void setAuthMgr(AuthenticationDao authMgr) {
		this.authMgr = authMgr;
	}

	/**
	 * Gets the user mgmt.
	 *
	 * @return the user mgmt
	 */
	public UserManagementDao getUserMgmt() {
		return userMgmt;
	}

	/**
	 * Sets the user mgmt.
	 *
	 * @param userMgmt the new user mgmt
	 */
	public void setUserMgmt(UserManagementDao userMgmt) {
		this.userMgmt = userMgmt;
	}


}
