/*
NetForm Library
---------------
Copyright (C) 2001-2004 - Sampsa Sohlman, Teemu Sohlman

This library is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation; either
version 2.1 of the License, or (at your option) any later version.

This library is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public
License along with this library; if not, write to the Free Software
Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA 
*/
package com.sohlman.netform;

import java.lang.reflect.Method;
import java.util.Hashtable;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Form manager handles all forms and is connection to HttpSession object
 * 
 * @author Sampsa Sohlman
/*
 * Version: 2003-11-20
 *
 */
public class FormManager implements HttpSessionListener
{
	private final static String FORM_MANAGER = "NetForm form Manager";
	private Form i_Form;
	private Object iO_LoginInfo = null;
	private String iS_NextPageAfterLogin = null;
	private Hashtable i_Hashtable = null; 
	private Hashtable iHs_Portlets = null;
	private HttpSession i_HttpSession = null;

	protected FormManager()
	{
	}

	private void setForm(Form a_Form)
	{
		i_Form = a_Form;
	}
	
	/**
	 * Add user level attribute. If you add user level 
	 * attribute with this method it is always available for
	 * all forms. Don't put very memory consuming objects here
	 * or you might create very heavy session object.
	 * <br>
	 * If key is already defined it is replaced by new.
	 * @param aS_Key Key
	 * @param a_Object Object 
	 */
	protected final void setUserAttribute(String aS_Key, Object a_Object)
	{
		if(i_Hashtable==null)
		{
			i_Hashtable = new Hashtable();
		}
		i_Hashtable.remove(aS_Key);	
	
		i_Hashtable.put(aS_Key, a_Object);
	}
	
	/**
	 * Get user attribute
	 * 
	 * @param aS_Key
	 * @return Object for key
	 */
	protected final Object getUserAttribute(String aS_Key)
	{
		if(i_Hashtable==null)
		{
			return null;
		}
		return i_Hashtable.get(aS_Key);
	}
	
	/**
	 * Remove key and object assiated with it.
	 * 
	 * @param aS_Key
	 */
	protected final void removeAttribute(String aS_Key)
	{
		if(i_Hashtable!=null)
		{
			i_Hashtable.remove(aS_Key);
		}	
	}
	
	/**
	 * <u>Implement this method if you have to do something on application level
	 * when session is expiring.</u>
	 * Session may end example if time out occurs
	 */
	protected void endSession()
	{
	}

	/**
	 * This for JSP pages without form to check if user is logged in
	 * 
	 * @param a_HttpServletRequest
	 * @param a_HttpServletResponse
	 * @param a_Class_Form
	 * @return boolean true if logged in false if not
	 */
	public static boolean isLoggedIn(HttpServletRequest a_HttpServletRequest, HttpServletResponse a_HttpServletResponse)
	{
		HttpSession l_HttpSession = a_HttpServletRequest.getSession(false);

		if (l_HttpSession == null)
		{
			return false;
		}

		FormManager l_FormManager = (FormManager) l_HttpSession.getAttribute(FormManager.FORM_MANAGER);
		if (l_FormManager == null)
		{
			return false;
		}
		
		return l_FormManager.isLoggedIn();
	}

	/**
	 * To use on JSP page<br>
	 * make example logout.jsp page and call this there and configure page to redirect after that.
	 * 
	 * @param a_HttpServletRequest
	 * @param a_HttpServletResponse
	 * @param a_Class_Form
	 */
	public static void logout(HttpServletRequest a_HttpServletRequest, HttpServletResponse a_HttpServletResponse)
	{
		HttpSession l_HttpSession = a_HttpServletRequest.getSession(false);
		if (l_HttpSession != null)
		{
			FormManager l_FormManager = (FormManager) l_HttpSession.getAttribute(FormManager.FORM_MANAGER);
			Form l_Form = l_FormManager.getForm();
			if (l_Form != null)
			{
				l_Form.formDestroyed();
			}
			l_HttpSession.removeAttribute(FormManager.FORM_MANAGER);
		}
	}

	private static boolean isLoginRequired(Class a_Class_Form)
	{
		try
		{
			Method l_Method = a_Class_Form.getMethod("isLoginRequired", null);
			Object l_Object = l_Method.invoke(null, null);
			return ((Boolean) l_Object).booleanValue();
		}
		catch (Exception l_Exception)
		{
			throw new NetFormException("FormManager.isLoginRequired NoSuchMethodError Exception", l_Exception);
		}
	}

	/**
	 * <b>JSP use</b>
	 * 
	 * @param a_HttpServletRequest current HttpServletRequest
	 * @param a_HttpServletResponse current HttpServletResponse
	 * @param a_ServletContext current ServletContext
	 * @param a_Class_Form Class which is implementing {@link Form Form}
	 * @return Form corresponding {@link Form Form}
	 * @throws NetFormException
	 * @throws DoRedirectException
	 */
	public static Form getForm(
		HttpServletRequest a_HttpServletRequest,
		HttpServletResponse a_HttpServletResponse,
		ServletContext a_ServletContext, 
		Class a_Class_Form)
		throws NetFormException, DoRedirectException
	{
		return getForm(a_HttpServletRequest, a_HttpServletResponse, a_ServletContext, a_Class_Form, null);
	}

	/**
	 * @return String containing next page after login. This is defined in 
	 * {@link com.sohlman.netform.LoginForm#setDefaultPageAfterLogin(String) LoginForm.setDefaultPageAfterLogin() } createForm() }
	 */
	public String getNextPageAfterLogin()
	{
		return iS_NextPageAfterLogin;
	}

	/**
	 * JSP use
	 * <p>Create form.
	 * 
	 * 
	 * 
	 * @param a_HttpServletRequest current HttpServletRequest
	 * @param a_HttpServletResponse current HttpServletResponse
	 * @param a_ServletContext current ServletContext
	 * @param a_Class_Form Class which is implementing {@link Form Form}
	 * @param aS_LoginPage Put null, if login is not required. If login is required, here link to login page.s
	 * @return Form corresponding {@link Form Form}
	 * @throws NetFormException
	 * @throws DoRedirectException
	 */
	public static Form getForm(
		HttpServletRequest a_HttpServletRequest,
		HttpServletResponse a_HttpServletResponse,
		ServletContext a_ServletContext,
		Class a_Class_Form,
		String aS_LoginPage)
		throws NetFormException, DoRedirectException
	{
		boolean lb_isLoginRequired = isLoginRequired(a_Class_Form);
		
		if(a_ServletContext==null)
		{
			throw new NullPointerException("getForm ServletContext is null");
		}

		if (lb_isLoginRequired && aS_LoginPage == null)
		{
			throw new NetFormException("Login page is not defined in JSP Page");
		}

		FormManager l_FormManager = null;

		Form l_Form = null;

		HttpSession l_HttpSession = a_HttpServletRequest.getSession();

		if (l_HttpSession == null)
		{
			throw new NetFormException("Failed to create session");
		}
		l_FormManager = (FormManager) l_HttpSession.getAttribute(FormManager.FORM_MANAGER);

		if (l_FormManager != null)
		{
			if((!l_FormManager.isLoggedIn()) && lb_isLoginRequired)
			{
				l_FormManager.iS_NextPageAfterLogin = a_HttpServletRequest.getContextPath() + a_HttpServletRequest.getServletPath();
				throw new DoRedirectException(aS_LoginPage);
			}
			
			Form l_Form_Old = l_FormManager.getForm();

			// Check if form is same

			if (l_Form_Old != null)
			{	
				String lS_CurrentFormName = getNameFromHttpServletRequest(a_HttpServletRequest);

				if ((!l_Form_Old.getClass().equals(a_Class_Form)) || (lS_CurrentFormName != null && !lS_CurrentFormName.equals(l_Form_Old.getName())))
				{
					if (l_Form_Old.allowFormChange())
					{
						l_Form_Old.formDestroyed();
					}
					else
					{
						throw new DoRedirectException(l_Form_Old.getName());
					}
				}
				else
				{
					l_Form = l_Form_Old;
				}
			}
		}
		else if (l_FormManager == null && lb_isLoginRequired)
		{
			l_FormManager = new FormManager();
			l_FormManager.iS_NextPageAfterLogin = a_HttpServletRequest.getContextPath() + a_HttpServletRequest.getServletPath();
			l_FormManager.i_HttpSession = l_HttpSession;
			l_HttpSession.setAttribute(FormManager.FORM_MANAGER, l_FormManager);

			throw new DoRedirectException(aS_LoginPage);
		}
		else if (l_FormManager == null)
		{
			l_FormManager = new FormManager();
			l_HttpSession.setAttribute(FormManager.FORM_MANAGER, l_FormManager);
		}

		if (l_Form == null)
		{
			l_Form = createForm(a_HttpServletRequest, a_Class_Form);
			if (l_Form != null)
			{
				l_FormManager.setForm(l_Form);
				l_Form.setFormManager(l_FormManager);
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
		l_Form.setServletContext(a_ServletContext);
		return l_Form;
	}

	private Form getForm()
	{
		return i_Form;
	}

	private static String getNameFromHttpServletRequest(HttpServletRequest a_HttpServletRequest)
	{
		String lS_QueryString = a_HttpServletRequest.getQueryString();
		if(lS_QueryString != null )
		{
			return a_HttpServletRequest.getContextPath() + a_HttpServletRequest.getServletPath() + "?" + lS_QueryString;
		}
		else
		{
			return a_HttpServletRequest.getContextPath() + a_HttpServletRequest.getServletPath();
		}
	}

	private static Form createForm(HttpServletRequest a_HttpServletRequest, Class a_Class) throws NetFormException
	{
		String lS_ServletPath = getNameFromHttpServletRequest(a_HttpServletRequest);

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
	 * If you override this you have to call super.sessionCreated(a_HttpSessionEvent)
	 */
	public void sessionCreated(HttpSessionEvent a_HttpSessionEvent)
	{

	}

	/**
	 * If you override this you have to call super.sessionDestroyed(a_HttpSessionEvent)
	 */
	public void sessionDestroyed(HttpSessionEvent a_HttpSessionEvent)
	{
		if (i_Form != null)
		{
			i_Form.formDestroyed();
		}
		endSession();
	}

	/**
	 * Set object to be assosiated to login.
	 * 
	 * @param aO_LoginInfo
	 */
	public void setLoginInfo(Object aO_LoginInfo)
	{
		iO_LoginInfo = aO_LoginInfo;
	}

	public void logOut()
	{
		iO_LoginInfo = null;
	}

	/**
	 * Returns object which is associated to login.
	 * 
	 * @return Object which is associated to login.
	 */
	public Object getLoginInfo()
	{
		return iO_LoginInfo;
	}

	/**
	 * <b>JSP also</b>
	 * 
	 * @return true if user is logged in false if not
	 */
	public boolean isLoggedIn()
	{
		if(iO_LoginInfo==null)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	
	/**
	 * Return current HttpSession
	 * 
	 * @return HttpSession
	 */
	public HttpSession getHttpSession()
	{
		return i_HttpSession;
	}
}
