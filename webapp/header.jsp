<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" import="java.lang.*,java.util.*" %>
<%
String path = request.getContextPath();
String basePath = "http://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%> 
<html>
	<head>
		<base href="<%=basePath%>">
		<link rel="stylesheet" type="text/css" href="css.css">
		<title>NetForm Test application</title>
	</head>
	
	<body>
			<table border="0" cellpadding="0" cellspacing="0"  >
				<tr>
					<td bgcolor="#27C303" colspan="5" ><img src="1x1.gif" height="1" width="790" alt="" border="0"></td>
				</tr>
				<tr>
					<td bgcolor="#27C303"><img src="1x1.gif" height="480" width="1" alt="" border="0"></td>
					<td valign="top">
					<img src="1x1.gif" height="1" width="250" alt="" border="0">
					<jsp:include page="navigation.jsp" />
					</td>
					<td bgcolor="#27C303"><img src="1x1.gif" height="480" width="1" alt="" border="0"></td>
					<td valign="top" align="left">
						<img src="1x1.gif" height="1" width="540" alt="" border="0">
						<table border="0" cellpadding="5" cellspacing="5"  >
							<td>
