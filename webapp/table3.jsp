<%@ page language="java" import="com.sohlman.webapp.netform.*, com.sohlman.netform.*, com.sohlman.netform.component.*, com.sohlman.netform.component.table.*" %>
<% 
	Table3Form form = null; 
	try 
	{
		form = (Table3Form)FormManager.getForm(request, response, getServletContext(), Table3Form.class, "login.jsp");	
		form.execute(); 
%>
<jsp:include page="header.jsp" />
<h1>3. Table Example</h1>
<h2>Introduction</h2>
<p>To show how to put Button inside table and catch events from that Button.</p> 
<h2>Usage</h2>
<p>Click button and see the value to be copied to textarea.</p>
<form method="POST" action="<%=form.getPostAction() %>">
<table cellpadding="1" cellspacing="1" border="1" >
	<tr>
		<td>	
			<table cellpadding="1" cellspacing="1" border="1" >
				<tr>
					<th>Button</th><th>Text to be set	</th>
				</tr>
			<%
			for(int li_y = 1 ; li_y <= form.table.getDisplayRowCount()  ; li_y++)
			{ %>				
				<tr>
					<td><input type="submit" name="<%=form.table.getComponentAt(li_y, 1).getResponseName() %>" value="Set"></td><td><%=form.table.getText(li_y, 1) %></td>
				</tr><%
			}
			%>
			</table>
		</td>
		<td>
			<table>
				<tr>
					<td>Value of selected field</td>
				</tr>			
				<tr>
					<td><textarea name="<%=form.textField.getResponseName()%>" rows="10" cols="20" ><%=form.textField.getText()%></textarea>
					</td>
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