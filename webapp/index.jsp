<%@ page language="java" import="com.sohlman.netform.*" %>
					<jsp:include page="header.jsp" />
						<h1>NetForm test application v0.64</h1>
						<h2>Introduction</h2>
						<p>NetForm example application is test and show case of NetForm framework 
						features and capabilities. </p>
<% 
		if(!FormManager.isLoggedIn(request, response))
		{
									%>						
						<h2>Login form (<a href="login.jsp">login.jsp</a>)</h2>
						<p>You first have to login to use other forms. If you click these other forms you 
						are redirected before to login form (<a href="login.jsp">login.jsp</a>) and then after login you are redirected form that you want to use.</p>
<%
		}
		else
		{
		%>	
						<h2>Logout (<a href="logout.jsp">logout.jsp</a>)</h2>						<p>You are now logged in. Logout itself, is not a form and don't contain HTML. It is JSP page, which only purpose is to redirect you prefined paged and terminate your login.</p>
<%} %>
						<h2>Fields form(<a href="fields.jsp">fields.jsp</a>)</h2>
						<p>Fields form is example of NetForm fields. There is Components like Button, TextField, NumberField, TimestampField and Table.</p>
						<p>This example also is showing the form ability to validate itself from 
						components. Form is validated everytime when button is pressed or page contest is send to server.</p>
						<h2>Has to valid form (<a href="hastobevalid.jsp">hastobevalid.jsp</a>)</h2>
						<p>This is validation example, which shows netform ability to prevent to move another page if form is not valid.
						</p>
						<h2>1. Table form (<a href="table1.jsp">table1.jsp</a>)</h2>
						<p>One of the power of the Netform is it's ability maintain table form data with Table component. 
						This table component is exactly same as used in Fields example, but it has been layouted little bit different way and also TextFields has been connected to
						it's columns.  
						</p>
						<h2>2. Table form (<a href="table2.jsp">table2.jsp</a>)</h2>
						<p>This is another example of Table form. Table itself can be used as Table component. 
						</p>
						<h2>3. Table Example (<a href="table3.jsp">table3.jsp</a>)</h2>
						<p>To show how to put Button inside table and catch events from that Button.</p>
						<h2>Tabs Example (<a href="container1.jsp">container1.jsp</a>)</h2>
						<p>This shows how to make server side tab sheets by using Container component.</p>						
					<jsp:include page="footer.jsp" />						
							