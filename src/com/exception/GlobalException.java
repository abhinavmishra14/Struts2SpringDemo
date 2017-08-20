package com.exception;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.util.ValueStack;
import com.propertyReader.PropertyReader;
import com.utility.Utilities;

/**
 * The Class GlobalException.
 */
public class GlobalException extends Utilities{

	/**
	 * Instantiates a new global exception.
	 */
	public GlobalException() {
		super();
	}

	/**
	 * Instantiates a new global exception.
	 *
	 * @param exception the exception
	 */
	public GlobalException(Exception exception) {
		super();
		this.exception = exception;
	}

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The exception. */
	private Exception exception;

	/** The message. */
	private String message;

	/** The exception class name. */
	private String exceptionClassName;

	/**
	 * Gets the exception class name.
	 *
	 * @return the exception class name
	 */
	public String getExceptionClassName() {
		return exceptionClassName;
	}

	/**
	 * Sets the exception class name.
	 *
	 * @param exceptionClassName the new exception class name
	 */
	public void setExceptionClassName(String exceptionClassName) {
		this.exceptionClassName = exceptionClassName;
	}

	/**
	 * Gets the message.
	 *
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Sets the message.
	 *
	 * @param message the new message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Gets the exception.
	 *
	 * @return the exception
	 */
	public Exception getException() {
		return exception;
	}

	/**
	 * Sets the exception.
	 *
	 * @param exception the new exception
	 */
	public void setException(Exception exception) {
		this.exception = exception;
	}

	/* (non-Javadoc)
	 * @see com.action.AbstractAction#execute()
	 */
	public String execute(){
		logger.info("GlobalException action invoked..");
		setExceptionClassName(exception.getClass().getName());
		logger.error("Error class: "+getExceptionClassName());
		setMessage(findExceptionBase());
		getResponse().setStatus(500);
		exception.printStackTrace();
		return PropertyReader.getStrutsConstantsProperty("globalException");
	}


	/**
	 * Find exception base.
	 *
	 * @return the string
	 */
	private String findExceptionBase() {        
		ActionContext ac = ActionContext.getContext();
		ValueStack vs = ac.getValueStack();
		return "Exception arised in "+vs.getRoot().get(2).getClass().getSimpleName().toString();
	}
}
