<%@ page language="java" import="com.sohlman.webapp.netform.*, com.sohlman.netform.*" %>
<% 
	Table1Form form = null; 
	ServletContext l_ServletContext = getServletContext();	
	try 
	{
		form = (Table1Form)FormManager.getForm(request, response, l_ServletContext, Table1Form.class, "login.jsp");
		form.execute(); 
%>
<jsp:include page="header.jsp" />
<h1>1. Table Example</h1>
<p>This is using <a href="http://dataset.sohlman.com">DataSet</a> as internal datastore</p>
<form method="POST" action="table1.jsp">
<table>
	<tr>
		<td>Selected</td><td>Number<td>First name</td><td>Last name</td><td>Birthdate</td>
	</tr><%
			for(int li_y = 1 ; li_y <= form.table.getDisplayRowCount()  ; li_y++)
			{ 
			if(form.table.isRowSelected(li_y))
			{
			%>
	<tr>
		<td>
			<input type="checkbox" name="<%=form.table.getResponseName() %>"  value="<%=form.table.getRowId(li_y) %>" checked>
		</td><%
			}  
			else
			{
			%><tr>
			<td> 
			<input type="checkbox" name="<%=form.table.getResponseName() %>" value="<%=form.table.getRowId(li_y) %>">
		</td><%
			}
		%>
		<td>
			<input type="text" name="<%=form.table.getComponentAt(li_y,1).getResponseName() %>" value="<%=((IntegerField)form.table.getComponentAt(li_y,1)).getText() %>">
		</td>		
		<td>
			<input type="text" name="<%=form.table.getComponentAt(li_y,2).getResponseName() %>" value="<%=((TextField)form.table.getComponentAt(li_y,2)).getText() %>">
		</td>
		<td>
			<input type="text" name="<%=form.table.getComponentAt(li_y,3).getResponseName() %>" value="<%=((TextField)form.table.getComponentAt(li_y,3)).getText() %>">
		</td>
		<td>
			<input type="text" name="<%=form.table.getComponentAt(li_y,4).getResponseName() %>" value="<%=((TimestampField)form.table.getComponentAt(li_y,4)).getText() %>">
		</td>		
	</tr><%
			} 
	%></table>
<input type="submit" name="<%=form.addRowButton.getResponseName()%>" value="Add">
<input type="submit" name="<%=form.insertRowButton.getResponseName()%>" value="Insert">
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
	
	} 
%>