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

import java.text.DecimalFormat;
import java.text.FieldPosition;

import com.sohlman.netform.Component;
import com.sohlman.netform.Form;
import com.sohlman.netform.Utils;

/**
 * DoubleField for number handling
 *
 * @author  Sampsa Sohlman
 * @version 2004-02-19
 */
public class DoubleField extends TextField
{
	protected String iS_Format;
	protected Double i_Double = null;
	protected DecimalFormat i_DecimalFormat = null;

	public DoubleField(Component a_Component_Parent)
	{
		super(a_Component_Parent);
	}

	public DoubleField(Form a_Form)
	{
		super(a_Form);
	}
	
	protected DecimalFormat getDecimalFormat()
	{
		if(i_DecimalFormat==null)
		{
			String lS_Format = getFormat(Double.class);
			if(lS_Format==null)
			{
				return null;
			}
			else
			{
				try
				{
					i_DecimalFormat = new DecimalFormat(lS_Format);
					return i_DecimalFormat;
				}
				catch(Exception l_Exception)
				{
					return null;
				}
			}
		}
		else
		{
			return i_DecimalFormat;
		}
	}

	/**
	 * @see #setDouble(double)
	 */
	public void setDouble(double a_double)
	{
		setDouble(new Double(a_double));
	}

	/**
	 * Set long value, this causes also value validation
	 * 
	 * @param a_Long
	 */
	public void setDouble(Double a_Double)
	{
		setDouble(a_Double, true);
	}
	
	protected void setDouble(Double a_Double, boolean ab_setData)
	{
		if (ib_isNullAllowed == false && a_Double == null)
		{
			setValid(false);
		}
		else
		{
			validate(new DoubleFieldValidate(this, a_Double));
		}
		if (isValidWithoutChilds())
		{
			i_Double = a_Double;
			DecimalFormat l_DecimalFormat = getDecimalFormat();
			if (l_DecimalFormat != null)
			{
				StringBuffer l_StringBuffer = new StringBuffer();
				l_DecimalFormat.format(a_Double.doubleValue(), l_StringBuffer, new FieldPosition(0));

				iS_Text = l_StringBuffer.toString();
			}
			else
			{
				iS_Text = String.valueOf(a_Double);
			}
			if (hasComponentData() && ab_setData)
			{
				setData(a_Double);
			}
		}
	}
	/**
	 * @see TextField#getText()
	 */
	public boolean setText(String aS_Text)
	{
		iS_Text = formatStringByRules(aS_Text);
		Double l_Double = null;
		if (aS_Text != null)
		{
			try
			{
				l_Double = new Double(iS_Text);
				validate(new DoubleFieldValidate(this, l_Double));
			}
			catch (NumberFormatException l_NumberFormatException)
			{
				setValid(false);
			}
		}
		else if(ib_isNullAllowed)
		{
			validate(new DoubleFieldValidate(this, l_Double));
		}
		else
		{
			setValid(false);
		}

		if (hasComponentData() && isValidWithoutChilds())
		{
			if (iS_Text == null)
			{
				setData(null);
			}
			else
			{
				setData(l_Double);
			}
		}
		return isValidWithoutChilds();
	}
	/**
	 * @see TextField#getText()
	 */
	public String getText()
	{
		if (hasComponentData() && isValidWithoutChilds())
		{
			Long l_Long = (Long) getData();
			if (l_Long == null)
			{
				return "";
			}
			else
			{
				DecimalFormat l_DecimalFormat = getDecimalFormat();
				if (l_DecimalFormat != null)
				{
					StringBuffer l_StringBuffer = new StringBuffer();
					l_DecimalFormat.format(l_Long.doubleValue(), l_StringBuffer, new FieldPosition(0));

					return Utils.stringToHTML(l_StringBuffer.toString());
				}
				else
				{
					return String.valueOf(l_Long);
				}
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
	 * @see com.sohlman.netform.Component#cloneComponent()
	 */
	public Component cloneComponent()
	{
		DoubleField l_DoubleField = new DoubleField(getParent());
		l_DoubleField.setVisible(isVisible());
		l_DoubleField.setEnabled(isEnabled());
		l_DoubleField.setNullIsAllowed(isNullAllowed());
		l_DoubleField.setEmptyIsNull(isEmptyNull());
		l_DoubleField.setTrim(isTrim());
		l_DoubleField.setComponentValidator(getComponentValidator());
		l_DoubleField.shareComponentListenerFrom(this);
		return l_DoubleField;
	}

	/**
	 * @see com.sohlman.netform.Component#syncronizeData()
	 */
	public void syncronizeData()
	{
		if (hasComponentData())
		{
			setDouble((Double)getData(), false);
		}
	}
}
