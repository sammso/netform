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
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

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
	protected String iS_TimeFormat = null;
	protected String iS_DateFormat = null;
	
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
	 * <b>JSP</b>
	 * 
	 * @return Date part of value
	 */
	public String getDateText()
	{
		return getTextByFormat(iS_DateFormat);	
	}
	
	/**
	 * <b>JSP</b>
	 * 
	 * @return Time part of value
	 */	
	public String getTimeText()
	{
		return getTextByFormat(iS_TimeFormat);
	}
	
	/**
	 * <b>JSP</b>
	 * <p>
	 * Returns value by specific format
	 * 
	 * @param aS_Format Specific format to be used
	 * @return Timestamp formated by this specific value
	 */
	public String getTextByFormat(String aS_Format)
	{
		if (hasComponentData() && isValidWithoutChilds())
		{
			i_Timestamp = (Timestamp) getData();
		}

		if (i_Timestamp == null)
		{
			return "";
		}
		else
		{
			try
			{
				SimpleDateFormat l_SimpleDateFormat = new SimpleDateFormat(aS_Format);
				return l_SimpleDateFormat.format(i_Timestamp);
			}
			catch(Exception l_Exception)
			{
				return "";
			}
				
		}		
	}
	
	/**
	 * @return Hour of day 0-23, 0 if Timestamp is null
	 */
	public int getHourOfDay()
	{
		return getTimestampPart(Calendar.HOUR_OF_DAY);
	}
	/**
	 * @return Minute of hour 0-59, 0 if Timestamp is null
	 */
	public int getMinute()
	{
		return getTimestampPart(Calendar.MINUTE);
	}

	/**
	 * @return Second of minute 0-59, 0 if Timestamp is null
	 */
	public int getSecond()
	{
		return getTimestampPart(Calendar.SECOND);
	}

	/**
	 * @return Millisecond, 0 if Timestamp is null
	 */	
	public int getMilliSecond()
	{
		return getTimestampPart(Calendar.MILLISECOND);
	}
	
	/**
	 * @return Day of month 1 - 31, 0 if Timestamp is null
	 */		
	public int getDayOfMonth()
	{
		return getTimestampPart(Calendar.DAY_OF_MONTH);
	}

	/**
	 * @return Month 1 - 12, 0 if Timestamp is null
	 */
	public int getMonth()
	{
		return getTimestampPart(Calendar.MONTH);
	}

	/**
	 * @return Year with full width, 0 if Timestamp is null
	 */	
	public int getYear()
	{
		return getTimestampPart(Calendar.YEAR);
	}
	
	/**
	 * @return GMZ offset value, 0 if Timestamp is null
	 */		
	public int getZoneOffSet()
	{
		return getTimestampPart(Calendar.ZONE_OFFSET);
	}

	/**
	 * @return Week of year 1 - 53, 0 if Timestamp is null
	 */
	public int getWeekOfYear()
	{
		return getTimestampPart(Calendar.WEEK_OF_YEAR);
	}
	
	private int getTimestampPart(int ai_type)
	{
		Timestamp l_Timestamp = getTimestamp();
		if(l_Timestamp==null)
		{
			return 0;
		}
		else
		{
			Calendar l_Calendar = Calendar.getInstance();
			l_Calendar.setTime(l_Timestamp);
			return l_Calendar.get(ai_type);
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
	
	/**
	 * Overiding TextField#checkIfNewValues()
	 * 
	 * @see com.sohlman.netform.Component#checkIfNewValues()
	 */
	public boolean checkIfNewValues()
	{	
		clearModifiedStatus();
				
		if(iS_DateFormat==null && iS_TimeFormat==null)
		{
			return super.checkIfNewValues();	
		}
		else
		{
			HttpServletRequest l_HttpServletRequest = getHttpServletRequest();
			Timestamp lTs_Time = null;
			Timestamp lTs_Date = null;
					
			if(iS_TimeFormat !=null)
			{
				// It is possilbe that date and time is handled differently
				String[] lS_Parameters = l_HttpServletRequest.getParameterValues(getResponseNameForTime());
				if (lS_Parameters != null && lS_Parameters.length > 0)
				{			
					// this is made because 
					// XSLT processor don't convert 10 at all only 13
					char[] lc_10 = { 10 };
					String lS_Text = Utils.replace(lS_Parameters[0], new String(lc_10), "");
					//lS_NewText = Utils.htmlToString(lS_NewText);
					
					lS_Text = formatStringByRules(lS_Text);
					lTs_Time = Utils.stringToTimestamp(lS_Text, iS_TimeFormat);
				}
			}
			if(iS_DateFormat !=null)
			{
				// It is possilbe that date and time is handled differently
				String[] lS_Parameters = l_HttpServletRequest.getParameterValues(getResponseNameForDate());
				if (lS_Parameters != null && lS_Parameters.length > 0)
				{			
					// this is made because 
					// XSLT processor don't convert 10 at all only 13
					char[] lc_10 = { 10 };
					String lS_Text = Utils.replace(lS_Parameters[0], new String(lc_10), "");
					//lS_NewText = Utils.htmlToString(lS_NewText);
					
					lS_Text = formatStringByRules(lS_Text);
					lTs_Date = Utils.stringToTimestamp(lS_Text, iS_DateFormat);
				}
			}
			
			
			
			Timestamp lTs_Final = combineDateAndTime(lTs_Date, lTs_Time);
			
			if( (lTs_Final==null && i_Timestamp == null) || ( lTs_Final!=null && lTs_Final.equals(i_Timestamp)))
			{
				return false;
			}
			else
			{
				setTimestamp(lTs_Final);
				return true;
			}
		}
	}	
	/**
	 * @return String containing Date part format of Timestamp
	 */
	public String getDateFormat()
	{
		return iS_DateFormat;
	}

	/**
	 * @return String containing Time part format of Timestamp
	 */
	public String getTimeFormat()
	{
		return iS_TimeFormat;
	}

	/**
	 * @param a_Format Contain Format of date part of Timestamp
	 */
	public void setDateFormat(String a_Format)
	{
		iS_DateFormat = a_Format;
	}

	/**
	 * @param a_Format Contain Format of date part of Timestamp
	 */
	public void setTimeFormat(String a_Format)
	{
		iS_TimeFormat = a_Format;
	}
	
	/**
	 * <b>JSP</b>
	 * 
	 * Returns response name for Date. This is to be used if Date and Time
	 * are send to server as separated fields.
	 * 
	 * @return String containing response name for Date
	 */
	public String getResponseNameForDate()
	{
		return getResponseName() + "D";
	}

	/**
	 * <b>JSP</b>
	 * 
	 * Returns response name for Time. This is to be used if Date and Time
	 * are send to server as separated fields.
	 * 
	 * @return String containing response name for Time
	 */
	public String getResponseNameForTime()
	{
		return getResponseName() + "T";
	}
	
	/**
	 * @param aTs_Date Date part
	 * @param aTs_Time Time part
	 * @return Timestamp containing these together, if null then 0 assumed if both null 
	 */
	private Timestamp combineDateAndTime(Timestamp aTs_Date, Timestamp aTs_Time)
	{	
		int li_day = 0, li_month = 0, li_year = 0;
		int li_hour = 0, li_minute = 0, li_second = 0, li_millisecond = 0;
		
		if(aTs_Date==null && aTs_Time==null)
		{
			return null;
		}
		
		Calendar l_Calendar = Calendar.getInstance();
		
		if(aTs_Date!=null)
		{
			l_Calendar.setTime(aTs_Date);
			li_day = l_Calendar.get(Calendar.DAY_OF_MONTH);
			li_month = l_Calendar.get(Calendar.MONTH);
			li_year = l_Calendar.get(Calendar.YEAR);			
		}
		if(aTs_Time!=null)
		{
			l_Calendar.setTime(aTs_Time);
			li_hour = l_Calendar.get(Calendar.HOUR);
			li_minute = l_Calendar.get(Calendar.MINUTE);
			li_second = l_Calendar.get(Calendar.SECOND);
			li_millisecond = l_Calendar.get(Calendar.MILLISECOND);
		}
		
		l_Calendar.set(li_year, li_month, li_day, li_hour, li_minute, li_second);
		l_Calendar.set(Calendar.MILLISECOND, li_millisecond);
		
		return new Timestamp(l_Calendar.getTimeInMillis());
	}

}
