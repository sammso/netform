/*
NetForm Library
---------------
Copyright (C) 2001-2005 - Sampsa Sohlman, Teemu Sohlman

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

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * <p>Form is place where to put your form logic 
 * <p><u><b>Usage:</b></u>
 * <p>Inherit your form class from Form.
 * <p><u>Implement:  
 * <ol>
 * 	<li>Make construtor where you create your Compoponents method <b>Required</b> Normally here you do form initalization.</li>
 * 	<li>{@link #init() init()} method <b>Required</b> Here you read first data to your components. </li>
 * 	<li>{@link #startService() startService()} method <i>Optional</i> </li>
 * 	<li>{@link #endService() endService()} method <i>Optional</i> </li>
 * 	<li>{@link #allowFormChange() allowFormChange()} method <i>Optional</i> To control if web page change is allowed.</li>
 * 	<li>{@link #isLoginRequired() isLoginRequired()} method <i>Optional</i> Tells if login is required</li>
 * </ol>
 * @author Sampsa Sohlman
 * @version 2004-16-20
 */
public abstract class Form
{
	public final static String FORM = "form";
	// TODO: To be removed start
	public final static int FORM_STATE_CONSTRUCTOR = 0;
	public final static int FORM_STATE_INIT = 1;
	public final static int FORM_STATE_EVENTS = 2;
	public final static int FORM_STATE_SETTINGS = 3;
	public final static int FORM_STATE_EXECUTE = 4;
	public final static int FORM_STATE_TEMPLATING = 5;
	
	private int ii_currentState = FORM_STATE_CONSTRUCTOR;
	//  To be removed end
	
	final static String FORM_CONTAINER = "@FORM_CONTAINER";
	final static String SESSION_PAGE = "@PAGE";
	private ArrayList iAL_Components = null;
	private ArrayList iAL_Portlets = null;
	private String iS_NextPage = null;
	private HttpServletRequest i_HttpServletRequest;
	private boolean ib_isInitialized = false;
	private boolean ib_isValid = true;
	
	private boolean ib_isSessionOutOfSync = true;
	
	private FormManager i_FormManager;
	private ServletContext i_ServletContext;

	private String iS_Name;
	private int ii_notValidChildComponentCount = 0; // Component is valid if this is 0

	/**
	 * Tells if form is initialized.
	 * With this method is possible to check
	 * if form is initialized.
	 * 
	 * Form is initialized if init() method has been executed
	 * 
	 * @return true if initialized false if not
	 */
	public final boolean isInitialized()
	{
		return ib_isInitialized;
	}
	
	/**
	 * Internal use
	 * 
	 * @param a_HttpServletRequest
	 */
	final void setHttpServletRequest(HttpServletRequest a_HttpServletRequest)
	{
		i_HttpServletRequest = getHttpServletRequest(a_HttpServletRequest);
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
		i_FormManager.setUserAttribute(aS_Key, a_Object);
	}

	/**
	 * Get user attribute
	 * 
	 * @param aS_Key
	 * @return Object for key
	 */
	protected final Object getUserAttribute(String aS_Key)
	{
		return i_FormManager.getUserAttribute(aS_Key);
	}

	/**
	 * Remove key and object assiated with it.
	 * 
	 * @param aS_Key
	 */
	protected final void removeAttribute(String aS_Key)
	{
		i_FormManager.removeAttribute(aS_Key);
	}

	/**
	 * By inherting this method is possible change HttpServletRequest object type.
	 * 
	 *
	 */
	protected HttpServletRequest getHttpServletRequest(HttpServletRequest a_HttpServletRequest)
	{
		return a_HttpServletRequest;
	}

	final public HttpServletRequest getHttpServletRequest()
	{
		return i_HttpServletRequest;
	}

	/** 
	 * Implement this method after
	 */
	public abstract void init();

	private void initPortlets()
	{
		if(iAL_Portlets!=null && iAL_Portlets.size() > 0)
		{
			Iterator l_Iterator = iAL_Portlets.iterator();
			while(l_Iterator.hasNext())
			{
				Portlet l_Portlet = (Portlet)l_Iterator.next();
				l_Portlet.startService();
			}
		}
	}
	
	/** 
	 * This method is called before events are generated, by defaults does nothing.
	 */
	public void startService()
	{
		
	}

	private void addPortlet(Portlet a_Portlet)
	{
		if (a_Portlet != null)
		{
			if (iAL_Portlets == null)
			{
				iAL_Portlets = new ArrayList();
			}
			iAL_Portlets.add(a_Portlet);
			addComponent(a_Portlet);
		}		
	}
	
	public Portlet getPortlet(Class a_Class_Portlet)
	{
		Object l_Object = null;
		
		if(a_Class_Portlet.isAssignableFrom(Portlet.class))
		{
			try
			{
				Constructor l_Constructor = a_Class_Portlet.getConstructor(new Class[] {Form.class});
				Portlet l_Portlet = (Portlet)l_Constructor.newInstance(new Object[]{ this });
			}
			catch(Exception l_Exception)
			{
				throw new NetFormException("Portlet Not Found");
			}
		}
		throw new NetFormException("Class is not portlet");
	}
	
	private void callPortletStartService()
	{
		if(iAL_Portlets!=null && iAL_Portlets.size() > 0)
		{
			Iterator l_Iterator = iAL_Portlets.iterator();
			while(l_Iterator.hasNext())
			{
				Portlet l_Portlet = (Portlet)l_Iterator.next();
				l_Portlet.startService();
			}
		}
	}
	
	/**
	 * This method is called after events were generated, by default does nothing.
	 */
	protected void endService()
	{
	}

	private void callPortletEndService()
	{
		if(iAL_Portlets!=null && iAL_Portlets.size() > 0)
		{
			Iterator l_Iterator = iAL_Portlets.iterator();
			while(l_Iterator.hasNext())
			{
				Portlet l_Portlet = (Portlet)l_Iterator.next();
				l_Portlet.endService();
			}
		}
	}	
	
	/** AddComponent to form. After this component is able to receive events.
	 * @param aS_Name Name of the component.
	 * @param a_Component Component to be added.
	 */
	final void addComponent(Component a_Component)
	{
		if (a_Component != null)
		{
			a_Component.setForm(this);

			// setParameters(a_Component)

			if (iAL_Components == null)
			{
				iAL_Components = new ArrayList();
			}
			iAL_Components.add(a_Component);
		}
	}

	private void preValidateComponents()
	{
		if (iAL_Components != null)
		{
			Iterator l_Iterator = iAL_Components.iterator();

			while (l_Iterator.hasNext())
			{
				Component l_Component = (Component) l_Iterator.next();
				l_Component.validate(); 
			}
		}
	}
	
	public final synchronized void execute() throws DoRedirectException
	{
		try
		{
			boolean lb_eventsGenerated = false;
			
			if (!ib_isInitialized)
			{
				ii_currentState = FORM_STATE_INIT;
				init();
				initPortlets();
				preValidateComponents();
				ib_isInitialized = true;
				initSessionOutOfSync();
				startService();
				callPortletStartService();
				
			}
			else
			{
				initSessionOutOfSync();
				startService();
				callPortletStartService();

				// We generate events only second run.
				// Faster :D

				if (iAL_Components != null)
				{
					Iterator l_Iterator = iAL_Components.iterator();

					while (l_Iterator.hasNext())
					{
						Component l_Component = (Component) l_Iterator.next();
						l_Component.parseValues();
					}
				}

				ii_currentState = FORM_STATE_EVENTS;

				// Generate events to to components

				if (iAL_Components != null)
				{
					Iterator l_Iterator = iAL_Components.iterator();

					while (l_Iterator.hasNext())
					{
						Component l_Component = (Component) l_Iterator.next();

						if (l_Component.haveEvents())
						{

							l_Component.generateEvent();
							lb_eventsGenerated = true;
						}
					}
				}
			}
			endService();
			callPortletEndService();
			// Do last thing to components

			if (iAL_Components != null)
			{
				Iterator l_Iterator = iAL_Components.iterator();
				while (l_Iterator.hasNext())
				{
					Component l_Component = (Component) l_Iterator.next();
					l_Component.lastIteration();
				}
			}

			redirectToNextPage();
			nextValueForComponentResponnsePrefix();
			ii_currentState = FORM_STATE_TEMPLATING;
		}
		catch(Error l_Error)
		{
			l_Error.printStackTrace();
			throw l_Error;
		}
		finally
		{
			finallyCleanUp(); 
		}
	}
	
	/**
	 * This method is for implement. If something happens on your application and you want to be
	 * sure that resources are not hanging implement this method to clean up.
	 * 
	 * Put db connections back to pool etc.
	 */
	protected void finallyCleanUp()
	{
		
	}

	/** Pass the parameters to page components.
	 *
	 * @param a_HttpServletRequest HttpServletRequest
	 */
	/*	private final void setHttpServletRequestToComponents(HttpServletRequest a_HttpServletRequest)
		{
	
		}
	*/
	/**
	 * Set's name for current form<br>
	 * Name of form is servlet url mapping which is binded in servlet init parameters<br>
	 * <br>
	 * <b>Only internal use</b><br>
	 * <br>
	 * @param aS_Name Name of the form
	 */

	void setName(String aS_Name)
	{
		iS_Name = aS_Name;
	}

	public final String getName()
	{
		return iS_Name;
	}

	/**
	 * Internal use
	 * Set if form is valid or not
	 * 
	 * @param ab_isValid
	 */
	final void setValid(boolean ab_isValid)
	{
		ib_isValid = ab_isValid;
	}
	
	final void setChildValid(boolean ab_isValid)
	{
		if(ab_isValid)
		{
			ii_notValidChildComponentCount--;
		}
		else
		{
			ii_notValidChildComponentCount++;
		}
	}

	/**
	 * This is for JSP use.
	 * 
	 * @return boolean valid if all components in form is valid.
	 */
	public final boolean isValid()
	{
		return ib_isValid && ii_notValidChildComponentCount == 0;
	}

	/**
	 * This method is called when session is expired or 
	 * user is going on other page. Do necessary actions
	 * 
	 * It also generate dispose event for all objects.
	 * 
	 */
	public void formDestroyed()
	{
		if (iAL_Components != null)
		{
			Iterator l_Iterator = iAL_Components.iterator();

			while (l_Iterator.hasNext())
			{
				Component l_Component = (Component) l_Iterator.next();
				
				// Don't dispose Portlets
				
				if(!Portlet.class.isInstance(l_Component))
				{
					l_Component.dispose();
				}
				else
				{
					Portlet l_Portlet = (Portlet)l_Component;
					// TODO: Fixit
					//getFormManager().removePortlet(l_Component);
				}
			}
		}
	}

	/**
	 * To be override if web page is not allowed to change.
	 * 
	 * @return boolean true if it is allowed
	 */
	public boolean allowFormChange()
	{
		return true;
	}

	final void setFormManager(FormManager a_FormManager)
	{
		i_FormManager = a_FormManager;
	}

	final protected FormManager getFormManager()
	{
		return i_FormManager;
	}

	/**
	 * This method is for need to be make re
	 * 
	 * @param aS_Page
	 */
	final protected void setNextPage(String aS_Page)
	{
		iS_NextPage = aS_Page;
	}

	final void redirectToNextPage() throws DoRedirectException
	{
		if (iS_NextPage != null)
		{
			String lS_NextPage = iS_NextPage;
			iS_NextPage = null; // Just in case
			throw new DoRedirectException(lS_NextPage);
		}
	}

	final protected String getNextPage()
	{
		return iS_NextPage;
	}

	/**
	 * This tells if user has to be logged in to system
	 * <br>default false
	 * @return true if login this form requires login
	 */
	public static boolean isLoginRequired()
	{
		return false;
	}

	private long il_count = 0;

	final String getComponentResponnsePrefix()
	{
		return String.valueOf(il_count);
	}

	private void nextValueForComponentResponnsePrefix()
	{
		il_count++;
	}

	public ServletContext getServletContext()
	{
		return i_ServletContext;
	}

	public void setServletContext(ServletContext a_ServletContext)
	{
		i_ServletContext = a_ServletContext;
	}

	public int getCurrentState()
	{
		return ii_currentState;
	}

	/**
	 * Same as HttpServletRequest.getRequestURI();
	 * 
	 * @return a String containing the part of the URL from the protocol name up to the query string
	 */
	public String getRequestURI()
	{
		return i_HttpServletRequest.getRequestURI();
	}

	/**
	 * Same as HttpServletRequest.getContextPath()
	 * 
	 * @return a String specifying the portion of the request URI that indicates the context of the request
	 */
	public String getContextPath()
	{
		return i_HttpServletRequest.getContextPath();
	}
	
	public String getPostAction()
	{	
		return getName();
	}
	
	
	protected final Integer getParameterInteger(String aS_Name)
	{
		String l_String = getParameter(aS_Name);
		
		if(l_String!=null)
		{
			return Integer.valueOf(l_String);
		}
		else
		{
			return null;
		}
	}
	protected final String getParameter(String aS_Name)
	{
		return i_HttpServletRequest.getParameter(aS_Name);
	}	
	
	/**
	 * This returns default formats
	 * 
	 * @param a_Class class to be search for format
	 * @return Format string to class
	 */
	final String getFormatFromParent(Class a_Class)
	{
		// TODO: Implement
		return null;	
	}

	/**
	 * <b>JSP</b> Return String if component is valid othervice ""
	 * 
	 * @param aS_ValidString String to be returned when component is valid
	 * @param aS_NotValidString String to be returned when component is not valid
	 * @return  String if component is valid othervice ""
	 */		
	public String getStringIfIsValid(String aS_ValidString, String aS_NotValidString)
	{
		if(isValid())
		{
			return aS_ValidString;
		}
		else
		{
			return aS_NotValidString;
		}
	}
	
	/**
	 * <b>JSP</b> Return String if component is valid othervice ""
	 * 
	 * @param aS_ValidString String to be returned
	 * @return  String if component is valid othervice ""
	 */	
	public String getStringIfIsValid(String aS_ValidString)
	{
		return getStringIfIsValid(aS_ValidString,"");
	}
	
	/**
	 * <b>JSP</b> Return String if component is not valid othervice ""
	 * 
	 * @param a_String String to be returned
	 * @return  String if component is valid othervice ""
	 */
	public String getStringIfIsNotValid(String a_String)
	{
		return getStringIfIsValid("", a_String);
	}
	
	
	/**
	 * <b>JSP</b> Tells if session is out of sync. Basically if 
	 * browsers back button was pressed on form.
	 * 
	 * @return true if it is out of sync false if not
	 */
	public final boolean isSessionOutOfSync()
	{
		return ib_isSessionOutOfSync;
	}
	
	final void setSessionIsSync()
	{
		ib_isSessionOutOfSync = false;
	}
	
	/**
	 * initialize ib_isSessionOutOfSync value
	 * this is executed on execute() method
	 */
	private void initSessionOutOfSync()
	{		
		if( iAL_Components == null || iAL_Components.size() <= 0 )
		{
			ib_isSessionOutOfSync = false;
		}
		else
		{
			ib_isSessionOutOfSync = true;
		}		
	}
	
	/**
	 * Returns current HttpSession
	 * 
	 * @return HttpSession
	 */
	public HttpSession getHttpSession()
	{
		return getFormManager().getHttpSession();
	}
}
