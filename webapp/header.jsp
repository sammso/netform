<%@ page language="java" import="java.lang.*,java.util.*" %>
<%
String path = request.getContextPath();
String basePath = "http://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
	<head>
		<base href="<%=basePath%>">
		<meta http-equiv="Content-Language" content="en"/>
		<meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
		<title>NetForm - Test Application
		</title>
		<link rel="stylesheet" type="text/css" href="css.css"/>
	</head>
	<body bgcolor="#AAAAAA">
		<table cellpadding="20" cellspacing="20" bgcolor="#FFFFFF" width="800" align="center">
			<tr>
				<td height="100%">
					<table border="0" cellspacing="0" cellpadding="0" align="center">
						<tr>
							<td width="100%" colspan="3" height="21" bgcolor="#FFFFFF" valign="top">
								<table border="0" cellpadding="0" cellspacing="0" width="100%">
									<tr>
										<td valign="middle" width="10%"><img src="logo.gif" alt=""/><br/><img src="1x1.gif" height="2" alt=""/></td>
										<td valign="middle" class="pagetitle" width="90%">  NetForm test application</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td width="100%" colspan="3" valign="middle" bgcolor="#0313DD"><img src="1x1.gif" border="0" height="2" alt=""/></td>
						</tr>
						<tr>
							<td width="20%" valign="top">
							<jsp:include page="navigation.jsp" />
							</td>
							<td width="80%" valign="top">
								<table cellpadding="0" cellspacing="5">
									<tr>
										<td>