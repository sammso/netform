<%@ page language="java" import="com.sohlman.webapp.netform.*, com.sohlman.netform.*, com.sohlman.netform.component.*" %>
<% 
	Table1Form form = null; 
	ServletContext l_ServletContext = getServletContext();	
	try 
	{
		form = (Table1Form)FormManager.getForm(request, response, l_ServletContext, Table1Form.class, "login.jsp");
		form.execute(); 
%> 
<jsp:include page="header.jsp" />
<h1>1. Table Example <% if(!form.isValid()) {%>(form is not valid)<% } %></h1>
<p>This is using <a href="http://dataset.sohlman.com">DataSet</a> as internal datastore</p>
<form method="POST" action="<%=form.getRequestURI() %>">
<table>
	<tr>
		<td>Selected</td><td>Number<td>First name</td><td>Last name</td><td>Birthdate</td>
	</tr><% 
			for(int li_y = 1 ; li_y <= form.table.getDisplayRowCount()  ; li_y++)
			{ %>
	<tr>
		<td>
			<input type="checkbox" name="<%=form.table.getResponseName() %>"  value="<%=form.table.getRowId(li_y) %>"<%=form.table.getStringIfRowSelected(li_y, " checked") %>>
		</td>
		<td><input type="text" name="<%=form.table.getComponentAt(li_y,1).getResponseName() %>" value="<%=form.table.getTextFieldAt(li_y,1).getText() %>" <%=form.table.getComponentAt(li_y, 1).getStringIfIsNotValid("class=\"notvalid\"") %>></td>		
		<td><input type="text" name="<%=form.table.getComponentAt(li_y,2).getResponseName() %>" value="<%=form.table.getTextFieldAt(li_y,2).getText() %>" <%=form.table.getComponentAt(li_y, 2).getStringIfIsNotValid("class=\"notvalid\"") %>></td>
		<td><input type="text" name="<%=form.table.getComponentAt(li_y,3).getResponseName() %>" value="<%=form.table.getTextFieldAt(li_y,3).getText() %>" <%=form.table.getComponentAt(li_y, 3).getStringIfIsNotValid("class=\"notvalid\"") %>></td>
		<td><input type="text" name="<%=form.table.getComponentAt(li_y,4).getResponseName() %>" value="<%=form.table.getTextFieldAt(li_y,4).getText() %>" <%=form.table.getComponentAt(li_y, 4).getStringIfIsNotValid("class=\"notvalid\"") %>></td>
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