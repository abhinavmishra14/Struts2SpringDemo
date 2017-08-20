package com.action;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 
 * The Class AbstractAction.
 */
public abstract class AbstractAction  extends ActionSupport{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The logger. */
	protected final Log logger = LogFactory.getLog(getClass());

	public AbstractAction() {
		super();
		logger.info("AbstractAction instantiated...");
	}

	/**
	 * Execute.
	 *
	 * @return the string
	 */
	public String execute()
	{
		if (logger.isDebugEnabled()) 
		{
			logger.debug("Default implementation of action request handling");
		}
		return "success";
	} 
}
