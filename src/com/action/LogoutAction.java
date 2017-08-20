package com.action;

import org.apache.struts2.dispatcher.SessionMap;
import com.utility.Utilities;

/**
 * The Class LogoutAction.
 */
public class LogoutAction  extends Utilities{

	/**
	 * Instantiates a new logout action.
	 */
	public LogoutAction(){
		super();
		logger.info("Log out action class init..");
	}

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Logout.
	 *
	 * @return the string
	 */
	@SuppressWarnings("rawtypes")
	public String logOut()throws Exception{
		if(getSession() instanceof org.apache.struts2.dispatcher.SessionMap){
			((SessionMap) getSession()).invalidate();
		}
		return SUCCESS;
	}
}
