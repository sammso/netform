package com.sohlman.netform;

import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * <p>Form is place where to your form logic. 
 * <p><u><b>Usage:</b></u>
 * <p>Inherit your form class from Form.
 * <p><u>Implement:  
 * <ol>
 * 	<li>{@link #init() init()} method <b>Required</b></li>
 * 	<li>{@link #startService() startService()} method <i>Optional</i> </li>
 * 	<li>{@link #endService() endService()} method <i>Optional</i> </li>
 * </ol>
 * @author Sampsa Sohlman
 * @version 2003-03-05
 */
public abstract class Form 
{
	final static String FORM_CONTAINER = "@FORM_CONTAINER";
	final static String SESSION_PAGE = "@PAGE";
	//private Hashtable iVe_Components;
	private ArrayList iAL_Components;
	private HttpServletRequest i_HttpServletRequest;
	private boolean ib_isInitialized = false;

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

	public final synchronized void execute()
	{
		if(!ib_isInitialized)
		{
			init();
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
}
