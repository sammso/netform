<%@ page language="java" import="com.sohlman.webapp.netform.*, com.sohlman.netform.*" %>
<%
	FieldForm form = null;
	try
	{
		form = (FieldForm)Form.getForm(request, FieldForm.class);
		form.getNumberField().setFormat("00000");
		form.getTimestampField().setFormat("yyyy-MM-dd hh:mm:ss");
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
		<td>Text field<br><i>Write anything</i></td>
		<td><input type="text" name="<%=form.getTextField().getResponseName() %>" value="<%=form.getTextField().getText() %>"></td></td>
	</tr>
	<tr>
		<td>Integer field</td>
		<td><input type="text" name="<%=form.getNumberField().getResponseName() %>" value="<%=form.getNumberField().getText() %>"></td><td><input name="<%=form.getIncreaceInt().getResponseName() %>" type="submit" value="+"> - <input name="<%=form.getDecreaseInt().getResponseName() %>" type="submit" value="-"></td>
	</tr>	
	<tr>
		<td>Timestamp field</td>
		<td><input type="text" name="<%=form.getTimestampField().getResponseName() %>" value="<%=form.getTimestampField().getText() %>"></td><td></td>
	</tr>		
</table>
<input type="submit" name="" value="Validate">
</form>
<jsp:include page="footer.jsp" />

