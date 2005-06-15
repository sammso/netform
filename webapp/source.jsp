<%@ page language="java" import="java.io.*"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<title>Source view</title>
</head>
<body>
<%
	FileInputStream fileInputStream = null;
	try
	{
		String lS_Parameter=request.getParameter("source");
		
		if(lS_Parameter==null)
		{
			%><p>No valid parameter</p><%
		}
		else
		{
			String lS_File = lS_Parameter.replaceAll(".","/");
			File file;
			
			if(lS_File.indexOf(".jsp"))
			{
				file = new File("/" + lS_File);
			}
			else if(
			{
				file = new File("WEB-INF/src",lS_File + ".java");
			}	
			
			if(file.exists())
			{
				fileInputStream = new FileInputStream(file);
	            for(int ch = in.read(); ch != -1; ch = in.read())
	            { %>
			<pre><%
	                if (ch == '<')
	                {
	                    out.print("&lt;");
					}
					else if(ch == '>')
					{
						out.print("&gt;");
					}
	                else
	                {
	                    out.print((char) ch);
					} 
				%>
           </pre><%
				}
			}
			else
			{%>
	<p>File doesn't exist</p>
	<%
			}
		}
	}
	catch(IOException ioException)
	{
		
	}
	finally
	{
		try
		{
			if(fileInputStream!=null) fileInputStream.close();
		}
		catch(IOException ioException)
		{
			
		}
	}
%>
</body>
</html>
