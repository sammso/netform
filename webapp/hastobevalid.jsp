<%@ page language="java" import="com.sohlman.webapp.netform.*, com.sohlman.netform.*, com.sohlman.netform.component.*" %>
<% 
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

	HasToBeValidForm form = null;
	try
	{
		form = (HasToBeValidForm)FormManager.getForm(request, response, getServletContext(), HasToBeValidForm.class, "login.jsp");
		form.execute();  
%> 
<jsp:include page="header.jsp" /> 
<h1>Has To Be Valid Form<%=form.getStringIfIsNotValid(" (Form is not valid)") %></h1>
<p>This example shows, shows NetForm capability to prevent user to change page, if it is not allowed.</p>
<p><b><u>NOTE! </u></b> this form has to be valid so that you can change to other form.</p>
<form method="POST" action="<%=form.getPostAction() %>">
<table>
	<tr>
		<td class="text">First TextField<br><i>Length has to be more than 5</i></td>
		<td class="text"><input type="text" tabindex="1" name="<%=form.firstTextField.getResponseName() %>" value="<%=form.firstTextField.getText() %>" <%=form.firstTextField.getStringIfIsNotValid("class=\"notvalid\"")%>></td>
	</tr>
	<tr>
		<td class="text">Second TextField<br><i>Length has to be more than 5</i></td>
		<td class="text"><input type="text" tabindex="2" name="<%=form.secondTextField.getResponseName() %>" value="<%=form.secondTextField.getText() %>" <%=form.secondTextField.getStringIfIsNotValid("class=\"notvalid\"")%>></td>
	</tr>	
	<tr>
		<td class="text"></td>
		<td class="text"><input type="submit" value="Validate"></td>
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

