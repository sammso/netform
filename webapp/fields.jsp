<%@ page language="java" import="com.sohlman.webapp.netform.*, com.sohlman.netform.*" %>
<%
	FieldForm form = null;
	try
	{
		form = (FieldForm)Form.getForm(request, FieldForm.class);
		form.numberField.setFormat("00000");
		form.timestampField.setFormat("yyyy-MM-dd");
		form.execute(); 
	}
	catch(NetFormException netFormException)
	{
		// Something is really wrong
	 
	}
%>
<jsp:include page="header.jsp" />
<h1>Field Example</h1>
<form method="POST" action="fields.jsp">
<table>
	<tr>
		<td class="text">TextField<br>component<br><i>Write anything</i></td>
		<td colspan="2"><input type="text" name="<%=form.textField.getResponseName() %>" value="<%=form.textField.getText() %>"></td>
	</tr>
	<tr>
		<td class="text">IntegerField component</td>
		<td><input type="text" name="<%=form.numberField.getResponseName() %>" value="<%=form.numberField.getText() %>"></td>
		<td><input name="<%=form.increaseNumberButton.getResponseName() %>" type="submit" value="+"> - <input name="<%=form.decreaseNumberButton.getResponseName() %>" type="submit" value="-"></td>
	</tr>	
	<tr>
		<td class="text">Timestamp field<br><i>(yyyy-mm-dd)</i></td>
		<td><input type="text" name="<%=form.timestampField.getResponseName() %>" value="<%=form.timestampField.getText() %>"></td>
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
			<input type="submit" name="<%=form.textToTableButton.getResponseName() %>" value="Textfield value ->"><br>
			<input type="submit" name="<%=form.numberToTableButton.getResponseName() %>" value="Numberfield value ->"><br>
			<input type="submit" name="<%=form.timestampToTableButton.getResponseName() %>" value="Timestamp value ->"><br><br><br>
			<input type="submit" name="<%=form.deleteSelectedFromTableButton.getResponseName() %>" value="Remove">
		</td> 
		<td colspan="2">	
			<select name="<%=form.table.getResponseName() %>" size="10" ><%
	DisplayRow[] l_DisplayRows = form.table.getDisplayRows();
	
	for(int li_y = 0 ; li_y < l_DisplayRows.length ; li_y++)
	{	
		if(l_DisplayRows[li_y].isSelected())
		{
		%>
				<option value="<%=l_DisplayRows[li_y].getRowId() %>" selected ><%=l_DisplayRows[li_y].getString(1) %></option><%
		} 
		else
		{
		%>
			<option value="<%=l_DisplayRows[li_y].getRowId() %>"><%=l_DisplayRows[li_y].getString(1) %></option><%
		}
	}
	%>
		</td>
	</tr>
</table>
<input type="submit" name="" value="Validate">
</form>
<jsp:include page="footer.jsp" />

