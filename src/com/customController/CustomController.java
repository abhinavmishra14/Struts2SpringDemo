package com.customController;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.dispatcher.ng.filter.StrutsPrepareAndExecuteFilter;
import com.dbInitializer.DBInitializer;
import com.propertyReader.PropertyReader;
import com.utility.MailUtil;


/**
 * The Class StrutsSpringDemoController.
 */
public class CustomController extends StrutsPrepareAndExecuteFilter {

	/** The logger. */
	protected final Log logger = LogFactory.getLog(getClass());

	/** The request. */
	protected HttpServletRequest request;

	/** Initial setup of database when application is deployeed first time on mechine */
	static{
		try {
			 if(PropertyReader.getProperty("dataAccessMode").equalsIgnoreCase("local")){
					DBInitializer.createDBAndTablesDerby();
			 }
		    else{
				DBInitializer.createDBAndTables();
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Instantiates a new struts spring demo controller.
	 */
	public CustomController(){
		logger.info("Custom controller instantiated..");
	}

	/* (non-Javadoc)
	 * @see org.apache.struts2.dispatcher.ng.filter.StrutsPrepareAndExecuteFilter#init(javax.servlet.FilterConfig)
	 */
	public void init(FilterConfig filterConfig)
	throws ServletException
	{
		logger.info("Custom controller init..");
		super. init(filterConfig) ;
		if(DBInitializer.flag){
			MailUtil sendMail=new MailUtil();
			String mailContent="Hi "+DBInitializer.getFirstname()+" "+DBInitializer.getLastname()+PropertyReader.getProperty("mailContent")+"UserName: "+DBInitializer.getUsername()+"<br/>Password: "+DBInitializer.getPassword()
			+PropertyReader.getProperty("mailContentLink")
			+PropertyReader.getProperty("mailConetntLinkRef")
			+PropertyReader.getProperty("mailContentThankStmt");

			if(PropertyReader.getProperty("mailMode").equalsIgnoreCase("orgMail"))
			{
				sendMail.sendMail(DBInitializer.getEmailid(),PropertyReader.getProperty("mailSubject"),mailContent);
			}else{
				sendMail.sendMailViaGmail(DBInitializer.getEmailid(),PropertyReader.getProperty("mailSubject"),mailContent);
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.apache.struts2.dispatcher.ng.filter.StrutsPrepareAndExecuteFilter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
	throws IOException, ServletException
	{
		logger.info("Custom controller doFilter..");
		request =(HttpServletRequest)req;
		String url = request.getRequestURL().toString();
		String servletPath = request.getServletPath();
		logger.info("Request data: url= "+url+" and servletPath= "+servletPath);
		String currentUrl = request.getRequestURI();
		String queryString = request.getQueryString();
		String typeOfRequest = request.getHeader("X-Requested-With");
		logger.info("Current URL :"+currentUrl+" Query String :"+queryString+" REQUEST TYPE: "+typeOfRequest);
		super.doFilter(req, res, chain);
	}

	/* (non-Javadoc)
	 * @see org.apache.struts2.dispatcher.ng.filter.StrutsPrepareAndExecuteFilter#destroy()
	 */
	public void destroy(){
		logger.info("Custom controller destroyed..");
		super.destroy();
	}
}
