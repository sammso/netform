/**
 * ToggleField.java
 *
 * @version 2001-12-28 
 * @author Sampsa Sohlman
 */

package com.sohlman.netform;

import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author  Sampsa Sohlman
 * @version
 */
public class Togglefield extends Component
{
	private Vector iVe_Listeners;
	private boolean ib_state = false;
	private boolean ib_oldState = false;
	private boolean ib_newState = false;
	private boolean ib_stateChanged = false;

	public Togglefield(Component a_Component_Parent)
	{
		super(a_Component_Parent);
	}

	public Togglefield(Form a_Form)
	{
		super(a_Form);
	}

	public void changeState()
	{
		if (ib_stateChanged)
		{
			ib_state = ib_newState;
		}
	}

	public boolean checkIfNewValues()
	{
		HttpServletRequest l_HttpServletRequest = getHttpServletRequest();

		String[] lS_Parameters = l_HttpServletRequest.getParameterValues(getResponseName());
		ib_stateChanged = false;
		if (lS_Parameters == null || lS_Parameters.length == 0)
		{
			// Check if the button is image then X-Y coordinates are send.

			lS_Parameters = l_HttpServletRequest.getParameterValues(getResponseName() + ".x");
		}
		String lS_Value;
		ib_newState = false;
		if (lS_Parameters != null && lS_Parameters.length > 0)
		{
			lS_Value = lS_Parameters[0];

			if (lS_Value != null)
			{
				ib_newState = true;
				ib_stateChanged = true;
				return true;
			}
		}
		return false;
	}

	public boolean getToggle()
	{
		return ib_state;
	}

	public void setToggle(boolean ab_state)
	{
		ib_state = ab_state;
	}

	/**
	 * Implement this method and read compoonent parameters
	 */
	public void readComponentParameters()
	{
	}

	public boolean isValid()
	{
		return true;
	}

	public Component newInstance()
	{
		throw new NoSuchMethodError("Togglefield is not supporting newInstance");
	}
	/* (non-Javadoc)
	 * @see com.sohlman.netform.Component#addComponent(java.lang.String, com.sohlman.netform.Component)
	 */
	protected void addComponent(String aS_Name, Component a_Component)
	{
		throw new NoSuchMethodError("Child components are not supported on Textfield");
	}
	
	/**
	 * Not supported
	 * @throws NoSuchMethodError
	 */
	protected void addComponent(Component a_Component)
	{
		throw new NoSuchMethodError("Child components are not supported on Textfield");
	}	
	/**
	 * Not supported
	 * @throws NoSuchMethodError
	 */	
	public Component cloneComponent()
	{
		throw new NoSuchMethodError("At moment clone component is not supported");
	}	
}
