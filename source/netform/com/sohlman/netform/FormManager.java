package com.sohlman.netform;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
	private final static String FORM_MANAGER = "NetForm form Manager";
	private Form i_Form;
	private Object iO_LoginInfo = null;
	
	private FormManager(Form a_Form)
	{
		i_Form = a_Form;
	}
	
	public static boolean isLoggedIn(HttpServletRequest a_HttpServletRequest, HttpServletResponse a_HttpServletResponse, Class a_Class_Form) 
	{
		HttpSession l_HttpSession = a_HttpServletRequest.getSession(false);
		
		if(l_HttpSession==null)
		{
			return false;
		}
		
		FormManager l_FormManager = (FormManager) l_HttpSession.getAttribute(FormManager.FORM_MANAGER);
		
		Form l_Form = l_FormManager.getForm();
		
		return l_Form.isLoginRequired();
	}
	
	public static Form getForm(HttpServletRequest a_HttpServletRequest, HttpServletResponse a_HttpServletResponse, Class a_Class_Form) throws NetFormException
	{
		FormManager l_FormManager = null;

		Form l_Form = null;

		HttpSession l_HttpSession;

		l_HttpSession = a_HttpServletRequest.getSession(false);

		if (l_HttpSession != null)
		{
			l_FormManager = (FormManager) l_HttpSession.getAttribute(FormManager.FORM_MANAGER);

			if (l_FormManager != null)
			{

				Form l_Form_Old = l_FormManager.getForm();

				// Check if form is same

				if (l_Form_Old != null)
				{
					String lS_ServletPath = a_HttpServletRequest.getContextPath() + a_HttpServletRequest.getServletPath();
					if ((!l_Form_Old.getClass().equals(a_Class_Form)) || (lS_ServletPath != null && !lS_ServletPath.equals(l_Form_Old.getName())))
					{
						if (l_Form_Old.allowFormChange())
						{
							l_Form_Old.formDestroyed();				
						}
						else
						{
							throw new NetFormException("Redirect",l_Form_Old.getName());
						}
					}
					else
					{
						l_Form = l_Form_Old;
					}
				}
			}
		}

		if (l_Form == null)
		{
			l_Form = createForm(a_HttpServletRequest, a_Class_Form);
			if (l_Form != null)
			{
				l_FormManager = new FormManager(l_Form);

				l_HttpSession = a_HttpServletRequest.getSession(true);
				l_HttpSession.setAttribute(FormManager.FORM_MANAGER, l_FormManager);
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
		}
		
		l_Form.setFormManager(l_FormManager);
		
		return l_Form;
	}

	private Form getForm()
	{
		return i_Form;
	}

	private static Form createForm(HttpServletRequest a_HttpServletRequest, Class a_Class) throws NetFormException
	{
		String lS_ServletPath = a_HttpServletRequest.getContextPath() + a_HttpServletRequest.getServletPath();

		try
		{
			Form l_Form = (Form) a_Class.newInstance();
			l_Form.setName(lS_ServletPath);

			return l_Form;
		}
		catch (LinkageError a_LinkageError)
		{
			a_LinkageError.printStackTrace();
			throw new NetFormException("LinkageError\nServlet path: " + lS_ServletPath + "\nClass name: " + a_Class.getName(), a_LinkageError);
		}
		catch (IllegalAccessException a_IllegalAccessException)
		{
			a_IllegalAccessException.printStackTrace();
			throw new NetFormException(
				"IllegalAccessException\nServlet path: " + lS_ServletPath + "\nClass name: " + a_Class.getName(),
				a_IllegalAccessException);
		}
		catch (InstantiationException a_InstantiationException)
		{
			a_InstantiationException.printStackTrace();
			throw new NetFormException(
				"InstantiationException\nServlet path: " + lS_ServletPath + "\nClass name: " + a_Class.getName(),
				a_InstantiationException);
		}
		catch (SecurityException a_SecurityException)
		{
			a_SecurityException.printStackTrace();
			throw new NetFormException("SecurityException\nServlet path: " + lS_ServletPath + "\nClass name: " + a_Class.getName(), a_SecurityException);
		}
	}

	/**
	 * If you override this you have to call super.
	 * 
	 * @see javax.servlet.http.HttpSessionListener#sessionCreated(javax.servlet.http.HttpSessionEvent)
	 */
	public void sessionCreated(HttpSessionEvent a_HttpSessionEvent)
	{

	}

	/**
	 * If you override this you have to call super.sessionDestroyed(a_HttpSessionEvent);
	 * @see javax.servlet.http.HttpSessionListener#sessionDestroyed(javax.servlet.http.HttpSessionEvent)
	 */
	public void sessionDestroyed(HttpSessionEvent a_HttpSessionEvent)
	{
		if (i_Form != null)
		{
			i_Form.formDestroyed();
		}
	}
	
	public void setLoginInfo(Object aO_LoginInfo)
	{
		iO_LoginInfo = aO_LoginInfo;
	}
	
	public void logOut()
	{
		iO_LoginInfo = null;
	}
	
	public Object getLoginInfo()
	{
		return iO_LoginInfo;
	}
}
