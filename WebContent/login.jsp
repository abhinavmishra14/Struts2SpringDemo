<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/struts-tags" prefix="s"%>
<html>
	<h2 style="color: red;"><center><noscript>Your browser does not support JavaScript! Application will not work properly.</noscript></center></h2>
    <head>
      <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
      <title>Login Page</title>
       <link rel="stylesheet" type="text/css" href="css/style.css" />             
        <script type="text/javascript" src="js/jquery.js"></script>
        <script type="text/javascript">
	      function clearErrors(){
	       	    $('.serverError').hide();
		     	$("#sessExpID").html("");
		     	$("#sessExpID").hide();
		     	$(".errorMessage").html("");
		     	$(".errorMessage").hide();
		     	$(".serverActionError").html("");
		     	$(".serverActionError").hide();
	      }
        </script>
     </head>
    <body  style="background-color: #FAF8CC">
        <%
		  if(session.getAttribute("sessionValidate")!=null && session.getAttribute("sessionValidate").equals("true"))
		  {
		    response.sendRedirect("homeAction.action");
		  }
		%>
		<div align="center" style="margin-top:180px">
			 <%
				if(request.getParameter("sess")!=null && request.getParameter("sess").equalsIgnoreCase("expired")){
			 %> 
				<label id="sessExpID" style="color: red; padding: 0px; width:100%; margin: 0px;"><s:text name="label.sessionExpired"/></label> 
			 <% }
			 %> 
	        <s:form action="loginAction" >
	           <table align="center" bordercolor="green" border="1">
		            <s:actionerror  id="serverError" cssClass="serverActionError" cssStyle="color: red; padding:0px; margin:0px;list-style:none;"/>
		            <s:textfield name="authBean.userName" key="label.username" required="true" onclick="clearErrors();"/>
		            <s:password name="authBean.password" key="label.pass"  required="true"  onclick="clearErrors();"/>
		            <%--<s:checkbox name="rememberMe" id="rememberMe" key="label.rememberMe"/>--%> <s:submit key="label.loginsub" name="submit" cssStyle="color:red;" />
	            </table>
	        </s:form>
        </div>
    </body>
</html>
