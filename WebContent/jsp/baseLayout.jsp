<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@ page language="java" session="true"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<h2 style="color: red;">
<center>
	<noscript>Your browser does not support JavaScript! Application will not work properly.</noscript>
</center>
</h2>
<head>
<meta http-equiv="PRAGMA" content="NO-CACHE"></meta>
<title><tiles:insertAttribute name="title" ignore="true" /></title>
<link rel="stylesheet" type="text/css" href="css/ddsmoothmenu.css" />
<link rel="stylesheet" type="text/css" href="css/style.css" />
<link rel="stylesheet" type="text/css" href="css/prompt.css" />
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/ddsmoothmenu.js"></script>
<script type="text/javascript" src="js/prompt.js"></script>
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript">
var sessionTime=0;
//getting the location at which session is expired..START
var loc = (window.location+'');
var hashLoc = loc.indexOf('#');
var hashPart = '';
var urlPart = '';
if(hashLoc!=-1)
{
	urlPart = loc.substring(0,hashLoc);
	hashPart = loc.substring(hashLoc);
}
//getting the location at which session is expired..END

$(document).ready(function() {
	sessionTime=$("#hdnSessionTime").val(); //hidden field value of session time out value
	setInterval(timeOut,1000); //calling timeOut function after each second till session expires.
});

function resetTimeOut(){
	'<%
	   session.setMaxInactiveInterval(session.getMaxInactiveInterval());
	%>'
	sessionTime=$("#hdnSessionTime").val(); //reseting timeout value with hidden field value of sessionTime time out value
	//console.log("timeout value reset to: "+sessionTime);
}

function timeOut(){
	//console.log("sessionTime counter started..");
	if(sessionTime!=0){
		sessionTime=sessionTime-1;
		if(sessionTime<=60){
			$.prompt('<div id="heading" align="left"><b>Struts2-SpringDemo</div><br/><div style="background: #D8D8D8;padding-left:15px;">You have not been active for awhile.</div></b><br/><div style="padding-left:15px;">You will be logged out in <b>&nbsp;'+sessionTime+ '&nbsp;seconds</b> and <br/>will be returned to the login screen. If you wish <br/>to extend your sessionTime, click Ok.</div>',{ 
					buttons:{Ok:true},
					prefix:'jqismooth',
					submit:function(v,m,f){ 
						if(!v) return true;
						else{
							window.location.reload();
						}
					}
			   }
			);
		}
	}
	if(sessionTime==0){
		$.prompt.close();
		window.location.href = "login.jsp?sess=expired";
	}
}
</script>
</head>
<body style="background-color: #FAF8CC">
<img id="ajaxImage" src="imgs/ajaxloader.gif" style="display: none;"></img>
<div id="pagewrap">
	<div id="ajaxBackground"></div>
	<div id="maincontainer">
	   <tiles:insertAttribute name="header" />
	   <tiles:insertAttribute name="body" /> 
	   <tiles:insertAttribute name="footer" />
	</div>
</div>

</body>
<s:hidden id="hdnSessionTime" value='%{#session.sessionTimeOut}' />
</html>