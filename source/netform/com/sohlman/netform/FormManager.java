package com.sohlman.netform;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Form manager handles all forms.
 * 
 * @author Sampsa Sohlman
/*
 * Version: 27.8.2003
 *
 */
public class FormManager implements HttpSessionListener
{
	public static Form getForm(HttpServletRequest a_HttpServletRequest, Class a_Class_Form) throws NetFormException
	{
		Form l_Form = null;
		HttpSession l_HttpSession;
		
		l_HttpSession = a_HttpServletRequest.getSession(false);

		if (l_HttpSession != null)
		{
			l_Form = (Form) l_HttpSession.getAttribute(Form.FORM_CONTAINER);
			String lS_Name = a_HttpServletRequest.getServletPath();
			if (l_Form!=null && lS_Name!=null)
			{
				lS_Name=lS_Name.substring(1);
				if(!lS_Name.equals(l_Form.getName()))
				{				
					l_Form = null;
				}
			}
			else
			{
				l_Form = null;
			}
		}

		if (l_Form == null)
		{
			l_Form = createForm(a_HttpServletRequest, a_Class_Form);
			if (l_Form != null)
			{
				l_HttpSession = a_HttpServletRequest.getSession(true);
				l_HttpSession.setAttribute(Form.FORM_CONTAINER, l_Form);
				l_HttpSession.setAttribute(Form.SESSION_PAGE, l_Form.getName());
				l_Form.setHttpServletRequest(a_HttpServletRequest);
			}
			else
			{
				throw new NetFormException("Couldn't create form");
			}
		}
		else
		{
			l_Form.setHttpServletRequest(a_HttpServletRequest);
			//l_Form.setServletConfig(getServletConfig());
		}
		return l_Form;
	}

	private static Form createForm(HttpServletRequest a_HttpServletRequest, Class a_Class) throws NetFormException
	{
		String lS_Name = a_HttpServletRequest.getServletPath().substring(1);

		try
		{
			Form l_Form = (Form) a_Class.newInstance();
			l_Form.setName(lS_Name);

			return l_Form;
		}
		catch (LinkageError a_LinkageError)
		{
			a_LinkageError.printStackTrace();
			throw new NetFormException("LinkageError\nServlet path: " + lS_Name + "\nClass name: " + a_Class.getName(), a_LinkageError);
		}
		catch (IllegalAccessException a_IllegalAccessException)
		{
			a_IllegalAccessException.printStackTrace();
			throw new NetFormException("IllegalAccessException\nServlet path: " + lS_Name + "\nClass name: " + a_Class.getName(), a_IllegalAccessException);
		}
		catch (InstantiationException a_InstantiationException)
		{
			a_InstantiationException.printStackTrace();
			throw new NetFormException("InstantiationException\nServlet path: " + lS_Name + "\nClass name: " + a_Class.getName(), a_InstantiationException);
		}
		catch (SecurityException a_SecurityException)
		{
			a_SecurityException.printStackTrace();
			throw new NetFormException("SecurityException\nServlet path: " + lS_Name + "\nClass name: " + a_Class.getName(), a_SecurityException);
		}
	}	

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpSessionListener#sessionCreated(javax.servlet.http.HttpSessionEvent)
	 */
	public void sessionCreated(HttpSessionEvent a_HttpSessionEvent)
	{
		// Do nothing
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpSessionListener#sessionDestroyed(javax.servlet.http.HttpSessionEvent)
	 */
	public void sessionDestroyed(HttpSessionEvent a_HttpSessionEvent)
	{
			
	}
}
