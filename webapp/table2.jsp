<%@ page import="sun.security.krb5.internal.n" %>
<%@ page language="java" import="com.sohlman.webapp.netform.*, com.sohlman.netform.*" %>
<% 
	Table2Form form = null; 
	ServletContext l_ServletContext = getServletContext();	
	try 
	{
		form = (Table2Form)FormManager.getForm(request, response, l_ServletContext, Table2Form.class, "login.jsp");	
		form.execute(); 
%>
<jsp:include page="header.jsp" />
<h1>1. Table Example</h1>
<p>This is using <a href="http://dataset.sohlman.com">DataSet</a> as internal datastore</p>
<form method="POST" action="table2.jsp">
<table>
	<tr>
		<td>Selected</td><td>Choise<td>First name</td><td>Last name</td><td>Birthdate</td>
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
				<option value="<%=l_Table.getRowId(li_s) %>"<% if(l_Table.isRowSelected(li_s)){ %> checked <% } %>><%=l_Table.getText(li_s, 1) %></option>
				<% } %>
			</select>
		</td>		
		<td>
			<input type="text" name="<%=form.tableList.getComponentAt(li_y,2).getResponseName() %>" value="<%=((TextField)form.tableList.getComponentAt(li_y,2)).getText() %>">
		</td>		
	</tr><%
			} 
	%></table>
<input type="submit" name="<%=form.addRowButton.getResponseName()%>" value="Add">
<input type="submit" name="<%=form.deleteRowButton.getResponseName()%>" value="Delete">
<input type="submit" name="" value="Validate">
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
	<body>Error</body>
</html>		
		<%
	} 
%>