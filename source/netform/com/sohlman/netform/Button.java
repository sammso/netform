/*
DataSet Library
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
	 * @return true if button is clicked false if not
	 */
	public final boolean isClicked()
	{
		return ib_isButtonClicked;
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
	/**
	 * @see com.sohlman.netform.Component#syncronizeData()
	 */
	public void syncronizeData()
	{
		// Here nothing tobe syncronized 
	}
}
