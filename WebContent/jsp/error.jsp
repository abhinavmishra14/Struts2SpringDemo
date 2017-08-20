<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
	<head>
		<SCRIPT type="text/javascript">
			function disableBackButton()
			{
			 window.history.forward();
			}
			setTimeout("disableBackButton()", 0);
		</SCRIPT>
	<title><s:text name="label.exceptionPage"/></title>  
	</head>
	<body  style="background-color: #FAF8CC">
		<h4><s:text name="label.heading"/></h4>
		<p><s:text name="label.headingMsg"/></p>
		----------------------------------------------------------------------------------------------------------------------------------------------------
		<h4 style="color: red;"><s:text name="label.exceptionName"/>&nbsp;<s:property value="exceptionClassName" /> </h4>
		<h4 style="color: red;"><s:text name="label.exceptionMsg"/>&nbsp;<s:property value="message"/> </h4>
		<h4 style="color: red;"><s:text name="label.stacktrace"/>&nbsp;<s:property value="exception.stackTrace"/></h4> 
		<p><a href="<s:url action='homeAction.action'/>">Home</a></p>
	    <p><a href="<s:url action='logOut.action'/>">Logout</a></p>
	</body>
</html>
