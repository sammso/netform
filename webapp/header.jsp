<%@ page language="java" import="java.lang.*,java.util.*" %>
<%
String path = request.getContextPath();
String basePath = "http://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
	<head>
		<base href="<%=basePath%>">
		<meta http-equiv="Content-Language" content="en">
		<meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
		<title>NetForm - Test Application
		</title>
		<link rel="stylesheet" type="text/css" href="css.css">
	</head>
	<body bgcolor="#AAAAAA">
		<table cellpadding="20" cellspacing="20" bgcolor="#FFFFFF" width="790" align="center">
			<tr>
				<td>
					<table border="0" cellspacing="0" cellpadding="0" align="center" width="750">
						<tr> 
						  <td valign="middle" width="130" colspan="3"><img src="logo.gif" alt=""></td>
						  <td valign="middle" class="pagetitle" width="620">NetForm test application</td>
						</tr>
						<tr> 
						  <td colspan="4" valign="middle" bgcolor="#0313DD"><img src="1x1.gif" border="0" height="2" alt=""></td>
						</tr>
						<tr>
							<td width="1" bgcolor="#0313DD"><img src="1x1.gif" border="0" height="1" width="1"></td>
							<td width="128" valign="top">
<jsp:include page="navigation.jsp" />
							</td>
							<td width="1" bgcolor="#0313DD"><img src="1x1.gif" border="0" height="1" width="1"></td>
							<td width="620" valign="top">
								<table border="0" cellpadding="10" cellspacing="10" >
									<tr>
										<td>
