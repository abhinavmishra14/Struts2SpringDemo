package com.action;

import java.util.Map;
import com.bean.AuthBean;
import com.propertyReader.PropertyReader;
import com.serviceImpl.ServiceAware;
import com.utility.Utilities;

/**
 * The Class Login.
 */
public class LoginAction extends Utilities{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;


	/**
	 * Instantiates a new login.
	 */
	public LoginAction() {
		logger.info("Login action class init..");
	}

	/** The user. */
	private AuthBean authBean;

	/** The service. */
	private ServiceAware service;

	/**
	 * Gets the service.
	 *
	 * @return the service
	 */
	public ServiceAware getService() {
		return service;
	}

	/**
	 * Sets the service.
	 *
	 * @param service the new service
	 */
	public void setService(ServiceAware service) {
		this.service = service;
	}

	/**
	 * Gets the user.
	 *
	 * @return the user
	 */
	public AuthBean getAuthBean() {
		return authBean;
	}

	/**
	 * Sets the user.
	 *
	 * @param authBean the new auth bean
	 */
	public void setAuthBean(AuthBean authBean) {
		this.authBean = authBean;
	}


	/**
	 * Do login.
	 *
	 * @return the string
	 * @throws SQLException
	 */
	public String doLogin() throws Exception{
		Map<String,Object> response=null;
		String returnStr="";
		String sessionValidate = (String) getSession().get("sessionValidate");
		logger.info("Session validate before login: "+sessionValidate);
		if (!(sessionValidate==null)){
			getAuthBean().setRedirectUrl(PropertyReader.getStrutsConstantsProperty("redirectAction"));
			getResponse().setStatus(200);
			returnStr= SUCCESS;
		}
		else{
			response=getService().getAuthMgrService().authenticate(
					getAuthBean().getUserName().toLowerCase(),
					Utilities.getPassword(getAuthBean().getPassword())
			);
			String authStatus=response.get("authResponse").toString();
			if(authStatus.equals("failed")){
				logger.info("Authentication status: "+authStatus);
				addActionError(PropertyReader.getErrorProperty("invalidLogin"));
				getResponse().setStatus(401);
				returnStr= LOGIN;
			}
			else{
				getSession().put("sessionValidate", "true");
				setUserProfile(response);
				Utilities.incrementCounter();
				logger.info("Authentication status: "+authStatus);
				getResponse().setStatus(200);
				getAuthBean().setRedirectUrl(PropertyReader.getStrutsConstantsProperty("redirectAction"));
				returnStr= SUCCESS;
			}
		}
		return returnStr;
	}
}