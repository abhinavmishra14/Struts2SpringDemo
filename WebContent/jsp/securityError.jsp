<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
	<head>
    <title>Demo Error</title>
	</head>
	<body  style="background-color: #FAF8CC">
		<h4>The application has malfunctioned.</h4>
		<p>Please contact technical support with the following information,there are some Security Violation in application:</p> 

		<h4>Exception Name: <s:property value="exception" /> </h4>
		<h4>Exception Details: <s:property value="exceptionStack" /></h4> 
		<p><a href="<s:url action='homeAction.action'/>">Home</a></p>
	    <p><a href="<s:url action='logOut.action'/>">Logout</a></p>
	</body>
</html>
