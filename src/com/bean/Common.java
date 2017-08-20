package com.bean;

import java.io.Serializable;

/**
 * The Class Common.
 */
class Common implements Serializable{

	/**
	 * Instantiates a new common.
	 */
	public Common() {
		super();
	}

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The redirect url. */
	private String redirectUrl;


	/**
	 * Gets the redirect url.
	 *
	 * @return the redirect url
	 */
	public String getRedirectUrl() {
		return redirectUrl;
	}

	/**
	 * Sets the redirect url.
	 *
	 * @param redirectUrl the new redirect url
	 */
	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

	/** The response. */
	private String response;


	/**
	 * Gets the response.
	 *
	 * @return the response
	 */
	public String getResponse() {
		return response;
	}

	/**
	 * Sets the response.
	 *
	 * @param response the new response
	 */
	public void setResponse(String response) {
		this.response = response;
	}
}
