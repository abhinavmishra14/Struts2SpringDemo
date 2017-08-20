package com.listener;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.catalina.SessionEvent;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.utility.Utilities;

/**
 * **********************************************************************
 * @ServletContextListener-----------------------------------------------
 * The listener interface for receiving appContextAndSession events.
 * The class that is interested in processing a appContextAndSession
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addAppContextAndSessionListener<code> method. When
 * the appContextAndSession event occurs, that object's appropriate
 * method is invoked.
 * @see AppContextAndSessionEvent
 * *************************** *********************************************
 * @HttpSessionListener----------------------------------------------------
 * The listener interface for receiving session events.
 * The class that is interested in processing a session
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addSessionListener<code> method. When
 * the session event occurs, that object's appropriate
 * method is invoked.
 * @see SessionEvent
 */
public class AppContextAndSessionListener implements ServletContextListener,HttpSessionListener {

	public AppContextAndSessionListener() {
		super();
		logger.info("AppContextAndSessionListener instantiated..");
	}

	/** The logger. */
	protected final Log logger = LogFactory.getLog(getClass());

	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		logger.info("contextDestroyed...");
	}

	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		logger.info("contextInitialized...");
	}


	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpSessionListener#sessionCreated(javax.servlet.http.HttpSessionEvent)
	 */
	public void sessionCreated(HttpSessionEvent httpSessionEvent) {
		logger.info("SessionListener creating a new session..");
		HttpSession sess=httpSessionEvent.getSession();
		sess.setAttribute("sessionTimeOut", sess.getMaxInactiveInterval());
	}


	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpSessionListener#sessionDestroyed(javax.servlet.http.HttpSessionEvent)
	 */
	public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
		logger.info("SessionListener destroying the session..");
		if(Utilities.getSessionCount()!=0){
			Utilities.decrementCounter();
			logger.info("Session counter decremented!");
		}
	}
}
