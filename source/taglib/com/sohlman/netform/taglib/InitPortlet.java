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

import javax.servlet.jsp.JspException;

/**
 * @author Sampsa Sohlman
 */
public class InitPortlet extends MasterTag
{
	private String iS_PortletClass = null;
	
	public void setPortlet(String aS_PortletClass)
	{
		iS_PortletClass = aS_PortletClass;
	}
	
	/**
	 * @see javax.servlet.jsp.tagext.Tag#doEndTag()
	 */
	public int doEndTag() throws JspException
	{
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * @see javax.servlet.jsp.tagext.Tag#doStartTag()
	 */
	public int doStartTag() throws JspException
	{
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * @see javax.servlet.jsp.tagext.Tag#release()
	 */
	public void release()
	{
		// TODO Auto-generated method stub

	}

}
