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

import java.text.DecimalFormat;
import java.text.FieldPosition;

import com.sohlman.netform.Component;
import com.sohlman.netform.Form;
import com.sohlman.netform.Utils;

/**
 * FloatField for number handling
 *
 * @author  Sampsa Sohlman
 * @version 2004-02-15
 */
public class FloatField extends TextField
{
	protected Float i_Float = null;
	protected DecimalFormat i_DecimalFormat = null;

	public FloatField(Component a_Component_Parent)
	{
		super(a_Component_Parent);
	}

	public FloatField(Form a_Form)
	{
		super(a_Form);
	}

	protected DecimalFormat getDecimalFormat()
	{
		if(i_DecimalFormat==null)
		{
			String lS_Format = getFormat(Float.class);
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
	 * @see #setFloat(float)
	 */
	public boolean setFloat(float a_float)
	{
		return setFloat(new Float(a_float));
	}
	/**
	 * Set long value, this causes also value validation
	 * 
	 * @param a_Long
	 */
	public boolean setFloat(Float a_Float)
	{
		return setFloat(a_Float, true);
	}

	protected boolean setFloat(Float a_Float, boolean ab_setData)
	{
		if (ib_isNullAllowed == false && a_Float == null)
		{
			setValid(false);
		}
		else
		{
			validate(new FloatFieldValidate(this, a_Float));
		}
		if (isValidWithoutChilds())
		{
			i_Float = a_Float;
			DecimalFormat l_DecimalFormat = getDecimalFormat();
			if (l_DecimalFormat != null)
			{
				StringBuffer l_StringBuffer = new StringBuffer();
				l_DecimalFormat.format(a_Float.floatValue(), l_StringBuffer, new FieldPosition(0));
				iS_Text = l_StringBuffer.toString();
			}
			else
			{
				iS_Text = String.valueOf(a_Float);
			}
			if (hasComponentData() && ab_setData)
			{
				setData(a_Float);
			}
			return true;
		}
		else
		{
			return false;
		}
	}
	/**
	 * @see TextField#getText()
	 */
	public boolean setText(String aS_Text)
	{
		iS_Text = formatStringByRules(aS_Text);
		Float l_Float = null;
		if (aS_Text != null)
		{
			try
			{
				l_Float = new Float(iS_Text);
				validate(new FloatFieldValidate(this, l_Float));
			}
			catch (NumberFormatException l_NumberFormatException)
			{
				setValid(false);
			}
		}
		else if(ib_isNullAllowed)
		{
			validate(new FloatFieldValidate(this, l_Float));
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
				setData(l_Float);
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
			i_Float = (Float) getData();
			if (i_Float == null)
			{
				return "";
			}
			else
			{
				DecimalFormat l_DecimalFormat = getDecimalFormat();
				if (l_DecimalFormat != null)
				{
					StringBuffer l_StringBuffer = new StringBuffer();
					l_DecimalFormat.format(i_Float.floatValue(), l_StringBuffer, new FieldPosition(0));

					return Utils.stringToHTML(l_StringBuffer.toString());
				}
				else
				{
					return String.valueOf(i_Float);
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
		FloatField l_FloatField = new FloatField(getParent());
		l_FloatField.setVisible(isVisible());
		l_FloatField.setEnabled(isEnabled());
		l_FloatField.setNullIsAllowed(isNullAllowed());
		l_FloatField.setEmptyIsNull(isEmptyNull());
		l_FloatField.setTrim(isTrim());
		l_FloatField.setComponentValidator(getComponentValidator());
		l_FloatField.shareComponentListenerFrom(this);

		return l_FloatField;
	}

	/**
	 * @see com.sohlman.netform.Component#syncronizeData()
	 */
	public void syncronizeData()
	{
		if (hasComponentData())
		{
			setFloat((Float) getData(), false);
		}
	}
}
