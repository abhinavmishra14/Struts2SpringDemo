<%@taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>
<div id="userImage">
  <center><h2><b
	style="font-family: sans-serif; color: navy">Struts2 File Upload Example</b></h2></center>
<br />
	<b>User Image:</b><s:property value="userImage"/>
	<br/>
	<b>Content Type:</b><s:property value="userImageContentType"/>
	<br/>
	<b>File Name:</b> <s:property value="userImageFileName"/>
	<br/>
	<b>Uploaded Image:</b>
	<br/>
	<img src="<s:property value="userImageFileName"/>"/>
</div>

