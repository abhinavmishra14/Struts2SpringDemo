package com.action;

import java.io.File;
import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;


import com.utility.Utilities;

/**
 * The Class FileUploaderAction.
 */
public class FileUploaderAction extends Utilities{

	/**
	 * Instantiates a new file uploader action.
	 */
	public FileUploaderAction() {
		super();
       logger.info("FileUploaderAction instantiated..");
	}

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The file. */
	private File userImage;
	
	/**
	 * @return the userImage
	 */
	public File getUserImage() {
		return userImage;
	}

	/**
	 * @param userImage the userImage to set
	 */
	public void setUserImage(File userImage) {
		this.userImage = userImage;
	}

	/** The user image content type. */
	private String userImageContentType;
	
	/** The user image file name. */
	private String userImageFileName;

		
	/**
	 * Gets the user image content type.
	 *
	 * @return the userImageContentType
	 */
	public String getUserImageContentType() {
		return userImageContentType;
	}

	/**
	 * Sets the user image content type.
	 *
	 * @param userImageContentType the userImageContentType to set
	 */
	public void setUserImageContentType(String userImageContentType) {
		this.userImageContentType = userImageContentType;
	}

	/**
	 * Gets the user image file name.
	 *
	 * @return the userImageFileName
	 */
	public String getUserImageFileName() {
		return userImageFileName;
	}

	/**
	 * Sets the user image file name.
	 *
	 * @param userImageFileName the userImageFileName to set
	 */
	public void setUserImageFileName(String userImageFileName) {
		this.userImageFileName = userImageFileName;
	}

	

	
	/**
	 * Upload file.
	 *
	 * @return the string
	 */
	public String uploadFile(){
		return SUCCESS;
	}
	
	/**
	 * Upload file.
	 *
	 * @return the string
	 * @throws Exception the exception
	 */
	public String uploadImageFile() throws Exception{
		try {

			String filePath = ServletActionContext.getRequest().getSession().getServletContext().getRealPath("/");
			logger.info("Server path:" + filePath);
			if(userImageFileName!=null && userImage!=null){
				File fileToCreate=new File(filePath, this.userImageFileName);
				FileUtils.copyFile(this.userImage, fileToCreate);
			}
			else{
				addActionMessage("An empty file can not be uploaded..");
				return INPUT;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			addActionError("Exception occured while uploading the file..");
			return INPUT;
		}
		return SUCCESS;
	}

}
