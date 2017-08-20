package com.daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.naming.NamingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.dao.ConnectionManager;
import com.dao.UserManagementDao;
import com.propertyReader.PropertyReader;
import com.utility.MailUtil;
import com.utility.Utilities;

/**
 * The Class UserManagementImpl.
 */
public class UserManagementDaoImpl implements UserManagementDao {

	/** The logger. */
	protected final Log logger = LogFactory.getLog(getClass());

	/**
	 * Instantiates a new user management impl.
	 */
	public UserManagementDaoImpl() {
		super();
		logger.info("UserManagementDaoImpl instantiated..");
	}


	/* (non-Javadoc)
	 * @see com.dao.UserManagement#updateUser(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public String updateUser(String userName, String firstName, String lastName,
			String emailId, String role) throws SQLException{
		logger.info("updateUser invoked..");
		logger.info("Recieved input> userName: "+userName+" firstName: "+firstName+" lastName: "+lastName+" emailId: "+emailId+" role: "+role);
		String retStr="";
		Connection con=null;
		PreparedStatement preStmtUpdate=null;
		try {
		    try {
				con=ConnectionManager.getConnection();
			} catch (NamingException e) {
				e.printStackTrace();
			}
			con.setAutoCommit(false);
			preStmtUpdate=con.prepareStatement("update user_profiles set firstName=?,lastName=?,emailId=?,role=? where username=?");
			preStmtUpdate.setString(1, firstName);
			preStmtUpdate.setString(2, lastName);
			preStmtUpdate.setString(3, emailId);
			preStmtUpdate.setString(4, role);
			preStmtUpdate.setString(5, userName);
			preStmtUpdate.executeUpdate();
			con.commit();
			int updateRes=preStmtUpdate.getUpdateCount();
			if(updateRes !=-1){
				retStr=PropertyReader.getErrorProperty("updateUserSuccess");
				logger.info("Update successfull..,response is: "+retStr);
			}
			else{
				retStr=PropertyReader.getErrorProperty("updateUserError");
				logger.info("Update failed..,response is: "+retStr);
			}
		} catch (SQLException sqle) {
			String msg=sqle.getMessage();
			retStr=msg.substring(0, msg.length()-9);
			sqle.printStackTrace();
			con.rollback();
		}
		finally{
			preStmtUpdate.close();
			con.close();
		}
		return retStr;
	}


	/* (non-Javadoc)
	 * @see com.dao.UserManagement#getUserProfile(java.lang.String)
	 */
	public Map<String, Object> getUserProfile(String userName)
	throws SQLException {
		logger.info("getUserProfile invoked..");
		logger.info("Recieved input> userName: "+userName);
		Map<String,Object> response;
		Connection con = null;
		try {
			con = ConnectionManager.getConnection();
		} catch (NamingException e) {
			e.printStackTrace();
		}
		response=new HashMap<String,Object>();
		PreparedStatement preStmt= con.prepareStatement("select username,emailId,role,firstName,lastName from user_profiles where username=?");
		preStmt.setString(1, userName);
		ResultSet rs=preStmt.executeQuery();
		ResultSetMetaData rsm=preStmt.getMetaData();
		if(rs.next()){
			for(int i=1;i<=rsm.getColumnCount();i++){
				response.put(rsm.getColumnName(i).toLowerCase(),rs.getString(rsm.getColumnName(i)));
			}
		}
		if(con!=null){
			rs.close();
			con.close();
		}
		logger.info("Response after getting profile: "+response);
		return response;
	}


	/* (non-Javadoc)
	 * @see com.dao.UserManagement#changePasword(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public String changePasword(String userName, String newPassword,
			String oldPassword) throws SQLException {
		String returnStr="";
		logger.info("changePasword invoked..");
		logger.info("Recieved input> userName: "+userName+" oldPassword: "+oldPassword+" newPassword: "+newPassword);
		Connection con = null;
		try {
			con = ConnectionManager.getConnection();
		} catch (NamingException e) {
			e.printStackTrace();
		}
		String query="update "+PropertyReader.getProperty("dbInitTableName")+ " set password=? where username=? and password=?";
		con.setAutoCommit(false);
		PreparedStatement preStmtUpdate=con.prepareStatement(query);
		preStmtUpdate.setString(1, newPassword);
		preStmtUpdate.setString(2, userName);
		preStmtUpdate.setString(3, oldPassword);
		preStmtUpdate.executeUpdate();
		con.commit();//if no errors..
		int updateRes=preStmtUpdate.getUpdateCount();
		if(updateRes !=-1){
			returnStr=PropertyReader.getErrorProperty("newPasswordSaved");
			logger.info("Password saved successfully..,response is: "+returnStr);
		}
		else{
			returnStr=PropertyReader.getErrorProperty("incorrectOldPassword");
			logger.info("Failed to save new password..,response is: "+returnStr);
		}
		if(con!=null){
			preStmtUpdate.close();
			con.close();
		}
		return returnStr;
	}


	/* (non-Javadoc)
	 * @see com.dao.UserManagementDao#createNewUser(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public String createNewUser(String userName,String firstName,
			String lastName, String emailId, String role) throws SQLException {
		logger.info("createNewUser invoked..");
		logger.info("Recieved Inupt: userName: "+userName+" firstName: "+firstName+" lastName: "+lastName+" emailId: "+emailId+" role: "+role);
		String password= MailUtil.getRandomString(8);
		String returnStr="";
		Connection con=null;
		PreparedStatement preStmtCreateUser=null;
		boolean sendMailFlag=false;
		String query="insert into "+PropertyReader.getProperty("dbInitTableName")+"(username,password,firstName,lastName,emailId,role) values(?,?,?,?,?,?)";
		try{
			try {
				con=ConnectionManager.getConnection();
			} catch (NamingException e) {
				e.printStackTrace();
			}
			con.setAutoCommit(false);
			preStmtCreateUser=con.prepareStatement(query);
			preStmtCreateUser.setString(1, userName);
			preStmtCreateUser.setString(2, Utilities.getPassword(password));
			preStmtCreateUser.setString(3, firstName);
			preStmtCreateUser.setString(4, lastName);
			preStmtCreateUser.setString(5, emailId);
			preStmtCreateUser.setString(6, role);
			preStmtCreateUser.executeUpdate();
			con.commit();   // If there is no error, commit the changes.
			int updateRes=preStmtCreateUser.getUpdateCount();
			if(updateRes!= -1){
				logger.info("User created successfully..");
				MailUtil sendMail=new MailUtil();
				String mailContent="Hi "+firstName+" "+lastName+PropertyReader.getProperty("mailContent")+"UserName: "+userName+"<br/>Password: "+password
				+PropertyReader.getProperty("mailContentLink")
				+PropertyReader.getProperty("mailConetntLinkRef")
				+PropertyReader.getProperty("mailContentThankStmt");
				if(PropertyReader.getProperty("mailMode").equalsIgnoreCase("orgMail"))
				{
					sendMailFlag=sendMail.sendMail(emailId,PropertyReader.getProperty("mailSubject"),mailContent);
				}else{
					sendMailFlag=sendMail.sendMailViaGmail(emailId,PropertyReader.getProperty("mailSubject"),mailContent);
				}
				if(sendMailFlag){
					returnStr=PropertyReader.getErrorProperty("createUserSuccess");
				}
				else{
					returnStr=PropertyReader.getErrorProperty("createUserSuccessMailFailed")+" "+PropertyReader.getProperty("dbInitEmail");
				}
				System.out.println("LOG FOR NEWLY CREATED USER> UserName: "+userName+" & Password: "+password);
			}
			else{
				returnStr=PropertyReader.getErrorProperty("createUserFailed");
				logger.info("Failed to create new user.");
			}
		}catch (SQLException sqle) {
			String msg=sqle.getMessage();
			returnStr=msg.substring(0, msg.length()-9);
			con.rollback();
			sqle.printStackTrace();
		}finally{
			preStmtCreateUser.close();
			con.close();
		}
		return returnStr;
	}


	/* (non-Javadoc)
	 * @see com.dao.UserManagementDao#getAllUsers(java.lang.String)
	 */
	@Override
	public Map<String, Object> getAllUsers(String role) throws SQLException {
		return null;
	}
}
