package com.action;

import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

import com.bean.AuthBean;
import com.serviceImpl.ServiceAware;
import com.utility.Utilities;


/**
 * The Class HomeAction.
 */
@SuppressWarnings("deprecation")
public class HomeAction extends Utilities{

	/**
	 * Instantiates a new home action.
	 */
	public HomeAction(){
		super();
		logger.info("HomeAction instantiated...");
		String users = "abhinav@gmail.com|" +
		"bhawna@gmail.com|" +
		"chikku@gmail.com|" +
		"rashmi@gmail.com|" +
		"vinay@gmail.com|" +
		"kunal@gmail.com|" +
		"rahul@gmail.com|" +
		"abhishek@gmail.com|" +
		"raju@gmail.com|" +
		"mamu@gmail.com|" +
		"dadu@gmail.com|" +
		"baby@gmail.com|" +
		"shikhu@gmail.com|" +
		"pinku@gmail.com|" +
		"sonu@gmail.com";
		userList = new ArrayList<String>();
		StringTokenizer st = new StringTokenizer(users, "|");

		while (st.hasMoreTokens()) {
			userList.add(st.nextToken().trim());
		}
		Collections.sort(userList);
		userMap.put("a",1);
		userMap.put("c",2);
		userMap.put("b",4);
		userMap.put("d",3);
	}

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The user. */
	private AuthBean authBean;

	/** The service aware. */
	private ServiceAware service;

	/** The input stream. */
	private InputStream inputStream;

	/** The list. */
	private List<String> userList;


	/**
	 * Gets the user list.
	 *
	 * @return the user list
	 */
	public List<String> getUserList() {
		return userList;
	}

	/**
	 * Sets the user list.
	 *
	 * @param userList the new user list
	 */
	public void setUserList(List<String> userList) {
		this.userList = userList;
	}

	/**
	 * Gets the input stream.
	 *
	 * @return the input stream
	 */
	public InputStream getInputStream() {
		return inputStream;
	}

	/**
	 * Sets the input stream.
	 *
	 * @param inputStream the new input stream
	 */
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

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
	 * Gets the auth bean.
	 *
	 * @return the auth bean
	 */
	public AuthBean getAuthBean() {
		return authBean;
	}

	/**
	 * Sets the auth bean.
	 *
	 * @param authBean the new auth bean
	 */
	public void setAuthBean(AuthBean authBean) {
		this.authBean = authBean;
	}

	/** The user map. */
	private Map<Object, Object> userMap=new HashMap<Object, Object>();

	/** The m. */
	private TreeMap<Object, Object> m;

	/**
	 * Gets the m.
	 *
	 * @return the m
	 */
	public TreeMap<Object, Object> getM() {
		return m;
	}

	/**
	 * Sets the m.
	 *
	 * @param m the m
	 */
	public void setM(TreeMap<Object, Object> m) {
		this.m = m;
	}

	/**
	 * Gets the user map.
	 *
	 * @return the user map
	 */
	public Map<Object, Object> getUserMap() {
		return userMap;
	}

	/**
	 * Sets the user map.
	 *
	 * @param userMap the user map
	 */
	public void  setUserMap(Map<Object, Object> userMap) {
		this.userMap = userMap;
	}

	/**
	 * Home page.
	 *
	 * @return the string
	 */
	public String homePage(){
		return SUCCESS;
	}

	/**
	 * Gets the user profile .
	 *
	 * @return the user profile action
	 * @throws Exception the exception
	 */
	public String getUserProfileAction()throws Exception{
		Map<String, Object> response=null;
		String responseStr="";
		String userName=getSession().get("username").toString();
		response = getService().getUserMgmtService().getUserProfile(userName);
		responseStr=response.get("username").toString()+"|"+response.get("firstname").toString()+"|"+response.get("lastname").toString()+"|"+response.get("emailid")+"|"+response.get("role");
		getAuthBean().setResponse(responseStr);
		getSession().putAll(response);
		inputStream = new StringBufferInputStream(responseStr);
		inputStream.close();
		return SUCCESS;
	}

	/**
	 * Update user profile action.
	 *
	 * @return the string
	 * @throws Exception the exception
	 */
	public String updateUserProfileAction()throws Exception{
		String response="";
		response = getService().getUserMgmtService().updateUser(getAuthBean().getUserName(),
				getAuthBean().getFirstName(),
				getAuthBean().getLastName(),
				getAuthBean().getEmailId(),
				getAuthBean().getRole()
		);
		getAuthBean().setResponse(response);
		inputStream = new StringBufferInputStream(response);
		inputStream.close();
		return SUCCESS;
	}

	/**
	 * Change password action.
	 *
	 * @return the string
	 * @throws Exception the exception
	 */
	public String changePasswordAction()throws Exception{
		String response="";
		response = getService().getUserMgmtService().changePasword(getAuthBean().getUserName(),Utilities.getPassword(getAuthBean().getNewPassword()),
				Utilities.getPassword(getAuthBean().getOldPassword()));
		getAuthBean().setResponse(response);
		inputStream = new StringBufferInputStream(response);
		inputStream.close();
		return SUCCESS;
	}

	/**
	 * Creates the user action.
	 *
	 * @return the string
	 * @throws Exception the exception
	 */
	public String createUserAction()throws Exception{
		String response="";
		response=getService().getUserMgmtService().createNewUser(
				getAuthBean().getUserName(),
				getAuthBean().getFirstName(),
				getAuthBean().getLastName(),
				getAuthBean().getEmailId(),
				getAuthBean().getRole()
		);
		getAuthBean().setResponse(response);
		inputStream = new StringBufferInputStream(response);
		inputStream.close();
		return SUCCESS;
	}

	/**
	 * Sort on key.
	 *
	 * @return the string
	 * @throws Exception the exception
	 */
	public String sortOnKey()throws Exception{
		m= new TreeMap<Object, Object>();
		m.putAll(userMap);
		return SUCCESS;
	}

	/**
	 * Sort on value.
	 *
	 * @return the string
	 * @throws Exception the exception
	 */
	public String sortOnValue()throws Exception{
		m= new TreeMap<Object, Object>(new HashMapValueComparator(userMap));
		m.putAll(userMap);
		return SUCCESS;
	}
}


/**
 * The Class HashMapValueComparator.
 */
class HashMapValueComparator implements Comparator<Object>{

	/** The map. */
	Map<Object, Object> map;

	/**
	 * Instantiates a new hash map value comparator.
	 *
	 * @param m the m
	 */
	public HashMapValueComparator(Map<Object, Object> m){
		super();
		this.map = m;
	}

	/**
	 * Instantiates a new hash map value comparator.
	 */
	public HashMapValueComparator() {
		super();
	}

	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	public int compare(Object o1, Object o2) {
		int retType;
		Object v1=map.get(o1);
		Object v2=map.get(o2);
		if(v1.hashCode()>v2.hashCode()){
			retType=1;
		}else if(v1.hashCode()<v2.hashCode()){
			retType=-1;
		}else{
			retType=0;
		}
		return retType;
	}
}