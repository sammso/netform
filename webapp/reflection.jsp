<%@ page language="java" %>
<%@ taglib uri="http://netform.sohlman.com/taglib" prefix="nf" %>
<nf:init form="com.sohlman.webapp.netform.reflection.PersonForm" loginpage="login.jsp">
</nf:init>
<jsp:include page="header.jsp" />
<h1>This example shows how to map Javabeans / POJOs to Form.</h1>
<p>This contains mapping of Person object, which contains Collection of PersonProperty objects.
</p>
<nf:form>
	<table>
		<tr>
			<td>First name : </td>
			<td><nf:textfield component="firstNameTextField"/></td>
		</tr>
		<tr>
			<td>Last name :</td>
			<td><nf:textfield component="lastNameTextField"/></td>
		</tr>
	</table>
	<table>
		<tr>
			<th colspan="3">Property<th>
		</tr>	
		<tr>
			<th>&nbsp;</th><th>Name</th><th>Value<th>
		</tr>
		<nf:table component="propertiesTable">
		<tr>		
			<td><nf:tableselection/></td>
			<td><nf:textfield index="1"/></td>
			<td><nf:textfield index="2"/></td>
		</tr>			
		</nf:table>
	</table>
	<nf:button component="addButton" value="Add"/>
	<nf:button component="removeButton" value="Remove"/>
</nf:form>
<jsp:include page="footer.jsp" />