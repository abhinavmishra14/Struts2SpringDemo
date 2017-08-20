package com.serviceImpl;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.service.AbstractService;
import com.service.AuthenticationService;


/**
 * The Class AuthenticationImpl.
 */
public class AuthenticationServiceImpl implements AuthenticationService{

	/** The logger. */
	protected final Log logger = LogFactory.getLog(getClass());

	/**
	 * Instantiates a new authentication impl.
	 */
	public AuthenticationServiceImpl() {
		super();
		logger.info("AuthenticationServiceImpl instantiated..");
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
	 * @see com.service.Authentication#authenticate(java.lang.String, java.lang.String)
	 */
	@Override
	public Map<String, Object> authenticate(String userName, String password) throws Exception{
		return getAbsService().getAuthMgr().authenticate(userName, password);
	}
}
