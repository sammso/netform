package com.sohlman.netform;

import javax.servlet.http.HttpServletRequest;

/**
 * Button component
 * @author  Sampsa Sohlman
 * @version 2001-12-10
 */
public class Button extends Component
{
	private boolean ib_isButtonClicked = false;

	public Button(Component a_Component_Parent)
	{
		super(a_Component_Parent);
	}
	
	public Button(Form a_Form)
	{
		super(a_Form);
	}	

	public boolean checkIfNewValues()
	{
		HttpServletRequest l_HttpServletRequest = getHttpServletRequest();
		String[] lS_Parameters = l_HttpServletRequest.getParameterValues(getResponseName());
		if (lS_Parameters == null || lS_Parameters.length == 0)
		{
			// Check if the button is image then X-Y coordinates are send.
			lS_Parameters = l_HttpServletRequest.getParameterValues(getResponseName() + ".x");
		}
		String lS_Value;
		ib_isButtonClicked = false;
		if (lS_Parameters != null && lS_Parameters.length > 0)
		{
			lS_Value = lS_Parameters[0];

			if (lS_Value != null)
			{
				ib_isButtonClicked = true;
				return true;
			}
		}

		return false;
	}

	/**
	 * Is button clicked.
	 */

	public final boolean isClicked()
	{
		return ib_isButtonClicked;
	}
	
	/* 
	 * Not supported
	 */
	protected void addComponent(Component a_Component)
	{
		throw new NoSuchMethodError("Child components are not supported on Textfield");
	}
}
