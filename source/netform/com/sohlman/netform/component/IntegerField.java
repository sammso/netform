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
 * Numberfield for number handling
 * 
 * @author Sampsa Sohlman
 * @version 2004-01-15
 */
public class IntegerField extends TextField
{
	protected Integer i_Integer = null;

	protected DecimalFormat i_DecimalFormat = null;

	public IntegerField(Component a_Component_Parent)
	{
		super(a_Component_Parent);
	}

	public IntegerField(Form a_Form)
	{
		super(a_Form);
	}

	protected DecimalFormat getDecimalFormat()
	{
		if(i_DecimalFormat == null)
		{
			String lS_Format = getFormat(Integer.class);
			if(lS_Format == null)
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
				catch (Exception l_Exception)
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
	 * @see #setInteger(Integer)
	 */
	public boolean setInt(int a_int)
	{
		return setInteger(new Integer(a_int));
	}

	/**
	 * @return Integer value null if contains null
	 */
	public Integer getInteger()
	{
		return i_Integer;
	}

	/**
	 * @return int value if null then 0
	 */
	public int getInt()
	{
		if(i_Integer == null)
		{
			return 0;
		}
		else
		{
			return i_Integer.intValue();
		}
	}

	/**
	 * Set int value, this causes also value validation
	 * 
	 * @param a_Integer
	 * @return true if set is success, false if not (propably validation failed)
	 */
	public boolean setInteger(Integer a_Integer)
	{
		return setInteger(a_Integer, true);
	}

	protected boolean setInteger(Integer a_Integer, boolean ab_setData)
	{
		if(ib_isNullAllowed == false && a_Integer == null)
		{
			setValid(false);
		}
		else
		{
			if(ab_setData)
			{
				validate(new IntegerFieldValidate(this, a_Integer));
			}
		}

		if(isValidWithoutChilds())
		{
			i_Integer = a_Integer;
			DecimalFormat l_DecimalFormat = getDecimalFormat();
			if(l_DecimalFormat != null)
			{
				StringBuffer l_StringBuffer = new StringBuffer();
				l_DecimalFormat.format(a_Integer.intValue(), l_StringBuffer, new FieldPosition(0));

				iS_Text = l_StringBuffer.toString();
			}
			else
			{
				iS_Text = String.valueOf(a_Integer);
			}
			if(hasComponentData() && ab_setData)
			{
				setData(a_Integer);
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
		i_Integer = null;
		if(aS_Text != null)
		{
			try
			{
				i_Integer = new Integer(iS_Text);
				validate(new IntegerFieldValidate(this, i_Integer));
			}
			catch (NumberFormatException l_NumberFormatException)
			{
				setValid(false);
			}
		}
		else if(ib_isNullAllowed)
		{
			validate(new IntegerFieldValidate(this, i_Integer));
		}
		else
		{
			setValid(false);
		}

		if(hasComponentData() && isValidWithoutChilds())
		{
			if(iS_Text == null)
			{
				setData(null);
			}
			else
			{
				setData(i_Integer);
			}
		}
		return isValidWithoutChilds();
	}

	/**
	 * @see TextField#getText()
	 */
	public String getText()
	{
		if(hasComponentData() && isValidWithoutChilds())
		{
			i_Integer = (Integer) getData();
			if(i_Integer == null)
			{
				return "";
			}
			else
			{
				DecimalFormat l_DecimalFormat = getDecimalFormat();
				if(l_DecimalFormat != null)
				{
					StringBuffer l_StringBuffer = new StringBuffer();
					l_DecimalFormat.format(i_Integer.intValue(), l_StringBuffer, new FieldPosition(0));

					return Utils.stringToHTML(l_StringBuffer.toString());
				}
				else
				{
					return String.valueOf(i_Integer);
				}
			}
		}
		else
		{
			if(iS_Text == null)
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
		IntegerField l_IntegerField = new IntegerField(getParent());
		l_IntegerField.setVisible(isVisible());
		l_IntegerField.setEnabled(isEnabled());
		l_IntegerField.setNullIsAllowed(isNullAllowed());
		l_IntegerField.setEmptyIsNull(isEmptyNull());
		l_IntegerField.setTrim(isTrim());
		l_IntegerField.setComponentValidator(getComponentValidator());
		l_IntegerField.shareComponentListenerFrom(this);

		return l_IntegerField;
	}

	/**
	 * @see com.sohlman.netform.Component#syncronizeData()
	 */
	public void syncronizeData()
	{
		if(hasComponentData())
		{
			setInteger((Integer) getData(), false);
		}
	}
	
	/**
	 * Override
	 * 
	 * @see com.sohlman.netform.Component#validate()
	 */
	public void validate()
	{
		validate(new IntegerFieldValidate(this, i_Integer));
	}	
}