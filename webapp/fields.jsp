<%@ page language="java" import="com.sohlman.webapp.netform.*, com.sohlman.netform.*" %>
<%
	FieldForm form = null;
	try
	{
		form = (FieldForm)FormManager.getForm(request, response, getServletContext(),FieldForm.class, "login.jsp");
		form.integerField.setFormat("00000");
		form.timestampField.setFormat("yyyy-MM-dd");
		form.execute();
%>
<jsp:include page="header.jsp" />
<h1>Field Example <% if(!form.isValid())
{
	%>(form is not valid)<%
}
%></h1> 
<form method="POST" action="<%=form.getRequestURI() %>">
<table>
	<tr>
		<td class="text">TextField<br>component<br><i>Valid when contains 1 - 20 charahters</i></td>
		<td colspan="2"><% if(!form.textField.isValid()){	%>*<% } %> <input type="text" name="<%=form.textField.getResponseName() %>" value="<%=form.textField.getText() %>"></td>
	</tr>
	<tr>  
		<td class="text">IntegerField component<br></td>
		<td><% if(!form.integerField.isValid()){	%>*<% } %><input type="text" name="<%=form.integerField.getResponseName() %>" value="<%=form.integerField.getText() %>"></td>
		<td><input name="<%=form.increaseIntButton.getResponseName() %>" type="submit" value="+"> - <input name="<%=form.decreaseIntButton.getResponseName() %>" type="submit" value="-"></td>
	</tr>
	<tr>  
		<td class="text">FloatField component<br></td>
		<td colspan="2"><% if(!form.floatField.isValid()){	%>*<% } %><input type="text" name="<%=form.floatField.getResponseName() %>" value="<%=form.floatField.getText() %>"></td>
	</tr>	
	<tr>  
		<td class="text">DoubleField component<br></td>
		<td colspan="2"><% if(!form.doubleField.isValid()){	%>*<% } %><input type="text" name="<%=form.doubleField.getResponseName() %>" value="<%=form.doubleField.getText() %>"></td>
	</tr>
	<tr>  
		<td class="text">LongField component<br><i>( value has to be 1 - 20 )</i></td>
		<td colspan="2"><% if(!form.longField.isValid()){	%>*<% } %><input type="text" name="<%=form.longField.getResponseName() %>" value="<%=form.longField.getText() %>"></td>
	</tr>	
	<tr>
		<td class="text">Timestamp field<br><i>(yyyy-mm-dd)</i></td>
		<td><% if(!form.timestampField.isValid()){	%>*<% } %><input type="text" name="<%=form.timestampField.getResponseName() %>" value="<%=form.timestampField.getText() %>"></td>
		<td>
			<input name="<%=form.yesterdayButton.getResponseName() %>" type="submit" value="Previous Day"> - <input name="<%=form.tomorrowButton.getResponseName() %>" type="submit" value="Next day"><br>
			<input name="<%=form.previousMonthButton.getResponseName() %>" type="submit" value="Previous Month"> - <input name="<%=form.nextMonthButton.getResponseName() %>" type="submit" value="Next Month">
		</td>
	</tr>	
	<tr>
		<td class="text" colspan="3">Table Component</td>
	</tr>		
	<tr>
		<td>
			<input type="submit" name="<%=form.doubleToTableButton.getResponseName() %>" value="Double value ->"><br>						
			<input type="submit" name="<%=form.floatToTableButton.getResponseName() %>" value="Float value ->"><br>			
			<input type="submit" name="<%=form.integerToTableButton.getResponseName() %>" value="IntegerField value ->"><br>
			<input type="submit" name="<%=form.longToTableButton.getResponseName() %>" value="LongField value ->"><br>			
			<input type="submit" name="<%=form.textToTableButton.getResponseName() %>" value="Textfield value ->"><br>			
			<input type="submit" name="<%=form.timestampToTableButton.getResponseName() %>" value="Timestamp value ->"><br><br><br>
			<input type="submit" name="<%=form.deleteSelectedFromTableButton.getResponseName() %>" value="Remove">
		</td> 
		<td colspan="2" valign="top" >	
			<select name="<%=form.table.getResponseName() %>" size="10" ><%
	
	for(int li_y = 1 ; li_y <= form.table.getDisplayRowCount() ; li_y++)
	{	
		if(form.table.isSelected(li_y))
		{
		%>
				<option value="<%=form.table.getRowId(li_y) %>" selected ><%=form.table.getText(li_y, 1) %></option><%
		} 
		else
		{
		%>
			<option value="<%=form.table.getRowId(li_y) %>"><%=form.table.getText(li_y, 1) %></option><%
		}
	}
	%>
		</td>
	</tr>
	<tr>
		<td class="text" colspan="3"><input type="submit" name="" value="Validate"></td>
	</tr>		
</table>
</form>
<jsp:include page="footer.jsp" /><%
	}
	catch(DoRedirectException doRedirectException)
	{
		response.sendRedirect(doRedirectException.getPage());
	}
	catch(NetFormException netFormException)
	{

	} 
%>


