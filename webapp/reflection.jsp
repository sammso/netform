<%@ page language="java" %>
<%@ taglib uri="http://netform.sohlman.com/taglib" prefix="nf" %>
<nf:init form="com.sohlman.webapp.netform.reflection.PersonForm" loginPage="login.jsp"/>
<jsp:include page="header.jsp" />
<h1>This example shows how to map Javabeans / POJOs to Form.</h1>
<p>This contains mapping of Person object, which contains Collection of PersonProperty objects.
</p>
<nf:form>
	<table>
		<tr>
			<td>First name : </td>
			<td><nf:textField component="firstNameTextField" notValidClass="notvalid"/></td>
		</tr>
		<tr>
			<td>Last name :</td>
			<td><nf:textField component="lastNameTextField"  notValidClass="notvalid"/></td>
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
			<td><nf:tableSelection/></td>
			<td><nf:textField index="1" notValidClass="notvalid"/></td>
			<td><nf:textField index="2" notValidClass="notvalid"/></td>
		</tr>			
		</nf:table> 
	</table>
	<nf:button component="addButton" value="Add" styleClass="submit"/>
	<nf:button component="insertButton" value="Insert" styleClass="submit"/>	
	<nf:button component="removeButton" value="Remove" styleClass="submit"/>
	<input type="submit" value="Validate">
</nf:form>
<jsp:include page="footer.jsp" />