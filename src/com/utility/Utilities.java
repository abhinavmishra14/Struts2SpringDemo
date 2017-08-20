
package com.utility;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.net.InetAddress;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;


/**
 * The Class CommonUtil.
 * @author VB8
 */
public class Utilities extends WebUtils{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The session count. */
	public static int sessionCount=0;

	/**
	 * Increment counter.
	 *
	 * @return the int
	 */
	public static int incrementCounter(){
		return sessionCount++;
	}

	/**
	 * Decrement counter.
	 *
	 * @return the int
	 */
	public static int decrementCounter(){
		return sessionCount--;
	}

	/**
	 * Gets the session count.
	 *
	 * @return the session count
	 */
	public static int getSessionCount() {
		return sessionCount;
	}


	/**
	 * Instantiates a new utility.
	 */
	public Utilities() {
		super();
		logger.info("UtilitIes instantiated..");
	}


	/**
	 * Gets the current date.
	 *
	 * @return the current date
	 */
	public static String getCurrentDate()
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String currentDate=dateFormat.format(date);
		return currentDate;
	}

	/**
	 * Gets the current time.
	 *
	 * @return the current time
	 */
	public static String getCurrentTime()
	{
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
		Date date = new Date();
		String currentTime=timeFormat.format(date);
		return currentTime;
	}

	/**
	 * Gets the current date time with zone.
	 *
	 * @return the current date time with zone
	 */
	public static String getCurrentDateTimeWithZone()
	{
		SimpleDateFormat timeFormat = new SimpleDateFormat("EEEE,dd-MMM-yyyy,HH:mm:ssZ z");
		Date date = new Date();
		String currentDateTimeWithZone=timeFormat.format(date);
		return currentDateTimeWithZone;
	}

	/**
	 * Gets the current time in millis.
	 *
	 * @return the current time in millis
	 */
	protected Long getCurrentTimeInmillis(){
		Calendar currentDate = Calendar.getInstance();
		Long currentTimeInmillis = currentDate.getTimeInMillis();
		return currentTimeInmillis;
	}

	/**
	 * Sets the user profile.
	 *
	 * @param userMap the user map
	 * @return the map
	 */
	public Map<String,Object> setUserProfile(Map<String,Object> userMap){
		getSession().putAll(userMap);
		return getSession();
	}

	/**
	 * Gets the password.
	 * It generate a MD5 for a user's password 
	 * @param password as String
	 * @return the password as MD5 String
	 */
	public static String getPassword(String password)
	{
		MessageDigest messageDigest = null;
		String hashOfPassword = null;
		try
		{
			messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.update(password.getBytes(),0,password.length());
			hashOfPassword = new BigInteger(1,messageDigest.digest()).toString(16);
		}
		catch(NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
		return hashOfPassword;
	}

	/**
	 * Gets the host name.
	 *
	 * @return the host name
	 */
	public static String getHostName(){
		InetAddress ownIP=null;
		try{
			ownIP=InetAddress.getLocalHost();
		}
		catch (Exception e){
			e.printStackTrace();
		}
		return ownIP.toString();
	}
	
	/**
	 * Serialize bean.
	 *
	 * @param user the user
	 * @throws FileNotFoundException the file not found exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	protected void serializeBean(Object object) throws FileNotFoundException, IOException{
		ObjectOutputStream objOut=new ObjectOutputStream(new FileOutputStream("userBean.txt"));
		objOut.writeObject(object);
		objOut.close();
		System.out.println("bean object serialized..");
	}

	/**
	 * Deserialize user bean.
	 *
	 * @return the object input stream
	 * @throws FileNotFoundException the file not found exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws ClassNotFoundException the class not found exception
	 */
	protected ObjectInputStream deserializeBean() throws FileNotFoundException, IOException, ClassNotFoundException{
		ObjectInputStream objIn= new ObjectInputStream(new FileInputStream("userBean.txt"));
		return objIn;
	}
	
	/**
     * Serialize.
     *
     * @param obj the obj
     * @return the byte[]
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static byte[] serialize(Object obj) throws IOException {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        ObjectOutputStream o = new ObjectOutputStream(b);
        o.writeObject(obj);
		System.out.println("Serialized..");
        return b.toByteArray();
    }

    /**
     * Deserialize.
     *
     * @param bytes the bytes
     * @return the object
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws ClassNotFoundException the class not found exception
     */
    public static Object deserialize(byte[] bytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream b = new ByteArrayInputStream(bytes);
        ObjectInputStream o = new ObjectInputStream(b);
		System.out.println("Deserialized..");
        return o.readObject();
    }
}
