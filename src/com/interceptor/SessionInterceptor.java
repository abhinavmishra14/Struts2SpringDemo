package com.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.propertyReader.PropertyReader;

/**
 * The Class SessionInterceptor.
 */
public class SessionInterceptor extends AbstractInterceptor {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	/** The logger. */
	protected final Log logger = LogFactory.getLog(getClass());


	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.interceptor.Interceptor#intercept(com.opensymphony.xwork2.ActionInvocation)
	 */
	public String intercept(ActionInvocation invocation) throws Exception {
		logger.info("SessionInterceptor intercepting...");
		final ActionContext context = invocation.getInvocationContext();
		HttpServletRequest request = (HttpServletRequest) context.get(ServletActionContext.HTTP_REQUEST);
		HttpSession session = request.getSession(true);	
		String sessionValidate = (String) session.getAttribute("sessionValidate");
		logger.info("Session validate: "+sessionValidate);
		logger.info("Max inactive interval is: "+session.getAttribute("sessionTimeOut"));
		String actionName = invocation.getInvocationContext().getName();
		logger.info("Passing to action: "+actionName);
		if(!actionName.equalsIgnoreCase("loginAction") && !(actionName.equalsIgnoreCase("demoExceptionHandler"))){
			if ((sessionValidate == null || sessionValidate.equals("false"))) {
				logger.info("User session expired..");
				return PropertyReader.getStrutsConstantsProperty("sessionExpired");
			}
		}
		String invocationString= invocation.invoke();
		logger.info("Back to session interceptor..");
		logger.info("Back from action: "+actionName);
		return invocationString;
	}
}