<%@ page language="java" import="com.sohlman.webapp.netform.*, com.sohlman.netform.*, com.sohlman.netform.component.*, com.sohlman.netform.component.table.*" %>
<% 
	Table2Form form = null; 
	ServletContext l_ServletContext = getServletContext();	
	try 
	{
		form = (Table2Form)FormManager.getForm(request, response, l_ServletContext, Table2Form.class, "login.jsp");	
		form.execute(); 
%>
<jsp:include page="header.jsp" />
<h1>2. Table Example</h1>
<h2>Purpose</h2> 
<p>Purpose of this example is to be test case, how to use Table inside Table.</p>
<p>First (1) table is table which has combobox, which is table component inside table component. Data source for this combobox is
same as s second (2) list  and by modifying data inside list naturally changed data inside combobox.</p>


<form method="POST" action="<%=form.getPostAction() %>">
<table cellpadding="1" cellspacing="1" border="1" >
<tr>
	<td>(1) Table</td><td>(2) Edit - Choise List in Table</td>
</tr>
<tr>
<td valign="top" >
<table>
	<tr>
		<th>Selected</th><th>Choise List<th>Some Text</th>
	</tr><%
			for(int li_y = 1 ; li_y <= form.tableList.getDisplayRowCount()  ; li_y++)
			{ 
			if(form.tableList.isRowSelected(li_y))
			{
			%>
	<tr>
		<td>
			<input type="checkbox" name="<%=form.tableList.getResponseName() %>"  value="<%=form.tableList.getRowId(li_y) %>" checked>
		</td><%
			}  
			else
			{
			%><tr>
			<td> 
			<input type="checkbox" name="<%=form.tableList.getResponseName() %>" value="<%=form.tableList.getRowId(li_y) %>">
		</td><%
			}
		%>
		<td>
			<select name="<%=form.tableList.getComponentAt(li_y,1).getResponseName() %>"><%
				Table l_Table = (Table)form.tableList.getComponentAt(li_y,1);
				for(int li_s = 1; li_s <= l_Table.getDisplayRowCount() ; li_s++)
				{
				
			%>
				<option value="<%=l_Table.getRowId(li_s) %>"<% if(l_Table.isRowSelected(li_s)){ %> selected <% } %>><%=l_Table.getText(li_s, 1) %></option>
				<% } %>
			</select>
		</td>		
		<td>
			<input type="text" name="<%=form.tableList.getComponentAt(li_y,2).getResponseName() %>" value="<%=((TextField)form.tableList.getComponentAt(li_y,2)).getText() %>">
		</td>		
	</tr><%
			} 
	%></table>

<input type="submit" name="<%=form.addRowButton.getResponseName()%>" value="Add" class="submit">
<input type="submit" name="<%=form.insertRowButton.getResponseName()%>" value="Insert" class="submit">
<input type="submit" name="<%=form.deleteRowButton.getResponseName()%>" value="Delete" class="submit">
<input type="submit" name="" value="Validate">
</td>
<td valign="top" >
	<table>
		<tr>
			<td>New Choise Text</td>
		</tr>
		<tr>
			<td><input type="text" name="<%=form.textField.getResponseName() %>" value="<%=form.textField.getText() %>"></td>
		</tr>
		<tr>
			<td><input type="submit" name="<%=form.addTextButton.getResponseName() %>" value="Add" class="submit"> <input type="submit" name="<%=form.insertTextButton.getResponseName() %>" value="Insert" class="submit">
		</tr>		
		<tr>
			<td>
			<select name="<%=form.tableSelect.getResponseName() %>" size="10" ><%
				for(int li_s = 1; li_s <= form.tableSelect.getDisplayRowCount() ; li_s++)
				{
				
			%>
				<option value="<%=form.tableSelect.getRowId(li_s) %>"<% if(form.tableSelect.isRowSelected(li_s)){ %> selected <% } %>><%=form.tableSelect.getText(li_s, 1) %></option>
				<% } %>
			</select>			
			</td>
		</tr>	
		<tr>
			<td><input type="submit" name="<%=form.removeTextButton.getResponseName() %>" value="Remove" class="submit"></td>
		</tr>
		<tr>
			<td>It is not possible to remove rows that are used on Table</td>
		</tr>							
	</table>
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
		netFormException.printStackTrace();
		%>
<html>
	<head><title>Error</title></head>
	<body>
		Error see System.out
	</body>
</html>		
		<%
	} 
%>