package com.propertyReader;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * The Class PropertyReader.
 */
public class PropertyReader {

	/** The Constant CONFIG_BUNDLE. */
	private static final ResourceBundle CONFIG_BUNDLE=ResourceBundle.getBundle("com.properties.config");

	/** The Constant STRUTSCONSTANTS_BUNDLE. */
	private static final ResourceBundle STRUTSCONSTANTS_BUNDLE=ResourceBundle.getBundle("com.properties.strutsConstants");

	/** The Constant EXCEPTIONMSGS_BUNDLE. */
	private static final ResourceBundle EXCEPTIONMSGS_BUNDLE=ResourceBundle.getBundle("com.properties.systemMessages");


	/**
	 * Instantiates a new property reader.
	 */
	private PropertyReader() {
		super();
	}

	/**
	 * Gets the property.
	 *
	 * @param property the property
	 * @return the property
	 */
	public static String getProperty(String property){
		String propertyValue = "";
		try
		{
			propertyValue = CONFIG_BUNDLE.getString(property);
		}
		catch (MissingResourceException e)
		{
			propertyValue = "!" + property + "!";
			e.printStackTrace();
		}
		return propertyValue;
	}


	/**
	 * Gets the struts constants property.
	 *
	 * @param property the property
	 * @return the struts constants property
	 */
	public static String getStrutsConstantsProperty(String property){
		String propertyValue = "";
		try
		{
			propertyValue = STRUTSCONSTANTS_BUNDLE.getString(property);
		}
		catch (MissingResourceException e)
		{
			propertyValue = "!" + property + "!";
			e.printStackTrace();
		}
		return propertyValue;
	}

	/**
	 * Gets the exception property.
	 *
	 * @param property the property
	 * @return the exception property
	 */
	public static String getErrorProperty(String property){
		String propertyValue = "";
		try
		{
			propertyValue = EXCEPTIONMSGS_BUNDLE.getString(property);
		}
		catch (MissingResourceException e)
		{
			propertyValue = "!" + property + "!";
			e.printStackTrace();
		}
		return propertyValue;
	}
}
