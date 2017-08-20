package com.dao;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.propertyReader.PropertyReader;

/**
 * The Class ConnectionManager.
 */
public class ConnectionManager{

	/** The logger. */
	protected static final Log logger = LogFactory.getLog(ConnectionManager.class);

	/** The DRIVER. */
	public static final String DRIVERNAME="mySqlDriver";

	/** The URL. */
	public static final String URL="mySqlUrl";

	/** The DRIVER_DERBY. */
	public static final String DRIVERNAME_DERBY="derbyDBDriver";

	/** The URL_DERBY. */
	public static final String URL_DERBY="derbyDBDriverUrl";

	/** The USER. */
	public static final String USER="mySqlUser";

	/** The PASSWORD. */
	public static final String PASSWORD="mySqlPassword";


	/** The context, kept for better performance */
	private static javax.naming.Context ctx=null;;


	/**
	 * Constructs the singleton, also obtaining the context.
	 */
	private static void init() throws NamingException {
		ctx = new javax.naming.InitialContext();
	}

	/**
	 * Gets a new connection by DSN.
	 *
	 *@param dataSourceName The name for the data source.
	 *@return A new database connection.
	 */
	public static Connection getConnectionFromPool(String dataSourceName) throws NamingException, SQLException {
		if (dataSourceName == null) {
			return null;
		}

		if(ctx==null) {
			init();
		}

		//TimerLogger tm = TimerLogger.getStartTimer(1, "DS-Lookup");
		DataSource ds = (DataSource)ctx.lookup("java:comp/env/"+dataSourceName);
		//tm.endTimer();

		return ds.getConnection();

	}


	/**
	 * Gets the connection.
	 *
	 * @param driverName the driver name
	 * @param driverUrl the driver url
	 * @param userName the user name
	 * @param password the password
	 * @return the connection
	 */
	private static Connection getConnection(String driverName, String driverUrl,String userName, String password) {
		Connection con=null;
		try {
			Class.forName(driverName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			con=DriverManager.getConnection(driverUrl, userName, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}



	/**
	 * Gets the connection common.
	 *
	 * @return the connection common
	 */
	public static Connection getConnectionCommon(){
		return getConnection(PropertyReader.getProperty(DRIVERNAME), PropertyReader.getProperty(URL),PropertyReader.getProperty(USER), PropertyReader.getProperty(PASSWORD));
	}


	/**
	 * Gets the connection.
	 *
	 * @param driverName the driver name
	 * @param driverUrl the driver url
	 * @param userName the user name
	 * @param password the password
	 * @return the connection
	 */
		private static Connection getConnectionDerby(String driverName, String driverUrl) {
		Connection con=null;
		try {
			Class.forName(driverName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			con=DriverManager.getConnection(driverUrl);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}


	/**
	 * Gets the connection.
	 *
	 * @return the connection
	 * @throws SQLException 
	 * @throws NamingException 
	 */
	public static Connection getConnection() throws NamingException, SQLException{
		if(PropertyReader.getProperty("dataAccessMode").equalsIgnoreCase("local")){
			//return getConnectionDerby(PropertyReader.getProperty(DRIVERNAME_DERBY),PropertyReader.getProperty(URL_DERBY));
			return getDerbyConnectionViaPool();
		}
		else{
			 return getMySqlConnectionViaPool();
			//return getConnection(PropertyReader.getProperty(DRIVERNAME), PropertyReader.getProperty(URL).concat(PropertyReader.getProperty("dbInitDBNAME")),PropertyReader.getProperty(USER), PropertyReader.getProperty(PASSWORD));
		}
	}


    /**
     * Gets the mysql connection via pool.
     *
     * @return the connection via pool
     * @throws NamingException the naming exception
     * @throws SQLException the sQL exception
     */
    public static Connection getDerbyConnectionViaPool() throws NamingException, SQLException{
		return getConnectionFromPool("jdbc/Derby");
    }
    
    
    /**
     * Gets the mysql connection via pool.
     *
     * @return the connection via pool
     * @throws NamingException the naming exception
     * @throws SQLException the sQL exception
     */
    public static Connection getMySqlConnectionViaPool() throws NamingException, SQLException{
		return getConnectionFromPool("jdbc/mysqldb");
    }
    
    
	/**
	 * De register database drivers.
	 */
	public static void deRegisterDatabaseDrivers(){
		Enumeration<Driver> drivers = DriverManager.getDrivers();
		while (drivers.hasMoreElements()) {   
			Driver driver = drivers.nextElement();   
			try {               
				DriverManager.deregisterDriver(driver);     
				logger.info(String.format("Deregistering jdbc driver: %s", driver));  
			} catch (SQLException e) {            
				logger.info(String.format("Error deregistering driver %s", driver), e);
			}        
		} 
	}
}
