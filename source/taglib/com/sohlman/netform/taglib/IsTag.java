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
package com.sohlman.netform.taglib;

import java.util.HashMap;

import javax.servlet.jsp.JspException;

/**
 * Depending component status
 * 
 * @author Sampsa Sohlman
 */
public class IsTag extends ComponentTag
{	
	public static final String VALID = "valid";
	public static final String VISIBLE = "visible";
	public static final String ENABLED = "enabled";
	private String iS_Type = null;
	
	public void setState(String aS_Type) throws JspException
	{
		if(ENABLED.equalsIgnoreCase(aS_Type))
		{
			aS_Type = ENABLED;
		}
		else if(VISIBLE.equalsIgnoreCase(aS_Type))
		{
			aS_Type = VISIBLE;
		}
		else if(ENABLED.equalsIgnoreCase(aS_Type))
		{
			aS_Type = ENABLED;
		}
		else
		{
			throw new JspException("");
		}
	}
	
	/**
	 * @see javax.servlet.jsp.tagext.Tag#doStartTag()
	 */
	public int doStartTag() throws JspException
	{
		return 0;
	}

	/**
	 * @see javax.servlet.jsp.tagext.Tag#doEndTag()
	 */
	public int doEndTag() throws JspException
	{
		return 0;
	}
}
