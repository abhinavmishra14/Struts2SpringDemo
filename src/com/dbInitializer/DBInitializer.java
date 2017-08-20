package com.dbInitializer;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.NamingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dao.ConnectionManager;
import com.propertyReader.PropertyReader;
import com.utility.MailUtil;
import com.utility.Utilities;


/**
 * The Class DBInitializer.
 */
public class DBInitializer {

	/**
	 * Instantiates a new dB initializer.
	 */
	public DBInitializer() {
		super();
		logger.info("DBInitializer instantiated..");
	}

	/** The logger. */
	private static final Log logger = LogFactory.getLog(DBInitializer.class);

	/** The Constant username. */
	private static final String username=PropertyReader.getProperty("dbInitUserName");
	/** The Constant password. */
	private static final String password=MailUtil.getRandomString(8);

	/** The Constant firstName. */
	private static final String firstName=PropertyReader.getProperty("dbInitFirstName");

	/** The Constant lastName. */
	private static final String lastName=PropertyReader.getProperty("dbInitLastName");

	/** The Constant emailId. */
	private static final String emailId=PropertyReader.getProperty("dbInitEmail");

	/** The Constant role. */
	private static final String role=PropertyReader.getProperty("dbInitRole");

	/** The Constant dbName. */
	private static final String dbName=PropertyReader.getProperty("dbInitDBNAME");

	/** The Constant tableName. */
	private static final String tableName=PropertyReader.getProperty("dbInitTableName");

	/** The Constant createDBStmt. */
	private static final String createDBStmt="create database "+dbName;

	/** The Constant createTableStmt. */
	private static final String createTableStmt="CREATE table "+tableName+" (username varchar(50) NOT NULL,password varchar(50),firstName varchar(50),lastName varchar(50),emailId varchar(50) NOT NULL,role varchar(50),lastLogin varchar(50),forgetPassword varchar(20) DEFAULT 'false',PRIMARY KEY (emailId),UNIQUE (username))";

	/** The Constant insertInitUser. */
	private static final String insertInitUser="INSERT INTO "+tableName+" (username, password, firstName,lastName,emailId,role) VALUES (?, ?, ?,?,?,?)";

	/** The flag. */
	public static volatile boolean flag=false;

	/**
	 * Gets the username.
	 *
	 * @return the username
	 */
	public static String getUsername() {
		return username;
	}

	/**
	 * Gets the password.
	 *
	 * @return the password
	 */
	public static String getPassword() {
		return password;
	}

	/**
	 * Gets the firstname.
	 *
	 * @return the firstname
	 */
	public static String getFirstname() {
		return firstName;
	}

	/**
	 * Gets the lastname.
	 *
	 * @return the lastname
	 */
	public static String getLastname() {
		return lastName;
	}

	/**
	 * Gets the emailid.
	 *
	 * @return the emailid
	 */
	public static String getEmailid() {
		return emailId;
	}


	/**
	 * Creates the db and tables.
	 *
	 * @return true, if successful
	 * @throws SQLException the sQL exception
	 * @throws NamingException 
	 */
	public static boolean createDBAndTables()throws SQLException, NamingException{
		logger.info("Creating initial database and tables..");
		boolean flagInt=false;
		Connection con=ConnectionManager.getConnectionCommon();
		ResultSet rs = con.getMetaData().getCatalogs();
		while (rs.next()) {
			if(!rs.getString("TABLE_CAT").equals(dbName)){
				flagInt=true;
			}
			else{
				flagInt=false;
			}
		}
		if(flagInt){
			createDB(con,createDBStmt);
		}
		else{
			logger.info("Specified DB already exist!");
			if(con!=null){
				con.close();
			}
		}
		flag=flagInt;
		return  flagInt;
	}

	/**
	 * Creates the db and tables derby.
	 *
	 * @return true, if successful
	 * @throws SQLException the sQL exception
	 * @throws NamingException 
	 */
	public static boolean createDBAndTablesDerby()throws SQLException, NamingException{
		logger.info("Creating initial database and tables to derby..");
		boolean flagInt=false;
		Connection con=ConnectionManager.getConnection();
		flagInt=createTables(con,createTableStmt);
		return  flagInt;
	}
	/**
	 * Creates the db.
	 *
	 * @param dbConnection the db connection
	 * @param createStmt the create stmt
	 * @return true, if successful
	 * @throws SQLException the sQL exception
	 * @throws NamingException 
	 */
	private static boolean createDB(Connection dbConnection,String createStmt)throws SQLException, NamingException{
		logger.info("Creating initial database..");
		boolean flagCreateDb=false;
		PreparedStatement createDBPreStmt=null;
		Connection commonConWithDb=null;
		try{
			createDBPreStmt=dbConnection.prepareStatement(createStmt);
			createDBPreStmt.execute();
			flagCreateDb=true;
			logger.info("DB created successfully..");
		}catch (SQLException e) {
			if(dbConnection!=null){
				dbConnection.close();
			}
			e.printStackTrace();
		}
		finally{
			if(dbConnection!=null){
				dbConnection.close();
			}
		}
		if(flagCreateDb){
			commonConWithDb=ConnectionManager.getConnection();
			createTables(commonConWithDb,createTableStmt);
		}
		flag=flagCreateDb;
		return  flagCreateDb;
	}


	/**
	 * Creates the tables.
	 *
	 * @param dbConnection the db connection
	 * @param createStmt the create stmt
	 * @return true, if successful
	 * @throws SQLException the sQL exception
	 */
	private static boolean createTables(Connection dbConnection,String createStmt) throws SQLException{
		logger.info("Creating initial table..");
		boolean bCreatedTables = false;
		PreparedStatement statement = null;
		try {
			DatabaseMetaData dbm = dbConnection.getMetaData();
			String[] types = {"TABLE"};
			ResultSet rs = dbm.getTables(null,null,"%",types);
			String table ="";
			while (rs.next())
			{
				table = rs.getString("TABLE_NAME");
			}
			if(!table.equalsIgnoreCase(tableName))
			{
				statement = dbConnection.prepareStatement(createStmt);
				statement.execute();
				logger.info("Table created successfully..");
				bCreatedTables = true;
			}
			else{
				bCreatedTables = false;
				logger.info("Table already exist..");
			}
		} catch (SQLException ex) {
			if(dbConnection!=null){
				dbConnection.close();
			}
			ex.printStackTrace();
		}
		if(bCreatedTables){
			insertInitValues(dbConnection,insertInitUser);
		}
		flag=bCreatedTables;
		return bCreatedTables;
	}

	/**
	 * Insert init values.
	 *
	 * @param dbConnection the db connection
	 * @param insertInitUsers the insert init users
	 * @return true, if successful
	 * @throws SQLException the sQL exception
	 */
	private static boolean insertInitValues(Connection dbConnection,String insertInitUsers) throws SQLException{
		logger.info("Inserting initial values..");
		boolean insertVal=false;
		PreparedStatement insertStmt = null;
		try{
			insertStmt = dbConnection.prepareStatement(insertInitUsers);
			insertStmt.setString(1,username);
			insertStmt.setString(2,Utilities.getPassword(password));
			insertStmt.setString(3,firstName);
			insertStmt.setString(4,lastName);
			insertStmt.setString(5,emailId);
			insertStmt.setString(6,role);
			insertStmt.executeUpdate();
			insertVal = true;
			System.out.println("LOG> UserName: "+DBInitializer.getUsername()+" & Password: "+DBInitializer.getPassword());
			logger.info("Values inserted successfully..");
		}catch (SQLException e) {
			if(dbConnection!=null){
				dbConnection.close();
			}
			e.printStackTrace();
		}
		finally{
			if(dbConnection!=null){
				dbConnection.close();
			}
		}
		flag=insertVal;
		return insertVal;
	}
}