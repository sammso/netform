package com.sohlman.netform;

import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>Form is place where to your form logic. 
 * <p><u><b>Usage:</b></u>
 * <p>Inherit your form class from Form.
 * <p><u>Implement:  
 * <ol>
 * 	<li>{@link #init() init()} method <b>Required</b></li>
 * 	<li>{@link #startService() startService()} method <i>Optional</i> </li>
 * 	<li>{@link #endService() endService()} method <i>Optional</i> </li>
 * 	<li>{@link #allowFormChange() allowFormChange()} method <i>Optional</i> To control if web page change is allowed.</li>
 * 	<li>{@link #isLoginRequired() isLoginRequired()} method <i>Optional</i> Tells if login is required</li>
 * </ol>
 * @author Sampsa Sohlman
 * @version 2003-11-20
 */
public abstract class Form 
{
	final static String FORM_CONTAINER = "@FORM_CONTAINER";
	final static String SESSION_PAGE = "@PAGE";
	//private Hashtable iVe_Components;
	private ArrayList iAL_Components;
	private String iS_NextPage = null;
	private HttpServletRequest i_HttpServletRequest;
	private boolean ib_isInitialized = false;
	private boolean ib_isValid = true;
	private FormManager i_FormManager;

	private String iS_Name;

	final void setHttpServletRequest(HttpServletRequest a_HttpServletRequest)
	{
		i_HttpServletRequest = getHttpServletRequest(a_HttpServletRequest);
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

	/** 
	 * This method is called before events are generated, by defaults does nothing.
	 */
	public void startService()
	{
		
	}

	/**
	 * This method is called after events were generated, by default does nothing.
	 */
	public void endService()
	{
	}

	/** AddComponent to form. After this component is able to receive events.
	 * @param aS_Name Name of the component. This is also leaf name of DOM tree.
	 * @param a_Component Component to be added.
	 */
	public final void addComponent(Component a_Component)
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
		boolean lb_eventsGenerated = false;
		if(!ib_isInitialized)
		{
			init();
			preValidateComponents();
			ib_isInitialized = true;
		}
		
		//
		// Check the all components if they have new values
		//
		if (iAL_Components != null)
		{
			Iterator l_Iterator = iAL_Components.iterator();

			while (l_Iterator.hasNext())
			{
				Component l_Component = (Component) l_Iterator.next();
				l_Component.clearModifiedStatus();
				if (l_Component.haveEvents())
				{
					if (l_Component.checkIfNewValues())
					{
						l_Component.componentIsModified();
					}
				}
			}
		}		
				
		startService();
		
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
		
		endService();
		
		if (iAL_Components != null)
		{
			Iterator l_Iterator = iAL_Components.iterator();
			while (l_Iterator.hasNext())
			{
				Component l_Component = (Component) l_Iterator.next();
				l_Component.changeVisibleEnable();
			}
		}
		if(!lb_eventsGenerated)
		{
//			checkIfOutOfSynch()
		}
		redirectToNextPage();
		nextValueForComponentResponnsePrefix();
	}

	/** Pass the parameters to page components.
	 *
	 * @param a_HttpServletRequest HttpServletRequest
	 */
	private final void setHttpServletRequestToComponents(HttpServletRequest a_HttpServletRequest)
	{

	}
	
	/**
	 * Set's name for current form<br>
	 * Name of form is servlet url mapping which is binded in servlet init parameters<br>
	 * <br>
	 * <b>Only internal use</b><br>
	 * <br>
	 * @param aS_Name Name of the form /../<nameoftheform>..
	 */
	
	void setName(String aS_Name)
	{
		iS_Name = aS_Name;
	}

	public final String getName()
	{
		return iS_Name;
	}

	public final String getTimestamp()
	{
		return "Made by Sampsa";
	}	
	
	/**
	 * Internal use
	 * Set if form is valid or not
	 * 
	 * @param ab_isValid
	 */
	final void setValid(boolean ab_isValid)
	{
		if (ib_isValid == true && ab_isValid == false)
		{
			ib_isValid = ab_isValid;
		}
		else if (ib_isValid == false && ab_isValid == true)
		{
			Iterator l_Enumeration = iAL_Components.iterator();
			
			boolean lb_isValid = ab_isValid;
			
			while (l_Enumeration.hasNext() && lb_isValid)
			{
				Component l_Component = (Component) l_Enumeration.next();
				lb_isValid = l_Component.isValid();
			}
			
			ib_isValid = lb_isValid;
		}
	}
	
	/**
	 * This is for JSP use.
	 * 
	 * @return boolean valid if all components in form is valid.
	 */
	public final boolean isValid()
	{
		return ib_isValid;
	}
	
	/**
	 * This method is called when session is expired or 
	 * user is going on other page. Do necessary actions.
	 * 
	 */
	public void formDestroyed()
	{
		
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
		if(iS_NextPage!=null)
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
}
