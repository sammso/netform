<%@ page language="java" import="com.sohlman.webapp.netform.*, com.sohlman.netform.*" %>
<%
	FieldForm form = null;
	try
	{
		form = (FieldForm)FormManager.getForm(request, response, getServletContext(),FieldForm.class, "login.jsp");
		form.integerField.setFormat("00000");
		form.timestampField.setTimeFormat("HH:mm");
		form.timestampField.setDateFormat("yyyy-MM-dd");
		form.execute();
%>
<jsp:include page="header.jsp" />
<h1>Field Example <% if(!form.isValid()) {%>(form is not valid)<% } %></h1>
<p>This case demonstrates NetForm different field types and validation system.
If one field is not valid then whole form is not valid. 
Not valid fields are shown as <b class="pink" >Pink</b></p>
<p>Only valid field can inserted to Table Component</p>
<form method="POST" action="<%=form.getPostAction() %>">
<table border="0" width="580">
	<tr>
		<td class="text">TextField<br>component<br><i>Valid when contains 1 - 20 characters</i></td>
		<td colspan="2"><input  style="width: 215px;" type="text" name="<%=form.textField.getResponseName() %>" value="<%=form.textField.getText() %>" <%=form.textField.getStringIfIsNotValid("class=\"notvalid\"")%>></td>
	</tr>
	<tr>  
		<td class="text">IntegerField component<br></td>
		<td><input style="width: 215px;" type="text" name="<%=form.integerField.getResponseName() %>" value="<%=form.integerField.getText() %>" <%=form.integerField.getStringIfIsNotValid("class=\"notvalid\"")%>></td>
		<td><input style="width: 30px;" name="<%=form.increaseIntButton.getResponseName() %>" type="submit" value="+" class="submit">
        <input style="width: 30px;" name="<%=form.decreaseIntButton.getResponseName() %>" type="submit" value="-" class="submit"></td>
	</tr>
	<tr>  
		<td class="text">FloatField component<br></td>
		<td><input style="width: 215px;" type="text" name="<%=form.floatField.getResponseName() %>" value="<%=form.floatField.getText() %>" <%=form.floatField.getStringIfIsNotValid("class=\"notvalid\"")%>></td>
		<td></td>		
	</tr>	
	<tr>  
		<td class="text">DoubleField component<br></td>
		<td><input style="width: 215px;" type="text" name="<%=form.doubleField.getResponseName() %>" value="<%=form.doubleField.getText() %>  <%=form.doubleField.getStringIfIsNotValid("class=\"notvalid\"")%>"></td>
		<td></td>		
	</tr>
	<tr>  
		<td class="text">LongField component<br><i>( value has to be 1 - 20 )</i></td>
		<td><input type="text" name="<%=form.longField.getResponseName() %>" value="<%=form.longField.getText() %>" <%=form.longField.getStringIfIsNotValid("class=\"notvalid\"")%>></td>
		<td></td>		
	</tr>	
	<tr>  
		<td rowspan="2" valign="top"  class="text">PasswordField component<br><i>Password fields has to be egual to be valid.</i></td>
		<td><input style="width: 215px;" type="password" name="<%=form.firstPasswordField.getResponseName() %>" value="<%=form.firstPasswordField.getText() %>" <%=form.firstPasswordField.getStringIfIsNotValid("class=\"notvalid\"")%>></td>
		<td></td>		
	</tr>	
	<tr>  
		<td><input style="width: 215px;" type="password" name="<%=form.secondPasswordField.getResponseName() %>" value="<%=form.secondPasswordField.getText() %>" <%=form.secondPasswordField.getStringIfIsNotValid("class=\"notvalid\"")%>></td>
		<td></td>		
	</tr>	

	<tr>
		<td class="text">Timestamp field<br><i>[<%=form.timestampField.getDateFormat()%>][<%=form.timestampField.getTimeFormat()%>]</i></td>
		<td>
			<input style="width: 100px;" type="text" name="<%=form.timestampField.getResponseNameForDate() %>" value="<%=form.timestampField.getDateText() %>" <%=form.timestampField.getStringIfIsNotValid("class=\"notvalid\"")%>>
			<input style="width: 100px;" type="text" name="<%=form.timestampField.getResponseNameForTime() %>" value="<%=form.timestampField.getTimeText() %>" <%=form.timestampField.getStringIfIsNotValid("class=\"notvalid\"")%>>
		</td>
		<td align="middle" >
			<table cellpadding="0" cellspacing="0" border="0" widht="104">
				<tr>
					<td width="40" class="text">Day:</td>
					<td width="64"><input style="width: 30px;" name="<%=form.yesterdayButton.getResponseName() %>" type="submit" value="-" class="submit"><input style="width: 30px;" name="<%=form.tomorrowButton.getResponseName() %>" type="submit" value="+" class="submit"></td>
				</tr>
				<tr>
					<td class="text">Month:</td>
					<td><input style="width: 30px;" name="<%=form.previousMonthButton.getResponseName() %>" type="submit" value="-" class="submit"><input style="width: 30px;" name="<%=form.nextMonthButton.getResponseName() %>" type="submit" value="+" class="submit"></td>
				</tr>
			</table>
		</td>
	</tr>	
	<tr>
		<td class="text">Table Component</td>
		<td colspan="2" valign="top" rowspan="2">	
			<select style="width: 320px;" name="<%=form.table.getResponseName() %>" size="18" <%=form.table.getStringIfIsNotValid("class=\"notvalid\"")%>><%
	
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
		</select>
		</td>		
	</tr>		
	<tr>
		<td align="center">
			<input style="width: 130px;" type="submit" name="<%=form.doubleToTableButton.getResponseName() %>" value="Double value ->" class="submit"><br>						
			<input style="width: 130px;" type="submit" name="<%=form.floatToTableButton.getResponseName() %>" value="Float value ->" class="submit"><br>			
			<input style="width: 130px;" type="submit" name="<%=form.integerToTableButton.getResponseName() %>" value="IntegerField value ->" class="submit"><br>
			<input style="width: 130px;" type="submit" name="<%=form.longToTableButton.getResponseName() %>" value="LongField value ->" class="submit"><br>			
			<input style="width: 130px;" type="submit" name="<%=form.passwordToTableButton.getResponseName() %>" value="Password value ->" class="submit"><br>						
			<input style="width: 130px;" type="submit" name="<%=form.textToTableButton.getResponseName() %>" value="Textfield value ->" class="submit"><br>			
			<input style="width: 130px;" type="submit" name="<%=form.timestampToTableButton.getResponseName() %>" value="Timestamp value ->" class="submit"><br><br><br>
			<input style="width: 130px;" type="submit" name="<%=form.deleteSelectedFromTableButton.getResponseName() %>" value="Remove" class="submit"><br><br>
			<input style="width: 130px;" type="submit" name="" value="Validate" class="submit">
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


