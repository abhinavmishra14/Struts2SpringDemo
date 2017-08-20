<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<meta http-equiv="refresh" content="5; url=<s:url id="execAndWait" includeParams="all" />"></meta>
<title>Request is in process..</title>
</head>
<body>
<br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/>
<center>
	<div id="loadingmsg" style="border-style: dotted; background-color: rgb(238, 238, 238); top: 100px; border-width: 1px; width: 345px; height: 120px;">  
	<font face='Arial' Size=3>
		 <br/>
		 <center><b>Processing your request please wait...</b></center>
	</font>
	<br>
	<center><img alt="progress" src="imgs/ajaxloader.gif"></center>
	</div>
</center>
</body>
</html>