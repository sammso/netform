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
package com.sohlman.netform.component;

import javax.servlet.http.HttpServletRequest;

import com.sohlman.netform.Component;
import com.sohlman.netform.Form;
import com.sohlman.netform.Utils;

/**
 * TextField is component to String data
 *
 * @version 2004-15-01
 * @author Sampsa Sohlman
 */
public class TextField extends Component
{
	protected String iS_Text = null;

	protected boolean ib_isEmptyNull = true;
	protected boolean  ib_isNullAllowed = false;
	protected boolean ib_isTrim = false;

	/** Creates new TextComponent */

	public TextField(Component a_Component_Parent)
	{
		super(a_Component_Parent);
	}

	public TextField(Form a_Form)
	{
		super(a_Form);
	}

	/**
	 * Set this on if you want that empty "" String is null<br>
	 * If String contains spaces then it not empty.<br>
	 * if not null then null is ""
	 *
	 * @param boolean true if empty allowed to be null false if not
	 */
	public void setEmptyIsNull(boolean ab_emptyIsNull)
	{
		ib_isEmptyNull = ab_emptyIsNull;
	}
	
	public void setNullIsAllowed(boolean ab_isNullAllowed)
	{
		ib_isNullAllowed = ab_isNullAllowed;
	}

	/**
	 * Set trim mode on for textfield.
	 * If trim mode is on then Space are trimmed of from data automaticly
	 * @param ab_isTrimmed boolean which tells if feature is on or off
	 */
	public void setTrim(boolean ab_isTrim)
	{
		ib_isTrim = ab_isTrim;
	}

	public boolean isTrim()
	{
		return ib_isTrim;
	}

	/**
	 * Textfield value is empty "" then it is considered as null,
	 * by default it is null.
	 * <p>
	 * Use {@link #setEmptyIsNull(boolean) setEmptyIsNull()} to set 
	 * behavior
	 * 
	 * @return boolean true if can be null else false
	 */

	public boolean isEmptyNull()
	{
		return ib_isEmptyNull;
	}
	
	/**
	 * Tells if null is allowed, by default nullIsAllowed
	 * 
	 * @return true if allowed false if not
	 */
	public boolean isNullAllowed()
	{
		return ib_isNullAllowed;
	}

	/**
	 * Set TextField value
	 * 
	 * @param aS_Text if parameter is null then and null is not allowed then
	 * it holds "" and {@link #isNullAllowed() isNullAllowed()} returns always false
	 */
	public boolean setText(String aS_Text)
	{
		return setText(aS_Text, true);
	}
	
	protected boolean setText(String aS_Text, boolean ab_setData)
	{
		iS_Text = formatStringByRules(aS_Text);
		
		validate(new TextFieldValidate(this,iS_Text));
		

		if (hasComponentData() && isValidWithoutChilds() && ab_setData)
		{
			setData(iS_Text);
		}
		return isValidWithoutChilds();
	}

	/**
	 * <b>JSP</b>  Returns current component text.<br>
	 * Never returns null value
	 * {@link com.sohlman.netform.ComponentData ComponentData} is not used
	 * is component is not valid or you are 
	 * calling this method in validation.
	 * <p>
	 * If value is null then {@link #isNullAllowed isNullAllowed()} tells if field is null
	 * or not
	 * @return String
	 */
	public String getText()
	{
		if (hasComponentData() && isValidWithoutChilds())
		{
			Object l_Object = getData();
			if (l_Object == null)
			{
				iS_Text = null;
				return "";
			}
			else
			{
				iS_Text = l_Object.toString();
				return Utils.stringToHTML(iS_Text);
			}
		}
		else
		{
			if (iS_Text == null)
			{
				return "";
			}
			else
			{
				return Utils.stringToHTML(iS_Text);
			}
		}
	}
	
	/**
	 * @return true if contains null false if not
	 */
	public boolean containsNull()
	{
		if (hasComponentData() && isValidWithoutChilds())
		{
			return getData()==null;
		}
		else
		{
			return iS_Text == null;
		}
	}
	
	/**
	 * @see com.sohlman.netform.Component#checkIfNewValues()
	 */
	public boolean checkIfNewValues(String[] aS_Parameters)
	{
		clearModifiedStatus();
		//HttpServletRequest l_HttpServletRequest = getHttpServletRequest();
		//String[] lS_Parameters = l_HttpServletRequest.getParameterValues(getResponseName());

		if (aS_Parameters != null && aS_Parameters.length > 0)
		{
			// this is made because 
			// XSLT processor don't convert 10 at all only 13
			char[] lc_10 = { 10 };
			String lS_NewText = Utils.replace(aS_Parameters[0], new String(lc_10), "");
			//lS_NewText = Utils.htmlToString(lS_NewText);
			
			lS_NewText = formatStringByRules(lS_NewText);
			
			if ( ( lS_NewText==null && iS_Text==null ) || ( lS_NewText!=null && lS_NewText.equals(iS_Text)) )
			{
				//System.out.println(iS_NewText +" = " + iS_Text);
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

	/**
	 * Format String by rules of TextField 
	 * 
	 * @param a_String to formatted
	 * @return String 
	 */
	protected String formatStringByRules(String a_String)
	{
		if (a_String == null && !ib_isEmptyNull)
		{
			a_String = "";
		}
		if(ib_isTrim && a_String!=null)
		{
			a_String = a_String.trim();
		}
		if(ib_isEmptyNull && a_String!=null && a_String.equals("") )
		{
			a_String = null;
		}
		return a_String;
	}
	

	/**
	 * @see com.sohlman.netform.Component#addComponent(Component)
	 */
	protected void addComponent(Component a_Component)
	{
		throw new NoSuchMethodError("Child components are not supported on TimestampField");
	}

	/**
	 * @see com.sohlman.netform.Component#cloneComponent()
	 */
	public Component cloneComponent()
	{
		TextField l_Textfield = new TextField(getParent());
		l_Textfield.setEmptyIsNull(isEmptyNull());
		l_Textfield.setTrim(isTrim());
		l_Textfield.setNullIsAllowed(isNullAllowed());
		
		l_Textfield.setVisible(isVisible());
		l_Textfield.setEnabled(isEnabled());
		l_Textfield.shareComponentListenerFrom(this);
		
		l_Textfield.setComponentValidator(getComponentValidator());
		return l_Textfield;
	}
	/**
	 * @see com.sohlman.netform.Component#syncronizeData()
	 */
	public void syncronizeData()
	{
		if(hasComponentData())
		{			
			setText((String)getData(), false);
		}
	}
	/**
	 * @see com.sohlman.netform.Component#validate()
	 */
	public void validate()
	{
		// SetText to validate
		setText(iS_Text);
	}

}
