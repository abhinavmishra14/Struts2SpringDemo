<%@taglib uri="/struts-tags" prefix="s"%>
<script type="text/javascript">
		ddsmoothmenu.init({
			mainmenuid: "toolsMenu", //menu DIV id
			orientation: 'h', //Horizontal or vertical menu: Set to "h" or "v"
			classname: 'ddsmoothmenu', //class added to menu's outer DIV
			contentsource: "markup" //"markup" or ["container_id", "path_to_menu_file"]
		});
	</script>

<span style="float: right;">&nbsp;<a href="<s:url action='homeAction.action'/>">Home</a>&nbsp;|&nbsp;<s:text name="label.welcome" />&nbsp;<a
	href="javascript:void(0);" onclick="showProfile();"><s:property
	value="#session.username" /></a> | <s:text name="label.lastlogin" />&nbsp;<b><s:property
	value="#session.lastlogin" /></b> <%-- ACCESSING A STATIC METHOD USING OGNL IN JSP --%>
| <s:text name="label.total" />&nbsp;<b><s:property
	value="@com.utility.Utilities@getSessionCount()" /></b> <%-- ACCESSING A STATIC METHOD USING OGNL IN JSP --%>
| &nbsp;&nbsp;<a href="<s:url action='logOut.action'/>"><s:text
	name="label.logout" /></a> </span>
<div id="toolsMenu" class="ddsmoothmenu" style="width: auto;">
<ul>
	<li><a href="javascript:void(0)"><s:text name="label.tools" /></a>
	<ul id="myTools">
		<li><a href="javascript:void(0)"
			onclick="javascript:showProfile();"><s:text
			name="label.myProfile" /></a></li>
		<li><a href="javascript:void(0)"
			onclick="javascript:getUserProfile();"><s:text
			name="label.updateMyProfile" /></a></li>
		<li><a href="javascript:void(0)"
			onclick="javascript:showChangePassword();"><s:text
			name="label.changePass" /></a></li>
	</ul>
	</li>
	<s:if test="%{#session.role == 'Super_Admin'}">
		<li><a href="javascript:void(0)"><s:text
			name="label.adminPanel" /></a>
		<ul id="manageUser">
			<li><a href="javascript:void(0)"
				onclick="javascript:createUsers();"><s:text name="label.cu" /></a></li>
		</ul>
		</li>
	</s:if>
	<s:if test="%{#session.role == 'Administrator'}">
		<li><a href="javascript:void(0)"><s:text
			name="label.adminPanel" /></a>
		<ul id="manageUser">
			<li><a href="javascript:void(0)"
				onclick="javascript:createUsers();"><s:text name="label.cu" /></a></li>
		</ul>
		</li>
	</s:if>
	<li><a href="javascript:void(0)">File Uploader</a>
	  <ul id="uploader">
		<li><a href="<s:url action='uploadFile.action'/>">File Uploader Test</a></li>
	  </ul>
	</li>
</ul>
<br style="clear: right" />
</div>
<h2 align="center" style="padding-top: 50px"><marquee
	behavior="scroll"><s:text name="label.bigWelcome" /></marquee></h2>
<div id="updateMyProfile" style="display: none;" class="userMgmt">
<center><SPAN><b
	style="font-family: sans-serif; color: navy"><s:text
	name="label.updateMyProfile" /></b></SPAN></center>
<br />
<s:form>
	<table align="center" bordercolor="#667C26" border="1">
		<s:textfield name="#session.username" key="label.usrName"
			id="username" size="65%" disabled="true" />
		<s:textfield
			onclick="javascript:clearErrors();javascript:clearFieldErrors(this);"
			onfocus="javascript:clearErrors();javascript:clearFieldErrors(this);"
			onblur="validateFirstNameOnBlur(this);" name="firstName"
			key="label.fName" id="firstName" size="65%" required="true" />
		<s:label id="frstNameReq" cssStyle="color:red;display: none;" />
		<s:textfield
			onclick="javascript:clearErrors();javascript:clearFieldErrors(this);"
			onfocus="javascript:clearErrors();javascript:clearFieldErrors(this);"
			onblur="validateLastNameOnBlur(this);" name="lastName"
			key="label.lName" id="lastName" size="65%" required="true" />
		<s:label id="lstNameReq" cssStyle="color:red;display: none;" />
		<s:if test="#session.role=='App_User'">
			<s:textfield
				onclick="javascript:clearErrors();javascript:clearFieldErrors(this);"
				disabled='true' onblur="validateEmailOnBlur(this);" name="emailId"
				key="label.email" id="emailId" size="65%" required="true" />
		</s:if>
		<s:else>
			<s:textfield
				onclick="javascript:clearErrors();javascript:clearFieldErrors(this);"
				onfocus="javascript:clearErrors();javascript:clearFieldErrors(this);"
				onblur="validateEmailOnBlur(this);" name="emailId" key="label.email"
				id="emailId" size="65%" required="true" />
		</s:else>
		<s:label id="emailReq" cssStyle="color:red;display: none;" />
		<s:radio id="role" disabled="true" key="label.role" name="role"
			list="#{'App_User':'App_User','Administrator':'Administrator','Super_Admin':'Super_Admin'}" />
		<tr>
			<td>&nbsp;</td>
			<td align="right"><input type="button"
				value="<s:text name='label.updateSub'/>"
				onclick="javascript:updateUserProfile();" />&nbsp;<input
				type="button" name="cancleUpdateUser"
				value="<s:text name='label.updateCancle'/>"
				onclick="cancleFunct(this);" /></td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td align="right">
			<center><label id="updateMsg"
				style="color: blue; display: none;"></label></center>
			</td>
		</tr>
	</table>
</s:form> <br style="clear: right" />
</div>
<div id="myProfile" style="display: none;" class="userMgmt">
<center><SPAN><b
	style="font-family: sans-serif; color: navy"><s:text
	name="label.myProfile" /></b></SPAN></center>
<br />
<s:form>
	<table align="center" bordercolor="#667C26" border="1">
		<tr>
			<td><b><s:text name="label.usrName" /></b>&nbsp;</td>
			<td align="center">&nbsp;<span id='myUserName'><s:property
				value="#session.username" /> </span></td>
		</tr>
		<tr>
			<td><b><s:text name="label.fName" /></b>&nbsp;</td>
			<td align="center">&nbsp;<span id='myFirstName'></span></td>
		</tr>
		<tr>
			<td><b><s:text name="label.lName" /></b>&nbsp;</td>
			<td align="center">&nbsp;<span id='myLastName'></span></td>
		</tr>
		<tr>
			<td><b><s:text name="label.email" /></b>&nbsp;</td>
			<td align="center">&nbsp;<span id='myEmailId'></span></td>
		</tr>
		<tr>
			<td><b><s:text name="label.role" /></b>&nbsp;</td>
			<td align="center">&nbsp;<span id='myRole'></span></td>
		</tr>
		<tr>
			<td></td>
			<td align="right"><input type="button" name="closeProfile"
				value="<s:text name='label.close'/>" onclick="cancleFunct(this);" /></td>
		</tr>
	</table>
</s:form> <br style="clear: right" />
</div>
<div id="changePassword" style="display: none;" class="userMgmt">
<center><SPAN><b
	style="font-family: sans-serif; color: navy"><s:text
	name="label.changePass" /></b></SPAN></center>
<br />
<s:form>
	<table align="center" bordercolor="#667C26" border="1">
		<s:textfield name="#session.username" key="label.usrName"
			id="username" size="65%" disabled="true" />
		<s:password
			onclick="javascript:clearErrors();javascript:clearFieldErrors(this);"
			onfocus="javascript:clearErrors();javascript:clearFieldErrors(this);"
			onblur="validateOldPassOnBlur(this);" name="oldPass"
			key="label.oldpass" id="oldPass" size="65%" required="true" />
		<s:label id="oldPassReq" cssStyle="color:red;display: none;" />
		<s:password
			onclick="javascript:clearErrors();javascript:clearFieldErrors(this);"
			onfocus="javascript:clearErrors();javascript:clearFieldErrors(this);"
			onblur="validateNewPassOnBlur(this);" name="newPass"
			key="label.newpass" id="newPass" size="65%" required="true" />
		<s:label id="newPassReq" cssStyle="color:red;display: none;" />
		<s:password
			onclick="javascript:clearErrors();javascript:clearFieldErrors(this);"
			onfocus="javascript:clearErrors();javascript:clearFieldErrors(this);"
			onblur="validateConfPassOnBlur(this);" name="confirmPass"
			key="label.confpass" id="confirmPass" size="65%" required="true" />
		<s:label id="confPassReq" cssStyle="color:red;display: none;" />
		<tr>
			<td>&nbsp;</td>
			<td align="right"><input type="button"
				value="<s:text name='label.changepassSub'/>"
				onclick="javascript:changePassword();" />&nbsp;<input type="button"
				name="cancleChangePass" value="<s:text name='label.updateCancle'/>"
				onclick="cancleFunct(this);" /></td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td align="right">
			<center><label id="changePassMsg"
				style="color: blue; display: none; float: "></label><label
				id="changePassMsg2" style="color: red; display: none; float: "></label></center>
			</td>
		</tr>
	</table>
</s:form> <br style="clear: right" />
</div>
<div id="createNewUser" style="display: none;" class="userMgmt">
<center><SPAN><b
	style="font-family: sans-serif; color: navy"><s:text
	name="label.cnu" /></b></SPAN></center>
<br />
<s:form>
	<table align="center" bordercolor="#667C26" border="1">
		<s:textfield name="userNameCu" key="label.usrName" id="userNameCu"
			size="65%"
			onfocus="javascript:clearErrors();javascript:clearFieldErrors(this);"
			onclick="javascript:clearErrors();javascript:clearFieldErrors(this);"
			onblur="validateUserNameOnBlur(this);" />
		<s:label id="userNameReqCu" cssStyle="color:red;display: none;" />
		<s:textfield
			onclick="javascript:clearErrors();javascript:clearFieldErrors(this);"
			onfocus="javascript:clearErrors();javascript:clearFieldErrors(this);"
			onblur="validateNewUserFirstNameOnBlur(this);" name="frstNameCu"
			key="label.fName" id="frstNameCu" size="65%" required="true" />
		<s:label id="frstNameReqCu" cssStyle="color:red;display: none;" />
		<s:textfield
			onclick="javascript:clearErrors();javascript:clearFieldErrors(this);"
			onfocus="javascript:clearErrors();javascript:clearFieldErrors(this);"
			onblur="validateNewUserLastNameOnBlur(this);" name="lastNameCu"
			key="label.lName" id="lastNameCu" size="65%" required="true" />
		<s:label id="lstNameReqCu" cssStyle="color:red;display: none;" />
		<s:textfield
			onclick="javascript:clearErrors();javascript:clearFieldErrors(this);"
			onfocus="javascript:clearErrors();javascript:clearFieldErrors(this);"
			onblur="validateNewUserEmailOnBlur(this);" name="emailCu"
			key="label.email" id="emailCu" size="65%" required="true" />
		<s:label id="emailReqCu" cssStyle="color:red;display: none;" />
		<s:if test="#session.role=='Super_Admin'">
			<s:select id="roleCu" key="label.role" name="roleCu"
				list="#{'-1':'Select','App_User':'App_User','Administrator':'Administrator'}"
				onchange="validateDropDwnOnChange(this);"></s:select>
		</s:if>
		<s:else>
			<s:select id="roleCu" key="label.role" name="roleCu"
				list="#{'-1':'Select','App_User':'App_User'}"></s:select>
		</s:else>
		<s:label id="rolReqCu" cssStyle="color:red;display: none;" />
		<tr>
			<td>&nbsp;</td>
			<td align="right"><input type="button"
				value="<s:text name='label.createUserSub'/>"
				onclick="javascript:createUser();" />&nbsp;<input type="button"
				name="cancleCreateUser" value="<s:text name='label.close'/>"
				onclick="cancleFunct(this);" /></td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td align="right">
			<center><label id="createMsg"
				style="color: blue; display: none;"></label> <label id="createMsg2"
				style="color: red; display: none;"></label></center>
			</td>
		</tr>
	</table>
</s:form> <br style="clear: right" />
</div>
