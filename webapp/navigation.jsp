<%@ page language="java" import="com.sohlman.netform.*" %>
								<table border="0" width="128" cellspacing="0" cellpadding="0">																
									<tr>
										<td width="128" colspan="2" class="menutitle1" bgcolor="#0313DD"><a href="index.jsp" class="menutitle1">Example forms</a></td>
									</tr><% 
		if(!FormManager.isLoggedIn(request, response))
		{
									%>
									<tr>
										<td width="18"></td>
										<td width="110"><a href="login.jsp" class="menuitem">Login</a></td>
									</tr>
									<tr>
										<td width="128" colspan="2" bgcolor="#0313DD"><img src="1x1.gif" height="1" width="128" border="0" alt=""></td>
									</tr>	
									<%
		}
		else
		{
		%>
									<tr>
										<td width="18"></td>
										<td width="110"><a href="logout.jsp" class="menuitem">Logout</a></td>
									</tr>
									<tr>
										<td width="128" colspan="2" bgcolor="#0313DD"><img src="1x1.gif" height="1" width="128" border="0" alt=""></td>
									</tr>								
									<%
		}%>
									<tr>
										<td width="18"></td>
										<td width="110"><a href="fields.jsp" class="menuitem">Fields</a></td>
									</tr>
									<tr>
										<td width="128" colspan="2" bgcolor="#0313DD"><img src="1x1.gif" height="1" width="128" border="0" alt=""></td>
									</tr>										
									<tr>
										<td width="18"></td>
										<td width="110"><a href="hastobevalid.jsp" class="menuitem">Has to be valid</a></td>
									</tr>
									<tr>
										<td width="128" colspan="2" bgcolor="#0313DD"><img src="1x1.gif" height="1" width="128" border="0" alt=""></td>
									</tr>																											
									<tr>
										<td width="18"></td>
										<td width="110"><a href="table1.jsp" class="menuitem">1. Table</a><br><a href="tagstable1.jsp" class="menuitem">with taglibrary</a></td>
									</tr>
									<tr>
										<td width="128" colspan="2" bgcolor="#0313DD"><img src="1x1.gif" height="1" width="128" border="0" alt=""></td>
									</tr>										
									<tr>
										<td width="18"></td>
										<td width="110"><a href="table2.jsp" class="menuitem">2. Table</a><br><a href="tagstable2.jsp" class="menuitem"> with taglibrary</a></td>
									</tr>
									<tr>
										<td width="128" colspan="2" bgcolor="#0313DD"><img src="1x1.gif" height="1" width="128" border="0" alt=""></td>
									</tr>									
									<tr>
										<td width="18"></td>
										<td width="110"><a href="table3.jsp" class="menuitem">3. Table</a></td>
									</tr>
									<tr>
										<td width="128" colspan="2" bgcolor="#0313DD"><img src="1x1.gif" height="1" width="128" border="0" alt=""></td>
									</tr>										
									<tr>
										<td width="18"></td>
										<td width="110"><a href="container1.jsp" class="menuitem">Tabs</a></td>
									</tr>
									<tr>
										<td width="128" colspan="2" class="menutitle1"  bgcolor="#0313DD">Project</td>
									</tr>																	
									<tr>
										<td width="18"></td>
										<td width="110"><a href="reflection.jsp" class="menuitem">Reflection</a></td>
									</tr>
									<tr>
										<td width="128" colspan="2" class="menutitle1"  bgcolor="#0313DD">Project</td>
									</tr>
									<tr>
										<td width="18"></td>
										<td width="110">
											<a href="http://netform.sohlman.com/index.html" class="menuitem">Homepage</a>
										</td>
									</tr>
									<tr>
										<td width="128" colspan="2" bgcolor="#0313DD"><img src="1x1.gif" height="1" width="128" border="0" alt=""></td>
									</tr>										
									<tr>
										<td width="18"></td>
										<td width="110">
											<a href="http://sourceforge.net/projects/netform/" class="menuitem">Sourceforge home</a>
										</td>
									</tr>
									<tr>
										<td width="128" colspan="2" bgcolor="#0313DD"><img src="1x1.gif" height="1" width="128" border="0" alt=""></td>
									</tr>												
									<tr>
										<td width="18"></td>
										<td width="110">
											<a href="http://sourceforge.net/project/showfiles.php?group_id=92608" class="menuitem">All downloads</a>
										</td>
									</tr>
									<tr>
										<td width="128" colspan="2" bgcolor="#0313DD"><img src="1x1.gif" height="1" width="128" border="0" alt=""></td>
									</tr>																																		
									<tr>
										<td width="128" colspan="2" class="menutitle1" bgcolor="#0313DD">Links</td>
									</tr>
									<tr>
										<td width="128" colspan="2" bgcolor="#0313DD"><img src="1x1.gif" height="1" width="128" border="0" alt=""></td>
									</tr>										
									<tr>
										<td width="18"></td>
										<td width="110">
											<a href="http://sampsa.sohlman.com" class="menuitem">Sampsa Sohlman</a>
										</td>
									</tr>
									<tr>
										<td width="128" colspan="2" bgcolor="#0313DD"><img src="1x1.gif" height="1" width="128" border="0" alt=""></td>
									</tr>										
									<tr>
										<td width="18"></td>
										<td width="110">
											<a href="http://dataset.sohlman.com" class="menuitem">DataSet Project</a>
										</td>
									</tr>
									<tr>
										<td width="128" colspan="2" bgcolor="#0313DD"><img src="1x1.gif" height="1" width="128" border="0" alt=""></td>
									</tr>										
									<tr>
										<td width="18"/>&nbsp;&nbsp;</td>
										<td width="110">
											<img src="http://www.sohlman.com/1x1.gif" width="1" height="300" alt=""/>
										</td>
									</tr>
								</table>