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
package com.sohlman.netform;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.sohlman.netform.component.IntegerField;
import com.sohlman.netform.component.TextField;
import com.sohlman.netform.component.TimestampField;

/**
 * This class contains static methods that are needed on various places.
 * 
 * @author Sampsa Sohlman
 * @version 2002-11-27
 */
public class Utils
{
	private Utils()
	{
		// Cannot created
	}

	/**
	 * Converts boolean to String<br>
	 * <i>Also null are handled correctly. All String parameters can have nulls.</i>
	 * @param a_boolean boolean which will be converted
	 * @param aS_True String which represents true value
	 * @param aS_False String which represents false value
	 * @return String corresponding value depeding of a_boolean
	 */
	public static String booleanToString(boolean a_boolean, String aS_True, String aS_False)
	{
		if (a_boolean)
		{
			return aS_True;
		}
		else
		{
			return aS_False;
		}
	}

	public static String replace(String aS_From, String aS_ToFind, String aS_ToReplace)
	{
		StringBuffer lSb_Out = new StringBuffer(aS_From.length());
		int li_start = 0, li_end = 0;

		while ((li_start = aS_From.indexOf(aS_ToFind, li_start)) >= 0)
		{
			lSb_Out.append(aS_From.substring(li_end, li_start)); // Add first
			li_end = li_start + aS_ToFind.length();
			lSb_Out.append(aS_ToReplace);
			li_start = li_end;
		}
		lSb_Out.append(aS_From.substring(li_end));

		return lSb_Out.toString();
	}

	/**
	 * Checks if datetime is aS_DateTime is same as in aS_Format
	 * @param aS_DateTime String containing datetime
	 * @param aS_Format String containing datetime format
	 * @return true if ok false if error. Null values = error
	 */

	public static boolean isTimestamp(String aS_DateTime, String aS_Format)
	{
		return stringToTimestamp(aS_DateTime, aS_Format, null, null) != null;
	}

	/**
	 * Converts string to java.sql.Timestamp using format string
	 * @param aS_Timestamp String containing timestamp
	 * @param aS_Format String containing timestamp format
	 * @return java.sql.Timestamp object if ok, else null
	 * @see java.text.SimpleDateFormat for format options
	 */
	public static java.sql.Timestamp stringToTimestamp(String aS_Timestamp, String aS_Format, Timestamp aTs_ValidStart, Timestamp aTs_ValidEnd)
	{
		SimpleDateFormat l_SimpleDateFormat;
		java.sql.Timestamp l_Timestamp;
		java.util.Date l_Date = null;

		if (aS_Timestamp == null || aS_Format == null)
		{
			return null;
		}

		l_SimpleDateFormat = new SimpleDateFormat(aS_Format);
		try
		{
			l_Date = l_SimpleDateFormat.parse(aS_Timestamp);
			l_Timestamp = new java.sql.Timestamp(l_Date.getTime());

			if (aTs_ValidStart == null || aTs_ValidEnd == null)
			{
				return l_Timestamp;
			}

			if (aTs_ValidStart.before(l_Timestamp) && aTs_ValidEnd.after(l_Timestamp))
			{
				return l_Timestamp;
			}
			else
			{
				return null;
			}
		}
		catch (ParseException a_ParseException)
		{
			return null;
		}
	}

	public static java.sql.Timestamp stringToTimestamp(String aS_Timestamp, String aS_Format)
	{
		return stringToTimestamp(aS_Timestamp, aS_Format, null, null);

	}

	/**
	* Converts String to boolean<br>
	* <i>Also null are handled correctly. All String parameters can have nulls.</i>
	* @param a_String String which will be converted
	* @param aS_True String which represents true value
	* @param aS_False String which represents false value
	* @param ab_default boolean this done when difference between true or false cannot be done or a_String don't match neither.
	* @return boolean resulted value
	*/
	public static boolean stringToBoolean(String a_String, String aS_True, String aS_False, boolean ab_default)
	{
		if (a_String == null)
		{
			if (aS_True == null && aS_False == null)
			{
				return ab_default;
			}
			else if (aS_True == null)
			{
				return true;
			}
			else if (aS_False == null)
			{
				return false;
			}
		}
		else if (aS_True.equals(aS_False))
		{
			return ab_default;
		}
		else if (a_String.equals(aS_True))
		{
			return true;
		}
		else if (a_String.equals(aS_False))
		{
			return false;
		}
		return ab_default;
	}

	/**
	 * Converts java.sql.Timestamp to String with using specified format
	 * @param a_Timestamp Timestamp containing time
	 * @param aS_Format String containing format specifications
	 * if format is null then format is "yyyy-MM-dd hh:mm:ss"
	 * @return String containing datetime
	 *
	 * @see java.text.SimpleDateFormat for format options
	 */

	public static String timestampToString(java.sql.Timestamp a_Timestamp, String aS_Format)
	{
		if(aS_Format==null)
		{
			aS_Format = "yyyy-MM-dd hh:mm:ss";
		}
		
		if (a_Timestamp == null)
			return "";

		SimpleDateFormat l_SimpleDateFormat = new SimpleDateFormat(aS_Format);

		return l_SimpleDateFormat.format(a_Timestamp);
	}

	/**
	 * Converts java.sql.Timestamp to String with using specified format
	 * @param a_Timestamp Timestamp containing time
	 * @param aS_Format String containing format specifications
	 * @return String containing datetime
	 *
	 * @see java.text.SimpleDateFormat for format options
	 */

	public static String timeToString(java.sql.Time a_Time, String aS_Format)
	{
		if (a_Time == null)
			return "";

		SimpleDateFormat l_SimpleTimeFormat = new SimpleDateFormat(aS_Format);

		return l_SimpleTimeFormat.format(a_Time);
	}

	/**
	 * Check if String is int
	 * @param a_String String containing value to be checked
	 * @return boolean true if it is int else false
	 */
	public static boolean isInt(String a_String)
	{
		if (a_String == null)
			return false;
		try
		{
			Integer.parseInt(a_String);
			return true;
		}
		catch (NumberFormatException a_NumberFormatException)
		{
			return false;
		}
	}

	/**
	 * Makes object to string if it is null then empty string "" is returned
	 * <br> <b>Advantage of this is that it is handling nulls correctly</b>
	 * @param a_Object Object which will be converted
	 * @return String current string value
	 */

	public static String objectToString(Object a_Object)
	{
		if (a_Object != null)
		{
			return a_Object.toString();
		}
		else
		{
			return "";
		}
	}

	/**
	 *	Parses int from from String if string is not valid int then returns default value
	 *
	 *	Function simplifies int parsing from string no need to use exception handling,
	 *	because it is already taken care of.
	 *
	 *	@param aS_String string to be parsed int
	 *	@param ai_default string to parsed int
	 *
	 *	@return parsed int value or default if String doesn't contain int
	 *
	 */
	public static int stringToInt(String aS_String, int ai_default)
	{
		if (aS_String == null)
			return ai_default;
		aS_String = aS_String.trim();
		try
		{
			return Integer.parseInt(aS_String);
		}
		catch (NumberFormatException a_NumberFormatException)
		{
			return ai_default;
		}
	}

	/**
	 *	Parses int from from String if string is not valid int then returns default value
	 *
	 *	Function simplifies int parsing from string no need to use exception handling,
	 *	because it is already taken care of.
	 *
	 *	@param aS_String string to be parsed int
	 *
	 *	@return parsed Integer value or null if it is not Integer
	 *
	 */
	public static Integer stringToInteger(String aS_String)
	{
		if (aS_String == null)
			return null;
		aS_String = aS_String.trim();
		try
		{
			return new Integer(aS_String);
		}
		catch (NumberFormatException a_NumberFormatException)
		{
			return null;
		}
	}

	/**
	 * stringToHTML
	 *
	 * @param aS_Text
	 * @return String HTML compatible String
	 */
	public static final String stringToHTML(String aS_Text)
	{
		// Thanks from this method to 
		// http://www.rgagnon.com/javadetails/java-0306.html

		StringBuffer l_StringBuffer = new StringBuffer();
		int li_length = aS_Text.length();
		for (int li_index = 0; li_index < li_length; li_index++)
		{
			char l_char = aS_Text.charAt(li_index);
			switch (l_char)
			{
				case '<' :
					l_StringBuffer.append("&lt;");
					break;
				case '>' :
					l_StringBuffer.append("&gt;");
					break;
				case '"' :
					l_StringBuffer.append("&quot;");
					break;
					/*	
				case '&' :
					l_StringBuffer.append("&amp;");
					break;			case 'à' :
										l_StringBuffer.append("&agrave;");
										break;
									case 'À' :
										l_StringBuffer.append("&Agrave;");
										break;
									case 'â' :
										l_StringBuffer.append("&acirc;");
										break;
									case 'Â' :
										l_StringBuffer.append("&Acirc;");
										break;
									case 'ä' :
										l_StringBuffer.append("&auml;");
										break;
									case 'Ä' :
										l_StringBuffer.append("&Auml;");
										break;
									case 'å' :
										l_StringBuffer.append("&aring;");
										break;
									case 'Å' :
										l_StringBuffer.append("&Aring;");
										break;
									case 'æ' :
										l_StringBuffer.append("&aelig;");
										break;
									case 'Æ' :
										l_StringBuffer.append("&AElig;");
										break;
									case 'ç' :
										l_StringBuffer.append("&ccedil;");
										break;
									case 'Ç' :
										l_StringBuffer.append("&Ccedil;");
										break;
									case 'é' :
										l_StringBuffer.append("&eacute;");
										break;
									case 'É' :
										l_StringBuffer.append("&Eacute;");
										break;
									case 'è' :
										l_StringBuffer.append("&egrave;");
										break;
									case 'È' :
										l_StringBuffer.append("&Egrave;");
										break;
									case 'ê' :
										l_StringBuffer.append("&ecirc;");
										break;
									case 'Ê' :
										l_StringBuffer.append("&Ecirc;");
										break;
									case 'ë' :
										l_StringBuffer.append("&euml;");
										break;
									case 'Ë' :
										l_StringBuffer.append("&Euml;");
										break;
									case 'ï' :
										l_StringBuffer.append("&iuml;");
										break;
									case 'Ï' :
										l_StringBuffer.append("&Iuml;");
										break;
									case 'ô' :
										l_StringBuffer.append("&ocirc;");
										break;
									case 'Ô' :
										l_StringBuffer.append("&Ocirc;");
										break;
									case 'ö' :
										l_StringBuffer.append("&ouml;");
										break;
									case 'Ö' :
										l_StringBuffer.append("&Ouml;");
										break;
									case 'ø' :
										l_StringBuffer.append("&oslash;");
										break;
									case 'Ø' :
										l_StringBuffer.append("&Oslash;");
										break;
									case 'ß' :
										l_StringBuffer.append("&szlig;");
										break;
									case 'ù' :
										l_StringBuffer.append("&ugrave;");
										break;
									case 'Ù' :
										l_StringBuffer.append("&Ugrave;");
										break;
									case 'û' :
										l_StringBuffer.append("&ucirc;");
										break;
									case 'Û' :
										l_StringBuffer.append("&Ucirc;");
										break;
									case 'ü' :
										l_StringBuffer.append("&uuml;");
										break;
									case 'Ü' :
										l_StringBuffer.append("&Uuml;");
										break;
									case '®' :
										l_StringBuffer.append("&reg;");
										break;
									case '©' :
										l_StringBuffer.append("&copy;");
										break;
									case '€' :
										l_StringBuffer.append("&euro;");
										break;
										// be carefull with this one (non-breaking whitee space)
									case ' ' :
										l_StringBuffer.append("&nbsp;");
										break;
					*/
				default :
					l_StringBuffer.append(l_char);
					break;
			}
		}
		return l_StringBuffer.toString();
	}

	public static final String htmlToString(String aS_Text)
	{
		if(aS_Text==null)
		{
			return null;
		}
		
		StringBuffer l_StringBuffer = new StringBuffer();
		int li_length = aS_Text.length();
		for (int li_index = 0; li_index < li_length; li_index++)
		{
			char l_char = aS_Text.charAt(li_index);
			switch (l_char)
			{
				case '&' :
					if (li_index + 3 < li_length
						&& aS_Text.charAt(li_index + 1) == 'l'
						&& aS_Text.charAt(li_index + 2) == 't'
						&& aS_Text.charAt(li_index + 3) == ';')
					{
						l_StringBuffer.append('<');
						li_index += 3;
					}
					else if (
						li_index + 3 < li_length
							&& aS_Text.charAt(li_index + 1) == 'g'
							&& aS_Text.charAt(li_index + 2) == 't'
							&& aS_Text.charAt(li_index + 3) == ';')
					{
						l_StringBuffer.append('>');
						li_index += 3;
					}
					else if (
						li_index + 4 < li_length
							&& aS_Text.charAt(li_index + 1) == 'a'
							&& aS_Text.charAt(li_index + 2) == 'm'
							&& aS_Text.charAt(li_index + 3) == 'p'
							&& aS_Text.charAt(li_index + 4) == ';')
					{
						l_StringBuffer.append('&');
						li_index += 4;
					}
					else if (
						li_index + 5 < li_length
							&& aS_Text.charAt(li_index + 1) == 'q'
							&& aS_Text.charAt(li_index + 2) == 'u'
							&& aS_Text.charAt(li_index + 3) == 'o'
							&& aS_Text.charAt(li_index + 4) == 't'
							&& aS_Text.charAt(li_index + 5) == ';')
					{
						l_StringBuffer.append('&');
						li_index += 5;
					}
					break;
				default :
					l_StringBuffer.append(l_char);
			}
		}
		return l_StringBuffer.toString();
	}

	public Component createComponentFromType(Class a_Class, Component a_Parent)
	{
		if (Integer.class.isAssignableFrom(a_Class))
		{
			return new IntegerField(a_Parent);
		}
		else if (Long.class.isAssignableFrom(a_Class))
		{ // Textfield
			return new TextField(a_Parent);
		}
		else if (String.class.isAssignableFrom(a_Class))
		{ // Textfield 
			return new TextField(a_Parent);
		}
		else if (Timestamp.class.isAssignableFrom(a_Class))
		{ // Timestamp field
			return new TimestampField(a_Parent);
		}
		return null;
	}
}
