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
<h1>Field Example <% if(!form.isValid()) {%>(form is not valid)<% } %></h1>
<p>This case demonstrates NetForm different field types and validation system. 
If one field is not valid then whole form is not valid. 
Not valid fields are shown as <b class="pink" >Pink</b></p>
<p>Only valid field can inserted to Table Component</p>
<form method="POST" action="<%=form.getRequestURI() %>">
<table>
	<tr>
		<td class="text">TextField<br>component<br><i>Valid when contains 1 - 20 characters</i></td>
		<td colspan="2"><input type="text" name="<%=form.textField.getResponseName() %>" value="<%=form.textField.getText() %>" <%=form.textField.getStringIfIsNotValid("class=\"notvalid\"")%>></td>
	</tr>
	<tr>  
		<td class="text">IntegerField component<br></td>
		<td><input type="text" name="<%=form.integerField.getResponseName() %>" value="<%=form.integerField.getText() %>" <%=form.integerField.getStringIfIsNotValid("class=\"notvalid\"")%>></td>
		<td><input name="<%=form.increaseIntButton.getResponseName() %>" type="submit" value="+" class="submit"> - <input name="<%=form.decreaseIntButton.getResponseName() %>" type="submit" value="-" class="submit"></td>
	</tr>
	<tr>  
		<td class="text">FloatField component<br></td>
		<td><input type="text" name="<%=form.floatField.getResponseName() %>" value="<%=form.floatField.getText() %>" <%=form.floatField.getStringIfIsNotValid("class=\"notvalid\"")%>></td>
		<td></td>		
	</tr>	
	<tr>  
		<td class="text">DoubleField component<br></td>
		<td><input type="text" name="<%=form.doubleField.getResponseName() %>" value="<%=form.doubleField.getText() %>  <%=form.doubleField.getStringIfIsNotValid("class=\"notvalid\"")%>"></td>
		<td></td>		
	</tr>
	<tr>  
		<td class="text">LongField component<br><i>( value has to be 1 - 20 )</i></td>
		<td><input type="text" name="<%=form.longField.getResponseName() %>" value="<%=form.longField.getText() %>" <%=form.longField.getStringIfIsNotValid("class=\"notvalid\"")%>></td>
		<td></td>		
	</tr>	
	<tr>
		<td class="text">Timestamp field<br><i>(yyyy-mm-dd)</i></td>
		<td><input type="text" name="<%=form.timestampField.getResponseName() %>" value="<%=form.timestampField.getText() %> <%=form.timestampField.getStringIfIsNotValid("class=\"notvalid\"")%>"></td>
		<td align="middle" >
			<input name="<%=form.yesterdayButton.getResponseName() %>" type="submit" value="Prev.Day" class="submit"> - <input name="<%=form.tomorrowButton.getResponseName() %>" type="submit" value="Next Day" class="submit"><br>
			<input name="<%=form.previousMonthButton.getResponseName() %>" type="submit" value="Prev.Month" class="submit"> - <input name="<%=form.nextMonthButton.getResponseName() %>" type="submit" value="Next Month" class="submit">
		</td>
	</tr>	
	<tr>
		<td class="text">Table Component</td>
		<td colspan="2" valign="top" rowspan="2">	
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
		<td>
			<input type="submit" name="<%=form.doubleToTableButton.getResponseName() %>" value="Double value ->" class="submit"><br>						
			<input type="submit" name="<%=form.floatToTableButton.getResponseName() %>" value="Float value ->" class="submit"><br>			
			<input type="submit" name="<%=form.integerToTableButton.getResponseName() %>" value="IntegerField value ->" class="submit"><br>
			<input type="submit" name="<%=form.longToTableButton.getResponseName() %>" value="LongField value ->" class="submit"><br>			
			<input type="submit" name="<%=form.textToTableButton.getResponseName() %>" value="Textfield value ->" class="submit"><br>			
			<input type="submit" name="<%=form.timestampToTableButton.getResponseName() %>" value="Timestamp value ->" class="submit"><br><br><br>
			<input type="submit" name="<%=form.deleteSelectedFromTableButton.getResponseName() %>" value="Remove" class="submit"><br><br>
			<input type="submit" name="" value="Validate" class="submit">
		</td> 
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


