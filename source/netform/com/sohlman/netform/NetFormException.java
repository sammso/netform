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

/** Exception object for NetForm error handling
 * @author Sampsa Sohlman
 * @version 2001-10-18
 */
public class NetFormException extends java.lang.Error
{
	/** Source exception. If other exception is cause for throwing this it is stored here.
	 */
	private Exception i_Exception_Source = null;
	/** Possible source object.
	 */
	private Object i_Object_Source;
	private String iS_UrlString = null;

	/** Creates new <code>DataSetException</code> without detail message.
	 */
	public NetFormException()
	{
	}

	/** Constructs an <code>DataSetException</code> with the specified detail message.
	 * @param aS_Msg Message for user.
	 */
	public NetFormException(String aS_Msg)
	{
		super(aS_Msg);
	}
	
	/** Constructs an <code>DataSetException</code> with the specified detail message.
	 * @param aS_Msg Message for user.
	 */
	public NetFormException(String aS_Msg, String aS_UrlString)
	{
		super(aS_Msg);
		iS_UrlString = aS_UrlString;
	}
	

	/** Constructs an <code>DataSetException</code>
	 * @param a_Exception Source exception
	 */
	public NetFormException(Exception a_Exception)
	{
		i_Exception_Source = a_Exception;
	}

	/** Constructs an <code>NetFormException</code>
	 * @param a_Object Source object
	 */
	public NetFormException(Object a_Object)
	{
		i_Object_Source = a_Object;
	}

	/** Constructs an <code>NetFormException</code>
	 * @param a_Exception Source exception
	 * @param a_Object Source object
	 */
	public NetFormException(Exception a_Exception, Object a_Object)
	{
		i_Exception_Source = a_Exception;
		i_Object_Source = a_Object;
	}

	/** Constructs an <code>NetFormException</code>
	 * @param aS_Msg Message for user.
	 * @param a_Exception Source exception
	 */
	public NetFormException(String aS_Msg, Exception a_Exception)
	{
		super(aS_Msg);
		i_Exception_Source = a_Exception;
	}

	/** Constructs an <code>NetFormException</code>
	 * @param aS_Msg Message for user.
	 * @param a_Object Source object
	 */
	public NetFormException(String aS_Msg, Object a_Object)
	{
		super(aS_Msg);
		i_Object_Source = a_Object;
	}

	/** Constructs an <code>DataSetException</code>
	 * @param aS_Msg Message for user.
	 * @param a_Exception Source Exception
	 * @param a_Object Source object
	 */
	public NetFormException(String aS_Msg, Exception a_Exception, Object a_Object)
	{
		super(aS_Msg);
		i_Exception_Source = a_Exception;
		i_Object_Source = a_Object;
	}

	/** Get's handle for SourceException
	 * @return Source Exception
	 */
	public Exception getSourceException()
	{
		return i_Exception_Source;
	}

	/** Get's handle for SourceObject
	 * @return SourceObject
	 */
	public Object getSourceObject()
	{
		return i_Object_Source;
	}

	/** Return's the message
	 * @return Message to be returned
	 */

	public String getMessage()
	{
		super.getMessage();

		if (i_Exception_Source != null)
		{
			return super.getMessage() + "\n Source Exception Message : " + i_Exception_Source.getMessage();
		}
		else
		{
			return super.getMessage();
		}
	}
	
	public String getUrlString()
	{
		return iS_UrlString;
	}
	
	public boolean hasToRedirect()
	{
		return iS_UrlString!=null;		
	}
	
	
	/**
	 * @see java.lang.Throwable#printStackTrace()
	 */
	public void printStackTrace()
	{
		System.out.println("===================================================================");
		super.printStackTrace();
		System.out.println("===================================================================");
		Exception l_Exception = getSourceException();
		int li_count = 5;
		while(l_Exception!=null && li_count > 0)
		{
			l_Exception.printStackTrace();
			System.out.println("===================================================================");
			l_Exception = getSourceException();
			li_count--;
		}
	}

}
