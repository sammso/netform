<%@ page language="java" import="com.sohlman.netform.*" %>
								<table border="0" width="100%" cellspacing="2" cellpadding="0">																
									<tr>
										<td width="100%" colspan="2" class="menutitle1"><a href="index.jsp" class="menutitle1">Example forms</a></td>
									</tr><% 
		if(!FormManager.isLoggedIn(request, response))
		{
									%>
									<tr>
										<td width="8%"></td>
										<td width="92%"><a href="login.jsp" class="menuitem">Login</a></td>
									</tr><%
		}
		else
		{
		%>
									<tr>
										<td width="8%"></td>
										<td width="92%"><a href="logout.jsp" class="menuitem">Logout</a></td>
									</tr><%
		}%>
									<tr>
										<td width="8%"></td>
										<td width="92%"><a href="fields.jsp" class="menuitem">Fields example</a></td>
									</tr>			
									<tr>
										<td width="8%"></td>
										<td width="92%"><a href="table1.jsp" class="menuitem">1. Table example</a></td>
									</tr>
									<tr>
										<td width="8%"></td>
										<td width="92%"><a href="table2.jsp" class="menuitem">2. Table example</a></td>
									</tr>																		
									<tr>
										<td width="100%" colspan="2" class="menutitle1">Project</td>
									</tr>
									<tr>
										<td width="8%"></td>
										<td width="92%">
											<a href="http://netform.sohlman.com/index.html" class="menuitem">Homepage</a>
										</td>
									</tr>
									<tr>
										<td width="8%"></td>
										<td width="92%">
											<a href="http://sourceforge.net/projects/netform/" class="menuitem">Sourceforge home</a>
										</td>
									</tr>			
									<tr>
										<td width="8%"></td>
										<td width="92%">
											<a href="http://sourceforge.net/project/showfiles.php?group_id=92608" class="menuitem">All downloads</a>
										</td>
									</tr>																
									<tr>
										<td width="100%" colspan="2" class="menutitle1">Links</td>
									</tr>
									<tr>
										<td width="8%"></td>
										<td width="92%">
											<a href="http://sampsa.sohlman.com" class="menuitem">Sampsa Sohlman</a>
										</td>
									</tr>
									<tr>
										<td width="8%"></td>
										<td width="92%">
											<a href="http://dataset.sohlman.com" class="menuitem">DataSet Project</a>
										</td>
									</tr>
									<tr>
										<td width="8%"/>
										<td width="92%"/>
									</tr>
									<tr>
										<td width="8%"/>
										<td width="92%"/>
									</tr>
									<tr>
										<td width="8%"/>
										<td width="92%">
											<img src="http://www.sohlman.com/1x1.gif" width="1" height="300" alt=""/>
										</td>
									</tr>
								</table>