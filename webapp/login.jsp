<%@ page language="java" import="com.sohlman.webapp.netform.*, com.sohlman.netform.*, com.sohlman.netform.component.*" %>
<% 
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

	LoginForm form = null;
	try
	{
		form = (DoLoginForm)FormManager.getForm(request, response, getServletContext(), DoLoginForm.class);
		form.setDefaultPageAfterLogin("index.jsp");
		form.execute();  
%> 
<jsp:include page="header.jsp" /> 
<h1>Login</h1>
<p>To use table.jsp or fields.jsp you need to login first. All user id's and passwords are accepted.</p>
<form method="POST" action="<%=form.getPostAction() %>">
<table>
	<tr>
		<td class="text">Login</td>
		<td colspan="2"><input type="text" autocomplete="off" name="<%=form.loginTextField.getResponseName() %>" value="<%=form.loginTextField.getText() %>"></td>
	</tr>
	<tr>
		<td class="text">Password</td>
		<td colspan="2"><input type="text" autocomplete="off" name="<%=form.passwordTextField.getResponseName() %>" value=""></td>
	</tr>	
	<tr>
		<td class="text"></td>
		<td colspan="2"><input type="submit" autocomplete="off" name="<%=form.loginButton.getResponseName() %>" value="Login"></td>
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

