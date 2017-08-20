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
import com.dao.AuthenticationDao;
import com.dao.ConnectionManager;
import com.utility.Utilities;

/**
 * The Class AuthenticationImpl.
 */
public class AuthenticationDaoImpl implements AuthenticationDao{

	/** The logger. */
	protected final Log logger = LogFactory.getLog(getClass());
	/**
	 * Instantiates a new authentication impl.
	 */
	public AuthenticationDaoImpl() {
		super();
		logger.info("AuthenticationDaoImpl instantiated..");
	}

	/* (non-Javadoc)
	 * @see com.service.Authentication#authenticate(java.lang.String, java.lang.String)
	 */
	@Override
	public Map<String, Object> authenticate(String userName, String password) throws SQLException{
		logger.info("authenticate invoked..");
		String currentDateTime=Utilities.getCurrentDateTimeWithZone();
		Map<String,Object> response=new HashMap<String,Object>();;
		PreparedStatement preStmt=null;
		PreparedStatement preStmtLastLogin=null;
		Connection con=null;
		try {
			con = ConnectionManager.getConnection();
		} catch (NamingException e) {
			e.printStackTrace();
		}
		preStmt= con.prepareStatement("select username,emailId,role,firstName,lastName,lastLogin from user_profiles where username=? and password=?");
		preStmt.setString(1, userName);
		preStmt.setString(2, password);
		ResultSet rs=preStmt.executeQuery();
		ResultSetMetaData rsm=preStmt.getMetaData();
		if(rs.next()){
			logger.info("Result set available..");
			response.put("authResponse", "passed");
			for(int i=1;i<=rsm.getColumnCount();i++){
				response.put(rsm.getColumnName(i).toLowerCase(),rs.getString(rsm.getColumnName(i)));
			}
		}
		else{
			response.put("authResponse", "failed");
		}
		if(response.get("lastlogin")==null){
			response.put("lastlogin",currentDateTime);
			preStmtLastLogin=con.prepareStatement("update user_profiles set lastLogin=? where username=?");
			preStmtLastLogin.setString(1,currentDateTime);
			preStmtLastLogin.setString(2,(String)response.get("username"));
			preStmtLastLogin.executeUpdate();
		}
		else{
			preStmtLastLogin=con.prepareStatement("update user_profiles set lastLogin=? where username=?");
			preStmtLastLogin.setString(1,currentDateTime);
			preStmtLastLogin.setString(2,(String)response.get("username"));
			preStmtLastLogin.executeUpdate();
		}
		logger.info("Response after auth: "+response);
		if(con!=null){
			rs.close();
			con.close();
		}
		return response;
	}
}
