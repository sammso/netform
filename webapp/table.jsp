<%@ page language="java" import="com.sohlman.webapp.netform.*, com.sohlman.netform.*" %>
<%
	TableForm form = null;
	try
	{
		form = (TableForm)Form.getForm(request, TableForm.class);
		form.execute(); 
	}
	catch(NetFormException netFormException)
	{
		// Something is really wrong
	 
	}
%>
<jsp:include page="header.jsp" />
<h1>Table Example</h1>
<form method="POST" action="table.jsp">
<table>
	<tr>
		<td>Selected</td><td>Column 1</td><td>Column 2</td>
	</tr><%
	DisplayRow[] l_DisplayRows = form.getTable().getDisplayRows();
	
	for(int li_y = 0 ; li_y < l_DisplayRows.length ; li_y++)
	{
	TextField l_Textfield_First_Name = (TextField)l_DisplayRows[li_y].getComponent(1);
	TextField l_Textfield_Last_Name = (TextField)l_DisplayRows[li_y].getComponent(2);
	
	if(l_DisplayRows[li_y].isSelected())
	{
	%><tr>
		<td>
			<input type="checkbox" name="<%=form.getTable().getResponseName() %>"  value="<%=l_DisplayRows[li_y].getRowId() %>" checked>
		</td>
		<td>
			<input type="text" name="<%=l_Textfield_First_Name.getResponseName() %>" value="<%=l_Textfield_First_Name.getText() %>">
		</td>
		<td>
			<input type="text" name="<%=l_Textfield_Last_Name.getResponseName() %>" value="<%=l_Textfield_Last_Name.getText() %>">
		</td>
	</tr><%
	} 
	else
	{
	%><tr>
		<td>
			<input type="checkbox" name="<%=form.getTable().getResponseName() %>" value="<%=l_DisplayRows[li_y].getRowId() %>">
		</td>
		<td>
			<input type="text" name="<%=l_Textfield_First_Name.getResponseName() %>" value="<%=l_Textfield_First_Name.getText() %>">
		</td>
		<td>
			<input type="text" name="<%=l_Textfield_Last_Name.getResponseName() %>" value="<%=l_Textfield_Last_Name.getText() %>">
		</td>
	</tr><%
	}
	}
	%></table>
<input type="submit" name="<%=form.getButtonAddRow().getResponseName()%>" value="Add">
<input type="submit" name="<%=form.getButtonDeleteRow().getResponseName()%>" value="Delete">
<input type="submit" name="" value="Validate">
</form>
<jsp:include page="footer.jsp" />

