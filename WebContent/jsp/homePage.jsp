<%@taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>
<sx:head />
<div id="userList" class="userMgmt">
<center><SPAN><b
	style="font-family: sans-serif; color: navy">Users</b></SPAN></center>
<br />
<s:form>
	<table align="center" bordercolor="#667C26" border="1">
		<sx:autocompleter id="userNameVal" showDownArrow="true"
			autoComplete="true" size="5" list="userList" name="userList"
			value="Enter names" label="User names" searchType="startstring"
			dropdownHeight="180" dropdownWidth="200"/>
		 <sx:datetimepicker displayFormat="dd/MM/yyyy" label="Select date"/>
	</table>
</s:form>
<br style="clear: right" />
</div>
<div id="userListMap" class="userMgmt">
<div style="float: left;">
<table bordercolor="#667C26">
	<thead>
		<tr>
			<td><b style="font-family: sans-serif; color: navy">Map Test</b></td>
		</tr>
		<tr>
			<th>Index</th>
			<th>key</th>
			<th>value</th>
		</tr>
	</thead>
	<tbody>
		<s:iterator value="userMap" status="stat">
		<s:if test="#stat.even==true" >
		   <tr style="background: #CCCCCC">
				<th><s:property value="#stat.index" /></th>
				<td><s:property value="key" /></td>
				<td><s:property value="value" /></td>
			</tr>
		</s:if>
		<s:else>
		  	<tr>
				<th><s:property value="#stat.index" /></th>
				<td><s:property value="key" /></td>
				<td><s:property value="value" /></td>
			</tr>
		</s:else>
		</s:iterator>
	</tbody>
</table>
</div>
<div>
<table bordercolor="#cccc">
	<thead>
		<tr>
			<td><SPAN><b style="font-family: sans-serif; color: navy">Map
			Test</b>&nbsp;&nbsp;[<a href="<s:url action='sortOnKey.action'/>">Sort
			on key</a>&nbsp;&nbsp;<a href="<s:url action='sortOnVal.action'/>">Sort
			on value</a> ]</SPAN></td>
		</tr>
		<tr>
			<th>Index</th>
			<th>key</th>
			<th>value</th>
		</tr>
	</thead>
	<tbody>
		<s:iterator value="m" status="stat">
			<s:if test="#stat.even==true" >
		   <tr style="background: #CCCEEE">
				<th><s:property value="#stat.index" /></th>
				<td><s:property value="key" /></td>
				<td><s:property value="value" /></td>
			</tr>
		</s:if>
		<s:else>
		  	<tr>
				<th><s:property value="#stat.index" /></th>
				<td><s:property value="key" /></td>
				<td><s:property value="value" /></td>
			</tr>
		</s:else>
		</s:iterator>
	</tbody>
</table>
</div>
<br style="clear: right" />
</div>
