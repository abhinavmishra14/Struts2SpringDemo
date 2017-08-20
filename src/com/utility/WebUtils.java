package com.utility;

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import com.action.AbstractAction;

/**
 * The Class CommonFunctions.
 */
public class WebUtils extends AbstractAction implements SessionAware,RequestAware,ServletResponseAware{

	/**
	 * Instantiates a new web utils.
	 */
	public WebUtils() {
		super();
		logger.info("WebUtils instantiated..");
	}

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The response. */
	protected HttpServletResponse response;

	/** The session. */
	protected Map<String,Object> session;

	/** The req. */
	protected Map<String,Object> req;

	/**
	 * Gets the req.
	 *
	 * @return the req
	 */
	public Map<String, Object> getReq() {
		return req;
	}

	/**
	 * Gets the session.
	 *
	 * @return the session
	 */
	public Map<String, Object> getSession() {
		return session;
	}


	/* (non-Javadoc)
	 * @see org.apache.struts2.interceptor.SessionAware#setSession(java.util.Map)
	 */
	@Override
	public void setSession(Map<String, Object> arg0) {
		this.session=arg0;
	}


	/* (non-Javadoc)
	 * @see org.apache.struts2.interceptor.RequestAware#setRequest(java.util.Map)
	 */
	@Override
	public void setRequest(Map<String, Object> arg0) {
		this.req=arg0;
	}

	/* (non-Javadoc)
	 * @see org.apache.struts2.interceptor.ServletResponseAware#setServletResponse(javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void setServletResponse(HttpServletResponse arg0) {
		this.response=arg0;

	}
	/**
	 * Gets the response.
	 *
	 * @return the response
	 */
	public HttpServletResponse getResponse() {
		return response;
	}

	/**
	 * Sets the response.
	 *
	 * @param response the new response
	 */
	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	/**
	 * Gets the cookie value.
	 *
	 * @param cookies the cookies
	 * @param cookieName the cookie name
	 * @param defaultValue the default value
	 * @return the cookie value
	 */
	public static String getCookieValue(Cookie[] cookies,String cookieName,String defaultValue)
	{
		for(int i=0; i<cookies.length; i++) 
		{
			Cookie cookie = cookies[i];
			if (cookieName.equals(cookie.getName()))
				return(cookie.getValue());
		}
		return(defaultValue);
	}
}
