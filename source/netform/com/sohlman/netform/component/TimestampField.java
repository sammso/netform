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

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.sohlman.netform.Component;
import com.sohlman.netform.Form;
import com.sohlman.netform.Utils;

/**
 * TimestampField to have Timestamp data
 * <p>
 * Default format of TimestampField is "yyyy-MM-dd hh:mm:ss" 
 *
  @version 2004-01-15
  @author Sampsa Sohlman
 */

public class TimestampField extends TextField
{
	protected SimpleDateFormat i_SimpleDateFormat = null;
	protected Timestamp i_Timestamp = null;
	
	public TimestampField(Component a_Component_Parent)
	{
		super(a_Component_Parent);
		ib_isTrim = true;
	}

	public TimestampField(Form a_Form)
	{
		super(a_Form);
		ib_isTrim = true;
	}

	protected SimpleDateFormat getSimpleDateFormat()
	{
		if(i_SimpleDateFormat==null)
		{
			String lS_Format = getFormat(Timestamp.class);
			if(lS_Format==null)
			{
				i_SimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			}
			else
			{
				try
				{
					i_SimpleDateFormat = new SimpleDateFormat(lS_Format);
				}
				catch(Exception l_Exception)
				{
					i_SimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				}
			}
		}
		return i_SimpleDateFormat;
	}
	
	public void setTimestamp(Timestamp a_Timestamp)
	{
		setTimestamp(a_Timestamp, true);
	}
	
	protected void setTimestamp(Timestamp a_Timestamp, boolean ab_setData)
	{
		if(ib_isNullAllowed==false && a_Timestamp==null)
		{
			setValid(false);
		}
		else
		{		
			validate(new TimestampFieldValidate(this, a_Timestamp));
		}
		if (isValidWithoutChilds())
		{
			// If iS_Format is null then format is default of
			
			i_Timestamp = a_Timestamp;
			SimpleDateFormat l_SimpleDateFormat = getSimpleDateFormat();
			if(i_Timestamp!=null)
			{
				// Generates null pointer error if Timestamp is null
				iS_Text = l_SimpleDateFormat.format(a_Timestamp);
			}
			else
			{
				iS_Text = formatStringByRules(null);
			}
			
			if (hasComponentData() && ab_setData)
			{
				setData(a_Timestamp);
			}
		}
	}
	
	public Timestamp getTimestamp()
	{
		return i_Timestamp;
	}
	
	/**
	 * @see TextField#getText()
	 */
	public boolean setText(String aS_Text)
	{
		iS_Text = formatStringByRules(aS_Text); 
		i_Timestamp = null;
		if(aS_Text!=null)
		{
			try
			{
				SimpleDateFormat l_SimpleDateFormat = getSimpleDateFormat();
				Date l_Date = l_SimpleDateFormat.parse(aS_Text);
				i_Timestamp = new Timestamp(l_Date.getTime());
				validate(new TimestampFieldValidate(this, i_Timestamp));
			}
			catch(ParseException l_ParseException)
			{
				setValid(false);
			}
		}
		else if(ib_isNullAllowed)
		{
			validate(new TimestampFieldValidate(this, i_Timestamp));
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
				setData(i_Timestamp); 
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
			i_Timestamp = (Timestamp) getData();
			if (i_Timestamp == null)
			{
				return "";
			}
			else
			{
				SimpleDateFormat l_SimpleDateFormat = getSimpleDateFormat();
				return l_SimpleDateFormat.format(i_Timestamp);
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
		TimestampField l_TimestampField = new TimestampField(getParent());
		l_TimestampField.setVisible(isVisible());
		l_TimestampField.setEnabled(isEnabled());
		l_TimestampField.setNullIsAllowed(isNullAllowed());
		l_TimestampField.setEmptyIsNull(isEmptyNull());
		l_TimestampField.setTrim(isTrim());
		l_TimestampField.setComponentValidator(getComponentValidator());
		l_TimestampField.shareComponentListenerFrom(this);
		
		return l_TimestampField;
	}

	/**
	 * @see com.sohlman.netform.Component#syncronizeData()
	 */
	public void syncronizeData()
	{
		if (hasComponentData())
		{
			setTimestamp((Timestamp)getData(), false);
		}
	}
}
