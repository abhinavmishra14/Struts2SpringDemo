package com.interceptor;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.action.AbstractAction;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import com.opensymphony.xwork2.util.ValueStack;

public class CaseChangerInterceptor extends AbstractAction implements Interceptor {

	/**
	 * Instantiates a new case changer intercepter.
	 */
	public CaseChangerInterceptor() {
		super();
		logger.info("CaseChangerInterceptor instantiated..");
	}

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.interceptor.Interceptor#destroy()
	 */
	@Override
	public void destroy() {
		logger.info("CaseChangerInterceptor destroy..");
	}

	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.interceptor.Interceptor#init()
	 */
	@Override
	public void init() {
		logger.info("CaseChangerInterceptor init..");
	}

	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.interceptor.Interceptor#intercept(com.opensymphony.xwork2.ActionInvocation)
	 */
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		logger.info("CaseChangerInterceptor intercept..");
		HttpServletRequest req=ServletActionContext.getRequest();
		ValueStack vs=invocation.getStack();
		Enumeration params=req.getParameterNames();
		while(params.hasMoreElements()){
			String paramName=(String)params.nextElement();
			if(!(paramName.equalsIgnoreCase("password")) && !(paramName.equalsIgnoreCase("role"))){
				//String paramVal=req.getParameter(paramName).toLowerCase();
				String paramVal=vs.findString(paramName).toLowerCase();
				vs.set(paramName, paramVal);
				logger.info("Changing parameters to lowercase,paramName: "+paramName+" | paramValue: "+paramVal);
			}
		}
		return invocation.invoke();
	}
}
