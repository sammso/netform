<%@ page language="java" import="com.sohlman.webapp.netform.*, com.sohlman.netform.*" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	
	FormManager.logout(request, response);
	response.sendRedirect("index.jsp"); 
%>

