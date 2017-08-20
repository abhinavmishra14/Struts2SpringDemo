
/*
 * Function is used to call ajaxRequest 
 */ 
function ajaxRequest(actionName,dataUrl, callBack,options){
	var optionsLocal = {"showSpinner":true};
	resetTimeOut();
	optionsLocal = $.extend(optionsLocal,options);
	$.ajax({
		url: actionName,
		type: "POST",
		cache: false,
		data:dataUrl,
		dataType:"html",
		context: document.body,
		success: function(data)
		{
		if(callBack){
			if(data=="404"){
				callBack("<b>No Data Found</b>",options);}
			else
				callBack(data,options);	
		}
		},
		beforeSend:function (){
			if(optionsLocal.showSpinner==true){
				$('#ajaxImage').show();
				$('#ajaxBackground').show();
			}				  
		},
		complete:function (){
			if(optionsLocal.showSpinner==true){
				$('#ajaxImage').hide();
				$('#ajaxBackground').hide();
			}
		},
		error: function(){
			//Hide the spinner div
			$('#ajaxImage').hide();
			$('#ajaxBackground').hide();
		}
	});	

}

function triggerOne(){
	var valueAtWhichTriigered= $("#dropDownValueUserName").val();
	$('#divDropdown option[value="'+valueAtWhichTriigered+'"]').attr("selected","selected"); //automatically select the value in dropdown
	$('#divDropdown').trigger('change',valueAtWhichTriigered);//trigger next on basis of that dropdown value
}

function trim(fieldVal){
	var field = fieldVal.replace(/^\s+|\s+$/g,"");
	return field;
}

function eraseCookie(name) {
	setCookie(name, "", -1);
}

function areCookiesEnabled(){
	var r = false;
	setCookie("test","hello",1);
	if (getCookie("test") != null) {
		r = true;
		eraseCookie("test");
	}
	return r;
}

function setCheckVal(){
	if(document.getElementById("rememberMe").checked){
		document.getElementById("rememberMe").value="Y";
	}
	else{
		document.getElementById("rememberMe").value="N";
	}
}

function getCookie(c_name){
	var i,x,y,ARRcookies=document.cookie.split(";");
	for (i=0;i<ARRcookies.length;i++){
		x=ARRcookies[i].substr(0,ARRcookies[i].indexOf("="));
		y=ARRcookies[i].substr(ARRcookies[i].indexOf("=")+1);
		x=x.replace(/^\s+|\s+$/g,"");
		if (x==c_name){
			return unescape(y);
		}
	}
}

function setCookie(c_name,value,exdays){
	var exdate=new Date();
	exdate.setDate(exdate.getDate() + exdays);
	var c_value=escape(value) + ((exdays==null) ? "" : "; expires="+exdate.toUTCString());
	document.cookie=c_name + "=" + c_value;
}


/**************************************GET USER PROFILE START***********************/

function getUserProfile(){
	ajaxRequest("userProfile.action","",getUserProfileManp,{"showSpinner":true});
}

function getUserProfileManp(data,options){
	var response=data.split("|");
	$("#firstName").val(response[1]);
	$("#lastName").val(response[2]);
	$("#emailId").val(response[3]);
	var role=response[4];
	$('#updateMyProfile input[@type=radio][value="'+role+'"]').attr("checked","checked");
	$("#updateMyProfile").show();
}

function showProfile(){
	ajaxRequest("userProfile.action","",showProfileManp,{"showSpinner":true});
}

function showProfileManp(data,options){
	var response=data.split("|");
	$("#myFirstName").html(response[1]);
	$("#myLastName").html(response[2]);
	$('#myEmailId').html(response[3]);
	$('#myRole').html(response[4]);
	$("#myProfile").show();
}

/**************************************GET USER PROFILE END***********************/

/**************************************USER MANAGEMENT START***************************/

function showChangePassword(){
	$("#changePassword").show();
}

function createUsers(){
	$("#createNewUser").show();
}


$(function() {
	$("#updateMyProfile input").keypress(function(e) {
		if (e.keyCode == 13)
		{
			updateUserProfile();
		}
	});
});



function updateUserProfile(){
	var validate=false;
	var usernameField=$("#username");
	var firstNameField=$("#firstName");
	var lastNameField=$("#lastName");
	var emailField=$("#emailId");
	var username=usernameField.val();
	var firstName=trim(firstNameField.val());
	var lastName=trim(lastNameField.val());
	var email=trim(emailField.val());
	var role=$("input[@type=radio]:checked").val();
	if(validateField(firstNameField,firstName,$("#frstNameReq"))==true){
		validate=true;
	}
	if(validateField(lastNameField,lastName,$("#lstNameReq")) ==true){
		validate=true;
	}
	if(validateEmail(emailField,email,$("#emailReq"))==true){
		validate=true;
	}
	var dataUrl="authBean.userName="+username+"&authBean.firstName="+firstName+"&authBean.lastName="+lastName+"&authBean.emailId="+email+"&authBean.role="+role;
	if(validate!=true){
		ajaxRequest("updateProfile.action",dataUrl,updateUserProfileManp,{"showSpinner":true});
	}
}

function updateUserProfileManp(data,options){
	$("#updateMsg").html(data);
	$("#updateMsg").show();
}


$(function() {
	$('#changePassword input').keypress(function(e) {
		if (e.keyCode == 13)
		{
			changePassword();
		}
	});
});

function changePassword(){
	var validate=false;
	var userNameField=$("#username");
	var oldPassField=$("#oldPass");
	var newPassField=$("#newPass");
	var confPassField=$("#confirmPass");
	var username=userNameField.val();
	var oldPassword=trim(oldPassField.val());
	var newPassword=trim(newPassField.val());
	var confirmPassword=trim(confPassField.val());
	var dataUrl="";
	if(validateField(oldPassField,oldPassword,$("#oldPassReq"))==true){
		validate=true;
	}
	if(validateField(newPassField,newPassword,$("#newPassReq"))==true){
		validate=true;
	}
	if(validateField(confPassField,confirmPassword,$("#confPassReq"))==true){
		validate=true;
	}
	if(confirmPassword==newPassword && validate!=true){
		dataUrl="authBean.userName="+username+"&authBean.oldPassword="+oldPassword+"&authBean.newPassword="+newPassword;
		ajaxRequest("changePassword.action",dataUrl,changePasswordManp,{"showSpinner":true});
	}
	else{
		if(validate!=true){
			newPassField.addClass("error");
			confPassField.addClass("error");
			$("#changePassMsg2").html("Confirmed password not matching with new password!!");
			$("#changePassMsg2").show();
		}
	}
}

function changePasswordManp(data,options){
	$('#changePassword input').removeClass('error');
	$("#oldPass").val('');
	$("#newPass").val('');
	$("#confirmPass").val('');
	$("#changePassMsg2").html('');
	$("#changePassMsg2").hide();
	if(data=="Old password is incorrect!"){
		$("#oldPass").addClass("error");
		$("#oldPassReq").html(data);
		$("#oldPassReq").show();
	}
	else{
		$("#changePassMsg").html(data);
		$("#changePassMsg").show();
	}
}

$(function() {
	$('#createNewUser input').keypress(function(e) {
		if (e.keyCode == 13)
		{
			createUser();
		}
	});
});


function createUser(){
	var validate=false;
	var dataUrl="";
	var userName=$("#userNameCu");
	var firstName=$("#frstNameCu");
	var lastName=$("#lastNameCu");
	var email=$("#emailCu");
	var role=$("#roleCu");
	var userNameVal=trim(userName.val());
	var firstNameVal=trim(firstName.val());
	var lastNameVal=trim(lastName.val());
	var emailVal=trim(email.val());
	var roleVal=role.val();

	if(validateField(userName,userNameVal,$("#userNameReqCu"))==true){
		validate=true;
	}
	if(validateField(firstName,firstNameVal,$("#frstNameReqCu")) ==true){
		validate=true;
	}
	if(validateField(lastName,lastNameVal,$("#lstNameReqCu")) ==true){
		validate=true;
	}
	if(validateEmail(email,emailVal,$("#emailReqCu"))==true){
		validate=true;
	}
	if(validateDropdown(role,roleVal,$("#rolReqCu"))==true){
		validate=true;
	}

	if(validate!=true){
		dataUrl="authBean.userName="+userNameVal+"&authBean.firstName="+firstNameVal+"&authBean.lastName="+lastNameVal+"&authBean.emailId="+emailVal+"&authBean.role="+roleVal;
		ajaxRequest("createUser.action",dataUrl,createUserManp,{"showSpinner":true});
	}
}

function createUserManp(data,options){
	$('#createNewUser input').removeClass('error');
	$("#userNameCu").val('');
	$("#frstNameCu").val('');
	$("#lastNameCu").val('');
	$("#emailCu").val('');
	$("#roleCu").val('');
	if(data.indexOf("Duplicate") != -1){
		$("#createMsg2").html(data);
		$("#createMsg2").show();
	}
	else{
		$("#createMsg").html(data);
		$("#createMsg").show();
	}
}

/**************************************USER MANAGEMENT END***************************/

/************************************VALIDATION LOGIC START****************************************************/

function validateField(fld,fldVal,errorFld){
	var validate =false;
	if(trim(fldVal) == ""){
		validate=true;
		fld.addClass("error");
		errorFld.html("This field is required.");
		errorFld.show();
	}
	return validate;
}

function validateDropdown(Id,Val,errorFld){
	var validate =false;
	if(Val == "-1"){
		validate=true;
		Id.addClass("error");
		errorFld.html("This field is required.");
		errorFld.show();
	}
	else{
		validate=false;
		Id.removeClass("error");
		errorFld.html("");
		errorFld.hide();
	}
	return validate;
}

function validateEmail(emailFld,emailVal,errorFld){
	var validate =false;
	if(trim(emailVal) == ""){
		validate=true;
		emailFld.addClass("error");
		errorFld.html("This field is required!");
		errorFld.show();
	}
	else{
		var filter = /^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?$/i;
		//Testing Email Regex
		if(!filter.test(emailVal)){
			emailFld.addClass("error");
			errorFld.html("Not a valid email!");
			errorFld.show();
			validate= true;
		}else if(emailVal.length > 50){
			emailFld.addClass("error");
			errorFld.html("Email must be less than 50 characters!");
			errorFld.show();
			validate= true;
		}
	}
	return validate;
}

/******************** VALIDATION FOR UPDATE USER START*********************************/
function validateUserNameOnBlur(obj){
	validateField($(obj),$(obj).val(),$("#userNameReqCu"));
}

function validateFirstNameOnBlur(obj){
	validateField($(obj),$(obj).val(),$("#frstNameReq"));
}

function validateLastNameOnBlur(obj){
	validateField($(obj),$(obj).val(),$("#lstNameReq"));
}

function validateEmailOnBlur(obj){
	if(trim($(obj).val())=="" ){
		validateField($(obj),$(obj).val(),$("#emailReq"));
	}else{
		validateEmail($(obj),$(obj).val(),$("#emailReq"));
	}
}

/*****************************VALIDATION FOR UPDATE USER END*********************************/

/*****************************VALIDATION FOR CREATE USER START ******************************/
function validateNewUserNameOnBlur(obj){
	validateField($(obj),$(obj).val(),$("#userNameReqCu"));
}

function validateNewUserFirstNameOnBlur(obj){
	validateField($(obj),$(obj).val(),$("#frstNameReqCu"));
}

function validateNewUserLastNameOnBlur(obj){
	validateField($(obj),$(obj).val(),$("#lstNameReqCu"));
}

function validateNewUserEmailOnBlur(obj){
	if(trim($(obj).val())=="" ){
		validateField($(obj),$(obj).val(),$("#emailReqCu"));
	}else{
		validateEmail($(obj),$(obj).val(),$("#emailReqCu"));
	}
}

function validateDropDwnOnChange(obj){
	validateDropdown($(obj), $(obj).val(), $("#rolReqCu"));
}

/******************************VALIDATION FOR CREATE USER END**********************************/

/********************************VALIDATION FOR CHANGE PASSWORD START *************************/
function validateOldPassOnBlur(obj){
	validateField( $(obj),$(obj).val(),$("#oldPassReq"));
}

function validateNewPassOnBlur(obj){
	validateField($(obj),$(obj).val(),$("#newPassReq"));
}

function validateConfPassOnBlur(obj){
	validateField($(obj),$(obj).val(),$("#confPassReq"));
}
/***************VALIDATION FOR CHANGE PASSWORD END**********************************************/

/************************************VALIDATION LOGIC END******************************************************/

/************************************CANCEL AND CLAER ERRORS START*****************************/

function cancleFunct(arg){
	var mode=$(arg).attr('name');
	if(mode=="cancleChangePass"){
		$("#oldPass").val('');
		$("#newPass").val('');
		$("#confirmPass").val('');
		$("#changePassMsg").html("");
		$("#changePassMsg").hide();
		$("#changePassMsg2").html("");
		$("#changePassMsg2").hide();
		$("#changePassword").hide();
		$("#oldPass").removeClass("error");
		$("#newPass").removeClass("error");
		$("#confirmPass").removeClass("error");
		$("#oldPassReq").html("");
		$("#oldPassReq").hide();
		$("#newPassReq").html("");
		$("#newPassReq").hide();
		$("#confPassReq").html("");
		$("#confPassReq").hide();
	}
	else if(mode=="closeProfile"){
		$("#myProfile").hide();
	}
	else if(mode=='cancleCreateUser'){
		$("#userNameCu").val('');
		$("#frstNameCu").val('');
		$("#lastNameCu").val('');
		$("#emailCu").val("");
		$("#roleCu").val("");
		$("#userNameCu").removeClass("error");
		$("#frstNameCu").removeClass('error');
		$("#lastNameCu").removeClass('error');
		$("#emailCu").removeClass("error");
		$("#roleCu").removeClass("error");
		$("#userNameReqCu").html("");
		$("#userNameReqCu").hide();
		$("#frstNameReqCu").html("");
		$("#frstNameReqCu").hide();
		$("#lstNameReqCu").html("");
		$("#lstNameReqCu").hide();
		$("#emailReqCu").html("");
		$("#emailReqCu").hide();
		$("#rolReqCu").html("");
		$("#rolReqCu").hide();
		$("#createMsg").html("");
		$("#createMsg").hide();
		$("#createMsg2").html("");
		$("#createMsg2").hide();
		$("#createNewUser").hide();
	}
	else{
		$("#updateMyProfile").hide();
		$("#updateMsg").html("");
		$("#updateMsg").hide();
		$("#errorMsgUpdate").html("");
		$("#errorMsgUpdate").hide();
		$("#frstNameReq").html("");
		$("#frstNameReq").hide();
		$("#lstNameReq").html("");
		$("#lstNameReq").hide();
		$("#emailReq").html("");
		$("#emailReq").hide();
		$("#firstName").removeClass("error");
		$("#lastName").removeClass("error");
		$("#emailId").removeClass("error");

	}
}

function clearFieldErrors(obj){
	var field=$(obj).attr('id');
	if(field=="userNameCu"){
		$("#userNameCu").removeClass('error');
		$("#userNameReqCu").html("");
		$("#userNameReqCu").hide();
	}
	else if(field=="firstName"){
		$("#firstName").removeClass('error');
		$("#frstNameReq").html("");
		$("#frstNameReq").hide();
	}
	else if(field=="lastName"){
		$("#lastName").removeClass('error');
		$("#lstNameReq").html("");
		$("#lstNameReq").hide();
	}
	else if(field=="emailId"){
		$("#emailId").removeClass('error');
		$("#emailReq").html("");
		$("#emailReq").hide();
	}
	else if(field=="oldPass"){
		$("#oldPass").removeClass('error');
		$("#oldPassReq").html("");
		$("#oldPassReq").hide();
	}
	else if(field=="newPass"){
		$("#newPass").removeClass('error');
		$("#newPassReq").html("");
		$("#newPassReq").hide();
	}
	else if(field=="confirmPass"){		
		$("#confirmPass").removeClass('error');
		$("#confPassReq").html("");
		$("#confPassReq").hide();
	}
	else if(field=="userNameCu"){
		$("#userNameCu").removeClass('error');
		$("#userNameReqCu").html("");
		$("#userNameReqCu").hide();
	}
	else if(field=="frstNameCu"){
		$("#frstNameCu").removeClass('error');
		$("#frstNameReqCu").html("");
		$("#frstNameReqCu").hide();
	}
	else if(field=="lastNameCu"){
		$("#lastNameCu").removeClass('error');
		$("#lstNameReqCu").html("");
		$("#lstNameReqCu").hide();
	}
	else if(field=="emailCu"){
		$("#emailCu").removeClass('error');
		$("#emailReqCu").html("");
		$("#emailReqCu").hide();
	}
	else if(field=="roleCu"){		
		$("#roleCu").removeClass('error');
		$("#rolReqCu").html("");
		$("#rolReqCu").hide();
	}
}

function clearErrors(){
	$('.serverError').hide();
	$("#sessExpID").html("");
	$("#sessExpID").hide();
	$(".errorMessage").html("");
	$(".errorMessage").hide();
	$(".serverActionError").html("");
	$(".serverActionError").hide();
	$("#updateMsg").html("");
	$("#updateMsg").hide();
	$("#errorMsgUpdate").html("");
	$("#errorMsgUpdate").hide();
	$("#changePassMsg").html("");
	$("#changePassMsg").hide();
	$("#changePassMsg2").html("");
	$("#changePassMsg2").hide();
	$("#createMsg").html("");
	$("#createMsg").hide();
	$("#createMsg2").html("");
	$("#createMsg2").hide();
}
/************************************CANCEL AND CLAER ERRORS END*****************************/