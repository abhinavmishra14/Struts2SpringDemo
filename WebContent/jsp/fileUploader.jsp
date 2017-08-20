<%@taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>
<div id="footer" style="float:right;margin-right:45%;">
  <center><SPAN><b
	style="font-family: sans-serif; color: navy">Upload a file here..</b></SPAN></center>
<br />
  <s:actionerror  id="serverError" cssClass="serverActionError" cssStyle="color: red; padding:0px; margin:0px;list-style:none;"/>
  <s:actionmessage id="serverMsg" cssClass="serverActionMsg" cssStyle="color: red; padding:0px; margin:0px;list-style:none;"/>
  <s:form action="uploadImage" method="post" enctype="multipart/form-data" >
     <s:file name="userImage" label="Upload Image File"/>
	 <s:submit value="Upload" align="right" />
	</s:form>
  <br />
  <br />
</div>

