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

import com.sohlman.netform.Component;
import com.sohlman.netform.Form;
import com.sohlman.netform.Utils;

/**
 * @author Sampsa Sohlman
 * 
 * @version 20-04-2004
 */
public class PasswordField extends TextField
{

	/**
	 * @see com.sohlman.netform.Component#Component(Component)
	 */
	public PasswordField(Component a_Component_Parent)
	{
		super(a_Component_Parent);
		ib_isEmptyNull=false;
		ib_isNullAllowed=false;
		ib_isTrim=true;
	}

	/**
	 * @see com.sohlman.netform.Component#Component(Form)
	 */
	public PasswordField(Form a_Form)
	{
		super(a_Form);
		ib_isEmptyNull=false;
		ib_isNullAllowed=false;
		ib_isTrim=true;
	}

	/**
	 * Overriden from TextField
	 * @see com.sohlman.netform.Component#checkIfNewValues()
	 */
	public boolean checkIfNewValues(String[] aS_Parameters)
	{
		clearModifiedStatus();
		if (aS_Parameters != null && aS_Parameters.length > 0)
		{
			char[] lc_10 = { 10 };
			String lS_NewText = Utils.replace(aS_Parameters[0], new String(lc_10), "");
			
			lS_NewText = formatStringByRules(lS_NewText);
			
			if ( ( lS_NewText==null && iS_Text==null ) || ( lS_NewText!=null && lS_NewText.equals(iS_Text)) || lS_NewText.equals(""))
			{
				return false;
			}
			else
			{
				setText(lS_NewText);
				return true;
			}
		}
		else
		{
			return false;
		}
	}
	
	public String getText()
	{
		return "";
	}
	
	public String getPassword()
	{
		return super.getText();
	}
}
