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
package com.sohlman.netform.component;

import javax.servlet.http.HttpServletRequest;

import com.sohlman.netform.Component;
import com.sohlman.netform.Form;

/**
 * Button component
 * @author  Sampsa Sohlman
 * @version 2001-12-10
 */
public class CheckBox extends Component
{
	private boolean ib_isSelected = false;
	private boolean ib_alwaysDisableSelection = false;

	public CheckBox(Component a_Component_Parent)
	{
		super(a_Component_Parent);
	}
	
	public CheckBox(Form a_Form)
	{
		super(a_Form);
	}	

	public boolean checkIfNewValues(String[] aS_Parameters)
	{
		HttpServletRequest l_HttpServletRequest = getHttpServletRequest();
		//String[] lS_Parameters = l_HttpServletRequest.getParameterValues(getResponseName());
		String[] lS_Parameters = aS_Parameters;
		if (lS_Parameters == null || lS_Parameters.length == 0)
		{
			// Check if the button is image then X-Y coordinates are send.
			lS_Parameters = l_HttpServletRequest.getParameterValues(getResponseName() + ".x");
		}
		String lS_Value;
		ib_isSelected = false;
		if (lS_Parameters != null && lS_Parameters.length > 0)
		{
			lS_Value = lS_Parameters[0];

			if (lS_Value != null)
			{
				ib_isSelected = true;
				return true;
			}
		}

		return false;
	}

	/**
	 * Is button clicked.
	 * @return true if button is clicked false if not
	 */
	public final boolean isSelected()
	{
		return ib_isSelected;
	}
	
	/**
	 * Set's selected value of Checkbox
	 * 
	 * @param ab_isSelected
	 */
	public final void setSelected(boolean ab_isSelected)
	{
		ib_isSelected = ab_isSelected;
	}
	
	/**
	 * Set this true if you want that it shows always disabled on page
	 * 
	 * @param ab_isSelected
	 */
	public final void setAlwaysDisableSelection(boolean ab_alwaysDisableSelection)
	{
		ib_alwaysDisableSelection = ab_alwaysDisableSelection;
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
	 * Clones button
	 */	
	public Component cloneComponent()
	{
		CheckBox l_CheckBox = new CheckBox(getParent());
		l_CheckBox.shareComponentListenerFrom(this);
		return l_CheckBox;
	}	
	/**
	 * @see com.sohlman.netform.Component#syncronizeData()
	 */
	public void syncronizeData()
	{
		// Here nothing tobe syncronized 
	}
	
	/**
	 * @see com.sohlman.netform.Component#validate()
	 */
	public void validate()
	{
		// Always valid
	}
	
	/**
	 * @see com.sohlman.netform.Component#lastComponentIteration()
	 */
	protected void lastComponentIteration()
	{
		super.lastComponentIteration();
		
		if(ib_alwaysDisableSelection)
		{
			ib_isSelected = false;
		}
	}
	
	/**
	 * <b>JSP</b>
	 * 
	 * @param aS_Selected to be returned if checkbox is selected
	 * @param aS_NotSelected to be returned if checkbox is not selected
	 * @return String if checkbox is selected then return aS_Selected else aS_NotSelected
	 */
	public String getStringIfSelected(String aS_Selected, String aS_NotSelected)
	{
		if(ib_isSelected)
		{
			return aS_Selected;
		}
		else
		{
			return aS_NotSelected;
		}
	}
	
	/**
	 * <b>JSP</b>
	 * 
	 * @param aS_Selected to be returned if checkbox is selected
	 * @return String if checkbox is selected then return aS_Selected else "" 
	 */
	public String getStringIfSelected(String aS_Selected)
	{
		if(ib_isSelected)
		{
			return aS_Selected;
		}
		else
		{
			return "";
		}
	}	
}
