<%@ page language="java" import="com.sohlman.webapp.netform.*, com.sohlman.netform.*, com.sohlman.netform.component.*, com.sohlman.netform.component.table.*" %>
<%@ taglib uri="http://netform.sohlman.com/taglib" prefix="nf" %>
<nf:init form="com.sohlman.webapp.netform.Table2Form" loginPage="login.jsp">
</nf:init>
<jsp:include page="header.jsp" />
<h1>2. Table Example</h1>
<h2>Purpose</h2> 
<p>Purpose of this example is to be test case, how to use Table inside Table.</p>
<p>First (1) table is table which has combobox, which is table component inside table component. Data source for this combobox is
same as s second (2) list  and by modifying data inside list naturally changed data inside combobox.</p>
<nf:form>
<table cellpadding="1" cellspacing="1" border="1" >
<tr>
	<td>(1) Table</td><td>(2) Edit - Choise List in Table</td>
</tr>
<tr>
<td valign="top" >
	<table>
		<tr>
			<th>Selected</th><th>Choise List<th>Some Text</th>
		</tr>
		<nf:table component="tableList">
		<tr>
			<td align="center">
				<nf:tableSelection/>
			</td>
			<td align="center">
				<nf:select index="1" style="width: 90;" notValidClass="notvalid">
					<nf:selectItem index="1"/>
				</nf:select>
			</td>
			<td align="center"><nf:textField index="2" style="width: 90;" notValidClass="notvalid"/></td>		
		</tr>
		</nf:table>
	</table>
	<br>
	<nf:button component="addRowButton" value="Add"/>
	<nf:button component="insertRowButton" value="Insert"/>
	<nf:button component="deleteRowButton" value="Delete"/>
	<input type="submit" name="" value="Validate" class="submit">
</td>
<td valign="top" >
	<table>
		<tr>
			<td>New Choise Text</td>
		</tr>
		<tr>
			<td><nf:textField component="textField"/></td>
		</tr>
		<tr>
			<td><nf:button component="addTextButton" value="Add"/></td>
		</tr>		
		<tr>
			<td>
				<nf:select component="tableSelect" size="10" >
					<nf:selectItem index="1"/>
				</nf:select>					
			</td>
		</tr>	
		<tr>
			<td><nf:button component="removeTextButton" value="Remove"/></td>
		</tr>
		<tr>
			<td>It is not possible to remove rows that are used on Table</td>
		</tr>							
	</table>
</td>
</tr>
</table>
</nf:form> 
<jsp:include page="footer.jsp" />